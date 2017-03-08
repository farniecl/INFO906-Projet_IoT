package fr.usmb.farnier.temperapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import fr.usmb.farnier.temperapp.rest.HttpRequests;
import fr.usmb.farnier.temperapp.rest.IHttpRequests;

public class RoomActivity extends Activity {


    private String room_number = "Chambre 000";
    private Double int_temp = 00.0;
    private Boolean state_fences = false;
    private Boolean state_windows = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_display);
        Intent intent = getIntent();

        // Permet d'exécuter des requêtes sur le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final TextView tv_room = (TextView) findViewById(R.id.tv_roomname);
        final TextView tv_hour = (TextView) findViewById(R.id.tv_hour);
        final TextView tv_tempext = (TextView) findViewById(R.id.tv_tempext);
        final TextView tv_tempint = (TextView) findViewById(R.id.tv_tempint);
        final SeekBar sb_temp = (SeekBar) findViewById(R.id.sb_temp);
        final Switch sw_fences = (Switch) findViewById(R.id.sw_fences);
        final Switch sw_windows = (Switch) findViewById(R.id.sw_windows);

        // Récupération du numéro de chambre
        if (intent != null) {
            tv_room.setText(intent.getStringExtra(room_number));
        }

        // Récupération de l'heure courante
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        tv_hour.setText(sdf.format(new Timestamp(System.currentTimeMillis())));

        // Récupération des données du serveur
        IHttpRequests hr = new HttpRequests();
        JSONObject json = hr.HttpGetRequest("http://192.168.43.197:3000/room/001");
        try {
            tv_tempext.setText(json.getString("out_temp") + "°C");
            tv_tempint.setText(json.getString("in_temp") + "°C");
            Double doubleTemp = json.getDouble("in_temp") * 10;
            int_temp = json.getDouble("in_temp");
            sb_temp.setProgress(doubleTemp.intValue());
            if (!json.getBoolean("state_fences")) {
                sw_fences.toggle();
                sw_fences.setChecked(true);
            }
            state_fences = json.getBoolean("state_fences");
            if (json.getBoolean("state_windows")) {
                sw_windows.toggle();
                sw_fences.setChecked(true);
            }
            state_windows = json.getBoolean("state_windows");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Toast.makeText(getBaseContext(), hr.HttpGetRequest("http://192.168.43.197:3000/room/001").toString(), Toast.LENGTH_LONG).show();
        // Toast.makeText(getBaseContext(), "Changements confirmés", Toast.LENGTH_LONG).show();

        // Listener de la seekbar
        sb_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar sb_temp, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                tv_tempint.setText(String.valueOf(String.valueOf(progress/10)) + "." +
                        String.valueOf(progress%10) + "°C");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sw_fences.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state_fences = true;
                } else {
                    state_fences = false;
                }
            }
        });

        sw_windows.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state_windows = true;
                } else {
                    state_windows = false;
                }
            }
        });

        // listener du bouton de validation
        Button bt_validate= (Button) findViewById(R.id.bt_validate);
        bt_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }
}