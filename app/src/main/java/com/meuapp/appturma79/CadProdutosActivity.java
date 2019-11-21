package com.meuapp.appturma79;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CadProdutosActivity extends BaseActivity {

    private String apiPath = "http://10.0.2.2/siteturma79/produtos/incluir/";
    private ProgressDialog progressDialog;
    private JSONArray resultJsonArray;
    private ProgressBar progressBar = null;
    private int result = 0;
    Context context;
    private int success = 0;
    private  int logado = 0;
    int sucesso;
    private  String msg,strusuario="";

    EditText edtnomeprodutos,edtunidade,edtvalidade;
    Button btnnovoproduto,btnconsultarproduto,btnvoltar;



    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if(savedInstanceState == null) {
            savedInstanceState = new Bundle();
        }
        savedInstanceState.putBoolean("drawerBackButton", true);

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_cad_produtos, null, false);
        drawer.addView(contentView, 0);
        //      navigationView.setCheckedItem(R.id.nav_view);

        edtnomeprodutos = (EditText) findViewById(R.id.edtNomeProdutos);
        edtunidade = (EditText) findViewById(R.id.edtUnidade);
        edtvalidade = (EditText) findViewById(R.id.edtValidade);

        btnnovoproduto = (Button) findViewById(R.id.btnNovoProduto);
        btnconsultarproduto = (Button) findViewById(R.id.btnConsultarProduto);
        btnvoltar = (Button) findViewById(R.id.btnVoltar);

        btnconsultarproduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent consultaprodutos = new Intent(CadProdutosActivity.this,ConsultaProdutosActivity.class); //Muda de CadProdutos para a Consulta de Produtos
                consultaprodutos.putExtra("produto",edtnomeprodutos.getText().toString()); //Passa os parametros.
                startActivity(consultaprodutos);
            }
        });

        btnnovoproduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int intcodigo = 0;
                String strnome, strunidade, strvalidade;

                strnome = edtnomeprodutos.getText().toString();
                strunidade = edtunidade.getText().toString();
                strvalidade = "";

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
                SimpleDateFormat entradadata = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    strvalidade=sdf.format(entradadata.parse(edtvalidade.getText().toString()));

                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Data Inválida",Toast.LENGTH_SHORT).show();
                }

                ProdutosTable produtostable = new ProdutosTable(intcodigo,strnome,strunidade,strvalidade);

                new IncluirProdutosAsyncTask().execute(produtostable);

                }






        });


        /*txtnomeprimeiro = (TextView)findViewById(R.id.txtNomePrimeiro);

        Intent base  = getIntent();

        strusuario = base.getStringExtra("usuario");

        //Atribuir ao TextView o conteúdo da varíavel strusuario

        txtnome.setText(String.valueOf(strusuario));
*/

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

    protected class IncluirProdutosAsyncTask extends AsyncTask<ProdutosTable, Void,JSONObject> {

        String response = "";
        HashMap<String, String> postParametros;

        ArrayList<ProdutosTable> produtostable = null;


        @Override
        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected JSONObject doInBackground(ProdutosTable... params) {
            produtostable = new ArrayList<ProdutosTable>();

            postParametros = new HashMap<String, String>();
            postParametros.put("HTTP_ACCEPT", "application/json");
            postParametros.put("txtNomeProdutos", params[0].getNOME());
            postParametros.put("txtUnidade", params[0].getTIPO());
            postParametros.put("txtValidade", params[0].getVALIDADE());



            HttpConnectionService service = new HttpConnectionService();
            response = service.sendRequest(apiPath, postParametros);

            try {

                success = 1;
                JSONObject resultJsonObject = new JSONObject(response);
                resultJsonArray = resultJsonObject.toJSONArray(resultJsonObject.names());
                JSONObject jsonObj = null;
                for (int i = 0; i < resultJsonArray.length(); i++) {
                    jsonObj = resultJsonArray.getJSONObject(i);
                    sucesso = jsonObj.getInt("sucesso");

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
            // progressBar.setVisibility(View.INVISIBLE);

            switch(sucesso) {
                case 0:
                    msg = "Erro ao cadastrar produto!";
                    break;
                case 1:
                    msg = "Produto cadastrado com sucesso!";
                    break;

            }
            new AlertDialog.Builder(CadProdutosActivity.this).setTitle("Aviso")
                    .setMessage(msg)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }

}