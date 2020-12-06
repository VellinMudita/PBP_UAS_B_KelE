package android.fortunaalya.resto;

import android.content.DialogInterface;
import android.content.Intent;
import android.fortunaalya.resto.addon.RecyclerItemClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterMenuCheckout adapterMenu;
    List<GetSetMenuCheckout> getSetMenuList;
    Session session;
    Button buttonBack, buttonBayar;
    TextView totalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        session = new Session(this);

        totalHarga = findViewById(R.id.totalHarga);
        recyclerView = findViewById(R.id.recycler_view_makanan);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CheckoutActivity.this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(CheckoutActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(final View view, int position) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        final GetSetMenuCheckout item = getSetMenuList.get(itemPosition);
                        AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        new CartTask().execute(item.getId());
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:

                                        break;
                                }
                            }
                        };

                        builder.setMessage("Ingin mengurangi jumlah makanan?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckoutActivity.this, MenuActivity.class));
                finish();
            }
        });

        buttonBayar = findViewById(R.id.buttonBayar);
        buttonBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BayarTask().execute();
            }
        });

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<String, Void, String> {

        Setting setting = new Setting();
        String json_url;
        URL url;

        @Override
        protected void onPreExecute() {
            json_url = setting.menu_makanan_checkout;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("id_user","UTF-8")+"="+ URLEncoder.encode(session.getID(),"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            String status = "", pesan = "";
            try {
                JSONObject jsonObject = new JSONObject(result);
                status = jsonObject.getJSONObject("server_response").getString("status");
                pesan = jsonObject.getJSONObject("server_response").getString("pesan");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!status.equals("FALSE")){
                adapterMenu = new AdapterMenuCheckout(getSetHistoryFunction(result), CheckoutActivity.this);
                recyclerView.setAdapter(adapterMenu);
            }else{
                Toast.makeText(CheckoutActivity.this, pesan, Toast.LENGTH_SHORT).show();
            }
        }

        public List<GetSetMenuCheckout> getSetHistoryFunction(String result) {
            getSetMenuList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONObject("server_response").getJSONArray("data");
                int count = 0;
                int totalharga = 0;
                String id, nama, harga, jumlah;
                while (count < jsonArray.length()){
                    JSONObject JO = jsonArray.getJSONObject(count);
                    id = JO.getString("id");
                    nama = JO.getString("nama");
                    harga = JO.getString("harga");
                    jumlah = JO.getString("jumlah");

                    int total = Integer.parseInt(harga) * Integer.parseInt(jumlah);
                    totalharga = totalharga + total;

                    getSetMenuList.add(new GetSetMenuCheckout(id, nama, harga, jumlah, String.valueOf(total)));
                    count++;
                }
                totalHarga.setText("Total Harga : " + totalharga);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return getSetMenuList;
        }
    }

    class CartTask extends AsyncTask<String, Void, String> {

        Setting setting = new Setting();
        String json_url;
        URL url;

        @Override
        protected void onPreExecute() {
            json_url = setting.cart_kurang;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(params[0],"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = jsonObject.getJSONObject("server_response").getString("status");
                String pesan = jsonObject.getJSONObject("server_response").getString("pesan");
                Toast.makeText(CheckoutActivity.this, pesan, Toast.LENGTH_LONG).show();
                if (status.equals("TRUE")){
                    startActivity(new Intent(CheckoutActivity.this, CheckoutActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    class BayarTask extends AsyncTask<String, Void, String> {

        Setting setting = new Setting();
        String json_url;
        URL url;

        @Override
        protected void onPreExecute() {
            json_url = setting.checkout;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("id_user","UTF-8")+"="+ URLEncoder.encode(session.getID(),"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = jsonObject.getJSONObject("server_response").getString("status");
                String pesan = jsonObject.getJSONObject("server_response").getString("pesan");
                Toast.makeText(CheckoutActivity.this, pesan, Toast.LENGTH_LONG).show();
                if (status.equals("TRUE")){
                    startActivity(new Intent(CheckoutActivity.this, MenuActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}