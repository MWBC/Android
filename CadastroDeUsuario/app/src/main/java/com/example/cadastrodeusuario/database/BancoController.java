package com.example.cadastrodeusuario.database;

import android.app.AlertDialog;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class BancoController{

    private UsuarioOpenHelper usuarioOpenHelper;
    private SQLiteDatabase conexao;

    public BancoController(){

    }

    public SQLiteDatabase criarConexao(ConstraintLayout constraintLayout, Context context){

        try{

            usuarioOpenHelper = new UsuarioOpenHelper(context);
            conexao = usuarioOpenHelper.getWritableDatabase();

        }catch (SQLException e){

            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setTitle("Erro");
            dlg.setMessage(e.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }

        return conexao;

    }
}