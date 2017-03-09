package fr.usmb.farnier.temperapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private String room_number = "000";
    private String[] rooms = new String[]{
            "Chambre 001", "Chambre 002"
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mListView = (ListView) findViewById(R.id.lv_room);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, rooms);
        mListView.setAdapter(adapter);


        // Listener de la liste
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int position, long id) {
                String room_name = (String) ((TextView)view).getText();
                if (room_name.equals("Chambre 001"))
                    room_number = "001";
                else if (room_name.equals("Chambre 002"))
                    room_number = "002";
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                intent.putExtra("room_number",room_number);
                startActivity(intent);
            }


        });

    }}
