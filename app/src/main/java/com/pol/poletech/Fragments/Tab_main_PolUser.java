package com.pol.poletech.Fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.pol.poletech.R;

import org.json.JSONArray;
import org.json.JSONObject;



public class Tab_main_PolUser extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ft_main_layout_poluser, container, false);


        Toast.makeText(getContext(), "ft_main_layout_poluser", Toast.LENGTH_SHORT).show();




        return view;
    }





    //GetJson *****************************************************************************



}