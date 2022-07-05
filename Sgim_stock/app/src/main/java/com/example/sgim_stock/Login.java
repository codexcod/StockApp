package com.example.sgim_stock;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    EditText txt_usuario,txt_constraseña;
    Button btn_iniciar;
    String Token ="";
    String Nombre ="";
    String Permiso ="";
    String ip ="";
    Integer numero_municipio =0;



    CheckBox btn_recordar;


    LinearLayout fondo;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fondo = (LinearLayout)findViewById(R.id.linearLayaout_login);
        fondo.setVisibility(View.GONE);

        btn_recordar=(CheckBox)findViewById(R.id.btn_recordar);
        txt_usuario=(EditText)findViewById(R.id.txt_usuario);
        txt_constraseña=(EditText)findViewById(R.id.txt_contrasena);
        btn_iniciar = (Button)findViewById(R.id.btn_iniciar_sesion);



        request = Volley.newRequestQueue(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},1);
            }
        }


        ConnectivityManager con = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            cargarPreferences();
        }else
        {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle("Conexion");
            builder.setMessage("Revise su conexion a internet");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.show();
        }

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

    }



    private void ejecutarServicio (String URL)
    {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(error.toString().equals("com.android.volley.ClientError")) {
            Snackbar.make(txt_constraseña.getRootView(), "El servidor no esta disponible", Snackbar.LENGTH_LONG).show();
        }else
        {
            Snackbar.make(txt_constraseña.getRootView(), "Revise su conexion", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json;

        json = response.optJSONArray("AutenticarResult");

        try {
            Token = json.getJSONObject(0).optString("Token");
            Nombre = json.getJSONObject(0).optString("Nombre");
            Permiso = json.getJSONObject(0).optString("Perfil");
            ip=json.getJSONObject(0).optString("destino");
            numero_municipio=json.getJSONObject(0).optInt("codigo_municipalidad");
        }
        catch(JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(this,"error de json",Toast.LENGTH_LONG).show();
        }

        if(!Token.equals(""))
        {

            if(btn_recordar.isChecked()) {
                guardarPreferences(txt_usuario.getText().toString(), txt_constraseña.getText().toString(), btn_recordar.isChecked());
            }
            Intent intent = new Intent (this,Main.class);
            intent.putExtra("ip",ip+":44123");
            intent.putExtra("municipio",numero_municipio);
            intent.putExtra("token",Token);
            intent.putExtra("usuario",Nombre);
            intent.putExtra("permiso",Permiso);
            startActivity(intent);

        }else
        {
            Snackbar.make(txt_constraseña.getRootView(),"Usuario o contraseña incorrectos", Snackbar.LENGTH_LONG).show();
        }

    }

    private  void cargarPreferences()
    {
        SharedPreferences preferences = getSharedPreferences("credenciales",Context.MODE_PRIVATE);

        btn_recordar.setChecked(preferences.getBoolean("recordar",false));

        if(preferences.getBoolean("recordar",false))
        {
            txt_usuario.setText(preferences.getString("usuario",""));
            txt_constraseña.setText(preferences.getString("contraseña",""));
            fondo.setVisibility(View.VISIBLE);
            iniciarSesion();
        }
    }

    private  void guardarPreferences(String usuario,String contraseña,Boolean recordar)
    {
        SharedPreferences preferences = getSharedPreferences("credenciales",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario",usuario);
        editor.putString("contraseña",contraseña);
        editor.putBoolean("recordar",recordar);

        editor.commit();
    }

    private void iniciarSesion()
    {
        if(!txt_usuario.getText().toString().equals("")) {
            if(!txt_constraseña.getText().toString().equals("")) {

                ConnectivityManager con = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = con.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    btn_iniciar.setText("Iniciando sesion");
                    ejecutarServicio("http://190.210.166.250:44123/SGIM_Service.svc/log/" + txt_usuario.getText().toString() + "/" + txt_constraseña.getText().toString());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txt_usuario.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(txt_constraseña.getWindowToken(), 0);
                }else {
                    Snackbar.make(txt_constraseña.getRootView(),"Revise su conexion a internet", Snackbar.LENGTH_LONG).show();
                }
            }else
            {
                Snackbar.make(txt_constraseña.getRootView(),"Inserte la contraseña", Snackbar.LENGTH_LONG).show();
            }
        }else
        {
            if(!txt_constraseña.getText().toString().equals("")) {
                Snackbar.make(txt_constraseña.getRootView(),"Inserte usuario y contraseña", Snackbar.LENGTH_LONG).show();
            }else
            {
                Snackbar.make(txt_constraseña.getRootView(),"Inserte un usuario", Snackbar.LENGTH_LONG).show();
            }
        }

    }

}