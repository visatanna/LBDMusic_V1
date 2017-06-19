package com.rogacheski.lbd.lbdmusic;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rogacheski.lbd.lbdmusic.adapter.TabAdapterBandaView;
import com.rogacheski.lbd.lbdmusic.base.baseActivity;
import com.rogacheski.lbd.lbdmusic.controllers.ControllerBanda;
import com.rogacheski.lbd.lbdmusic.entity.BandEntity;
import com.rogacheski.lbd.lbdmusic.entity.TagEntity;
import com.rogacheski.lbd.lbdmusic.session.Session;
import com.rogacheski.lbd.lbdmusic.singleton.PicassoSingleton;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ProfileMusicianActivity extends baseActivity
        implements NavigationView.OnNavigationItemSelectedListener , PicassoSingleton.PicassoCallbacksInterface {

    private TextView mUsernameEdit;
    private ImageView mUserPicture;
    BandEntity banda;
    boolean isBandaDaTela = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilemusician);

        ButterKnife.bind(this);

        TAG = "ProfileMusicianActivity";
        mContext = ProfileMusicianActivity.this;
        session = new Session(mContext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        mUsernameEdit = (TextView)header.findViewById(R.id.username);
        mUserPicture = (ImageView)header.findViewById(R.id.drawer_profilePicture);

              if(!CheckUserLogado()) {
            TransitionRight(LoginActivity.class);
        } else {
            if(!session.gettype().equals("musician")) {
                TransitionRightExtraId(MainActivity.class , "id", session.getid());
            } else {
                //TODO CHAMA MAIN DE NOVO
            }
        }


        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        ActivityManager.TaskDescription tDesk = new ActivityManager.TaskDescription(getString(R.string.app_name),bm,getResources().getColor(R.color.colorPrimaryDark));
        this.setTaskDescription(tDesk);
        getWindow().setBackgroundDrawableResource(R.color.white);

        jbInit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            session.setusename("");
            TransitionLeft(LoginActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onPicassoSuccessCallback() {
    }

    @Override
    public void onPicassoErrorCallback() {
        Log.e("TAG", "error");
    }

    private void jbInit() {
        int idBanda = Integer.parseInt(getIntent().getExtras().getString("id"));
        int idSession = Integer.parseInt(this.session.getid());

        isBandaDaTela = idBanda == idSession;

        //seta as tags particulares da banda x

        banda = ControllerBanda.getBandaWithId(idBanda);

        setBandaImage(banda);
        setNomeBanda(banda.getsNomeBanda());
        DisplayTags(banda.getTags());
        //setando a nota média na tela
        RatingBar nota= (RatingBar)findViewById(R.id.ratingStars);
        nota.setRating((float)banda.getAverageRating());

        //cria gerenciador dos fragmentos (views do tab layout)
        ViewPager viewPager = (ViewPager) findViewById(R.id.Pager);
        PagerAdapter pagerAdapter = new TabAdapterBandaView(getSupportFragmentManager() , this , banda , idSession );
        viewPager.setAdapter(pagerAdapter);

        TabLayout barraViewsBanda = (TabLayout)findViewById(R.id.LayoutTabBandaHomePage);

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
            tag.setTextColor(getResources().getColor(R.color.white));
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
        PicassoSingleton picasso = PicassoSingleton.getInstance( new WeakReference<>(mContext), new WeakReference<PicassoSingleton.PicassoCallbacksInterface>(ProfileMusicianActivity.this));
        ImageView backgroundImageBanda = (ImageView) findViewById(R.id.imageViewBanda);
        picasso.setPostPictureAsync(backgroundImageBanda , banda.getdImagemBanda() , getDrawable(R.drawable.logo));
        backgroundImageBanda.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //backgroundImageBanda.setImageAlpha(200);
        backgroundImageBanda.setColorFilter(R.color.green_500);
    }
}
