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
import androidx.cardview.widget.CardView;

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

public class MenuActivity extends AppCompatActivity {

    CardView cardViewMenuMakanan, cardViewLogout, cardViewCheckout,
            cardViewProfile, cardViewReservation, cardViewReservationList;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        session = new Session(this);
        cardViewMenuMakanan = findViewById(R.id.cardViewMenuMakanan);
        cardViewMenuMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, MenuMakananActivity.class));
                finish();
            }
        });
        cardViewCheckout = findViewById(R.id.cardViewCheckout);
        cardViewCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, CheckoutActivity.class));
                finish();
            }
        });
        cardViewProfile = findViewById(R.id.cardViewProfile);
        cardViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
                finish();
            }
        });
        cardViewLogout = findViewById(R.id.cardViewLogout);
        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
                finish();
            }
        });
        cardViewReservation = findViewById(R.id.cardViewReservation);
        cardViewReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, ReservationActivity.class));
                finish();
            }
        });

        cardViewReservationList = findViewById(R.id.cardViewReservationList);
        cardViewReservationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, ReservationListActivity.class));
                finish();
            }
        });
    }
}