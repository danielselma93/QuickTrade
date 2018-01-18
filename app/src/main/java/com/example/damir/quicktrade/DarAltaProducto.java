package com.example.damir.quicktrade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damir.quicktrade.model.Producto;
import com.example.damir.quicktrade.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DarAltaProducto extends AppCompatActivity {

    //Spinner spinnerUsuarios;
    EditText editTextNombreProducto, editTextDescripcion, editTextPrecio;
    RadioGroup radioGroupTipo;
    Button buttonAddProducto;
    TextView textViewUsuarioLogueado;

    ArrayList<String> ArrayKeys = new ArrayList<String>();
    String keyUsuarioElegido;

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_alta_producto);

        //spinnerUsuarios = findViewById(R.id.spinnerUsuariosAltaProductos);
        editTextNombreProducto = findViewById(R.id.editTextNombreProducto);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        buttonAddProducto = findViewById(R.id.buttonAddProducto);
        textViewUsuarioLogueado = findViewById(R.id.textViewUsuarioLogueado);

        textViewUsuarioLogueado.setText("Usuario: "+LoginActivity.usuarioFirebase.getEmail());

        dbReference = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoUsuarios));

        //Relleno de usuarios al spinner
        /*dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for(DataSnapshot datasnapshoIntern: dataSnapshot.getChildren()){
                    listado.add(datasnapshoIntern.getValue(Usuario.class).getUsuario());
                    ArrayKeys.add(datasnapshoIntern.getKey());
                }

                adaptador = new ArrayAdapter<String>(DarAltaProducto.this,android.R.layout.simple_list_item_1,listado);
                spinnerUsuarios.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Listener en el spinner
        spinnerUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Usuario Elegido
                keyUsuarioElegido = ArrayKeys.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        buttonAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editTextNombreProducto.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();
                String categoria = ((RadioButton)findViewById(radioGroupTipo.getCheckedRadioButtonId())).getText().toString();
                String precio = editTextPrecio.getText().toString();

                if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(categoria) && !TextUtils.isEmpty(precio)) {
                    Producto productoNuevo = new Producto(nombre, descripcion, categoria, Float.parseFloat(precio));

                    DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoProductosConcatenarKey)+keyUsuarioElegido);

                    //Unica para cada producto
                    String keyProducto = dbReference.push().getKey();

                    dbReference.child(keyProducto).setValue(productoNuevo);

                    DatabaseReference dbReferenceCategoria = null;

                    dbReferenceCategoria = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoProductos));

                    switch (categoria) {
                        case "tecnologia":
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaTecnologia)).child(keyProducto).setValue(productoNuevo);
                            break;
                        case "coches":
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaCoches)).child(keyProducto).setValue(productoNuevo);
                            break;
                        case "hogar":
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaHogar)).child(keyProducto).setValue(productoNuevo);
                            break;
                    }

                    Toast.makeText(DarAltaProducto.this, "Se ha creado el Producto", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DarAltaProducto.this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
