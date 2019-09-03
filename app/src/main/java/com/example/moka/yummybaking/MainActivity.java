package com.example.moka.yummybaking;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.moka.yummybaking.adapter.RecipeAdapter;
import com.example.moka.yummybaking.model.Baking;
import com.example.moka.yummybaking.networking.Service;
import com.example.moka.yummybaking.networking.DataServiceGenerator;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static Context mContext;
    private RecyclerView recyclerView;
    private List<Baking> baking = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(MainActivity.this, recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent details=new Intent(MainActivity.this,activity_recipe_details.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("steps",
                        (ArrayList<? extends Parcelable>) baking.get(position).getSteps());
                bundle.putParcelableArrayList("ingredients",
                        (ArrayList<? extends Parcelable>) baking.get(position).getIngredients());
                bundle.putString("recipe_name",baking.get(position).getName());
                details.putExtra("bundle",bundle);

                startActivity(details);

            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        loadData();
    }

    private void loadData() {
        Service service = DataServiceGenerator.createService(Service.class);
        Call<JsonArray> call = service.fetchBakingData();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String listString = response.body().toString();

                        Type listType = new TypeToken<List<Baking>>() {}.getType();
                        baking = getListFromJson(listString,listType);

                        //recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(new RecipeAdapter(getApplicationContext(), baking));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    private static <T> List<T> getListFromJson(String jsonString, Type type) {
        if (!Valid(jsonString)) {
            return null;
        }
        return new Gson().fromJson(jsonString, type);
    }

    private static boolean Valid(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException jse) {
            return false;
        }
    }
}