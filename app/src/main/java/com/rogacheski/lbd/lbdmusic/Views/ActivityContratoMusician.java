package com.rogacheski.lbd.lbdmusic.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.base.baseActivity;
import com.rogacheski.lbd.lbdmusic.singleton.PicassoSingleton;

/**
 * Created by Cliente on 18/06/2017.
 */

public class ActivityContratoMusician extends baseActivity
        implements NavigationView.OnNavigationItemSelectedListener , PicassoSingleton.PicassoCallbacksInterface{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratosmusician);

        final Button button = (Button) findViewById(R.id.buttonContratosMusician);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText valorFixo = (EditText) findViewById(R.id.valorFixoMusician);
                EditText valorVar = (EditText) findViewById(R.id.valorVarMusician);
                EditText nHorasFixo = (EditText) findViewById(R.id.horasMaxTurno);
                EditText nHorasVar = (EditText) findViewById(R.id.horasMaxVar);

                String sValorFixo = valorFixo.getText().toString();
                String sValorVar = valorVar.getText().toString();
                String sHorasFixo = nHorasFixo.getText().toString();
                String sHorasVar = nHorasVar.getText().toString();

                // Inserir valores no BD
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPicassoSuccessCallback() {

    }

    @Override
    public void onPicassoErrorCallback() {

    }
}
