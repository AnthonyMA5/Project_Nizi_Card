package com.example.proyecto_tarjeta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Info_Movimientos> {

    Context context;
    List<Info_Movimientos> arraymovimientos;


    public ListAdapter(@NonNull Context context, List<Info_Movimientos> arraymovimientos) {
        super(context, R.layout.list_layout, arraymovimientos);
        this.context=context;
        this.arraymovimientos = arraymovimientos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, null, true);

        TextView tipo = view.findViewById(R.id.nameMov);
        TextView fecha = view.findViewById(R.id.dateinfoMov);
        TextView monto = view.findViewById(R.id.montoMov);

        tipo.setText(arraymovimientos.get(position).getTipo());
        fecha.setText(arraymovimientos.get(position).getFecha()+arraymovimientos.get(position).getHora());
        monto.setText(arraymovimientos.get(position).getMonto());
        return super.getView(position, convertView, parent);
    }
}
