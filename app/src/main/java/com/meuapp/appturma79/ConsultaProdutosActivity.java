package com.meuapp.appturma79;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsultaProdutosActivity extends BaseActivity {

    private String apiPath = "http://10.0.2.2/siteturma79/produtos/consultar/";
    private ProgressDialog progressDialog;
    private JSONArray resultJsonArray;
    private ProgressBar progressBar = null;
    private int result = 0;
    Context context;
    private int success = 0;
    private  int logado = 0;
    int sucesso;

    String strproduto;
    private ArrayAdapter listViewAdapter;
    private ArrayList<String> arrayList;
    private ListView listv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if(savedInstanceState == null) {
            savedInstanceState = new Bundle();
        }
        savedInstanceState.putBoolean("drawerBackButton", true);

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_consulta_produtos, null, false);
        drawer.addView(contentView, 0);
        //      navigationView.setCheckedItem(R.id.nav_view);

        Intent cadprodutos = getIntent();
        strproduto = cadprodutos.getStringExtra("produto");

        context = getApplicationContext();

        arrayList = new ArrayList<String>();
        listv = (ListView) findViewById(R.id.produtoslist);

        listViewAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);

        listv.setAdapter(listViewAdapter);


        new ListarProdutosAsyncTask().execute(strproduto);


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

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                super.onBackPressed();
            }
        }
    }

    private class ListarProdutosAsyncTask extends AsyncTask<String, JSONObject, ArrayList<ProdutosTable>> {

        String response = "";
        HashMap<String, String> postParametros;

        ArrayList<ProdutosTable> produtostable = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Por favor aguarde ...");
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        }


        @Override
        protected ArrayList<ProdutosTable> doInBackground(String... params){
            produtostable = new ArrayList<ProdutosTable>();

            postParametros = new HashMap<String, String>();
            postParametros.put("HTTP_ACCEPT", "application/json");
            postParametros.put("txtLista", params[0].toString());


            HttpConnectionService service = new HttpConnectionService();
            response = service.sendRequest(apiPath, postParametros);

            try {

                success = 1;
                JSONObject resultJsonObject =  new JSONObject(response);
                resultJsonArray = resultJsonObject.getJSONArray("RetornoDados");
                JSONObject jsonObj = null;
                for (int i = 0; i < resultJsonArray.length(); i++) {
                    jsonObj = resultJsonArray.getJSONObject(i);
                    produtostable.add(new ProdutosTable(jsonObj.getInt("CODIGO_PRODUTO"), jsonObj.getString("NOME"), jsonObj.getString("TIPO"), jsonObj.getString("VALIDADE")));

                }
            } catch (JSONException e) {
                success = 0;
                e.printStackTrace();
            }

            return produtostable;
        }

        @Override
        protected void onPostExecute(ArrayList<ProdutosTable> produtostable) {
            super.onPostExecute(produtostable);
            //  progressBar.setVisibility(View.INVISIBLE);

         /*   if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }*/

            if (success == 1) {
                if (null != resultJsonArray) {
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject = resultJsonArray.getJSONObject(i);
                            arrayList.add(produtostable.get(i).getNOME());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listViewAdapter.notifyDataSetChanged();
                }
            }





        }
    }
}
