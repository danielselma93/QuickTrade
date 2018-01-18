package com.example.damir.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.damir.quicktrade.model.Producto;
import com.example.damir.quicktrade.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsuariosProductos extends AppCompatActivity {

    Spinner spinnerUsuarios;
    ListView listViewProductos;
    Button buttonProductoNuevo;

    ArrayList<String> ArrayKeys = new ArrayList<String>();
    String keyUsuarioElegido;

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_productos);

        spinnerUsuarios = findViewById(R.id.spinnerUsuarios);
        listViewProductos = findViewById(R.id.listViewProductos);
        buttonProductoNuevo = findViewById(R.id.buttonProductoNuevo);

        dbReference = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoUsuarios));

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for(DataSnapshot datasnapshoIntern: dataSnapshot.getChildren()){
                    listado.add(datasnapshoIntern.getValue(Usuario.class).getUsuario());
                    ArrayKeys.add(datasnapshoIntern.getKey());
                }

                adaptador = new ArrayAdapter<String>(UsuariosProductos.this,android.R.layout.simple_list_item_1,listado);
                spinnerUsuarios.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spinnerUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                keyUsuarioElegido = ArrayKeys.get(i);

                DatabaseReference dbReference2 = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoProductosConcatenarKey)+keyUsuarioElegido);
                dbReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayAdapter<String> adaptador;
                        ArrayList<String> listado = new ArrayList<String>();

                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            listado.add(datasnapshot.getValue(Producto.class).toString());
                        }

                        adaptador = new ArrayAdapter<String>(UsuariosProductos.this,android.R.layout.simple_list_item_1,listado);
                        listViewProductos.setAdapter(adaptador);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
