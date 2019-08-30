package com.example.moka.yummybaking.fragment;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.moka.yummybaking.BakingAppRemoteViewsFactory;
import com.example.moka.yummybaking.FragmentOneListener;
import com.example.moka.yummybaking.R;
import com.example.moka.yummybaking.RecyclerViewItemClickListener;
import com.example.moka.yummybaking.adapter.StepsAdapter;
import com.example.moka.yummybaking.adapter.ingredientAdapter;
import com.example.moka.yummybaking.bakingWidget;
import com.example.moka.yummybaking.json.Recipe;
import com.example.moka.yummybaking.model.Ingredient;
import com.example.moka.yummybaking.model.Step;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class detailsFragment extends android.app.Fragment {
    FragmentOneListener listener;

    @BindView(R.id.stepsList)
    RecyclerView steprecycle;
    @BindView(R.id.ingredientList)
    RecyclerView ingredientrecycle;

    ArrayList<Step> steps;
    ArrayList<Ingredient> ingredients;

    int[] trackers,ingred;
    int index;
    String json=null;
    boolean tablet;
    RecyclerView.LayoutManager ingredientsManager, stepsManager;
    int scrollingredientpos, scrollsteppos;
    String recipename;
    public void setFragmentListener(FragmentOneListener listener) {
        this.listener = listener;
    }
    public static final String SHARED_PREFS = "sharedPrefs";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.details_fragment, container, false);

        ButterKnife.bind(this, root);
        //ingredientrecycle=(RecyclerView)getView().findViewById(R.id.ingredientList);
        //ingredientrecycle=(RecyclerView)getView().findViewById(R.id.stepsList);

        ingredientsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        stepsManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        Button btn=(Button)root.findViewById(R.id.bbbb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(json==null){
                    addLatestIngredientsTojson();
                    Toast.makeText(getActivity(),"ingredients saved ;)",Toast.LENGTH_SHORT).show();}
                else Toast.makeText(getActivity(),"already saved ;)",Toast.LENGTH_SHORT).show();
            }
        });
        if (savedInstanceState == null) {
            Bundle extra = getArguments();
            ingredients = extra.getParcelableArrayList("ingredients");
            recipename=extra.getString("recipe_name");
            tablet = extra.getBoolean("tablet", false);
            steps = extra.getParcelableArrayList("steps");

            index = 0;
        } else {
            ingredients = savedInstanceState.getParcelableArrayList("ingredients");
            tablet = savedInstanceState.getBoolean("tablet", false);
            steps = savedInstanceState.getParcelableArrayList("steps");
            recipename=savedInstanceState.getString("recipe_name");
            index = savedInstanceState.getInt("position");

            scrollingredientpos = savedInstanceState.getInt("scrollingredientpos");
            scrollsteppos = savedInstanceState.getInt("scrollsteppos");
        }
        trackers = new int[steps.size()];
        //get all the widget ingredient and make it blue
        //List<ingredient> getallingredeintwidget(recipename);
        //instead ot that
        if (tablet) {
            trackers[index] = 1;
        }


        ingredientrecycle.setLayoutManager(ingredientsManager);
        ingredientrecycle.setAdapter(new ingredientAdapter(getActivity(), ingredients));
        if (scrollingredientpos != 0) {
            ingredientrecycle.getLayoutManager().scrollToPosition(scrollingredientpos);
        }


        steprecycle.setLayoutManager(stepsManager);
        steprecycle.setAdapter(new StepsAdapter(getActivity(), steps, trackers));
        if (scrollsteppos != 0) {
            steprecycle.getLayoutManager().scrollToPosition(scrollsteppos);
        }

        steprecycle.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(), steprecycle, new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        listener.setStep(position, steps);
                        updateView(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );

        if (tablet) {
            updateView(index);
            listener.setStep(index, steps);
        }

        return root;
    }



    public void updateView(int index) {
        this.index = index;
        if (!tablet) {
            return;
        }
        trackers = new int[steps.size()];
        try {
            trackers[index] = 1;
            ((StepsAdapter) steprecycle.getAdapter()).trackers = trackers;
            steprecycle.getAdapter().notifyDataSetChanged();
            steprecycle.scrollToPosition(index);
        } catch (ArrayIndexOutOfBoundsException E) {

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ingredients", ingredients);
        outState.putParcelableArrayList("steps", steps);
        outState.putBoolean("tablet", tablet);
        outState.putString("recipe_name",recipename);
        outState.putInt("position", index);

        outState.putInt("scrollingredientpos", ((LinearLayoutManager) ingredientrecycle.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
        outState.putInt("scrollsteppos", ((LinearLayoutManager) steprecycle.getLayoutManager()).findFirstCompletelyVisibleItemPosition());


    }
    private void addLatestIngredientsTojson() {
        Gson gson=new Gson();
        Recipe rec=new Recipe(recipename,ingredients);
        json=gson.toJson(rec);
        //save json

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("json", json);

        editor.apply();
        //update on the widget direct
        Intent intent = new Intent(getActivity(),bakingWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getActivity().getApplication())
                .getAppWidgetIds(new ComponentName(getActivity().getApplication(), bakingWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getActivity().sendBroadcast(intent);

        Toast.makeText(getActivity(), "ingredient saved as a widget", Toast.LENGTH_SHORT).show();
    }

}
