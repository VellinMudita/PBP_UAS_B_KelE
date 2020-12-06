package android.fortunaalya.resto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class ReservationActivity extends AppCompatActivity {

    Button cancel, save;
    Session session;
    Spinner spinner;
    DatePicker datePicker, datePicker2;
    TimePicker timePicker, timePicker2;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        session = new Session(this);

        spinner = findViewById(R.id.spinner);
        datePicker = findViewById(R.id.datePicker);
        datePicker2 = findViewById(R.id.datePicker2);
        timePicker = findViewById(R.id.timePicker);
        timePicker2 = findViewById(R.id.timePicker2);
        timePicker.setIs24HourView(true);
        timePicker2.setIs24HourView(true);

        final String[] tanggal_mulai = {""};
        final String[] tanggal_selesai = {""};

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bulan = datePicker.getMonth() + 1;
                int bulan2 = datePicker2.getMonth() + 1;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                     tanggal_mulai[0] = datePicker.getYear() + "-" +
                            bulan + "-" +
                            datePicker.getDayOfMonth() + " " +
                            timePicker.getHour() + ":" +
                            timePicker.getMinute() + ":00";

                    tanggal_selesai[0] = datePicker2.getYear() + "-" +
                            bulan2 + "-" +
                            datePicker2.getDayOfMonth() + " " +
                            timePicker2.getHour() + ":" +
                            timePicker2.getMinute() + ":00";
                }
                new ReservasiTask().execute(spinner.getSelectedItem().toString(),
                        tanggal_mulai[0],
                        tanggal_selesai[0]);
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                finish();
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
            json_url = setting.getDataMeja;
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
                String data = URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(session.getID(),"UTF-8");
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
                Toast.makeText(ReservationActivity.this, pesan, Toast.LENGTH_SHORT).show();
                adapter =  new ArrayAdapter<String>(ReservationActivity.this, android.R.layout.simple_spinner_item, getData(result));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.notifyDataSetChanged();
                spinner.setAdapter(adapter);
            }else{
                Toast.makeText(ReservationActivity.this, pesan, Toast.LENGTH_SHORT).show();
            }
        }

        public List<String> getData(String result) {
            data = new ArrayList<String>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONObject("server_response").getJSONArray("data");
                int count = 0;
                String id;
                while (count < jsonArray.length()){
                    JSONObject JO = jsonArray.getJSONObject(count);
                    id = JO.getString("nomor_meja");

                    data.add(id);
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return data;
        }
    }

    class ReservasiTask extends AsyncTask<String, Void, String> {

        Setting setting = new Setting();
        String json_url;
        URL url;

        @Override
        protected void onPreExecute() {
            json_url = setting.reservation;
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
                String data = URLEncoder.encode("nomor_meja","UTF-8")+"="+ URLEncoder.encode(params[0],"UTF-8")+"&"+
                        URLEncoder.encode("tanggal_mulai","UTF-8")+"="+ URLEncoder.encode(params[1],"UTF-8")+"&"+
                        URLEncoder.encode("tanggal_selesai","UTF-8")+"="+ URLEncoder.encode(params[2],"UTF-8")+"&"+
                        URLEncoder.encode("id_user","UTF-8")+"="+ URLEncoder.encode(session.getID(),"UTF-8");
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
                if (status.equals("TRUE")){
                    Toast.makeText(ReservationActivity.this, pesan, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    finish();
                }else{
                    Toast.makeText(ReservationActivity.this, pesan, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        finish();
        super.onBackPressed();
    }
}