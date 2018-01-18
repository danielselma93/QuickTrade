package com.example.damir.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.damir.quicktrade.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MostrarUsuarios extends AppCompatActivity {

    Button buttonVolver;
    ListView listViewUsusarios;

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_usuarios);

        listViewUsusarios = (ListView)findViewById(R.id.listViewUsusarios);

        dbReference = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoUsuarios));

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    listado.add(datasnapshot.getValue(Usuario.class).getUsuario());
                }

                adaptador = new ArrayAdapter<String>(MostrarUsuarios.this,android.R.layout.simple_list_item_1,listado);
                listViewUsusarios.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
