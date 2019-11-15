package com.example.cat_app_task3;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchFragment extends Fragment {
    private String url;
    public String BASE_BREED_URL = "https://api.thecatapi.com/v1/breeds";
    public String BASE_SEARCH_URL = "https://api.thecatapi.com/v1/breeds/search?q=" ;
    private RequestQueue requestQueue;

    // constructor
    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_search, container, false);

        //initiating requestQueue
        requestQueue = Volley.newRequestQueue(this.getContext());

        //initiating searchBar and search text and search button
        final EditText searchBar = frameLayout.findViewById(R.id.searchText);
        final ImageButton searchButton = frameLayout.findViewById(R.id.searchButton);

        //initiating recyclerView
        final RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        recyclerView = frameLayout.findViewById(R.id.search_recycler_view);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //call the API to obtain all of cats data into recyclerView
        url = BASE_BREED_URL;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SearchAdapter searchAdapter = new SearchAdapter();
                Gson gson = new Gson();
                CatModel[] catArray = gson.fromJson(response,CatModel[].class);
                ArrayList<CatModel> catArrayList =
                        new ArrayList<CatModel>(Arrays.asList(catArray));
                searchAdapter.setData(catArrayList);
                recyclerView.setAdapter(searchAdapter);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(stringRequest);

        //handling enter button on keyboard of searchBar
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(frameLayout.getContext().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    String searchQuery = searchBar.getText().toString().toLowerCase();
                    if(searchQuery.length() == 0){
                        url = BASE_BREED_URL;
                    } else{
                        url =  BASE_SEARCH_URL + searchQuery;
                    }
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SearchAdapter searchAdapter = new SearchAdapter();
                            Gson gson = new Gson();
                            CatModel[] catArray = gson.fromJson(response,CatModel[].class);
                            ArrayList<CatModel> catArrayList = new ArrayList<CatModel>(Arrays.asList(catArray));
                            searchAdapter.setData(catArrayList);
                            recyclerView.setAdapter(searchAdapter);
                        }
                    };
                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    };
                    StringRequest sr =
                            new StringRequest(Request.Method.GET, url, responseListener, errorListener);
                    requestQueue.add(sr);
                    return true;
                }
                return false;
            }
        });

        //handling searchButton click to populating recyclerView based on the searchBar query
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(frameLayout.getContext().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                String searchQuery = searchBar.getText().toString().toLowerCase();
                if(searchQuery.length() == 0){
                    url = BASE_BREED_URL;
                } else{
                    url = BASE_SEARCH_URL  + searchQuery;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SearchAdapter searchAdapter = new SearchAdapter();
                        Gson gson = new Gson();
                        CatModel[] catModelArray = gson.fromJson(response,CatModel[].class);
                        ArrayList<CatModel> catArrayList =
                                new ArrayList<CatModel>(Arrays.asList(catModelArray));
                        searchAdapter.setData(catArrayList);
                        recyclerView.setAdapter(searchAdapter);
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                };
                StringRequest sr = new StringRequest(Request.Method.GET, BASE_BREED_URL, responseListener, errorListener);
                requestQueue.add(sr);

            }
        });

        return frameLayout;
    }
}
