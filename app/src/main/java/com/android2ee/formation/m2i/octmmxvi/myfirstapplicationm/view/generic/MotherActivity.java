/**
 * <ul>
 * <li>MotherActivity</li>
 * <li>com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm</li>
 * <li>10/10/2016</li>
 * <p>
 * <li>======================================================</li>
 * <p>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.generic;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.R;

/**
 * Created by Mathias Seguy - Android2EE on 10/10/2016.
 */
public class MotherActivity extends AppCompatActivity {
    private static final String TAG = "MotherActivity";
    protected boolean isICS=false,isLollipop=false,isNougat=false;
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        if(getResources().getBoolean(R.bool.postNougat)){
           isICS=isLollipop= isNougat=true;
        }else if(getResources().getBoolean(R.bool.postLollipop)){
            isICS=isLollipop=true;
        }else if(getResources().getBoolean(R.bool.postICS)){
            isICS=true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() called");
    }
}
