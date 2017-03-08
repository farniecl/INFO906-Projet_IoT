package fr.usmb.farnier.temperapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import fr.usmb.farnier.temperapp.rest.HttpRequests;
import fr.usmb.farnier.temperapp.rest.IHttpRequest;

public class RoomActivity extends Activity {


    final String ROOM_NAME = "nom_chambre";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_display);

        // Permet d'exécuter des requêtes sur le thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SeekBar sb_temp = (SeekBar)findViewById(R.id.sb_temp);
        final TextView tv_tempint= (TextView)findViewById(R.id.tv_tempint);

        Intent intent = getIntent();
        TextView loginDisplay = (TextView) findViewById(R.id.tv_roomname);

        if (intent != null) {
            loginDisplay.setText(intent.getStringExtra(ROOM_NAME));
        }

        // toast (notif de bas de page
        Toast.makeText(getBaseContext(), ROOM_NAME, Toast.LENGTH_LONG).show();


        // Liaison entre la seek bar et la valeur temp interieur
        sb_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar sb_temp, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                tv_tempint.setText(String.valueOf(String.valueOf(progress/10)).concat(".").concat(String.valueOf(progress%10)).concat("°C"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar sb_temp) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar sb_temp) {
                // TODO Auto-generated method stub
            }
        });


        // listener sur le bouton valider
        Button bt_validate= (Button) findViewById(R.id.bt_validate);
        bt_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IHttpRequest hr = new HttpRequests();
                Toast.makeText(getBaseContext(), hr.HttpGetRequest("http://192.168.43.197:3000/room/001").toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Changements confirmés", Toast.LENGTH_LONG).show();

            }
        });
    }
}