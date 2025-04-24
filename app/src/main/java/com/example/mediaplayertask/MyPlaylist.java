package com.example.mediaplayertask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPlaylist extends AppCompatActivity {

    RecyclerView playlist;
    MyPlaylistAdapter myPlaylistAdapter;
    PlaylistItems playlistDB;
    ArrayList<String> urls;
    String loggedInUser;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_playlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUser = sharedPreferences.getString("LoggedInUser", "Guest");
        urls = new ArrayList<>();
        myPlaylistAdapter = new MyPlaylistAdapter(this, urls);
        playlistDB = new PlaylistItems(MyPlaylist.this);
        playlist = findViewById(R.id.myPlayListRecyclerView);
        playlist.setAdapter(myPlaylistAdapter);
        playlist.setLayoutManager(new LinearLayoutManager(MyPlaylist.this));

        displayPlaylistData();
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPlaylist.this, URL_HOME.class);
                startActivity(intent);
            }
        });

    }

    void displayPlaylistData(){
        Cursor cursor = playlistDB.getPlaylistForUser(loggedInUser);
        if (cursor == null) {
            Toast.makeText(this, "null data", Toast.LENGTH_SHORT).show();
        }
        else if (cursor.getCount() == 0)  {
            Toast.makeText(this, "no data.", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                urls.add(cursor.getString(1));
            }
        }
        cursor.close();
    }
}