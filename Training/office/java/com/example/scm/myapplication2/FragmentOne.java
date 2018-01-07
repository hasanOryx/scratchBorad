package com.example.scm.myapplication2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scm.myapplication2.R;

/**
 * Created by SCM on 2017-02-07.
 */

public class FragmentOne extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(
        R.layout.frament_one, container, false);
    }

}
