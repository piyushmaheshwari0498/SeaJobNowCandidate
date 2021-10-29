package com.example.seajobnowcandidate.actions;

import android.view.View;
import com.google.android.material.snackbar.Snackbar;

public class ShowSnackbar {

    public void shortSnackbar(View view,String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
