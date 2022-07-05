package com.example.sgim_stock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorBusqueda extends RecyclerView.Adapter<AdaptadorBusqueda.ViewHolderItems_2> implements View.OnClickListener {


    ArrayList<Item> listaItems;
    private View.OnClickListener listener;

    public AdaptadorBusqueda(ArrayList<Item> listaItems) {
        this.listaItems = listaItems;
    }
    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ViewHolderItems_2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_2, null, false);

        view.setOnClickListener(this);
        return new ViewHolderItems_2(view);
    }

    public void SetOnClickListener(View.OnClickListener listener) {
        this.listener = listener;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItems_2 holder, int position) {

        Item item = listaItems.get(position);
        holder.txt_nombre.setText(item.getNombre());
        holder.foto.setImageResource(item.getSrc());
    }


    @Override
    public int getItemCount() {
        return listaItems.size();
    }


    public class ViewHolderItems_2 extends RecyclerView.ViewHolder {
        TextView txt_nombre;
        ImageView foto;

        public ViewHolderItems_2(@NonNull View itemView) {
            super(itemView);

            txt_nombre = (TextView) itemView.findViewById(R.id.txt_nombre_item_2);
            foto = (ImageView) itemView.findViewById(R.id.img_mostrar_item_2);


        }
    }
}
