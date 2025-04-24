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

import java.util.ArrayList;

public class URL_HOME extends AppCompatActivity {


    EditText URLEditText;
    Button PlayButton, AddToPlaylistButton, MyPlaylistButton;
    MyPlaylistAdapter myPlaylistAdapter;
    PlaylistItems playlistDB;
    ArrayList<String> urls;
    String loggedInUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_url_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myPlaylistAdapter = new MyPlaylistAdapter(this, urls);
        urls = new ArrayList<>();
        playlistDB = new PlaylistItems(URL_HOME.this);




        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        loggedInUser = sharedPreferences.getString("LoggedInUser", "Guest"); // Default to "Guest" if not found

        Toast.makeText(URL_HOME.this, "Logged in as: " + loggedInUser, Toast.LENGTH_SHORT).show();

        URLEditText = findViewById(R.id.URLEditText);
        AddToPlaylistButton = findViewById(R.id.addButton);
        AddToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playlistDB.addItem(URLEditText.getText().toString(), loggedInUser);
            }
        });
        MyPlaylistButton = findViewById(R.id.myPlaylistButton);
        MyPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(URL_HOME.this, MyPlaylist.class);
                startActivity(intent);
            }
        });
        PlayButton = findViewById(R.id.playButton);
        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoUrl = URLEditText.getText().toString();

                if (videoUrl.isEmpty() || !videoUrl.contains("youtube.com/watch?v=")) {
                    Toast.makeText(URL_HOME.this, "Please enter a valid YouTube URL", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert the regular YouTube URL to an embeddable URL
                videoUrl = videoUrl.replace("watch?v=", "embed/");

                Intent intent = new Intent(URL_HOME.this, MediaPlayer.class);
                intent.putExtra("VIDEO_URL", videoUrl);
                startActivity(intent);
            }
        });


    }


}