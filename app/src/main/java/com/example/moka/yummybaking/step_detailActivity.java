package com.example.moka.yummybaking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moka.yummybaking.fragment.StepDetailsFragment;

public class step_detailActivity extends AppCompatActivity {
    StepDetailsFragment detailsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        if(savedInstanceState == null)
        {
            Bundle bundle = getIntent().getExtras();

            if(bundle.containsKey("name"))
            {
                getSupportActionBar().setTitle(bundle.getString("name")+" Steps");
            }
            bundle.putBoolean("tablet",false);

            detailsFragment = new StepDetailsFragment();
            detailsFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .add(R.id.stepFragment, detailsFragment)
                    .commit();
        }
    }
}
