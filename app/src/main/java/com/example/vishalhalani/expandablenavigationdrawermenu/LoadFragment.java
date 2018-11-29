package com.example.vishalhalani.expandablenavigationdrawermenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoadFragment extends Fragment {
    private TextView homeTitle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.homefragment,container,false);
        homeTitle=(TextView)view.findViewById(R.id.titleHome);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title= "";
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
        homeTitle.setText(title);

    }
}
