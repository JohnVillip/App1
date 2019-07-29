package com.example.villip.app1.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.villip.app1.R;


public class TestFragment extends Fragment {
    EditText editText;
    Button buttonOk;

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_test, container, false);

        editText = rootView.findViewById(R.id.editText);
        buttonOk = rootView.findViewById(R.id.buttonOk);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(editText.getText().toString().length() > 0) {
                    Intent intent = new Intent("ViewPictureApp2");
                    intent.putExtra("linkApp1", editText.getText().toString());
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }
}
