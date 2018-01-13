package muse.pe.com.alumnoidat;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import muse.pe.com.alumnoidat.model.TareaModel;

public class DetalleActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    ArrayList<TareaModel> listaTareas;
    private ProgressDialog pdialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    TextView objTvID, objTvTITULO, objTvDESCRIPCION, objTvCURSO, objTvPROFESOR, objTvVENCIMIENTO;
    ToggleButton objTbESTADO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        objTvID = findViewById(R.id.tv_detalle_id);
        objTvTITULO = findViewById(R.id.et_detalle_titulo);
        objTvDESCRIPCION = findViewById(R.id.et_detalle_descripcion);
        objTvCURSO = findViewById(R.id.et_detalle_curso);
        objTvPROFESOR = findViewById(R.id.et_detalle_profesor);
        objTvVENCIMIENTO = findViewById(R.id.et_detalle_fechaentrega);
        objTbESTADO = findViewById(R.id.tb_detalle_estado);

        String tarea_id = getIntent().getStringExtra("tarea_id");
        objTvID.setText(tarea_id);
        request = Volley.newRequestQueue(DetalleActivity.this);
        showDetalle(tarea_id);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        pdialog.hide();
        Toast.makeText(DetalleActivity.this,"No se encontró el registro",Toast.LENGTH_SHORT).show();
        Log.i("Error",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        TareaModel tareas;
        JSONArray jsonArray = response.optJSONArray("tareas");
        try {
            for(int i=0; i< jsonArray.length(); i++){
                tareas = new TareaModel();
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);

                objTvTITULO.setText(jsonObject.getString("tarea_titulo"));
                objTvDESCRIPCION.setText(jsonObject.getString("tarea_descripcion"));
                objTvCURSO.setText(jsonObject.getString("tarea_curso"));
                objTvPROFESOR.setText(jsonObject.getString("tarea_profesor"));
                objTvVENCIMIENTO.setText(jsonObject.getString("tarea_vencimiento"));

                if (jsonObject.getString("tarea_estado").equals("1")) {
                    objTbESTADO.setChecked(false);
                } else {
                    objTbESTADO.setChecked(true);
                }

                listaTareas.add(tareas);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showDetalle(String id) {
        String url="http://10.144.114.116:3000/tarea" + "/" +id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }
}
