package muse.pe.com.alumnoidat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import muse.pe.com.alumnoidat.adapter.TareaAdapter;
import muse.pe.com.alumnoidat.model.TareaModel;

public class AgregarActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private int año, mes, dia, hora, minuto;
    private EditText campoTitulo;
    private EditText campoDescripcion;
    private EditText campoCurso;
    private EditText campoProfesor;
    private TextView campoFecha;
    private TextView campoHora;
    private ImageButton botonFecha;
    private ImageButton botonHora;
    private Button botonAgregar;
    private ProgressDialog pdialog;
    private static final int TIPO_DIALOGO = 0;
    private static DatePickerDialog.OnDateSetListener oyenteSelectorFecha;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String pfecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        campoTitulo = findViewById(R.id.et_agregar_titulo);
        campoDescripcion = findViewById(R.id.et_agregar_descripcion);
        campoCurso = findViewById(R.id.et_agregar_curso);
        campoProfesor = findViewById(R.id.et_agregar_profesor);
        campoFecha = findViewById(R.id.tv_agregar_fechaentrega);
        campoHora = findViewById(R.id.tv_agregar_horaentrega);

        botonFecha = findViewById(R.id.ib_agregar_fecha);
        botonFecha.setOnClickListener(this);

        botonHora = findViewById(R.id.ib_agregar_hora);
        botonHora.setOnClickListener(this);

        botonAgregar = findViewById(R.id.btn_agregar_agregar);
        botonAgregar.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        año = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH) +1;
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mostrarFecha();

        oyenteSelectorFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                año = i;
                mes = i1;
                dia = i2;
                mostrarFecha();
            }
        };
    }

    @Override
    public void onClick(View view) {
        if (view == botonFecha) {
            mostrarCalendario(view);
        } else if (view == botonHora) {
            final Calendar calendar = Calendar.getInstance();
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minuto = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    campoHora.setText(hour +":" +minute);
                }
            }, hora, minuto, true);
            timePickerDialog.show();
        } else if (view == botonAgregar) {
            if (validarCampos() == false) Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_LONG).show();
            else{
                request = Volley.newRequestQueue(AgregarActivity.this);
                agregarTarea();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        pdialog.hide();
        Toast.makeText(getApplicationContext(),"Hubo un error al ejecutar el comando... "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("Error",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        pdialog.hide();
        Toast.makeText(getApplicationContext(),"Se registró correctamente",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void agregarTarea() {
        pdialog= new ProgressDialog(AgregarActivity.this);
        pdialog.setMessage("Registrando...");
        pdialog.show();
        String url="http://10.144.114.116:3000/tarea";
        final JSONObject data = new JSONObject();

        try {
            data.put("tarea_titulo", campoTitulo.getText().toString());
            data.put("tarea_descripcion", campoDescripcion.getText().toString());
            data.put("tarea_curso", campoCurso.getText().toString());
            data.put("tarea_profesor", campoProfesor.getText().toString());
            data.put("tarea_vencimiento", pfecha +" " +campoHora.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,data,this,this);
        request.add(jsonObjectRequest);
    }

    private Boolean validarCampos(){

        if (campoTitulo.getText().toString().isEmpty() ||
                campoDescripcion.getText().toString().isEmpty() ||
                campoCurso.getText().toString().isEmpty() ||
                campoProfesor.getText().toString().isEmpty() ||
                campoFecha.getText().toString().isEmpty() ||
                campoHora.getText().toString().isEmpty())
        return false;
        else return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0: return new DatePickerDialog(this, oyenteSelectorFecha, año, mes, dia);
        }
        return null;
    }

    private void mostrarFecha(){
        campoFecha.setText(dia +"/" +(mes+1) +"/" +año);
        pfecha = año +"/" +(mes+1) +"/" +dia;
    }

    public void mostrarCalendario(View control){
        showDialog(TIPO_DIALOGO);
    }
}
