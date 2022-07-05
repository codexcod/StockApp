package com.example.sgim_stock;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;


public class Home extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    RecyclerView recyclerItems,recyclerBusquedas;

    View vista;

    NavController navController;

    ArrayList<Item> items;
    ArrayList<Item> items_2;

    AdapterItems adapter;
    AdaptadorBusqueda adapter_2;

    TextView txt_usuario;

    String permiso;

    TextView txt_nombre_item;



    LinearLayout lin_animacion;

    ImageView btn_volver_menu;



    public Home() {

    }


    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_home, container, false);





        btn_volver_menu = (ImageView)vista.findViewById(R.id.btn_volver_menu);
        btn_volver_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subir(900);
            }
        });




        items = new ArrayList<>();
        items_2 = new ArrayList<>();

        txt_nombre_item =(TextView)vista.findViewById(R.id.txt_nombre_item_home);

        lin_animacion =(LinearLayout) vista.findViewById(R.id.lin_animacion);


        permiso = getActivity().getIntent().getStringExtra("permiso");

        adapter = new AdapterItems(items);
        adapter_2 = new AdaptadorBusqueda(items_2);

        try {
            navController = NavHostFragment.findNavController(this);
        }catch (Exception e){
            Toast.makeText(getContext(),"" + e,Toast.LENGTH_LONG).show();
        }


        recyclerBusquedas = (RecyclerView)vista.findViewById(R.id.recyclerBusquedas);
        recyclerBusquedas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerItems= (RecyclerView) vista.findViewById(R.id.recyclerItems);
        recyclerItems.setLayoutManager(new GridLayoutManager(getContext(),2));



        llenarItems();
        txt_usuario = vista.findViewById(R.id.txt_usuario);
        txt_usuario.setText("¡Hola " + getActivity().getIntent().getStringExtra("usuario") + "!");


        return vista;
    }


    private void llenarItems() {

        Boolean cargarItems = true;

        if(permiso.substring(0,1).equals("1")) {
            items.add(new Item("Depositos", R.drawable.icono_cajas));
        }
        if(permiso.substring(2,3).equals("1")) {
            items.add(new Item("Articulos",R.drawable.icono_articulo));
        }
        if(permiso.substring(1,2).equals("1")) {
            items.add(new Item("Movimientos",R.drawable.icono_movimientos));
        }
        if(permiso.substring(3,4).equals("1")) {
            items.add(new Item("Tablero de control",R.drawable.icono_tablero));
        }


        adapter_2 = new AdaptadorBusqueda(items_2);

        adapter_2.SetOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        recyclerBusquedas.setAdapter(adapter_2);
        adapter = new AdapterItems(items);
        adapter.SetOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animarBajar(items.get(recyclerItems.getChildAdapterPosition(view)).getNombre());
            }
        });
        recyclerItems.setAdapter(adapter);

    }

    private void animarBajar(String item)
    {
        if(item.equals("Depositos"))
        {
            txt_nombre_item.setText(item);
            items_2.clear();
            adapter_2.notifyDataSetChanged();
            recyclerBusquedas.setAdapter(adapter_2);
            items_2.add(new Item("Buscar deposito por Nombre",R.drawable.buscar));
            recyclerBusquedas.setAdapter(adapter_2);
            bajar(1500);
        }
        if(item.equals("Articulos"))
        {
            txt_nombre_item.setText(item);
            items_2.clear();
            adapter_2.notifyDataSetChanged();
            recyclerBusquedas.setAdapter(adapter_2);
            items_2.add(new Item("Buscar articulos por nombre",R.drawable.buscar));
            recyclerBusquedas.setAdapter(adapter_2);
            bajar(1500);
        }
        if(item.equals("Movimientos"))
        {
            txt_nombre_item.setText(item);
            items_2.clear();
            adapter_2.notifyDataSetChanged();
            recyclerBusquedas.setAdapter(adapter_2);
            items_2.add(new Item("Buscar movimientos por fecha",R.drawable.buscar));

            recyclerBusquedas.setAdapter(adapter_2);
            bajar(1500);
        }
        if(item.equals("Tablero de control"))
        {
            txt_nombre_item.setText(item);
            items_2.clear();
            adapter_2.notifyDataSetChanged();
            recyclerBusquedas.setAdapter(adapter_2);
            items_2.add(new Item("Buscar por Patente",R.drawable.buscar));

            recyclerBusquedas.setAdapter(adapter_2);
            bajar(1500);
        }


    }
    private void bajar(long duracion)
    {
        ObjectAnimator animator;
        animator = ObjectAnimator.ofFloat(lin_animacion,"y",4000);
        animator.setDuration(duracion);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator);
        animatorSet.start();
    }

    private void subir(long duracion)
    {
        ObjectAnimator animator;
        animator = ObjectAnimator.ofFloat(lin_animacion,"y",275);
        animator.setDuration(duracion);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator);
        animatorSet.start();
    }

}