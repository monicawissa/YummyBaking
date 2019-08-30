package com.example.moka.yummybaking;

import com.example.moka.yummybaking.model.Step;

import java.util.ArrayList;


public interface FragmentOneListener {

    void setStep(int index, ArrayList<Step> steps);


    void setCurrent(int index);

}
