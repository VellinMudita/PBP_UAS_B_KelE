package android.fortunaalya.resto;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

public class ProfileActivity extends AppCompatActivity {

    EditText txtname, txtuser, txtNoPhone;
    Button takePhoto, cancel, save;
    ImageButton back;
    Session session;
    private static final int PERMISSION_CODE = 100;
    private static final int IMAGE_CAPTURE_CODE = 101;
    private Uri imageUri;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new Session(this);

        txtname = findViewById(R.id.txtname);
        txtuser = findViewById(R.id.txtuser);
        txtNoPhone = findViewById(R.id.txtNoPhone);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, MenuActivity.class));
                finish();
            }
        });
        takePhoto = findViewById(R.id.takePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED) {

                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    ActivityCompat.requestPermissions(ProfileActivity.this, permission, PERMISSION_CODE);

                }else if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

                    openCamera();

                }
               // Toast.makeText(ProfileActivity.this, "Fitur ini belum berfungsi", Toast.LENGTH_SHORT).show();
            }
        });
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetDataTask().execute();
            }
        });
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateTask().execute(txtname.getText().toString(), txtuser.getText().toString(), txtNoPhone.getText().toString());
            }
        });

        new GetDataTask().execute();
    }
    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From tubes APP");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        //START CAMERA
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,IMAGE_CAPTURE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            photo.setImageURI(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ProfileActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(ProfileActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            if (grantResults.length > 0
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ProfileActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(ProfileActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    class UpdateTask extends AsyncTask<String, Void, String> {

        Setting setting = new Setting();
        String json_url;
        URL url;

        @Override
        protected void onPreExecute() {
            json_url = setting.updateprofile;
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
                String data = URLEncoder.encode("id_user","UTF-8")+"="+ URLEncoder.encode(session.getID(),"UTF-8")+"&"+
                        URLEncoder.encode("nama","UTF-8")+"="+ URLEncoder.encode(params[0],"UTF-8")+"&"+
                        URLEncoder.encode("username","UTF-8")+"="+ URLEncoder.encode(params[1],"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+ URLEncoder.encode(params[2],"UTF-8");
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
                    Toast.makeText(ProfileActivity.this, pesan, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                    finish();
                }else{
                    Toast.makeText(ProfileActivity.this, pesan, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    class GetDataTask extends AsyncTask<String, Void, String> {

        Setting setting = new Setting();
        String json_url;
        URL url;

        @Override
        protected void onPreExecute() {
            json_url = setting.getDataUser;
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
                if (status.equals("TRUE")){
                    String nama = jsonObject.getJSONObject("server_response").getJSONObject("data").getString("nama");
                    String username = jsonObject.getJSONObject("server_response").getJSONObject("data").getString("username");
                    String phone = jsonObject.getJSONObject("server_response").getJSONObject("data").getString("phone");
                    txtname.setText(nama);
                    txtuser.setText(username);
                    txtNoPhone.setText(phone);
                }else{
                    Toast.makeText(ProfileActivity.this, pesan, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}