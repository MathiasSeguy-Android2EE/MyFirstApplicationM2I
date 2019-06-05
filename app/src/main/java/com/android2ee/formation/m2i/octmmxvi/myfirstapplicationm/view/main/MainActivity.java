package com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.main;

import android.os.Bundle;

import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.R;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.generic.MotherActivity;

public class MainActivity extends MotherActivity  {
    private static final String TAG = "MainActivity";

    /***********************************************************
     * Managing LifeCycle
     **********************************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instanciate the view
        setContentView(R.layout.activity_main);
    }

}
