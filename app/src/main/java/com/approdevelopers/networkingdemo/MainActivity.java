package com.approdevelopers.networkingdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    private NewsAdapter newsAdapter;
    private RecyclerView recyclerNews;
    private TextView txtNoNews;


    //Okhttp instance
    private OkHttpClient client;
    private Gson gson = new Gson();

    private interface NewsInterface{

        @GET("everything")
        retrofit2.Call<NewsModel> getNewsList(@Query("q") String keyword,@Query("apiKey") String apiKey);

    }

    NewsInterface newsInterface;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI hooks
        //UI instances
        Button btnFetch = findViewById(R.id.btn_connect);
        recyclerNews = findViewById(R.id.recycler_news);
        txtNoNews  = findViewById(R.id.no_news);


        recyclerNews.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        newsAdapter = new NewsAdapter(new DiffUtilsNewsModel());

        //initialising okhttp client
        //client = new OkHttpClient();

        //initialising retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                        .build();
        newsInterface= retrofit.create(NewsInterface.class);





        btnFetch.setOnClickListener(view -> fetchNewsWithRetrofit());
    }


    // Method to fetch News JSON from news API
    /*private void fetchNews(){



        //Creating a okhttp request
        Request request = new Request.Builder()
                .url("https://newsapi.org/v2/everything?q=keyword&apiKey="+"YOUR_API_KEY")
                .get()
                .build();

        //Making a new asynchronous request via OkHttp client
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                //Show toast when request failed to get any response
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Unable to fetch latest news", Toast.LENGTH_SHORT).show());

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                //Getting response
                String responseJson = Objects.requireNonNull(response.body()).string();
                Type listType = new TypeToken<List<NewsModel>>(){}.getType();

                JSONObject responseJsonObj;
                JSONArray articlesArray;
                try {
                    responseJsonObj = new JSONObject(responseJson);
                    // Get the "articles" array
                    articlesArray = responseJsonObj.getJSONArray("articles");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                if (!articlesArray.toString().equals("")){
                    //Getting list of NewsModels from response Json using GSON Library
                    List<NewsModel> newsResults = gson.fromJson(articlesArray.toString(),listType);

                    //Update UI with latest News Models
                    runOnUiThread(() -> {
                        if (newsResults!=null && !newsResults.isEmpty()){
                            txtNoNews.setVisibility(View.GONE);
                            recyclerNews.setVisibility(View.VISIBLE);
                            newsAdapter.submitList();
                            recyclerNews.setAdapter(newsAdapter);
                        }

                    });

                }


            }
        });


    }

     */


    private void fetchNewsWithRetrofit(){




        newsInterface.getNewsList("Nature","YOUR_API_KEY").enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                NewsModel models = response.body();
                txtNoNews.setVisibility(View.GONE);
                recyclerNews.setVisibility(View.VISIBLE);
                newsAdapter.submitList(models.getArticles());
                recyclerNews.setAdapter(newsAdapter);
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

}