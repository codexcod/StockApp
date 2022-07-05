package com.example.sgim_stock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterItems extends RecyclerView.Adapter<AdapterItems.ViewHolderItems> implements View.OnClickListener {


    ArrayList<Item> listaItems;
    private View.OnClickListener listener;

    public AdapterItems(ArrayList<Item> listaItems) {
        this.listaItems = listaItems;
    }

    @NonNull
    @Override
    public ViewHolderItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_1,null,false);

        view.setOnClickListener(this);
        return new ViewHolderItems(view);
    }

    public void SetOnClickListener(View.OnClickListener listener){
        this.listener = listener;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItems holder, int position) {

        Item item = listaItems.get(position);
        holder.txt_nombre.setText(item.getNombre());
        holder.foto.setImageResource(item.getSrc());


    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null)
        {
            listener.onClick(view);
        }
    }

    public class ViewHolderItems extends RecyclerView.ViewHolder {

        TextView txt_nombre;
        ImageView foto;

        public ViewHolderItems(@NonNull View itemView) {
            super(itemView);

            txt_nombre=(TextView)itemView.findViewById(R.id.txt_nombre_item);
            foto =(ImageView)itemView.findViewById(R.id.img_mostrar_item);



        }
    }


}
