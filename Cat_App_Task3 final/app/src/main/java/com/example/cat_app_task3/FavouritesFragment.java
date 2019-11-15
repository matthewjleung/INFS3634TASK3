package com.example.cat_app_task3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {
    private RequestQueue requestQueue;
    private ArrayList<CatModel> catArrayList = new ArrayList<CatModel>();

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_favourites, container, false);

        //initiating recyclerView
        final RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        recyclerView = frameLayout.findViewById(R.id.favourites_recycler_view);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //retrieving all the favourite Cat in the arrayList
        catArrayList = MainActivity.favouritesCatModelArrayList;

        //loading favourite cats into the recyclerView
        FavouritesAdapter favouritesAdapter = new FavouritesAdapter();
        favouritesAdapter.setData(catArrayList);
        recyclerView.setAdapter(favouritesAdapter);

        return frameLayout;
    }
}
