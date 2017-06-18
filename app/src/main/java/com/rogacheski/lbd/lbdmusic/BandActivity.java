package com.rogacheski.lbd.lbdmusic;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import com.rogacheski.lbd.lbdmusic.adapter.TabAdapterBandaView;
import com.rogacheski.lbd.lbdmusic.controllers.BandController;
import com.rogacheski.lbd.lbdmusic.entity.TagEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class BandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jbInit();

    }

    private void jbInit() {

            //esconde barra do app
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }

            int id_banda = 0; //TODO ainda não sei como receber o id como parâmetro

            //seta as tags particulares da banda x
            Random r = new Random();

            id_banda = r.nextInt(2);
            BandEntity banda = BandController.buscarBandaPorId(id_banda, this);

            setBandaImage(banda);
            setNomeBanda(banda.getsNomeBanda());
            DisplayTags(banda.getTags());
            //setando a nota média na tela
            RatingBar nota = (RatingBar) findViewById(R.id.ratingStars);
            nota.setRating((float) banda.getAverageRating());

            //cria gerenciador dos fragmentos (views do tab layout)
            ViewPager viewPager = (ViewPager) findViewById(R.id.Pager);
            PagerAdapter pagerAdapter = new TabAdapterBandaView(getSupportFragmentManager(), this, banda);
            viewPager.setAdapter(pagerAdapter);

            TabLayout barraViewsBanda = (TabLayout) findViewById(R.id.LayoutTabBandaHomePage);

            barraViewsBanda.setupWithViewPager(viewPager);
    }

    private List<TextView> DisplayTags(List<TagEntity> listaTags) {
        ArrayList<TextView> listaTextosTag = new ArrayList<TextView>();
        ImageView backgroundImageBanda = (ImageView) findViewById(R.id.imageViewBanda);
        LinearLayout layoutLinearTags = (LinearLayout) findViewById(R.id.lLTags);
        listaTextosTag = new ArrayList<TextView>();

        //Posicionando as tags

        int iterador =  listaTags.size();
        if(listaTags.size() > 4){
            iterador = 4;
        }

        for (int i = 0; i < iterador; i++) {
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textViewParams.setMargins(50,0,0,30);
            TextView tag = new TextView(this);
            tag.setText(listaTags.get(i).getGenre());
            tag.setTextSize(15);
            tag.setId(i);
            tag.setTextColor(getResources().getColor(R.color.colorCornFlowerBlue));
            layoutLinearTags.addView(tag, textViewParams);
            listaTextosTag.add(tag);
        }
        return listaTextosTag;
    }
    public void setNomeBanda(String nomeBanda){
        TextView telaNomeBanda = (TextView)findViewById(R.id.tvTituloDaBanda);
        telaNomeBanda.setText(nomeBanda);
    }
    public void setBandaImage(BandEntity banda) {
        //seta imagem de fundo da banda
        ImageView backgroundImageBanda = (ImageView) findViewById(R.id.imageViewBanda);
        backgroundImageBanda.setImageDrawable(banda.getdImagemBanda());
        backgroundImageBanda.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //backgroundImageBanda.setImageAlpha(200);
        backgroundImageBanda.setColorFilter(R.color.colorGrey);
    }


}
