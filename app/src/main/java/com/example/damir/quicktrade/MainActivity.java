package com.example.damir.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.damir.quicktrade.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Objetos de la vista
    EditText editTextUsuario, editTextNombre, editTextApellidos, editTextEmail, editTextDireccion;
    Button buttonGuardarUsuario, buttonMostrarUsuarios, buttonModificarUsuario, buttonProductosPorUsuario, buttonProductoNuevo, buttonModificarBorrarProducto, buttonProductosPorCategoria;



    Usuario nuevoUsuario;

    //DBREference
    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se enlazan los objetos con la vista
        buttonMostrarUsuarios = findViewById(R.id.buttonMostrarUsuarios);
        buttonModificarUsuario = findViewById(R.id.buttonModificarUsuario);
        buttonProductosPorUsuario = findViewById(R.id.buttonProductosPorUsuario);
        buttonProductoNuevo = findViewById(R.id.buttonProductoNuevo);
        buttonModificarBorrarProducto = findViewById(R.id.buttonModificarBorrarProducto);
        buttonProductosPorCategoria = findViewById(R.id.buttonProductosPorCategoria);

        //Se instancia en el nodo de los usuarios
        //dbReference = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoUsuarios));

        //Listener del boton guardar usuarios
        /*buttonGuardarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se guardan los valores de los campos
                String usuario = editTextUsuario.getText().toString();
                String nombre = editTextNombre.getText().toString();
                String apellidos = editTextApellidos.getText().toString();
                String email = editTextEmail.getText().toString();
                String direccion = editTextDireccion.getText().toString();

                if(!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellidos) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(direccion)) {

                    nuevoUsuario = new Usuario(usuario,email,nombre,apellidos,direccion);

                    Query q = dbReference.orderByChild(getString(R.string.campoUsuario)).equalTo(usuario);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                Toast.makeText(MainActivity.this, "Este nombre de usuario ya esta elegido", Toast.LENGTH_SHORT).show();
                            }else{
                                String clave = dbReference.push().getKey();
                                dbReference.child(clave).setValue(nuevoUsuario);
                                Toast.makeText(MainActivity.this, "Se ha creado el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(MainActivity.this, "Falta por rellenar uno de los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

        buttonMostrarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MostrarUsuarios.class);
                MainActivity.this.startActivity(intent);
            }
        });

        buttonModificarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ModificarUsuarios.class);
                MainActivity.this.startActivity(intent);
            }
        });

        buttonProductosPorUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UsuariosProductos.class);
                MainActivity.this.startActivity(intent);
            }
        });

        buttonProductoNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DarAltaProducto.class);
                MainActivity.this.startActivity(intent);
            }
        });

        buttonModificarBorrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ModificarBorrarProducto.class);
                MainActivity.this.startActivity(intent);
            }
        });

        buttonProductosPorCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProductosPorCategoria.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }
}
