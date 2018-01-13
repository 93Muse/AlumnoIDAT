package muse.pe.com.alumnoidat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import muse.pe.com.alumnoidat.adapter.TareaAdapter;
import muse.pe.com.alumnoidat.model.TareaModel;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    ListView objLvTareas;
    ArrayList<TareaModel> listaTareas;
    TextView objTvId;
    private ProgressDialog pdialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AgregarActivity.class);
                startActivity(myIntent);
            }
        });

        listaTareas = new ArrayList<>();
        objLvTareas = findViewById(R.id.lv_tareas);
        request = Volley.newRequestQueue(MainActivity.this);
        listActuales();

        objLvTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TareaModel art = (TareaModel) objLvTareas.getItemAtPosition(i);
                String id = String.valueOf(art.getId());
                enviarId(id);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_history) {
            listAll();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        pdialog.hide();
        Toast.makeText(MainActivity.this,"No se encontraron registros",Toast.LENGTH_SHORT).show();
        Log.i("Error",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        pdialog.hide();
        TareaModel tareas;
        JSONArray jsonArray = response.optJSONArray("tareas");
        try {
            for(int i=0; i< jsonArray.length(); i++){
                tareas = new TareaModel();
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);

                tareas.setId(jsonObject.optInt("tarea_id"));
                tareas.setTitulo(jsonObject.optString("tarea_titulo"));
                tareas.setCurso(jsonObject.optString("tarea_curso"));
                tareas.setVencimiento("Vence: " +jsonObject.optString("tarea_vencimiento"));
                listaTareas.add(tareas);
            }

            TareaAdapter adapter = new TareaAdapter(MainActivity.this, listaTareas);
            objLvTareas.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void listAll() {
        pdialog = new ProgressDialog(MainActivity.this);
        pdialog.setMessage("Cargando tareas...");
        pdialog.show();
        String url="http://localhost:3000/tarea";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    private void listActuales(){
        pdialog = new ProgressDialog(MainActivity.this);
        pdialog.setMessage("Cargando tareas...");
        pdialog.show();
        String url="http://localhost:3000/tareas";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    public void enviarId(String id){
        Intent x = new Intent(this, DetalleActivity.class);
        x.putExtra("tarea_id", id);
        startActivity(x);
    }
}
