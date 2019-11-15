package com.example.cat_app_task3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityDetail extends AppCompatActivity {
    private RequestQueue requestQueue;
    private String BASE_URL = "https://api.thecatapi.com/v1/images/search?breed_id=";
    private int favSelected = 0;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent1 = getIntent();
        id = intent1.getStringExtra("id");
        favSelected = intent1.getIntExtra("favSelected", 0);

        final ConstraintLayout activityDetail = findViewById(R.id.activity_detail);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                if(favSelected == 1){
                    i.putExtra("id", id);
                } else if (favSelected == 0){
                    i.putExtra("removeId", id);
                }
                startActivity(i);
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = BASE_URL + id;

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                CatDetailModel[] catDetailArray = gson.fromJson(response,CatDetailModel[].class);
                ArrayList<CatDetailModel> catDetailArrayList = new ArrayList<CatDetailModel>(Arrays.asList(catDetailArray));
                CatDetailModel catDetailObject = catDetailArrayList.get(0);
                CatModel[] catArrayObject = catDetailObject.getBreeds();
                ArrayList<CatModel> catArrayListObject = new ArrayList<CatModel>(Arrays.asList(catArrayObject));
                CatModel catObject = catArrayListObject.get(0);
                CatWeightModel catWeightArrayObject = catObject.getWeight();

                ImageView catImage = activityDetail.findViewById(R.id.cat_image);
                Glide.with(getApplicationContext()).load(catDetailObject.getUrl()).into(catImage);

                TextView catName1 = activityDetail.findViewById(R.id.catName);
                catName1.setText(catObject.getName());

                TextView catDescription = activityDetail.findViewById(R.id.cat_description);
                catDescription.setText("\"" + catObject.getDescription() + "\"");

                TextView catTemperament = activityDetail.findViewById(R.id.cat_temperament);
                catTemperament.setText(catObject.getTemperament());

                TextView catDogFriendlinessLevel = activityDetail.findViewById(R.id.catDogFriendlinessLevel);
                catDogFriendlinessLevel.setText((Integer.toString(catObject.getDog_friendly()) + " / 5"));

                TextView catWeight = activityDetail.findViewById(R.id.cat_weight);
                catWeight.setText(catWeightArrayObject.getMetric()  + " KG.");

                TextView catLifeSpan = activityDetail.findViewById(R.id.cat_lifespan);
                catLifeSpan.setText(catObject.getLife_span() + " YEARS.");

                TextView catWikipediaURL = activityDetail.findViewById(R.id.catWikipediaURL);
                catWikipediaURL.setText(catObject.getWikipedia_url());

                TextView catOrigin = activityDetail.findViewById(R.id.cat_origin);
                catOrigin.setText(catObject.getOrigin());

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };

        StringRequest sr = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(sr);

        final Button customButton = findViewById(R.id.custom_button);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favSelected == 0){
                    favSelected = 1;
                    TextView favourites_button_Text = activityDetail.findViewById(R.id.favourites_button_Text);
                    favourites_button_Text.setText("Added to Favourites!");

                } else if (favSelected == 1){
                    favSelected = 0;
                    TextView favourites_button_Text = activityDetail.findViewById(R.id.favourites_button_Text);
                    favourites_button_Text.setText("");
                }
            }
        });

    }
}
