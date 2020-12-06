package android.fortunaalya.resto;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegistrationActivity extends AppCompatActivity {

    EditText txtfullname, txtusername, txtemail, txtphone, txtpass;
    Button buttonRegister;
    TextView simpleText;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        session = new Session(this);

        txtfullname = findViewById(R.id.txtfullname);
        txtusername = findViewById(R.id.txtusername);
        txtemail = findViewById(R.id.txtemail);
        txtphone = findViewById(R.id.txtphone);
        txtpass = findViewById(R.id.txtpass);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegisterTask().execute(txtfullname.getText().toString(),
                        txtusername.getText().toString(),
                        txtpass.getText().toString(),
                        txtphone.getText().toString(),
                        txtemail.getText().toString());
            }
        });
        simpleText = findViewById(R.id.simpleText);
        simpleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, RegistrationActivity.class));
                finish();
            }
        });
    }

    class RegisterTask extends AsyncTask<String, Void, String> {

        Setting setting = new Setting();
        String json_url;
        URL url;

        @Override
        protected void onPreExecute() {
            json_url = setting.register;
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
                String data = URLEncoder.encode("nama","UTF-8")+"="+ URLEncoder.encode(params[0],"UTF-8")+"&"+
                        URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(params[1],"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(params[2],"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+ URLEncoder.encode(params[3],"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+ URLEncoder.encode(params[4],"UTF-8");
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
                    Toast.makeText(RegistrationActivity.this, pesan, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegistrationActivity.this, pesan, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}