package com.example.moviesdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.moviesdb.api.MovieService;
import com.example.moviesdb.models.Result;
import com.example.moviesdb.preference.Prefs;
import com.example.moviesdb.utils.PaginationAdapterCallback;

import java.util.List;

public class WishListActivity extends AppCompatActivity implements PaginationAdapterCallback {

    private WishListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerView rv;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        rv = findViewById(R.id.wishlist_recycler);
        adapter = new WishListAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);
        List<Result> results = null;
        results = Prefs.loadFavorites(this);
        adapter.addAll(results);


    }

    @Override
    public void retryPageLoad() {

    }
}