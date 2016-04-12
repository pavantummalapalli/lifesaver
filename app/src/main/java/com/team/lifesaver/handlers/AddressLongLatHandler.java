package com.team.lifesaver.handlers;

import android.view.View;
import android.widget.EditText;

/**
 * Created by pavan.t on 10/03/16.
 */
public class AddressLongLatHandler {

    private EditText longEditView;
    private EditText latEditView;

    public AddressLongLatHandler(EditText longEditView, EditText latEditView) {
        this.longEditView = longEditView;
        this.latEditView = latEditView;
    }

    public void displayLongLat(Double lang,Double lat){

        longEditView.setVisibility(View.VISIBLE);
        longEditView.setText(Double.toString(lang));
        longEditView.setEnabled(false);
        latEditView.setVisibility(View.VISIBLE);
        latEditView.setText(Double.toString(lat));
        latEditView.setEnabled(false);
    }
}
