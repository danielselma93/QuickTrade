package com.example.damir.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damir.quicktrade.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModificarUsuarios extends AppCompatActivity {

    EditText editTextNombre, editTextApellidos, editTextEmail, editTextDireccion, editTextUsuario;
    TextView textViewUsuarioElegido;

    Button buttonModificarUsuario, buttonBuscarUsuario;
    String keyUsuario = "0";
    String usuario;

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuarios);

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        buttonModificarUsuario = findViewById(R.id.buttonModificar);
        //buttonBuscarUsuario = findViewById(R.id.buttonBuscar);
        textViewUsuarioElegido = findViewById(R.id.textViewUsuarioElegido);

        dbReference = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoUsuarios));

        //Recuperamos el usuario segun su uid
        Query q = dbReference.orderByKey().equalTo(LoginActivity.usuarioFirebase.getUid());

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(ModificarUsuarios.this, "No se encuetra este usuario", Toast.LENGTH_SHORT).show();
                } else {
                    Usuario usuarioModificar = dataSnapshot.getChildren().iterator().next().getValue(Usuario.class);

                    keyUsuario = dataSnapshot.getChildren().iterator().next().getKey();
                    editTextUsuario.setText(usuarioModificar.getUsuario());
                    editTextNombre.setText(usuarioModificar.getNombre());
                    editTextApellidos.setText(usuarioModificar.getApellidos());
                    editTextEmail.setText(usuarioModificar.getEmail());
                    editTextDireccion.setText(usuarioModificar.getDireccion());
                    textViewUsuarioElegido.setText("Usuario elegido: "+LoginActivity.usuarioFirebase.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonModificarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = editTextUsuario.getText().toString();
                String nombre = editTextNombre.getText().toString();
                String apellidos = editTextApellidos.getText().toString();
                final String email = editTextEmail.getText().toString();
                String direccion = editTextDireccion.getText().toString();

                if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellidos) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(direccion)) {
                    Query q = dbReference.orderByKey().equalTo(keyUsuario);

                    final Usuario usuarioModificado = new Usuario(usuario,email,nombre,apellidos,direccion);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                dbReference.child(keyUsuario).setValue(usuarioModificado);
                                LoginActivity.usuarioFirebase.updateEmail(email);
                                Toast.makeText(ModificarUsuarios.this, "Usuario modificado", Toast.LENGTH_SHORT).show();
                           }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                } else {
                    Toast.makeText(ModificarUsuarios.this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
                }

                }
        });

    }
}
