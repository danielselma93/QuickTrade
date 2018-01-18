package com.example.damir.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.damir.quicktrade.model.Producto;
import com.example.damir.quicktrade.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductosPorCategoria extends AppCompatActivity {

    Spinner spinnerCategorias;
    ListView listViewProductos;

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_por_categoria);
        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        listViewProductos = findViewById(R.id.listViewProductos);

        final ArrayList<String> lista = new ArrayList<String>();
        lista.add("coches");
        lista.add("hogar");
        lista.add("tecnologia");
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(ProductosPorCategoria.this,android.R.layout.simple_list_item_1,lista);
        spinnerCategorias.setAdapter(adaptador);

        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String categoria = lista.get(i);

                switch (categoria) {
                    case "coches":
                        dbReference = FirebaseDatabase.getInstance().getReference("productos/productosCoches");
                        Query q = dbReference.orderByChild(getString(R.string.nodoCategoriaCoches));
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ArrayAdapter<String> adaptador;
                                ArrayList<String> listado = new ArrayList<String>();

                                for(DataSnapshot datasnapshoIntern: dataSnapshot.getChildren()){
                                    listado.add(datasnapshoIntern.getValue(Producto.class).toString());
                                }

                                adaptador = new ArrayAdapter<String>(ProductosPorCategoria.this,android.R.layout.simple_list_item_1,listado);
                                listViewProductos.setAdapter(adaptador);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "hogar":
                        dbReference = FirebaseDatabase.getInstance().getReference("productos/productosHogar");
                        Query q2 = dbReference.orderByChild(getString(R.string.nodoCategoriaCoches));
                        q2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ArrayAdapter<String> adaptador;
                                ArrayList<String> listado = new ArrayList<String>();

                                for(DataSnapshot datasnapshoIntern: dataSnapshot.getChildren()){
                                    listado.add(datasnapshoIntern.getValue(Producto.class).toString());
                                }

                                adaptador = new ArrayAdapter<String>(ProductosPorCategoria.this,android.R.layout.simple_list_item_1,listado);
                                listViewProductos.setAdapter(adaptador);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;
                    case "tecnologia":
                        dbReference = FirebaseDatabase.getInstance().getReference("productos/productosTecnologia");
                        Query q3 = dbReference.orderByChild(getString(R.string.nodoCategoriaCoches));
                        q3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ArrayAdapter<String> adaptador;
                                ArrayList<String> listado = new ArrayList<String>();

                                for(DataSnapshot datasnapshoIntern: dataSnapshot.getChildren()){
                                    listado.add(datasnapshoIntern.getValue(Producto.class).toString());
                                }

                                adaptador = new ArrayAdapter<String>(ProductosPorCategoria.this,android.R.layout.simple_list_item_1,listado);
                                listViewProductos.setAdapter(adaptador);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
