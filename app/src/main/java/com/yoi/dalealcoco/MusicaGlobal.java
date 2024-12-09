package com.yoi.dalealcoco;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicaGlobal extends Service {

    private MediaPlayer mediaPlayer; // Objeto para manejar la reproducción de música
    public static final String EXTRA_MUSIC_RESOURCE = "EXTRA_MUSIC_RESOURCE"; // Constante para pasar la música a reproducir

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Este servicio no se vincula con otros componentes, por lo que se retorna null
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Música predeterminada (si no se especifica otra en el Intent)
        int musicResId = R.raw.jazzbluemain;

        // Verifica si el Intent contiene un recurso de música y lo actualiza si es necesario
        if (intent != null && intent.hasExtra(EXTRA_MUSIC_RESOURCE)) {
            musicResId = intent.getIntExtra(EXTRA_MUSIC_RESOURCE, R.raw.jazzbluemain);
        }

        // Reproduce la música seleccionada
        playMusic(musicResId);

        return START_STICKY; // Mantiene el servicio en ejecución incluso si se detiene la aplicación
    }

    // Método para iniciar la reproducción de música
    private void playMusic(int resId) {
        // Si ya se está reproduciendo música, deténla y libera los recursos
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Crea un nuevo MediaPlayer con el archivo de música y lo reproduce
        mediaPlayer = MediaPlayer.create(this, resId);
        mediaPlayer.setLooping(true); // Hace que la música se repita en bucle
        mediaPlayer.start(); // Inicia la reproducción
    }

    // Método público para cambiar la música
    public void changeMusic(int resId) {
        playMusic(resId); // Llama al método playMusic para cambiar la música
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Detiene y libera los recursos de MediaPlayer al destruir el servicio
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
    // Método para pausar la música
    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // Pausa la música
        }
    }

    // Método para reanudar la música
    public void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // Reanuda la música si está pausada
        }
    }
}