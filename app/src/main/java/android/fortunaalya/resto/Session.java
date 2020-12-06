package android.fortunaalya.resto;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setID(String id) {
        prefs.edit().putString("id", id).commit();
    }

    public String getID() {
        String id = prefs.getString("id","");
        return id;
    }

    public void setEmail(String email) {
        prefs.edit().putString("email", email).commit();
    }

    public String getEmail() {
        String email = prefs.getString("email","");
        return email;
    }
}
