package com.santidev.databaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Referencias a los botones y edit text de la UI
    Button btnInsert, btnDelete, btnSearch, btnSelect;
    EditText editName, editAge, editDelete, editSearch;

    //Referencia a nuestro DataManeger para llamar a los diferentes metodos segun el boton pulsado
    DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DataManager(this);

        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSelect = (Button) findViewById(R.id.btn_select);

        editName = (EditText) findViewById(R.id.edit_name);
        editAge = (EditText) findViewById(R.id.edit_age);
        editDelete = (EditText) findViewById(R.id.edit_delete);
        editSearch = (EditText) findViewById(R.id.edit_search);

        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnSelect.setOnClickListener(this);

    }

    //metodo que desde el cursor nos recorrera toda la informacion que queremos mostrar...
    public void showData(Cursor cursor){
        while (cursor.moveToNext()){
            Log.i(cursor.getString(1), cursor.getString(2));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_insert:
                //aqui podemos filtar antes de llamar a DM, la informacion que el usuario ha metido en las edit text
                dm.insert(editName.getText().toString(), editAge.getText().toString());
                break;
            case R.id.btn_delete:
                dm.delete(editDelete.getText().toString());
                break;
            case R.id.btn_search:
                showData(dm.search(editSearch.getText().toString()));
                break;
            case R.id.btn_select:
                showData(dm.selectAll());
                break;
        }
    }
}