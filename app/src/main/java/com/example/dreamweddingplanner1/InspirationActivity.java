package com.example.dreamweddingplanner1;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dreamweddingplanner1.adapters.InspirationAdapter;
import com.example.dreamweddingplanner1.api.UnsplashPhoto;
import com.example.dreamweddingplanner1.api.UnsplashResponse;
import com.example.dreamweddingplanner1.api.UnsplashService;
import com.example.dreamweddingplanner1.models.Inspiration;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InspirationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InspirationAdapter adapter;
    private List<Inspiration> inspirationList = new ArrayList<>();
    private static final String UNSPLASH_ACCESS_KEY = "EFuAbLPSYf2LcHG7X6M5UKStTlhu78M-XBm87NSHQCg"; // Your key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspiration);

        recyclerView = findViewById(R.id.recyclerViewInspiration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new InspirationAdapter(this, inspirationList);
        recyclerView.setAdapter(adapter);

        loadWeddingInspirations();
    }

    private void loadWeddingInspirations() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UnsplashService service = retrofit.create(UnsplashService.class);

        service.searchPhotos("wedding", 12, UNSPLASH_ACCESS_KEY)
                .enqueue(new Callback<UnsplashResponse>() {
                    @Override
                    public void onResponse(Call<UnsplashResponse> call, Response<UnsplashResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().results != null) {
                            inspirationList.clear();
                            for (UnsplashPhoto photo : response.body().results) {
                                String url = (photo.urls != null) ? photo.urls.regular : "";
                                String desc = (photo.description != null) ? photo.description : "Beautiful Wedding Inspiration";
                                inspirationList.add(new Inspiration(url, desc));
                            }
                            adapter.notifyDataSetChanged();
                            Toast.makeText(InspirationActivity.this, "Loaded " + inspirationList.size() + " images", Toast.LENGTH_SHORT).show();
                        } else {
                            showDummyData();
                        }
                    }

                    @Override
                    public void onFailure(Call<UnsplashResponse> call, Throwable t) {
                        Toast.makeText(InspirationActivity.this, "Network error - Showing demo images", Toast.LENGTH_LONG).show();
                        showDummyData();
                    }
                });
    }

    private void showDummyData() {
        inspirationList.clear();
        inspirationList.add(new Inspiration("https://picsum.photos/id/1015/800/600", "Elegant Outdoor Wedding"));
        inspirationList.add(new Inspiration("https://picsum.photos/id/133/800/600", "Romantic Ceremony"));
        inspirationList.add(new Inspiration("https://picsum.photos/id/201/800/600", "Luxury Reception Hall"));
        inspirationList.add(new Inspiration("https://picsum.photos/id/251/800/600", "Floral Decor"));
        inspirationList.add(new Inspiration("https://picsum.photos/id/870/800/600", "Modern Minimalist Wedding"));
        adapter.notifyDataSetChanged();
    }
}