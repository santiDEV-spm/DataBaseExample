package com.santidev.databaseexample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataManager {

    //Referencia a la base de datos
    private SQLiteDatabase db;

    //Referencia a cada una de las columnas de la tabla a tratar
    //las hacemos publicas para usarlas tanto en Datamanager y fuera de el

    public static final String TABLE_ROW_ID = "ID";
    public static final String TABLE_ROW_NAME = "name";
    public static final String TABLE_ROW_AGE = "age";

    /*
    *   Referencia a cada tabla de la base de datos
    *   Las hacemos privadas porque nadie salvo el DataManager necesita saber de la estructura de la db
    * */
    private static final String DB_NAME = "AddressBookDb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAMES_AND_ADDRESS = "NamesAndAddress";


    public DataManager(Context context){
        //creamos nuestra propia instancia del helper interno para conextarnos a la BD
        MyCustomSQLiteOpenHelper helper = new MyCustomSQLiteOpenHelper(context);

        //obtenemos del helper una base de datos para leer y escribir...
        db = helper.getWritableDatabase();
    }

    //las operaciones con la BD se implementan como metodos de nuestro Data manager

    //insertar un usuario
    public void insert(String name, String age){
        String query = "INSERT INTO " +
                        TABLE_NAMES_AND_ADDRESS + " (" +
                        TABLE_ROW_NAME + ", " +
                        TABLE_ROW_AGE + ") " +
                        "VALUES (" +
                        "'" + name + "'" + ", " +
                        "'" + age +"'" + ");";

        Log.i("insert() = ", query);
        db.execSQL(query);
    }

    //borrar un usuario
    public void delete(String name){
        String query = "DELETE FROM " +
                        TABLE_NAMES_AND_ADDRESS +
                        " WHERE " + TABLE_ROW_NAME +
                        " = '" + name + "';";
        Log.i("delete() = ", query);
        db.execSQL(query);
    }

    //obetener un usuario conceto
    public Cursor search(String name){
        String query = "SELECT " +
                        TABLE_ROW_ID +  ", " +
                        TABLE_ROW_NAME+ ", " +
                        TABLE_ROW_AGE +
                        " FROM " +
                        TABLE_NAMES_AND_ADDRESS +
                        " WHERE " +
                        TABLE_ROW_NAME + " = " + "'"+name+"';";
        Log.i("search(name) = ", query);
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    //obtener todos los usuario
    public Cursor selectAll(){
        String query = "SELECT * FROM "+ TABLE_NAMES_AND_ADDRESS+";";
        Cursor cursor = db.rawQuery(query, null);
        Log.i("selectAll() = ", query);
        return cursor;
    }

    /*
    *  Clase interna para gestionar nuestro propio helper de conexion
    *  a la base de datos
    * */
    private class MyCustomSQLiteOpenHelper extends SQLiteOpenHelper{

        public MyCustomSQLiteOpenHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        //solamente se ejecuta la primera vez que arranca la app
        // y debe crearse la base de datos
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String newTablesQuery = "CREATE TABLE "+
                                    TABLE_NAMES_AND_ADDRESS + " (" +
                                    TABLE_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                    TABLE_ROW_NAME + " TEXT NOT NULL, " +
                                    TABLE_ROW_AGE + " TEXT NOT NULL);";
            Log.i("Creating DataBase", newTablesQuery);;
            sqLiteDatabase.execSQL(newTablesQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
                //aqui no implementaremos nada hasta tener mejoras de las base de datos
                if(oldVersion < newVersion ){
                    //aqui implementaremos todas las mejoras desde oldVersion+1 hasta newVersion
                    for (int i = oldVersion+1; i<=newVersion; i++){
                        Log.i("Upgrading", "onUpgrade: Estamos haciendo la actualizacion a la nueva version de la base de datos numero " + i);
                        updateToVersion(i);
                    }
                }
        }

        private void updateToVersion(int versionToUpdate){
            switch (versionToUpdate){
                case 1:
                    //crear una base de datos...
                    break;
                case 2:
                    //insertar un campo a la tabla XXX
                    break;
                case 3:
                    //Destruir una tabla que ya no se usa...
                    break;
                case 4:
                    String query = String.valueOf(R.string.update_4);
                    //ejecutar el update a partir de la query de strings...
                default:
                    break;
            }
        }
    }
}
