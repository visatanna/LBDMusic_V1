package com.rogacheski.lbd.lbdmusic.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.rogacheski.lbd.lbdmusic.ImageNotRegisteredException;
import com.rogacheski.lbd.lbdmusic.ProfileMusicianActivity;
import com.rogacheski.lbd.lbdmusic.R;
import com.rogacheski.lbd.lbdmusic.entity.AdressEntity;
import com.rogacheski.lbd.lbdmusic.entity.BandEntity;
import com.rogacheski.lbd.lbdmusic.entity.ContatoEntity;
import com.rogacheski.lbd.lbdmusic.singleton.PicassoSingleton;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vinicius on 28-Apr-17.
 */

public class BandAboutUsPage extends BaseFragment implements View.OnClickListener,PicassoSingleton.PicassoCallbacksInterface  {
    private List<String> listaTiposQueContemIcones = Arrays.asList("facebook","soundcloud","youtube");
    private ArrayList<ContatoEntity> listaIcones = new ArrayList<ContatoEntity>();
    private View view;


    public BandAboutUsPage(){
        //TODO Se considerarmos que podem ter mais de 3 tipos de contatos com icones
        //TODO talvez seja melhor fazer disso um objeto do tipo tablayout

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_us,container , false );
        BandEntity banda = (BandEntity)getArgument();

        setListaDeContatos(banda.getListaContatos());

        setImagemDescBanda(banda.getdImagemDescBanda());
        setDescText(banda.getsDescricaoBanda());
        setEndereco(banda.getAdress());

        return view;
    }
    private void setListaDeContatos(List<ContatoEntity> contatos){
        int numeroDeIcones = 0;
        if(contatos != null) {
            for (ContatoEntity contato : contatos) {
                try {
                    String tipo = contato.getDescription();
                    if (listaTiposQueContemIcones.contains(contato.getDescription().toLowerCase()) && numeroDeIcones < 3) {
                        ImageView icon = null;
                        switch (numeroDeIcones) {
                            case 0:
                                icon = (ImageView) view.findViewById(R.id.IconLeft);
                                icon.setOnClickListener(this);
                                break;
                            case 1:
                                icon = (ImageView) view.findViewById(R.id.IconMiddle);
                                icon.setOnClickListener(this);
                                break;
                            case 2:
                                icon = (ImageView) view.findViewById(R.id.IconRight);
                                icon.setOnClickListener(this);
                                break;
                        }
                        if(icon != null){
                            try{
                                icon.setImageResource(getImage(tipo));
                                icon.setAdjustViewBounds(true);
                                listaIcones.add(contato);
                                numeroDeIcones++;
                            } catch (Exception e) {
                                e.printStackTrace();
                                icon.setVisibility(View.GONE);
                                continue;
                            }
                        }
                    } else {
                        TextView contatosSemIcone = (TextView) view.findViewById(R.id.ContatosFinalConteudo);
                        contatosSemIcone.setText(contatosSemIcone.getText() + " " + contato.getDescription() + " " + contato.getsValue() + " /");
                    }
                } catch (Exception e) {
                    //AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    //alertDialog.setTitle("Alert");
                    //alertDialog.setMessage(e.getMessage());
                    /**
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                     */
                }
            }
        }
    }
    private int getImage(String tipo) throws Exception {
       switch (tipo.toLowerCase()){
           case "youtube":
               return R.drawable.unnamed;
           case "facebook":
               return R.drawable.facebook_azul_claro_burned;
           case "soundCloud":
               return R.drawable.sc_light_blue_burned;
           default:
                throw new ImageNotRegisteredException();
       }
    }
    private void setImagemDescBanda(String img){
        ImageView imagemDesc = (ImageView) view.findViewById(R.id.ImagemDesc);
        if(img != null) {
            PicassoSingleton picasso = PicassoSingleton.getInstance(new WeakReference<>(getContext()), new WeakReference<PicassoSingleton.PicassoCallbacksInterface>(this));

            picasso.setPostPictureAsync(imagemDesc, img, getContext().getDrawable(R.drawable.logo));
            imagemDesc.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            imagemDesc.setImageDrawable(getContext().getDrawable(R.drawable.band_4));
        }
    }
    private void setDescText(String descText){
        TextView descricao = (TextView) view.findViewById(R.id.descricaoBanda);
        if(descText != null) {
            descricao.setText(descText);
        }else{
            descricao.setText("Escreva uma descrição para sua banda aqui.");
        }
    }
    private void setEndereco(AdressEntity endereco){
        TextView tvEndereco = (TextView) view.findViewById(R.id.EnderecoBandaConteudo);
        if(endereco != null){
            String cidade = endereco.getCity() == null ? "" : endereco.getCity();
            String estado = endereco.getState() == null ? "" : endereco.getState();
            tvEndereco.setText(" "+cidade.trim() + " - " + estado.trim());
        }else{
            tvEndereco.setText("Ajude-nos a encontrar melhores contratos para você! Cadastre seu endereço.");
        }
    }

    public void enterImageLink(int number){

    if(listaIcones.get(number) != null) {
        ContatoEntity contato = listaIcones.get(number);
        Uri uri = Uri.parse(contato.getsValue());
        Intent chamadaDoPrograma = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(chamadaDoPrograma);
    }
}

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.IconLeft:
                enterImageLink(0);
                break;
            case R.id.IconMiddle:
                enterImageLink(1);
                break;
            case R.id.IconRight:
                enterImageLink(2);
                break;
        }

    }


    @Override
    public void onPicassoSuccessCallback() {

    }

    @Override
    public void onPicassoErrorCallback() {
        Log.e("TAG", "error");
    }
}