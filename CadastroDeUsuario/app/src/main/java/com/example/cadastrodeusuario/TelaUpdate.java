package com.example.cadastrodeusuario;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cadastrodeusuario.database.BancoController;

public class TelaUpdate extends AppCompatActivity {

    private Repositorio repositorio;
    private SQLiteDatabase conexao;
    private BancoController bancoController;
    private ConstraintLayout constraintLayout;
    private Usuario usuario;
    private EditText edtNome, edtEndereco, edtEmail, edtTelefone;
    private Button btnEditar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        bancoController = new BancoController();
        constraintLayout = (ConstraintLayout) findViewById(R.id.layoutUpdate);

        edtNome = findViewById(R.id.edtNome);
        edtEndereco = findViewById(R.id.edtEndereço);
        edtEmail = findViewById(R.id.edtEmail);
        edtTelefone = findViewById(R.id.edtTelefone);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        conexao = bancoController.criarConexao(constraintLayout,this);
        usuario = new Usuario();
        repositorio = new Repositorio(conexao);
        usuario = repositorio.buscarCliente(bundle.getInt("codigo"));
        edtNome.setText(usuario.getNome());
        edtEndereco.setText(usuario.getEndereço());
        edtEmail.setText(usuario.getEmail());
        edtTelefone.setText(usuario.getTelefone());

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TelaUpdate.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnEditar = (Button) findViewById(R.id.btnOK);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                repositorio = new Repositorio(conexao);
                usuario.setNome(edtNome.getText().toString());
                usuario.setEndereço(edtEndereco.getText().toString());
                usuario.setEmail(edtEmail.getText().toString());
                usuario.setTelefone(edtTelefone.getText().toString());
                repositorio.alterar(usuario);

                Intent intent1 = new Intent(TelaUpdate.this, MainActivity.class);
                startActivity(intent1);


            }
        });



    }
}