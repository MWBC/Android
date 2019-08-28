package com.example.cadastrodeusuario.database;

public class ScriptDll {

    public static String getCreateTableInformacoes(){

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS informacoes(");
        sql.append("codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("nome VARCHAR(155) NOT NULL, ");
        sql.append("endereço VARCHAR(155) NOT NULL, ");
        sql.append("email VARCHAR(155), ");
        sql.append("telefone VARCHAR(155) NOT NULL);");

        return sql.toString();
    }
}
