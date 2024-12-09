package com.yoi.dalealcoco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResultadosActivity extends Activity {

    // Método de creación de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        // Obtiene los resultados pasados a través de un Intent y los muestra en un TextView
        Intent param = getIntent();
        String resultados = param.getStringExtra("resultados");
        ((TextView)findViewById(R.id.textViewResultados)).setText(resultados);

        // Inicia un servicio para reproducir música de fondo (jazz)
        Intent intent = new Intent(ResultadosActivity.this, MusicaGlobal.class);
        intent.putExtra(MusicaGlobal.EXTRA_MUSIC_RESOURCE, R.raw.jazzblueresultados);
        startService(intent);

        // Configura el listener del BottomNavigationView para la navegación
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_inicio) {
                    Intent homeIntent = new Intent(ResultadosActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                }
                else if (id == R.id.nav_juegoRapido) {
                    Intent profileIntent = new Intent(ResultadosActivity.this, JuegoActivity.class);
                    startActivity(profileIntent);
                    return true;
                }
                else if (id == R.id.nav_comoJugar) {
                    Intent playIntent = new Intent(ResultadosActivity.this, ComojugarActivity.class);
                    startActivity(playIntent);
                    return true;
                }

                return false;
            }
        });
    }

    // Método que regresa a la pantalla principal cuando se hace clic en un botón
    public void regresar(View view){
        Intent nuevaVentana = new Intent(this, MainActivity.class);
        startActivity(nuevaVentana);
    }

    // Ajustes adicionles
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detén el servicio de música cuando la actividad se destruya (cuando el usuario cierre la aplicación)
        Intent intent = new Intent(ResultadosActivity.this, MusicaGlobal.class);
        stopService(intent); // Detén el servicio de música
    }

    // Aspectos adicionales
    @Override
    protected void onPause() {
        super.onPause();
        // Detén la música cuando la actividad pase a segundo plano
        Intent intent = new Intent(ResultadosActivity.this, MusicaGlobal.class);
        stopService(intent); // O puedes pausar en lugar de detener
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Detén la música cuando la actividad pase a segundo plano
        Intent intent = new Intent(ResultadosActivity.this, MusicaGlobal.class);
        intent.putExtra(MusicaGlobal.EXTRA_MUSIC_RESOURCE, R.raw.jazzblueresultados);
        startService(intent);
    }
}
