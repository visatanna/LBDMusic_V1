package com.rogacheski.lbd.lbdmusic;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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

public class MainActivity extends baseActivity
        implements NavigationView.OnNavigationItemSelectedListener , PicassoSingleton.PicassoCallbacksInterface {

    private TextView mUsernameEdit;
    private ImageView mUserPicture;

    /** Texto superior - "Pesquisar por:"*/
    private TextView searchMessage;

    /** ImageView -> Ícone de pesquisa*/
    private ImageView seach_button_icon;

    /** searchBar -> Barra de pesquisa*/
    private EditText searchBar;

    /** Número de opções de pesquisa*/
    private int numberOfOptions = 6;

    /** Opções de pesquisa*/
    private ImageView option1;
    private ImageView option2;
    private ImageView option3;
    private ImageView option4;
    private ImageView option5;
    private ImageView option6;

    /** Os ícones estão inicializados?*/
    private boolean inicializado = false;

    /** Lista de ícones de pesquisa*/
    private ImageView options[] = {option1, option2, option3, option4, option5, option6};

    /** Lista de imagens para o estado OFF desses icones*/
    int imagesOff[] = {R.drawable.option1off, R.drawable.option2off, R.drawable.option3off, R.drawable.option4off, R.drawable.option5off, R.drawable.option6};

    /** Lista de imagens para o estado ON desses icones*/
    int imagesOn[] = {R.drawable.option1on, R.drawable.option2on, R.drawable.option3on, R.drawable.option4on, R.drawable.option5on, R.drawable.option6};

    /** Estados dos ícones (Pressionado/Solto)*/
    private boolean optionsState[] = {false, false, false, false, false};

    public user userLogado = new user();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        TAG = "MainActivity";
        mContext = MainActivity.this;
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
            if(!session.gettype().equals("contractor")) {
                TransitionRight(ProfileMusicianActivity.class);
            } else {
                loadUser();
            }
        }


        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        ActivityManager.TaskDescription tDesk = new ActivityManager.TaskDescription(getString(R.string.app_name),bm,getResources().getColor(R.color.colorPrimaryDark));
        this.setTaskDescription(tDesk);
        getWindow().setBackgroundDrawableResource(R.color.white);
    }

    /** A caixa de busca existe? Se sim, o foco está nela?*/
    public boolean checkFocus(int focus, EditText searchBar) {
        if(searchBar != null) {
            if (focus == searchBar.getId()) {
                return true;
            }
                return false;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            /** Se o foco estiver na caixa de busca, feche a tela de pesquisa simples*/
            if(checkFocus(this.getCurrentFocus().getId(), searchBar)) {
                fecharPesquisaSimples(searchBar);
                /** Se o foco não estiver na caixa de busca (ou ela não existir), então continue o código*/
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
            if(userLogado.getType() == "Musician") {

            } else {
                TransitionRight(ProfileContractorActivity.class);
            }
        } else if (id == R.id.nav_logout) {
            session.setusename("");
            TransitionLeft(LoginActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadUser() {
        WriteLog("Iniciando UserLogado");

        ShowDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.lbd.bravioseguros.com.br/userrest/email/"+session.getusename(),new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                try {
                    JSONObject dataJson= (JSONObject) response.get("data");
                    String id = dataJson.get("idUsuario").toString();
                    String type = (String) response.get("type");
                    String email = (String) response.get("email");
                    WriteLog(type);
                    if(!id.equals("false")) {
                        DismissDialog();
                        userLogado.setEmail(email);
                        userLogado.setFantasyName(dataJson.get("fantasyName").toString());
                        userLogado.setFName(dataJson.get("firstName").toString());
                        userLogado.setLName(dataJson.get("lastName").toString());
                        userLogado.setPicture(dataJson.get("profileImage").toString());
                        userLogado.setType(type);
                        session.settype(type);
                        if(type.equals("musician")) {
                            userLogado.setBackpicture(dataJson.get("backpicture").toString());
                        } else {
                            userLogado.setDocument(dataJson.get("identDocument").toString());
                        }
                        mUsernameEdit.setText(userLogado.getFantasyName());
                        PicassoSingleton.getInstance(new WeakReference<>(mContext), new WeakReference<PicassoSingleton.PicassoCallbacksInterface>(MainActivity.this))
                                .setProfilePictureAsync(mUserPicture, userLogado.getPicture(),getDrawable(R.drawable.ic_account_circle_white));
                    } else {
                        DismissDialog();
                        session.setusename("");
                        TransitionLeft(LoginActivity.class);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    DismissDialog();
                    session.setusename("");
                    TransitionLeft(LoginActivity.class);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                DismissDialog();
                WriteMessage("No Internet Connection!","long");
                session.setusename("");
                TransitionLeft(LoginActivity.class);
            }

            @Override
            public void onFinish() {

            }

        });

    }

    @Override
    public void onPicassoSuccessCallback() {
    }

    @Override
    public void onPicassoErrorCallback() {
        Log.e("TAG", "error");
    }


    /**
     *
     * Código da home page
     *
     * Dois estados principais:
     * - Estado 1 - Pesquisa simples escondida
     *      Contém o ícone de pesquisa
     * - Estado 2 - Pesquisa simples selecionada
     *      Contém o campo de busca, as opções e o botão para busca avançada
     *
     */


    public void changeState(View view){
        ImageView atual = (ImageView) view;
        int option = (int) atual.getTag();
        if(!optionsState[option]) {
            setOn(option);
        }
    }

    public void setOn(int option){
        for(int i=0; i<numberOfOptions-1; i++){
            if(i == option){
                optionsState[i] = true;
                options[i].setImageResource(imagesOn[i]);
            } else{
                optionsState[i] = false;
                options[i].setImageResource(imagesOff[i]);
            }
        }
    }

    public int getSettings(){
        for(int i=0; i<numberOfOptions-1; i++){
            if(optionsState[i]){
                return i;
            }
        }
        return -1;
    }

    public void abrirPesquisaSimples(View view) {

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.home);

        /** PARTE 1 - Esconder os ícones atuais na tela*/

        /**
         * TODO Esconda toda a interface gráfica presente na tela
         * */

        /** PARTE 2 - Esconder o ícone de pesquisa da barra inferior*/

        seach_button_icon = (ImageView) findViewById(R.id.search_button_home);
        seach_button_icon.setVisibility(View.INVISIBLE);

        /** PARTE 3 - Criar/Mostrar o campo de pesquisa */

        /** Caso a barra não exista, é necessario cria-la*/
        if(searchBar == null){
            // Aninhar o EditText na BarraInferior
            RelativeLayout mRlayout = (RelativeLayout) findViewById(R.id.barraInferior);

            // Criar o parâmetro "Match_parent" para a largura
            RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            mRparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            // Margens
            mRparams.bottomMargin = 5;
            mRparams.leftMargin = 10;
            mRparams.rightMargin = 10;

            // Criar caixa de texto
            searchBar = new EditText(this);

            // Utilizar parâmetros já criados
            searchBar.setLayoutParams(mRparams);

            // Tamanho máximo da altura da entrada
            searchBar.setMaxHeight(100);

            // Id do objeto
            searchBar.setId(R.id.searchBar);

            // Mensagem padrão (dica)
            searchBar.setHint("Procurar");

            // Cor da mensagem padrão
            searchBar.setHintTextColor(Color.parseColor("#999999"));

            // Cor do texto inserido
            searchBar.setTextColor(Color.parseColor("#ffffff"));

            // Limitar a somente uma linha
            searchBar.setSingleLine();

            // Modificar o botão de ação para "Search" (Lupa)
            searchBar.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

            // Criar método que chama outro método quando o usuário clicar no botão pesquisar
            searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        simpleSearch(getSettings());
                        handled = true;
                    }
                    return handled;
                }
            });

            // Adicionar o objeto ao layout
            mRlayout.addView(searchBar);

        } else {
            /** Caso a barra já exista, não é necessario cria-la, apenas mostra-la*/
            searchBar.setVisibility(View.VISIBLE);
        }

        /** PARTE 4 - Criar/Mostrar opções de pesquisa */

        // Se uma opção não existe, nenhuma opção existe
        if(!inicializado) {
            for (int i = 0; i < numberOfOptions; i++) {
            options[i] = new ImageView(this);
                options[i].setImageResource(imagesOff[i]);
                //int optionW = (int) (getResources().getDimension(R.dimen.OptionW) / getResources().getDisplayMetrics().density);
                int optionH = (int) (getResources().getDimension(R.dimen.OptionH));
                //options[i].setMaxWidth(optionW);
                //options[i].setMaxHeight(optionH);
                options[i].setTag(i);

                // Cálculo do tamanho da margem
                //int margin = (rl.getWidth() - (R.dimen.OptionW * 3)) / 4;
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            int leftMargin = 30;
            int margin = 30;
            lp.leftMargin = leftMargin + (margin * (i % 3) + (200 * (i%3)));

            // Margem superior fixa
            int margemSuperior = 150;

            // Se a opção estiver na primeira linha
            if (i < 3) {
                lp.topMargin = margemSuperior;
            } else {
                // Se a opção estiver na segunda linha
                lp.topMargin = margemSuperior + optionH + margin;
            }

            // Add Listener

            ImageView.OnClickListener listener;
            if(i < 6){
                listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeState(v);
                    }
                };
            }else {
                listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callAdvancedSearch();
                    }
                };
            }

            options[i].setOnClickListener(listener);

            rl.addView(options[i], lp);
        }
        setOn(0);
        inicializado = true;
    } else {
        for(int i=0; i<numberOfOptions; i++){
            options[i].setVisibility(View.VISIBLE);
        }
    }

        /** PARTE 5 - Criar o texto superior*/
        if(searchMessage == null) {
            searchMessage = new TextView(this);
            String mensagem = "Procurar por:";
            searchMessage.setText(mensagem);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            lp.leftMargin = 150;
            lp.topMargin = 20;
            searchMessage.setTextSize(30);
            searchMessage.setTextColor(Color.parseColor("#000000"));
            rl.addView(searchMessage, lp);
        } else {
            searchMessage.setVisibility(View.VISIBLE);
        }
    }

    public void fecharPesquisaSimples(EditText searchBar){
        // Turn the serach box invisible
        searchBar.setVisibility(View.INVISIBLE);

        // Clear the contents of the search box
        searchBar.getText().clear();

        // Turn the options invisible
        for(int i=0; i<numberOfOptions; i++){
            options[i].setVisibility(View.INVISIBLE);
        }

        // Turn the search button visible
        seach_button_icon = (ImageView) findViewById(R.id.search_button_home);
        seach_button_icon.setVisibility(View.VISIBLE);

        searchMessage.setVisibility(View.INVISIBLE);

        /** TODO Tornar os icones da tela visiveis*/
    }

    public void simpleSearch(int option) {

        // TODO
        //TransitionRight(.class);

        String pesquisa = searchBar.getText().toString();

        if(pesquisa.equals("")){
            return;
        }

        ShowDialog();

        String requestTerm = "";
        String failText = "";

        switch (option) {

            // Caso nenhuma opção esteja precionada (o que não devia ocorrer)
            case -1: return;

                // NOME
            case 0:  requestTerm = "/musicianrest/search/";
                     failText = "Não encontramos nenhum músico ou banda com o nome \"" + pesquisa + "\"";
                     break;

                // GÊNERO
            case 1:  requestTerm = "/musicianrest/search/"; //TODO
                     failText = "Não encontramos nenhum gênero com o nome \"" + pesquisa + "\"";
                     break;

                // LOCAL
            case 2:  requestTerm = "/musicianrest/search/"; //TODO
                     failText = "Não encontramos nenhum local com o nome \"" + pesquisa + "\"";
                     break;

                // MAIOR PREÇO
            case 3:  requestTerm = "/musicianrest/search/"; //TODO
                     failText = "Não encontramos nenhum músico ou banda com o nome \"" + pesquisa + "\"";
                     break;

                // MENOR PREÇO
            case 4:  requestTerm = "/musicianrest/search/"; //TODO
                     failText = "Não encontramos nenhum músico ou banda com o nome \"" + pesquisa + "\"";
                     break;
        }

        final String failTextFinal = failText;
        //Log.d("Info", "Pesquisa: " + pesquisa);
        //Log.d("Info", "option: " + String.valueOf(option));

        DismissDialog();
        WriteLog("Iniciando pesquisaSimples");

        ShowCustomDialog("Searching");
        AsyncHttpClient searchRequest = new AsyncHttpClient();
        searchRequest.get("http://www.lbd.bravioseguros.com.br" + requestTerm + pesquisa, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                try {
                    // Ler do conteúdo "data" da resposta
                    JSONArray dataJson = (JSONArray) response.get("data");


                    if(dataJson.length() == 0) {
                            DismissDialog();
                            WriteMessage(failTextFinal, "long");
                            // TODO
                            //TransitionLeft(MainActivity.class);
                            return;
                    }
                    int numberOfResults = dataJson.length();

                    BandEntity resultados[] = new BandEntity[numberOfResults];
                    //band resultados[] = new band [numberOfResults];

                    // Para cada valor encontrado
                    for(int i=0; i<numberOfResults; i++){
                        resultados[i] = new BandEntity();
                        //resultados[i] = new band();
                        JSONObject atual = dataJson.getJSONObject(i);

                        String idUsuarioString = atual.get("idUsuario").toString();
                        if(!idUsuarioString.equals("") || idUsuarioString.equals("null")){
                            resultados[i].setIdUsuario(Integer.parseInt(idUsuarioString));
                        }
                        String idAddressString = atual.get("idAddress").toString();
                        if(!(idAddressString.equals("") || idAddressString.equals("null"))){
                            resultados[i].setIdAddress(Integer.parseInt(idAddressString));
                        }
                        resultados[i].setsNomeBanda(atual.get("fantasyName").toString());
                        resultados[i].setFname(atual.get("firstName").toString());
                        resultados[i].setLname(atual.get("lastName").toString());
                        resultados[i].setdImagemBanda(atual.get("profileImage").toString());
                        resultados[i].setdImagemDescBanda(atual.get("backpicture").toString());

                    }
                    DismissDialog();
                    Intent intent = new Intent(MainActivity.this, searchResult.class);
                    intent.putExtra("com.rogacheski.lbd.lbdmusic.MESSAGE", resultados);
                    intent.putExtra("com.rogacheski.lbd.lbdmusic.USER", userLogado);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

                } catch (JSONException e) {
                    e.printStackTrace();
                    DismissDialog();
                    //TransitionLeft(LoginActivity.class);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                DismissDialog();
                WriteMessage(failTextFinal, "long");
                //TransitionLeft(MainActivity.class);
            }

            @Override
            public void onFinish() {

            }
        });
    }


    public void callAdvancedSearch(){
        Log.d("Info", "AdvancedSearch");
        /** TODO implementar a busca avançada*/
    }



}
