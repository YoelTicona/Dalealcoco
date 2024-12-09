package com.yoi.dalealcoco;

import com.yoi.dalealcoco.R.*;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public MusicaGlobal musica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilitar ajuste de pantalla completa (Edge to Edge)
        EdgeToEdge.enable(this);
        setContentView(layout.activity_main);

        // Ajustar los márgenes para los elementos del sistema (barras de estado y navegación)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ============== CONFIGURACIÓN DE MUSICA ==============
        // Inicia el servicio para la música
        musica = new MusicaGlobal();
        Intent intent = new Intent(this, musica.getClass());
        startService(intent);

        // =======================================
        // Configuración de animación para una imagen
        ImageView imagen = (ImageView) findViewById(id.imageView2);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imagen, "translationY", -40f, 40f);
        animator.setDuration(1000); // Duración de la animación (1 segundo)
        animator.setRepeatCount(ObjectAnimator.INFINITE); // Repite indefinidamente
        animator.setRepeatMode(ObjectAnimator.REVERSE); // Animación en ida y vuelta
        animator.start(); // Inicia la animación

        // =======================================
        // Configuración del BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_inicio) {
                    Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    return true;
                } else if (id == R.id.nav_juegoRapido) {
                    Intent profileIntent = new Intent(MainActivity.this, JuegoActivity.class);
                    startActivity(profileIntent);
                    return true;
                } else if (id == R.id.nav_comoJugar) {
                    Intent playIntent = new Intent(MainActivity.this, ComojugarActivity.class);
                    startActivity(playIntent);
                    return true;
                }
                return false;
            }
        });
    }

    // Método para iniciar la actividad "Cómo jugar"
    public void comoJugar(View view) {
        Intent nuevaVentana = new Intent(this, ComojugarActivity.class);
        startActivity(nuevaVentana);
    }

    // Método para iniciar la actividad "Modos de juego"
    public void jugar(View view) {
        Intent nuevaVentana = new Intent(this, ModosdejuegoActivity.class);
        startActivity(nuevaVentana);
    }

    // Método para salir de la aplicación
    public void salir(View view) {
        // Detén el servicio de música cuando la actividad se destruya (cuando el usuario cierre la aplicación)
        Intent intent = new Intent(this, MusicaGlobal.class);

        stopService(intent); // Detén el servicio de música
        finishAffinity(); // Cierra todas las actividades en la pila
        System.exit(0);  // Termina el proceso de la aplicación completamente

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detén el servicio de música cuando la actividad se destruya (cuando el usuario cierre la aplicación)
        Intent intent = new Intent(this, MusicaGlobal.class);
        stopService(intent); // Detén el servicio de música
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Aquí es donde pausamos la música cuando la actividad se detiene (aplicación en segundo plano o la actividad es reemplazada)
        musica.pauseMusic();
        Intent intent = new Intent(this, musica.getClass());
        stopService(intent); // Detén o pausa el servicio de música
    }

    @Override
    protected void onResume() {
        super.onResume();
        musica.resumeMusic();
        Intent intent = new Intent(this, musica.getClass());
        startService(intent); // Asegúrate de que el servicio siga en ejecución
    }


}
