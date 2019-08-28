package com.example.cadastrodeusuario.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuarioOpenHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "Usuarios";
    private static final int VERSION = 1;

    private ScriptDll scriptDll;
    public UsuarioOpenHelper(Context context) {

        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(scriptDll.getCreateTableInformacoes());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS informacoes");
        onCreate(sqLiteDatabase);
    }
}
