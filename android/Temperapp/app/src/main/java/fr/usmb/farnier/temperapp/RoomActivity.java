package fr.usmb.farnier.temperapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RoomActivity extends Activity {


    final String ROOM_NAME = "nom_chambre";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_display);

        Intent intent = getIntent();
        TextView loginDisplay = (TextView) findViewById(R.id.tv_roomname);

        if (intent != null) {
            loginDisplay.setText(intent.getStringExtra(ROOM_NAME));
        }
    }
}
