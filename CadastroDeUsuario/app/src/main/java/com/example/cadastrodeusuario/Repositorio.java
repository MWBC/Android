package com.example.cadastrodeusuario;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {

    private SQLiteDatabase conexao;

    public Repositorio(SQLiteDatabase conexao){

        this.conexao = conexao;
    }

    public void inserir(Usuario usuario){

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", usuario.getNome());
        contentValues.put("endereço", usuario.getEndereço());
        contentValues.put("email", usuario.getEmail());
        contentValues.put("telefone", usuario.getTelefone());

        conexao.insertOrThrow("informacoes", null, contentValues);
    }

    public void excluir(int codigo){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);
        conexao.delete("informacoes", "codigo = ?", parametros);
    }

    public void alterar(Usuario usuario){


        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", usuario.getNome());
        contentValues.put("endereço", usuario.getEndereço());
        contentValues.put("email", usuario.getEmail());
        contentValues.put("telefone", usuario.getTelefone());

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(usuario.getCodigo());
        conexao.update("informacoes",contentValues, "codigo = ?", parametros);
    }

    public List<Usuario> buscarTodos(){

        List<Usuario> usuarios = new ArrayList<Usuario>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT codigo, nome, email, telefone, endereço ");
        sql.append("FROM informacoes");
        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if(resultado.getCount() > 0){

            resultado.moveToFirst();

            do{

                Usuario usuario = new Usuario();
                usuario.setCodigo(resultado.getInt(resultado.getColumnIndexOrThrow("codigo")));
                usuario.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
                usuario.setEmail(resultado.getString(resultado.getColumnIndexOrThrow("email")));
                usuario.setTelefone(resultado.getString(resultado.getColumnIndexOrThrow("telefone")));
                usuario.setEndereço(resultado.getString(resultado.getColumnIndexOrThrow("endereço")));
                usuarios.add(usuario);

            }while (resultado.moveToNext());
        }

        return usuarios;
    }

    public Usuario buscarCliente(int codigo){

        Usuario usuario = new Usuario();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM informacoes ");
        sql.append("WHERE codigo = ?");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);

        if(resultado.getCount() > 0){

            resultado.moveToFirst();
            usuario.setCodigo(resultado.getInt(resultado.getColumnIndexOrThrow("codigo")));
            usuario.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
            usuario.setEmail(resultado.getString(resultado.getColumnIndexOrThrow("email")));
            usuario.setTelefone(resultado.getString(resultado.getColumnIndexOrThrow("telefone")));
            usuario.setEndereço(resultado.getString(resultado.getColumnIndexOrThrow("endereço")));
            return usuario;
        }
        return null;
    }
}