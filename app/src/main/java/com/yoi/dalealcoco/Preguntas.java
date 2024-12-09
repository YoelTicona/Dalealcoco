package com.yoi.dalealcoco;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Preguntas{
    // Constantes para los nombres de las columnas en la base de datos
    private static final String ID = "id";
    private static final String CAT = "categoria";
    private static final String PRE = "pregunta";
    private static final String OPA = "opcion_a";
    private static final String OPB = "opcion_b";
    private static final String OPC = "opcion_c";
    private static final String OPD = "opcion_d";
    private static final String RES = "respuesta";

    // Nombre de la base de datos y tabla
    private static final String NDB = "trivias.db";
    private static final String NTB = "pregunta";
    private static final int VERSION = 1;

    // Instancia de la clase para crear o gestionar la base de datos
    private Creatura Control;
    private final Context nContexto;
    private SQLiteDatabase pBD;

    // Clase interna para la creación de la base de datos
    public static class Creatura extends SQLiteOpenHelper{
        public Creatura(Context context){
            super(context, NDB, null, VERSION);
        }

        // Crea la tabla si no existe
        @Override
        public void onCreate(@NonNull SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + NTB + "( " +
                    ID + " INTEGER PRIMARY KEY, " +
                    CAT + " TEXT NOT NULL, " +
                    PRE + " TEXT NOT NULL, " +
                    OPA + " TEXT NOT NULL, " +
                    OPB + " TEXT NOT NULL, " +
                    OPC + " TEXT NOT NULL, " +
                    OPD + " TEXT NOT NULL, " +
                    RES + " TEXT NOT NULL); ");
        }
        // Si la versión de la base de datos cambia, elimina la tabla anterior y crea una nueva
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " +NTB);
            onCreate(db);
        }
    }
    // Constructor que recibe el contexto de la aplicación
    public Preguntas (Context c){
        nContexto = c;
    }
    // Verifica si la tabla ya tiene datos
    public boolean isTablePopulated(SQLiteDatabase db, String xTabla) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + xTabla, null);
        boolean hasData = false;
        if (cursor.moveToFirst()) {
            hasData = cursor.getInt(0) > 0;
        }
        cursor.close();
        return hasData;
    }

    // Método para obtener la base de datos en modo lectura
    public SQLiteDatabase getReadableDatabase(){
        return pBD;
    }

    // Método para abrir la base de datos
    public Preguntas apertura() throws Exception {
        Control = new Creatura(nContexto);
        pBD = Control.getWritableDatabase();
        return this;
    }

    // Cierra la conexión con la base de datos
    public void cerrar(){
        Control.close();
    }

    // Método para insertar una nueva pregunta en la base de datos
    public long Insertar(int qId, String qCat, String qPre, String qOpa, String qOpb, String qOpc, String qOpd, String qRes){
        ContentValues cv = new ContentValues();
        cv.put(ID, qId);
        cv.put(CAT, qCat);
        cv.put(PRE, qPre);
        cv.put(OPA, qOpa);
        cv.put(OPB, qOpb);
        cv.put(OPC, qOpc);
        cv.put(OPD, qOpd);
        cv.put(RES, qRes);
        return pBD.insert(NTB, null, cv);
    }

    // Funcion para obtener una lista de preguntas aleatorias de la base de datos
    public ArrayList<String[]> obtenerPreguntas(){
        // Obtendremos los id de las preguntas aleatoriamente
        HashSet<Integer> idPreguntas = new HashSet<Integer>();
        Random random = new Random();
        int[] rangos = {1001, 2001, 3001, 4001}; // Los valores iniciales de los rangos
        // Guardamos los id de las preguntas
        while (idPreguntas.size() < 10){
            int rangoBase = rangos[random.nextInt(rangos.length)]; // Elegimos un rango al azar
            int numero = rangoBase + random.nextInt(10); // Sumamos un número aleatorio
            idPreguntas.add(numero);
        }

        // Guardamos las preguntas obtenidas
        ArrayList<String[]> datosPreguntas = new ArrayList<>();
        Iterator<Integer> iterador = idPreguntas.iterator();

        // Consulta la base de datos para obtener las preguntas correspondientes
        while (iterador.hasNext()) {
            int xid = iterador.next();
            // Realizamos la consulta
            String[] preguntaX = new String[7];
            String query = "SELECT * FROM pregunta WHERE id="+xid;
            Cursor cursor = pBD.rawQuery(query, null); // Ejecutamos la consulta

            // Guarda los resultados en el arreglo de la pregunta
            if (cursor.moveToFirst()){
                int iid = cursor.getColumnIndex(ID);
                int ipregunta = cursor.getColumnIndex(PRE);
                int iopa = cursor.getColumnIndex(OPA);
                int iopb = cursor.getColumnIndex(OPB);
                int iopc = cursor.getColumnIndex(OPC);
                int iopd = cursor.getColumnIndex(OPD);
                int irespuesta = cursor.getColumnIndex(RES);

                // Guardamos el resultado en una variable String
                preguntaX[0] = cursor.getString(iid);
                preguntaX[1] = cursor.getString(ipregunta);
                preguntaX[2] = cursor.getString(iopa);
                preguntaX[3] = cursor.getString(iopb);
                preguntaX[4] = cursor.getString(iopc);
                preguntaX[5] = cursor.getString(iopd);
                preguntaX[6] = cursor.getString(irespuesta);
            }

            // Guardamos el dato en nuestro ArrayList
            datosPreguntas.add(preguntaX);

            // Cerramos el cursor
            cursor.close();
        }
        return datosPreguntas;
    }
}
