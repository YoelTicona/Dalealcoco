package com.yoi.dalealcoco;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

// Clase principal para manejar el juego de preguntas y respuestas
public class JuegoActivity extends Activity {
    Preguntas preguntas = new Preguntas(JuegoActivity.this); // Objeto para gestionar las preguntas de la base de datos
    ArrayList<String[]> datosPreguntas = new ArrayList<>(); // Lista de preguntas para el juego

    private MediaPlayer mediaPlayer; // Reproduce sonidos durante el juego
    Handler retraso = new Handler(); // Permite manejar retrasos en la ejecución

    // Componentes de la interfaz de usuario
    TextView pregunta;
    TextView TextoNroPreguntas;
    Button opcion_a;
    Button opcion_b;
    Button opcion_c;
    Button opcion_d;

    // Variables de control del juego
    String respuesta; // Respuesta correcta de la pregunta actual
    int contador = 0; // Número de preguntas mostradas
    int aciertos = 0; // Número de respuestas correctas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        // Asocia los elementos de la interfaz con sus IDs en el diseño
        pregunta = findViewById(R.id.textViewPregunta);
        TextoNroPreguntas = findViewById(R.id.textViewNroPregunta);
        opcion_a = findViewById(R.id.buttonPregunta1);
        opcion_b = findViewById(R.id.buttonPregunta2);
        opcion_c = findViewById(R.id.buttonPregunta3);
        opcion_d = findViewById(R.id.buttonPregunta4);

        // Inicia la música de fondo del juego
        Intent intent = new Intent(JuegoActivity.this, MusicaGlobal.class);
        intent.putExtra(MusicaGlobal.EXTRA_MUSIC_RESOURCE, R.raw.kirbysuperstartimetolearnjugacion);
        startService(intent);

        try {
            preguntas.apertura(); // Abre la conexión con la base de datos

            // Verifica si la tabla tiene datos; si no, los carga
            SQLiteDatabase db = preguntas.getReadableDatabase();
            if (!preguntas.isTablePopulated(db, "pregunta")) {
                cargarDatos();
            }

            // Obtiene las preguntas para el juego
            datosPreguntas = preguntas.obtenerPreguntas();

            // Muestra la primera pregunta
            damePregunta();
        } catch (Exception e) {
            // Maneja errores y notifica al usuario
            retraso.postDelayed(() -> {}, 3000);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Muestra una pregunta nueva en pantalla
    public void damePregunta() {
        if (contador < 10) {
            String[] pregunta1 = datosPreguntas.get(contador);
            contador++;
            TextoNroPreguntas.setText("Número de Pregunta: " + contador + "/10");

            // Configura la pregunta y las opciones
            pregunta.setText(pregunta1[1]);
            opcion_a.setText(pregunta1[2]);
            opcion_b.setText(pregunta1[3]);
            opcion_c.setText(pregunta1[4]);
            opcion_d.setText(pregunta1[5]);
            respuesta = pregunta1[6];

            // Log de la pregunta actual para depuración
            Log.d("Consola", "Pregunta: " + pregunta1[1]);
        } else {
            // Finaliza el juego y muestra los resultados
            Toast.makeText(this, "Procesando sus resultados", Toast.LENGTH_LONG).show();
            Intent nuevaVentana = new Intent(this, ResultadosActivity.class);
            String resultados = "Nro de Preguntas: " + contador + "\n" +
                    "Preguntas respondidas: " + aciertos + "\n" +
                    "Preguntas equivocadas: " + (contador - aciertos);
            nuevaVentana.putExtra("resultados", resultados);
            startActivity(nuevaVentana);
        }
    }

    // Verifica si la respuesta seleccionada es correcta
    public void verificaPregunta(String opcion) {
        if (opcion.equals(respuesta)) {
            aciertos++; // Incrementa los aciertos si es correcta
            mediaPlayer = MediaPlayer.create(this, R.raw.kirbysuperstarfeliz); // Sonido para respuesta correcta
        } else {
            mediaPlayer = MediaPlayer.create(this, R.raw.robloxmuerte); // Sonido para respuesta incorrecta
        }
        mediaPlayer.start();
        damePregunta(); // Muestra la siguiente pregunta
    }

    // Métodos para manejar las opciones seleccionadas por el usuario
    public void pregunta1(View view) {
        verificaPregunta(opcion_a.getText().toString());
    }
    public void pregunta2(View view) {
        verificaPregunta(opcion_b.getText().toString());
    }
    public void pregunta3(View view) {
        verificaPregunta(opcion_c.getText().toString());
    }
    public void pregunta4(View view) {
        verificaPregunta(opcion_d.getText().toString());
    }

    // Inserta preguntas en la base de datos
    public void cargarDatos(){
        preguntas.Insertar(1001, "culturaGeneral", "¿Cuál es la capital constitucional de Bolivia?", "Sucre", "La Paz", "Cochabamba", "Santa Cruz", "Sucre");
        preguntas.Insertar(1002, "culturaGeneral", "¿Qué océano baña las costas de Bolivia?", "Atlántico", "Pacífico", "Ninguno", "Índico", "Ninguno");
        preguntas.Insertar(1003, "culturaGeneral", "¿Qué instrumento es tradicional en la música folclórica boliviana?", "Charango", "Guitarra", "Violín", "Piano", "Charango");
        preguntas.Insertar(1004, "culturaGeneral", "¿En qué año Bolivia declaró su independencia?", "1809", "1825", "1830", "1810", "1825");
        preguntas.Insertar(1005, "culturaGeneral", "¿Cuál es el salar más grande del mundo, ubicado en Bolivia?", "Salar de Coipasa", "Salar de Uyuni", "Salar del Hombre Muerto", "Salar de Atacama", "Salar de Uyuni");
        preguntas.Insertar(1006, "culturaGeneral", "¿Qué país es conocido como el \"gigante de Sudamérica\"?", "Argentina", "Brasil", "Perú", "México", "Brasil");
        preguntas.Insertar(1007, "culturaGeneral", "¿En qué año se llevaron a cabo los primeros Juegos Olímpicos modernos?", "1896", "1900", "1912", "1920", "1896");
        preguntas.Insertar(1008, "culturaGeneral", "¿Qué cordillera atraviesa Bolivia?", "Los Alpes", "Los Andes", "Los Pirineos", "Los Himalayas", "Los Andes");
        preguntas.Insertar(1009, "culturaGeneral", "¿Cuál es el lago navegable más alto del mundo compartido entre Bolivia y Perú?", "Lago Titicaca", "Lago Poopó", "Lago Victoria", "Lago Chad", "Lago Titicaca");
        preguntas.Insertar(1010, "culturaGeneral", "¿Quién escribió \"El Ingenioso Hidalgo Don Quijote de la Mancha\"?", "Miguel de Cervantes", "Gabriel García Márquez", "Pablo Neruda", "Jorge Luis Borges", "Miguel de Cervantes");
        preguntas.Insertar(2001, "geografia", "¿Cuál es la capital de Bolivia?", "Sucre", "La Paz", "Cochabamba", "Santa Cruz", "Sucre");
        preguntas.Insertar(2002, "geografia", "¿Qué país no tiene salida al mar?", "Brasil", "Bolivia", "Argentina", "Chile", "Bolivia");
        preguntas.Insertar(2003, "geografia", "¿Cuál es el lago navegable más alto del mundo?", "Titicaca", "Victoria", "Baikal", "Superior", "Titicaca");
        preguntas.Insertar(2004, "geografia", "¿Qué continente es el más grande del mundo?", "África", "Asia", "América", "Europa", "Asia");
        preguntas.Insertar(2005, "geografia", "¿Cuál es el río más largo de Bolivia?", "Mamoré", "Beni", "Pilcomayo", "Guaporé", "Mamoré");
        preguntas.Insertar(2006, "geografia", "¿Qué ciudad boliviana es conocida como la \"Ciudad Blanca\"?", "Sucre", "Potosí", "Tarija", "Oruro", "Sucre");
        preguntas.Insertar(2007, "geografia", "¿Cuál es el país más pequeño de Sudamérica?", "Uruguay", "Surinam", "Guyana", "Chile", "Surinam");
        preguntas.Insertar(2008, "geografia", "¿En qué año se pintó \"La Noche Estrellada\" de Vincent van Gogh?", "1885", "1890", "1889", "1883", "1889");
        preguntas.Insertar(2009, "geografia", "¿En qué departamento boliviano se encuentra el Salar de Uyuni?", "Oruro", "Potosí", "Cochabamba", "Tarija", "Potosí");
        preguntas.Insertar(2010, "geografia", "¿Qué océano está al oeste de Sudamérica?", "Atlántico", "Índico", "Ártico", "Pacífico", "Pacífico");
        preguntas.Insertar(3001, "artes", "¿Quién es conocido como el \"Padre de la pintura boliviana\"?", "Alfredo Da Silva", "Cecilio Guzmán de Rojas", "Arturo Borda", "María Luisa Pacheco", "Cecilio Guzmán de Rojas");
        preguntas.Insertar(3002, "artes", "¿Qué danza boliviana es reconocida por sus máscaras artesanales?", "La Diablada", "Morenada", "Caporales", "Tinku", "La Diablada");
        preguntas.Insertar(3003, "artes", "¿Cómo se llama la famosa escultura de la Venus de Milo?", "Escultura griega antigua", "Escultura romana antigua", "Escultura egipcia", "Escultura medieval", "Escultura griega antigua");
        preguntas.Insertar(3004, "artes", "¿En qué departamento se encuentra la famosa Catedral de San Lorenzo, conocida por sus tallados en madera?", "Potosí", "Cochabamba", "Santa Cruz", "La Paz", "Santa Cruz");
        preguntas.Insertar(3005, "artes", "¿Qué artista boliviano es famoso por sus obras surrealistas?", "Arturo Borda", "Mamani Mamani", "Marina Núñez del Prado", "Cecilio Guzmán de Rojas", "Arturo Borda");
        preguntas.Insertar(3006, "artes", "¿Qué danza boliviana es representada por personajes de la colonia española?", "Cueca", "Waca Waca", "Morenada", "Tinku", "Waca Waca");
        preguntas.Insertar(3007, "artes", "¿En qué ciudad boliviana se realiza el Carnaval de Oruro, Patrimonio Oral e Intangible de la Humanidad?", "Sucre", "Cochabamba", "Oruro", "Santa Cruz", "Oruro");
        preguntas.Insertar(3008, "artes", "¿En qué museo se encuentra la obra \"La Última Cena\"?", "Museo del Prado", "Museo del Louvre", "Museo Británico", "Museo Guggenheim", "Museo del Louvre");
        preguntas.Insertar(3009, "artes", "¿Qué instrumento es típico del folklore boliviano y acompaña muchas danzas tradicionales?", "Guitarra", "Charango", "Quena", "Bombo", "Charango");
        preguntas.Insertar(3010, "artes", "¿Quién pintó la Mona Lisa?", "Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Salvador Dalí", "Leonardo da Vinci");
        preguntas.Insertar(4001, "ciencias", "¿Cuál es el planeta más cercano al Sol?", "Venus", "Tierra", "Mercurio", "Marte", "Mercurio");
        preguntas.Insertar(4002, "ciencias", "¿Cuál es el compuesto químico del agua?", "H2O", "CO2", "O2", "H2SO4", "H2O");
        preguntas.Insertar(4003, "ciencias", "¿Qué parte del cuerpo humano bombea la sangre?", "El cerebro", "Los pulmones", "El corazón", "Los riñones", "El corazón");
        preguntas.Insertar(4004, "ciencias", "¿Qué organelo celular produce energía?", "Núcleo", "Mitocondria", "Retículo endoplásmico", "Lisosomas", "Mitocondria");
        preguntas.Insertar(4005, "ciencias", "¿Cuál es el elemento químico con el símbolo \"O\"?", "Oxígeno", "Oro", "Osmio", "Ozono", "Oxígeno");
        preguntas.Insertar(4006, "ciencias", "¿En qué estado de la materia se encuentra el agua a temperatura ambiente?", "Sólido", "Líquido", "Gaseoso", "Plasma", "Líquido");
        preguntas.Insertar(4007, "ciencias", "¿Cuál es la ley de la gravedad propuesta por Newton?", "Ley de la conservación de la energía", "Ley de la inercia", "Ley de la gravitación universal", "Ley de acción y reacción", "Ley de la gravitación universal");
        preguntas.Insertar(4008, "ciencias", "¿Cuál es la fórmula química del dióxido de carbono?", "CO2", "CO", "C2O2", "O2C", "CO2");
        preguntas.Insertar(4009, "ciencias", "¿Qué gas es necesario para la respiración humana?", "Oxígeno", "Nitrógeno", "Hidrógeno", "Dióxido de carbono", "Oxígeno");
        preguntas.Insertar(4010, "ciencias", "¿Qué tipo de célula no tiene núcleo?", "Célula animal", "Célula vegetal", "Célula procariota", "Célula eucariota", "Célula procariota");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detén el servicio de música cuando la actividad se destruya (cuando el usuario cierre la aplicación)
        Intent intent = new Intent(JuegoActivity.this, MusicaGlobal.class);
        stopService(intent); // Detén el servicio de música
    }
    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(JuegoActivity.this, MusicaGlobal.class);
        stopService(intent); // O puedes pausar en lugar de detener
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Detén la música cuando la actividad pase a segundo plano
        Intent intent = new Intent(JuegoActivity.this, MusicaGlobal.class);
        intent.putExtra(MusicaGlobal.EXTRA_MUSIC_RESOURCE, R.raw.kirbysuperstartimetolearnjugacion);
        startService(intent);
    }
}