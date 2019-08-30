package com.example.moka.yummybaking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.moka.yummybaking.fragment.StepDetailsFragment;
import com.example.moka.yummybaking.fragment.detailsFragment;
import com.example.moka.yummybaking.json.Recipe;
import com.example.moka.yummybaking.model.Step;
import com.google.gson.Gson;

import java.util.ArrayList;

public class activity_recipe_details extends AppCompatActivity implements FragmentOneListener {
    FrameLayout stepdetail_Fragment;
    boolean Tablet;
    private ArrayList<Step> steps;

    String name;

    //details of step
    StepDetailsFragment stepdetailsFragment;

    //step and ingredient fragment
    detailsFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        stepdetail_Fragment = (FrameLayout) findViewById(R.id.fragmentTwo);
        Tablet = true;
        Bundle extras = getIntent().getBundleExtra("bundle");
        name = extras.getString("recipe_name");
        getSupportActionBar().setTitle(name);

        steps = extras.getParcelableArrayList("steps");
        extras.putBoolean("tablet", stepdetail_Fragment != null);

        if (savedInstanceState == null) {
            fragment = new detailsFragment();
            fragment.setFragmentListener(this);
            fragment.setArguments(extras);
            getFragmentManager().beginTransaction().add(R.id.fragmentOne, fragment).commit();
            //checking if screen size greater than 600dp
            if (stepdetail_Fragment == null) {
                Tablet = false;
            }
            else {
                this.setStep(0, steps);
            }
        } else {
            fragment= (detailsFragment) getFragmentManager().getFragment(savedInstanceState,"main");
            fragment.setFragmentListener(this);


            if (!fragment.isAdded())
                getFragmentManager().beginTransaction().add(R.id.fragmentOne, fragment).commit();

            if(stepdetail_Fragment !=null)
            {
                stepdetailsFragment = (StepDetailsFragment) getFragmentManager().getFragment(savedInstanceState,"detail");
                getFragmentManager().beginTransaction().replace(R.id.fragmentTwo, stepdetailsFragment).commit();
            }
        }




    }



    @Override
    public void setStep(int index, ArrayList<Step> steps) {
        if (!Tablet) {
            Intent intent = new Intent(this, step_detailActivity.class);
            intent.putExtra("steps", steps);
            intent.putExtra("current", index);
            intent.putExtra("name", name);
            startActivity(intent);
        } else {
            stepdetailsFragment = new StepDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("steps", steps);
            stepdetailsFragment.setFragmentListener(this);
            bundle.putInt("current", index);
            bundle.putBoolean("tablet", true);
            stepdetailsFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.fragmentTwo, stepdetailsFragment).commit();
        }
    }

    @Override
    public void setCurrent(int index) {
        if (Tablet) {
            fragment.updateView(index);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "main", fragment);

        if (Tablet && stepdetail_Fragment!=null)
        {
            try{
                getFragmentManager().putFragment(outState, "detail", stepdetailsFragment);
            }catch (NullPointerException e) {}

        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        //&&getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
        if (stepdetail_Fragment == null) {
            Tablet = false;
        }
    }

}