package com.example.moviesdb.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.widget.BaseAdapter;

import com.example.moviesdb.models.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prefs {

    public static String PrefsName = "STORE";
    public static String wishlistKey = "wishListKey";
    public static final String FAVORITES = "Favorite";

    static Result result;

    public static void savePref(Context context, String Key, Result value) {
        SharedPreferences share = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor shedit = share.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value); // myObject - instance of MyObject
        shedit.putString(wishlistKey, json);
        shedit.apply();
    }

    public static void readPref(Context context, String Key, Result defaultValue) {
        SharedPreferences share = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = share.getString(wishlistKey, "");
        Type type = new TypeToken<ArrayList<Result>>() {
        }.getType();
        defaultValue = gson.fromJson(json, type);
        if (defaultValue == null) {
            defaultValue = new Result();
        }
    }


    public static void storeFavorites(Context context, List<Result> favorites) {
        // used for store arrayList in json format
        SharedPreferences share = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        SharedPreferences.Editor shedit = share.edit();
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson sExposeGson = builder.create();
        String jsonFavorites = sExposeGson.toJson(favorites);
        shedit.putString(FAVORITES, jsonFavorites);
        shedit.commit();
    }

    public static ArrayList<Result> loadFavorites(Context context) {
        // used for retrieving arraylist from json formatted string
        SharedPreferences settings;
        List favorites;
        settings = context.getSharedPreferences(PrefsName, Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);

            GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
            builder.excludeFieldsWithoutExposeAnnotation();
            Gson sExposeGson = builder.create();
            Result[] favoriteItems = sExposeGson.fromJson(jsonFavorites, Result[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList(favorites);
        } else
            return null;
        return (ArrayList) favorites;
    }

    public static void addFavorite(Context context, Result myModel) {
        List<Result> favorites = loadFavorites(context);
        if (favorites == null)
            favorites = new ArrayList();
        favorites.add(myModel);
        storeFavorites(context, favorites);
    }

    public static void removeFavorite(Context context, Result myModel) {
        ArrayList<Result> favorites = loadFavorites(context);
        if (favorites != null) {
            for (int i = favorites.size() - 1; i >= 0; i--) {
                if (myModel.getId().equals(favorites.get(i).getId())) {
                    favorites.remove(i);
                    notifyDataSetChanged();
                }
            }
            storeFavorites(context, favorites);

        }
    }

}
