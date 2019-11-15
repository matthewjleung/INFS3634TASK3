package com.example.cat_app_task3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> favouritesCatIdArrayList = new ArrayList<String>();
    public static ArrayList<CatModel> favouritesCatModelArrayList = new ArrayList<CatModel>();

    private RequestQueue requestQueue;
    private String BASE_URL = "https://api.thecatapi.com/v1/images/search?breed_id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initiating the fragment manager and the initial searchFragment
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_slot, new SearchFragment());
        fragmentTransaction.commit();

        Intent i = getIntent();
        String id = i.getStringExtra("id");
        String idDelete = i.getStringExtra("idDelete");

        // setup the bottomNavigation listener
        BottomNavigationView bottomNavigation  = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch(id){
                    case R.id.SearchMenu: {
                        fragmentManager.beginTransaction().replace(R.id.fragment_slot,
                                new SearchFragment()).commit();
                    } break;
                    case R.id.FavouritesMenu: {
                        fragmentManager.beginTransaction().replace(R.id.fragment_slot,
                                new FavouritesFragment()).commit();
                    } break;
                }
                return true;
            }
        });

        // work out if adding a cat to the favourites cat array
        for(int x = 0; x < MainActivity.favouritesCatIdArrayList.size(); x++)
        {
            if(idDelete != null){
                if(idDelete.equals(MainActivity.favouritesCatIdArrayList.get(x)))
                {
                    // user remove the cat from the favourite list
                    favouritesCatIdArrayList.remove(idDelete);
                    favouritesCatModelArrayList.remove(x);
                    break;
                }
            } else{
                if(id.equals(MainActivity.favouritesCatIdArrayList.get(x)))
                {
                    id = null;
                    break;
                }
            }
        }

        if(id != null){
            favouritesCatIdArrayList.add(id);
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url = BASE_URL + id;
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    CatDetailModel[] catDetailArray = gson.fromJson(response, CatDetailModel[].class);

                    ArrayList<CatDetailModel> catDetailArrayList =
                            new ArrayList<CatDetailModel>(Arrays.asList(catDetailArray));

                    CatDetailModel catDetailObject = catDetailArrayList.get(0);

                    CatModel[] catModelArrayObject = catDetailObject.getBreeds();
                    ArrayList<CatModel> catArrayListObject =
                            new ArrayList<CatModel>(Arrays.asList(catModelArrayObject));
                    CatModel catObject = catArrayListObject.get(0);

                    // user add cat to the favourites cat list
                    favouritesCatModelArrayList.add(catObject);
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            };
            StringRequest sr = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
            requestQueue.add(sr);
        }

    }
}
