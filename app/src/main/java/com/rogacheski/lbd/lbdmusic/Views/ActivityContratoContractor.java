package com.rogacheski.lbd.lbdmusic.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.base.baseActivity;
import com.rogacheski.lbd.lbdmusic.singleton.PicassoSingleton;

/**
 * Created by Cliente on 18/06/2017.
 */

public class ActivityContratoContractor extends baseActivity
        implements NavigationView.OnNavigationItemSelectedListener , PicassoSingleton.PicassoCallbacksInterface{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratoscontractor);

        RadioButton bValorFixo = (RadioButton) findViewById(R.id.rButtonValorFixo);
        RadioButton bValorVar = (RadioButton) findViewById(R.id.rButtonValorVar);
        RadioButton bValorCom = (RadioButton) findViewById(R.id.rValorACombinar);

        RadioButton bAComFixo = (RadioButton) findViewById(R.id.rACombinarFixo);
        RadioButton bAComVar = (RadioButton) findViewById(R.id.rACombinarVar);


        bValorFixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton bValorFixo = (RadioButton) findViewById(R.id.rButtonValorFixo);
                RadioButton bValorVar = (RadioButton) findViewById(R.id.rButtonValorVar);
                RadioButton bValorCom = (RadioButton) findViewById(R.id.rValorACombinar);

                RadioButton bAComFixo = (RadioButton) findViewById(R.id.rACombinarFixo);
                RadioButton bAComVar = (RadioButton) findViewById(R.id.rACombinarVar);

                bValorFixo.setChecked(true);
                bValorVar.setChecked(false);
                bValorCom.setChecked(false);

                bAComFixo.setChecked(false);
                bAComVar.setChecked(false);
            }
        });

        bValorVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton bValorFixo = (RadioButton) findViewById(R.id.rButtonValorFixo);
                RadioButton bValorVar = (RadioButton) findViewById(R.id.rButtonValorVar);
                RadioButton bValorCom = (RadioButton) findViewById(R.id.rValorACombinar);

                RadioButton bAComFixo = (RadioButton) findViewById(R.id.rACombinarFixo);
                RadioButton bAComVar = (RadioButton) findViewById(R.id.rACombinarVar);

                bValorVar.setChecked(true);
                bValorFixo.setChecked(false);
                bValorCom.setChecked(false);

                bAComFixo.setChecked(false);
                bAComVar.setChecked(false);
            }
        });

        bValorCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton bValorFixo = (RadioButton) findViewById(R.id.rButtonValorFixo);
                RadioButton bValorVar = (RadioButton) findViewById(R.id.rButtonValorVar);
                RadioButton bValorCom = (RadioButton) findViewById(R.id.rValorACombinar);

                RadioButton bAComFixo = (RadioButton) findViewById(R.id.rACombinarFixo);
                RadioButton bAComVar = (RadioButton) findViewById(R.id.rACombinarVar);

                bValorVar.setChecked(false);
                bValorFixo.setChecked(false);
                bValorCom.setChecked(true);

                bAComFixo.setChecked(true);
                bAComVar.setChecked(false);
            }
        });

        bAComFixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton bValorFixo = (RadioButton) findViewById(R.id.rButtonValorFixo);
                RadioButton bValorVar = (RadioButton) findViewById(R.id.rButtonValorVar);
                RadioButton bValorCom = (RadioButton) findViewById(R.id.rValorACombinar);

                RadioButton bAComFixo = (RadioButton) findViewById(R.id.rACombinarFixo);
                RadioButton bAComVar = (RadioButton) findViewById(R.id.rACombinarVar);

                bValorVar.setChecked(false);
                bValorFixo.setChecked(false);
                bValorCom.setChecked(true);

                bAComFixo.setChecked(true);
                bAComVar.setChecked(false);
            }
        });

        bAComVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton bValorFixo = (RadioButton) findViewById(R.id.rButtonValorFixo);
                RadioButton bValorVar = (RadioButton) findViewById(R.id.rButtonValorVar);
                RadioButton bValorCom = (RadioButton) findViewById(R.id.rValorACombinar);

                RadioButton bAComFixo = (RadioButton) findViewById(R.id.rACombinarFixo);
                RadioButton bAComVar = (RadioButton) findViewById(R.id.rACombinarVar);

                bValorVar.setChecked(false);
                bValorFixo.setChecked(false);
                bValorCom.setChecked(true);

                bAComFixo.setChecked(false);
                bAComVar.setChecked(true);
            }
        });

        Button bContratar = (Button) findViewById(R.id.bContratar);

        bContratar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton bValorFixo = (RadioButton) findViewById(R.id.rButtonValorFixo);
                RadioButton bValorVar = (RadioButton) findViewById(R.id.rButtonValorVar);
                RadioButton bValorCom = (RadioButton) findViewById(R.id.rValorACombinar);

                RadioButton bAComFixo = (RadioButton) findViewById(R.id.rACombinarFixo);
                RadioButton bAComVar = (RadioButton) findViewById(R.id.rACombinarVar);

                TextView textView;
                String valor;

                if(bValorFixo.isChecked()){
                    textView = (TextView) findViewById(R.id.viewValorFixo);

                    valor = textView.getText().toString();
                }
                else if(bValorVar.isChecked()){
                    textView = (TextView) findViewById(R.id.viewValorVar);

                    valor = textView.getText().toString();
                }
                else if(bValorCom.isChecked()){
                    EditText editText;

                    if(bAComFixo.isChecked()){
                        editText = (EditText) findViewById(R.id.propostaConFixo);

                        valor = editText.getText().toString();
                    }
                    else if(bAComVar.isChecked()){
                        editText = (EditText) findViewById((R.id.propostaConVar));

                        valor = editText.getText().toString();
                    }
                }

                // Insere valor no BD
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
