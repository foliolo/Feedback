package com.feedback.football.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.feedback.football.R;

import java.util.ArrayList;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity implements ParseURL.ParseUrlListener {
    private static final String TAG = "MainActivity";

    private AppCompatSpinner partidos;
    private SpinnerAdapter dataAdapter;
    private ArrayList<String> listaPartidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        partidos = (AppCompatSpinner) findViewById(R.id.form_partidos_jornada);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: Guardarlo en firebase y obtener los datos de ahí
        if (listaPartidos != null && listaPartidos.size() == 0) {
            (new ParseURL(this, this/*ParseUrlListener*/)).execute(URL);
        } else {
            dataAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listaPartidos);
        }
        partidos.setAdapter(dataAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("partidos", listaPartidos);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        listaPartidos = savedInstanceState.getStringArrayList("partidos");
    }

    @Override
    public void onParseUrlListener(ArrayList<String> listaPartidos) {
        this.listaPartidos = listaPartidos;
        if (listaPartidos != null && listaPartidos.size() > 0)
            dataAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listaPartidos);
        else
            dataAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Datos no cargados"});

        partidos.setAdapter(dataAdapter);
    }

    /**
     * Funcionalidad al pulsar la votación
     *
     * @param view
     */
    public void votingClick(View view) {
        Toast.makeText(this, "Se ha guardado su voto", Toast.LENGTH_SHORT).show();
    }
}

//Web Scraping
//https://jarroba.com/scraping-java-jsoup-ejemplos/