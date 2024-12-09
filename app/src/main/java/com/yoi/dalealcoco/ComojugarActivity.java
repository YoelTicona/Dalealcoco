package com.yoi.dalealcoco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// Clase para la pantalla "Cómo jugar"
public class ComojugarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establece el diseño de la actividad "Cómo jugar"
        setContentView(R.layout.activity_comojugar);

        // Configuración del BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_inicio) {
                    Intent homeIntent = new Intent(ComojugarActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (id == R.id.nav_juegoRapido) {
                    Intent profileIntent = new Intent(ComojugarActivity.this, JuegoActivity.class);
                    startActivity(profileIntent);
                    return true;
                } else if (id == R.id.nav_comoJugar) {
                    Intent playIntent = new Intent(ComojugarActivity.this, ComojugarActivity.class);
                    startActivity(playIntent);
                    return true;
                }
                return false;
            }
        });
    }

    // Método para regresar a la pantalla principal
    public void regresar(View view) {
        // Crea un intento para abrir la actividad principal
        Intent nuevaVentana = new Intent(this, MainActivity.class);
        startActivity(nuevaVentana); // Inicia la actividad principal
    }

}
