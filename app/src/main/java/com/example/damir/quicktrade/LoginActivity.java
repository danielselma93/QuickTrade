package com.example.damir.quicktrade;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.damir.quicktrade.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsuario, editTextPassword, editTextNombre, editTextApellidos, editTextEmail, editTextDireccion;
    Button buttonLogin, buttonRegistrarse;

    Usuario nuevoUsuario;

    DatabaseReference dbReference;

    FirebaseAuth mAuth;
    static FirebaseUser usuarioFirebase;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidos = findViewById(R.id.editTextApellidos);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDireccion = findViewById(R.id.editTextDireccion);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegistrarse = findViewById(R.id.buttonRegistrarse);

        buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                String usuario = editTextUsuario.getText().toString();
                String nombre = editTextNombre.getText().toString();
                String apellidos = editTextApellidos.getText().toString();
                String direccion = editTextDireccion.getText().toString();

                if(!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellidos) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(direccion)) {

                    nuevoUsuario = new Usuario(usuario,email,nombre,apellidos,direccion);

                    dbReference = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoUsuarios));

                    Query q = dbReference.orderByChild(getString(R.string.campoUsuario)).equalTo(usuario);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                Toast.makeText(LoginActivity.this, "Este nombre de usuario ya esta elegido", Toast.LENGTH_SHORT).show();
                            }else{
                                mAuth = FirebaseAuth.getInstance();
                                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Se ha creado correctamente", Toast.LENGTH_SHORT).show();

                                            //Se guarda la UID
                                            usuarioFirebase = mAuth.getCurrentUser();
                                            String clave = usuarioFirebase.getUid();

                                            dbReference.child(clave).setValue(nuevoUsuario);
                                            Toast.makeText(LoginActivity.this, "Se ha creado el usuario", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                            LoginActivity.this.startActivity(intent);

                                        } else {
                                            Toast.makeText(LoginActivity.this, "NOP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(LoginActivity.this, "Falta por rellenar uno de los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Se iniciado correctamente", Toast.LENGTH_SHORT).show();
                                    usuarioFirebase = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    LoginActivity.this.startActivity(intent);

                                } else {
                                    Toast.makeText(LoginActivity.this, "NOP", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
