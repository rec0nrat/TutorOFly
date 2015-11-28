package com.tonikamitv.loginregister;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tylweiss on 11/22/2015.
 */
public class TabFragment2 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.tab_fragment_2 ,null);

        return rootView;
    }
}