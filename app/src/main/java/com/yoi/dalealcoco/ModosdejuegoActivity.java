package com.yoi.dalealcoco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ModosdejuegoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modosdejuego); // Establece el layout de la actividad

        // Inicia la música de fondo del juego
        Intent intent = new Intent(ModosdejuegoActivity.this, MusicaGlobal.class);
        intent.putExtra(MusicaGlobal.EXTRA_MUSIC_RESOURCE, R.raw.wallemodosjuego);
        startService(intent);

        // Configura el BottomNavigationView para manejar las selecciones del menú
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId(); // Obtiene el ID del item seleccionado

                // Maneja la acción de cada opción del menú de navegación
                if (id == R.id.nav_inicio) {
                    Intent homeIntent = new Intent(ModosdejuegoActivity.this, MainActivity.class);
                    startActivity(homeIntent); // Inicia la actividad
                    return true;
                } else if (id == R.id.nav_juegoRapido) {
                    Intent profileIntent = new Intent(ModosdejuegoActivity.this, JuegoActivity.class);
                    startActivity(profileIntent);
                    return true;
                } else if (id == R.id.nav_comoJugar) {
                    Intent playIntent = new Intent(ModosdejuegoActivity.this, ComojugarActivity.class);
                    startActivity(playIntent);
                    return true;
                }

                return false; // Si no se selecciona ninguna opción válida
            }
        });
    }

    // Método que se llama cuando el usuario selecciona "Juego rápido"
    public void juegoRapido(View view){
        // Inicia la actividad de juego rápido
        Intent nuevaVentana = new Intent(this, JuegoActivity.class);
        startActivity(nuevaVentana);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detén el servicio de música cuando la actividad se destruya (cuando el usuario cierre la aplicación)
        Intent intent = new Intent(ModosdejuegoActivity.this, MusicaGlobal.class);
        stopService(intent); // Detén el servicio de música
    }

    // Aspectos adicionales
    @Override
    protected void onPause() {
        super.onPause();
        // Detén la música cuando la actividad pase a segundo plano
        Intent intent = new Intent(ModosdejuegoActivity.this, MusicaGlobal.class);
        stopService(intent); // O puedes pausar en lugar de detener
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Detén la música cuando la actividad pase a segundo plano
        Intent intent = new Intent(ModosdejuegoActivity.this, MusicaGlobal.class);
        intent.putExtra(MusicaGlobal.EXTRA_MUSIC_RESOURCE, R.raw.wallemodosjuego);
        startService(intent);
    }
}
