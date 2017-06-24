package com.rogacheski.lbd.lbdmusic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rogacheski.lbd.lbdmusic.base.baseActivity;
import com.rogacheski.lbd.lbdmusic.entity.BandEntity;
import com.rogacheski.lbd.lbdmusic.session.Session;
import com.rogacheski.lbd.lbdmusic.model.user;
import com.rogacheski.lbd.lbdmusic.singleton.PicassoSingleton;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ContractorActivity extends baseActivity
        implements NavigationView.OnNavigationItemSelectedListener , PicassoSingleton.PicassoCallbacksInterface {

    private TextView mUsernameEdit;
    private ImageView mUserPicture;

    // Informações de usuário atual;
    private user userLogado;

    // Corpo principal da tela
    private RelativeLayout mainBodyRL;

    // Corpo de pesquisa
    private RelativeLayout searchRL;

    /** Search variables*/
    private boolean searchIsOpen = false;
    private boolean searchInitialized = false;
    private int nOptions = 3;
    private int images[] = {R.drawable.pesquisapornome, R.drawable.pesquisaporgenero, R.drawable.pesquisaporlocal};

    /** Animation time*/
    int animationTime = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor);

        mContext = ContractorActivity.this;
        session = new Session(mContext);

        Intent intent = getIntent();
        userLogado = (user) intent.getSerializableExtra("com.rogacheski.lbd.lbdmusic.USER");

        /** Ache os RelativeLayout relacionados a tela principal e a tela de pesquisa*/
        mainBodyRL = (RelativeLayout) findViewById(R.id.bodyContractor);
        searchRL = (RelativeLayout) findViewById(R.id.searchContractor);


        /** Set contractor's name and image*/
        loadProfileBar();

        ButterKnife.bind(this);

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

        // Se o usuário for um músico e chegou nessa tela (o que não devia acontecer), mande ele de volta para o login
        if(userLogado.getType().equals("musician")) {
            TransitionRight(LoginActivity.class);
        }

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        ActivityManager.TaskDescription tDesk = new ActivityManager.TaskDescription(getString(R.string.app_name),bm,getResources().getColor(R.color.colorPrimaryDark));
        this.setTaskDescription(tDesk);
        getWindow().setBackgroundDrawableResource(R.color.windowColor);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(searchIsOpen){
                closeSearch();
            } else {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                //super.onBackPressed();
            }

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

        if (id == R.id.nav_profile) {
            callProfile();
        } else if (id == R.id.nav_logout) {
            session.setusename("");
            TransitionLeft(LoginActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callProfile(){
        if(userLogado.getType() == "Musician") {

        } else {
            TransitionRight(ProfileContractorActivity.class);
        }
    }

    public void chamarProfile(View view){
        callProfile();
    }

    @Override
    public void onPicassoSuccessCallback() {
    }

    @Override
    public void onPicassoErrorCallback() {
        Log.e("TAG", "error");
    }

    private void loadProfileBar(){

        /** Name */
        TextView contractorName = (TextView) findViewById(R.id.textViewContractor);
        String name = userLogado.getFantasyName();
        int maxSize = 15;

        // Se o nome for muito grande, corte o nome
        // WayToLongOfAName --> WayToLongO...
        if(name.length() > maxSize){
            name = name.substring(0, maxSize-3);
            name = name + "...";
        }

        contractorName.setText(name);

        /** Logo */

        ImageView contractorLogo = (ImageView) findViewById(R.id.imageViewContractor);

        String image = userLogado.getPicture();
        if(!(image.equals("") || image.equals("null"))){
            PicassoSingleton.getInstance(new WeakReference<>(mContext), new WeakReference<PicassoSingleton.PicassoCallbacksInterface>(ContractorActivity.this))
                    .setProfilePictureAsync(contractorLogo, image , getDrawable(R.drawable.ic_account_circle_white));
        }
    }


    public void search(View view){
        if(searchIsOpen){
            closeSearch();
        } else {
            openSearch();
        }
    }

    private void openSearch(){
        searchIsOpen = true;

        searchRL.setAlpha(0f);
        searchRL.setVisibility(View.VISIBLE);

        mainBodyRL.setAlpha(1f);
        mainBodyRL.setVisibility(View.VISIBLE);

        mainBodyRL.animate()
                .alpha(0f)
                .setDuration(animationTime)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mainBodyRL.setVisibility(View.GONE);
                    }
                });

        searchRL.animate()
                .alpha(1f)
                .setDuration(animationTime)
                .setListener(null);

        checkObjects();
    }

    private void closeSearch(){
        searchIsOpen = false;

        searchRL.setAlpha(1f);
        searchRL.setVisibility(View.VISIBLE);

        mainBodyRL.setAlpha(0f);
        mainBodyRL.setVisibility(View.VISIBLE);

        mainBodyRL.animate()
                .alpha(1f)
                .setDuration(animationTime)
                .setListener(null);

        searchRL.animate()
                .alpha(0f)
                .setDuration(animationTime)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        searchRL.setVisibility(View.GONE);
                    }
                });
    }


    private void chamarPesquisa(View view){
        ImageView atual = (ImageView) view;
        int option = (int) atual.getTag();

        if(option > 2 || option < 0){
            return;
        }

        Intent intent = new Intent(mContext, LoginActivity.class);

        if(option == 0){
            intent = new Intent(mContext, SearchByNameActivity.class);
        }
        if(option == 1){
            intent = new Intent(mContext, SearchByGenreActivity.class);
        }
        if(option == 2){
            intent = new Intent(mContext, SearchByLocationActivity.class);
        }

        try{
            intent.putExtra("com.rogacheski.lbd.lbdmusic.USER", userLogado);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }catch(Exception e){
            e.printStackTrace();
        }


    }


    private void checkObjects(){
        if(searchInitialized){
            return;
        } else {
            // Cria as três imagens e adiciona propriedades de botão
            for(int i=0; i<nOptions; i++){
                ImageView option = new ImageView(mContext);

                option.setImageResource(images[i]);
                option.setTag(i);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(260, 260);

                ImageView.OnClickListener listener;
                listener = new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        chamarPesquisa(v);
                        }
                };

                if(i == 0){
                    lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    lp.topMargin = 100;
                    lp.leftMargin = 50;
                }
                if(i == 1){
                    lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    lp.topMargin = 100;
                    lp.rightMargin = 50;
                }
                if(i == 2){
                    lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    lp.topMargin = 350;
                }

                option.setOnClickListener(listener);

                searchRL.addView(option, lp);
            }
            searchInitialized = true;
        }
    }
}
