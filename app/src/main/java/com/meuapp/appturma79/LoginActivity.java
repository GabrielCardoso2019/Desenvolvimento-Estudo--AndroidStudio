package com.meuapp.appturma79;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private String apiPath = "http://10.0.2.2/siteturma79/usuarios/lista/";
    private ProgressDialog progressDialog;
    private JSONArray resultJsonArray;
    private ProgressBar progressBar = null;
    private int result = 0;
    Context context ;
    private int success = 0;
    private int logado = 0;
    private String msg,strusuario = "";
    EditText edtusuario,edtsenha;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtusuario =(EditText) findViewById(R.id.edtUsuario);
        edtsenha =(EditText) findViewById(R.id.edtSenha);
        btnlogin = (Button) findViewById(R.id.btnLogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strUsuario, strSenha;

                strUsuario = edtusuario.getText().toString();
                strSenha = edtsenha.getText().toString();

                int codigousuario,logado;

                codigousuario =0;
                logado=0;

                UsuariosTable usuariosTable =
                        new UsuariosTable(codigousuario,strUsuario,strSenha,logado);


                new VerificarUsuariosAsyncTask().execute(usuariosTable);

            }
        });
    }


    protected class VerificarUsuariosAsyncTask extends AsyncTask<UsuariosTable, Void,JSONObject> {

        String response = "";
        HashMap<String, String> postParametros;

        ArrayList<UsuariosTable> usuariostable = null;


        @Override
        protected void onPreExecute() {
        //    progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected JSONObject doInBackground(UsuariosTable... params) {
            usuariostable = new ArrayList<UsuariosTable>();

            postParametros = new HashMap<String, String>();
            postParametros.put("HTTP_ACCEPT", "application/json");
            postParametros.put("txtUsuario", params[0].getNomeUsuario());
            postParametros.put("txtSenha", params[0].getSenhaUsuario());



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
                    strusuario = params[0].getNomeUsuario();

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
            //progressBar.setVisibility(View.INVISIBLE);
            switch(logado) {
                case 2:
                    msg = "Usuário ou senha incorretos";
                    break;
                case 3:
                    msg = "Usuário já esta conectado";
                    break;
                case 4:
                    msg = "Bem vindo ao Sistema";
                    break;
            }

            new AlertDialog.Builder(LoginActivity.this).setTitle("Aviso")
                    .setMessage(msg)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (logado == 4) {

                                Intent base = new Intent(LoginActivity.this,
                                        BaseActivity.class);
                                base.putExtra("usuario",strusuario);


                                startActivity (base);
                                finish();
                            }
                        }
                    })
                    .show();



        }
    }
}
