    package com.example.mediaplayertask;

    import static androidx.core.content.ContextCompat.startActivity;

    import android.app.Activity;
    import android.content.Context;
    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.LinearLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import java.lang.reflect.Array;
    import java.util.ArrayList;

    public class MyPlaylistAdapter extends RecyclerView.Adapter<MyPlaylistAdapter.MyViewHolder>{

        private Context context;
        Activity activity;
        ArrayList urls;

       public MyPlaylistAdapter(Context context, ArrayList urls){
           this.context = context;
            this.urls = urls;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.my_playlist_items, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyPlaylistAdapter.MyViewHolder holder, int position) {

            holder.url.setText(String.valueOf((urls.get(position))));

            // Set the click listener for the item
            holder.mainLayout.setOnClickListener(v -> {
                // Get the URL based on the clicked position
                String videoUrl = String.valueOf(urls.get(position)); // Pull the URL dynamically

                // Check if the URL is valid and contains the YouTube watch parameter
                if (videoUrl.isEmpty() || !videoUrl.contains("youtube.com/watch?v=")) {
                    Toast.makeText(context, "Please enter a valid YouTube URL", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Convert the regular YouTube URL to an embeddable URL
                videoUrl = videoUrl.replace("watch?v=", "embed/");

                // Pass the URL to the MediaPlayer activity
                Intent intent = new Intent(context, MediaPlayer.class);
                intent.putExtra("VIDEO_URL", videoUrl);
                context.startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return urls.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            LinearLayout mainLayout;

            TextView url;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                url = itemView.findViewById(R.id.url);

                mainLayout = itemView.findViewById(R.id.mainLayout);
            }
        }
    }
