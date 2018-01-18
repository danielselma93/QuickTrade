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
import android.widget.Toast;

import com.example.damir.quicktrade.model.Producto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModificarBorrarProducto extends AppCompatActivity {

    Spinner spinnerProducto;
    EditText editTextNombre, editTextDescripcion, editTextPrecio;
    RadioGroup radioGroupTipo;
    Button buttonModificarProducto ,buttonBorrarProducto;

    ArrayAdapter<String> adaptador;
    ArrayList<String> listado = new ArrayList<String>();

    ArrayList<String> keysProductos = new ArrayList<String>();
    String keySeleccionada;

    ArrayList<Producto> productos = new ArrayList<Producto>();

    DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_borrar_producto);

        spinnerProducto = findViewById(R.id.spinnerProducto);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        buttonModificarProducto = findViewById(R.id.buttonModificarProducto);
        buttonBorrarProducto = findViewById(R.id.buttonBorrarProducto);

        dbReference = FirebaseDatabase.getInstance().getReference((getString(R.string.nodoProductosConcatenarKey))+LoginActivity.usuarioFirebase.getUid());

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                keysProductos = new ArrayList<String>();
                productos = new ArrayList<Producto>();
                listado = new ArrayList<String>();

                for (DataSnapshot dataSnapshotThis: dataSnapshot.getChildren()) {
                    keysProductos.add(dataSnapshotThis.getKey());
                    Producto p = dataSnapshotThis.getValue(Producto.class);
                    productos.add(p);
                    listado.add(p.getNombre());
                }

                adaptador = new ArrayAdapter<String>(ModificarBorrarProducto.this,android.R.layout.simple_list_item_1,listado);
                spinnerProducto.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spinnerProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                keySeleccionada = keysProductos.get(i);

                Producto productoSeleccionado = productos.get(i);

                editTextNombre.setText(productoSeleccionado.getNombre());
                editTextDescripcion.setText(productoSeleccionado.getDescripcion());
                editTextPrecio.setText(""+productoSeleccionado.getPrecio());
                switch (productoSeleccionado.getCategoria()) {
                    case "coches":
                        radioGroupTipo.check(R.id.radioButtonCoches);
                        break;
                    case "hogar":
                        radioGroupTipo.check(R.id.radioButtonHogar);
                        break;
                    case "tecnologia":
                        radioGroupTipo.check(R.id.radioButtonTecnologia);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonModificarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editTextNombre.getText().toString();
                String descripcion = editTextDescripcion.getText().toString();
                String precio = editTextPrecio.getText().toString();
                String categoria = ((RadioButton)findViewById(radioGroupTipo.getCheckedRadioButtonId())).getText().toString();

                if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(descripcion) && !TextUtils.isEmpty(categoria) ) {
                    Producto productoModificado = new Producto(nombre,descripcion,categoria,Float.parseFloat(precio));

                    DatabaseReference dbReferenceCategoria = null;

                    dbReferenceCategoria = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoProductos));

                    switch (categoria) {
                        case "tecnologia":
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaTecnologia)).child(keySeleccionada).removeValue();
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaCoches)).child(keySeleccionada).removeValue();
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaHogar)).child(keySeleccionada).removeValue();

                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaTecnologia)).child(keySeleccionada).setValue(productoModificado);
                            dbReference.child(keySeleccionada).removeValue();
                            dbReference.child(keySeleccionada).setValue(productoModificado);
                            break;
                        case "coches":
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaTecnologia)).child(keySeleccionada).removeValue();
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaCoches)).child(keySeleccionada).removeValue();
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaHogar)).child(keySeleccionada).removeValue();

                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaCoches)).child(keySeleccionada).setValue(productoModificado);
                            dbReference.child(keySeleccionada).removeValue();
                            dbReference.child(keySeleccionada).setValue(productoModificado);
                            break;
                        case "hogar":
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaTecnologia)).child(keySeleccionada).removeValue();
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaCoches)).child(keySeleccionada).removeValue();
                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaHogar)).child(keySeleccionada).removeValue();

                            dbReferenceCategoria.child(getString(R.string.nodoCategoriaHogar)).child(keySeleccionada).setValue(productoModificado);
                            dbReference.child(keySeleccionada).removeValue();
                            dbReference.child(keySeleccionada).setValue(productoModificado);
                            break;
                    }

                    Toast.makeText(ModificarBorrarProducto.this, "Se ha creado el Producto", Toast.LENGTH_SHORT).show();

                }
            }
        });

        buttonBorrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = ((RadioButton)findViewById(radioGroupTipo.getCheckedRadioButtonId())).getText().toString();

                DatabaseReference dbReferenceCategoria = null;

                dbReferenceCategoria = FirebaseDatabase.getInstance().getReference(getString(R.string.nodoProductos));

                switch (categoria) {
                    case "tecnologia":
                        dbReferenceCategoria.child(getString(R.string.nodoCategoriaTecnologia)).child(keySeleccionada).removeValue();
                        break;
                    case "coches":
                        dbReferenceCategoria.child(getString(R.string.nodoCategoriaCoches)).child(keySeleccionada).removeValue();
                        break;
                    case "hogar":
                        dbReferenceCategoria.child(getString(R.string.nodoCategoriaHogar)).child(keySeleccionada).removeValue();
                        break;
                }
            }
        });

    }

}
