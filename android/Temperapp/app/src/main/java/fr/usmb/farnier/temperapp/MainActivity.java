package fr.usmb.farnier.temperapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    final String ROOM_NAME = "nom_chambre";
    ListView mListView;
    String[] rooms = new String[]{
            "Chambre 001", "Chambre 002"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.lv_room);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, rooms);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int position, long id) {


                // toast (notif de bas de page
                String room_name = (String) ((TextView)view).getText();


                //passage à l'activite suivante
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                intent.putExtra(ROOM_NAME,room_name);
                startActivity(intent);
            }


        });

    }}
