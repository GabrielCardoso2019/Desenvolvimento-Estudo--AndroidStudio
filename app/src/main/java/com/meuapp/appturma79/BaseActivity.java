package com.meuapp.appturma79;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog progressDialog;
    private String apiPath = "http://10.0.2.2/siteturma79/usuarios/encerrar/";
    private JSONArray resultJsonArray;
    private int success = 0;
    private ArrayList<String> arrayList;
    private int logado = 0;
    private String msg = "";
    private String strUsuario = "";

    DrawerLayout drawer;
    String strusuario;
    TextView txtnome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View cabecMenu = navigationView.getHeaderView(0);

        txtnome = cabecMenu.findViewById(R.id.txtNomeUsuario);

        //Recuperar o valor enviado através do Intent

        Intent login = getIntent();

        strusuario = login.getStringExtra("usuario");

        //Atribuir ao TextView o conteúdo da variável strusuario

        txtnome.setText(String.valueOf(strusuario));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_primeiro) {

            Intent primeiroactivity = new Intent(getApplicationContext(),PrimeiroActivity.class);
            primeiroactivity.putExtra("usuario",strusuario);
            startActivity(primeiroactivity);
            finish();

            // Handle the camera action
        } else if (id == R.id.nav_produtos) {
            Intent cadprodutosactivity = new Intent(getApplicationContext(),CadProdutosActivity.class);
            cadprodutosactivity.putExtra("usuario",strusuario);
            startActivity(cadprodutosactivity);
            finish();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_sair) {

            new AlertDialog.Builder(BaseActivity.this).setTitle("Aviso")
                    .setMessage("Deseja realmente SAIR?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new DesconectarAsyncTask().execute(txtnome.getText().toString());

                        }
                    })

                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class DesconectarAsyncTask extends AsyncTask<String, String, JSONObject> {
        private Context mContext = getApplicationContext();
        private Activity mActivity = BaseActivity.this;
        String response = "";
        HashMap<String, String> postParametros;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Por favor aguarde ...");
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            postParametros = new HashMap<String, String>();
            postParametros.put("HTTP_ACCEPT", "application/json");
            postParametros.put("txtLogout", params[0]);


            HttpConnectionService service = new HttpConnectionService();
            response = service.sendRequest(apiPath, postParametros);
            try {
                success = 1;
                JSONObject resultJsonObject = new JSONObject(response);
                resultJsonArray = resultJsonObject.getJSONArray("RetornoDados");
                JSONObject jsonObj = null;
                for (int i = 0; i < resultJsonArray.length(); i++) {
                    jsonObj = resultJsonArray.getJSONObject(i);
                    logado = jsonObj.getInt("logado");

                }
            } catch (JSONException e) {
                success = 0;
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(JSONObject response) {
            super.onPostExecute(response);
           /* progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Por favor aguarde ...");
            progressDialog.setCancelable(false);
            progressDialog.show();*/

            if (logado != 5) {
                msg = "Problemas para sair do sistema,entre em contato com o Administrador.";


                new AlertDialog.Builder(BaseActivity.this).setTitle("Aviso")
                        .setMessage(msg)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            } else {
                finish();
            }

        }

    }
}


