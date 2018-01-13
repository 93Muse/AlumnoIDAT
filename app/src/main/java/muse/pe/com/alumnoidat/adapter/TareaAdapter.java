package muse.pe.com.alumnoidat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import muse.pe.com.alumnoidat.R;
import muse.pe.com.alumnoidat.model.TareaModel;

public class TareaAdapter extends BaseAdapter{

    static Context context;
    public ArrayList<TareaModel> listaTareas;

    RequestQueue request;


    public TareaAdapter(Context context, ArrayList<TareaModel> listaTareas) {
        this.context = context;
        this.listaTareas = listaTareas;
        request = Volley.newRequestQueue(context);
    }

    @Override
    public int getCount() {
        return listaTareas.size();
    }

    @Override
    public Object getItem(int i) {
        return listaTareas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaTareas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista = view;
        LayoutInflater inflate = LayoutInflater.from(context);
        vista = inflate.inflate(R.layout.adapter_tareas, null);

        TextView TAREA_ID;
        TextView TAREA_TITULO;
        TextView TAREA_DESCRIPCION;
        TextView TAREA_CURSO;
        TextView TAREA_PROFESOR;
        TextView TAREA_VENCIMIENTO;

        TAREA_ID = vista.findViewById(R.id.tv_id);
        TAREA_TITULO = vista.findViewById(R.id.tv_titulo);
        TAREA_CURSO = vista.findViewById(R.id.tv_curso);
        TAREA_VENCIMIENTO = vista.findViewById(R.id.tv_vencimiento);

        TAREA_ID.setText(String.valueOf(listaTareas.get(i).getId()));
        TAREA_TITULO.setText(listaTareas.get(i).getTitulo().toString());
        TAREA_CURSO.setText(listaTareas.get(i).getCurso().toString());
        TAREA_VENCIMIENTO.setText(listaTareas.get(i).getVencimiento().toString());

        return vista;
    }
}
