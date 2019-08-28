package com.example.cadastrodeusuario;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cadastrodeusuario.database.BancoController;
import com.example.cadastrodeusuario.database.UsuarioOpenHelper;

public class TelaCadastro extends AppCompatActivity {

    static final int INSERIR_USUARIO = 1;
    static final int CANCELAR = 2;
    EditText edtNome;
    EditText edtEndereço;
    EditText edtEmail;
    EditText edtTelefone;
    private SQLiteDatabase conexao;
    private UsuarioOpenHelper usuarioOpenHelper;
    private Repositorio repositorio;
    private Usuario usuario;
    private BancoController bancoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtEndereço = findViewById(R.id.edtEndereço);
        edtTelefone = findViewById(R.id.edtTelefone);
        ConstraintLayout constraintLayout;
        constraintLayout = findViewById(R.id.containerCad);
        bancoController = new BancoController();
        conexao = bancoController.criarConexao(constraintLayout, this);
        repositorio = new Repositorio(conexao);
    }

    //transformando xml na view
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cad, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //método que identifica o click nos botões do menu e realiza ações
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        Intent intent;

        switch (id){

            case R.id.menuItemOk:

                String nome, email, telefone, endereço;
                nome = edtNome.getText().toString();
                email = edtEmail.getText().toString();
                endereço = edtEndereço.getText().toString();
                telefone = edtTelefone.getText().toString();

                if(validaCampos()){

                }else {

                    Usuario usuario = new Usuario(nome, endereço, email, telefone);
                    repositorio.inserir(usuario);
                    intent = new Intent(TelaCadastro.this, MainActivity.class);
                    setResult(INSERIR_USUARIO, intent);
                    finishAfterTransition();
                }
                break;

            case R.id.menuItemCancelar:

                intent = new Intent(TelaCadastro.this, MainActivity.class);
                setResult(CANCELAR, intent);
                finishAfterTransition();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validaCampos(){

        boolean res;

        String nome = edtNome.getText().toString();
        String endereco = edtEndereço.getText().toString();
        String email = edtEmail.getText().toString();
        String telefone = edtTelefone.getText().toString();

        if(res = isCampoVazio(nome)){
            edtNome.requestFocus();

        }else if(res = isCampoVazio(endereco)){
            edtEndereço.requestFocus();

        }else if (res = !isEmailValido(email)){
            edtEmail.requestFocus();

        }else if(res = isCampoVazio(telefone)){
            edtTelefone.requestFocus();

        }

        if(res){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Falha ao inserir o contato!");
            dlg.setMessage("Campos inválidos ou em branco");
            dlg.setNeutralButton("ok",null);
            dlg.show();
        }
        return res;
    }

    public boolean isCampoVazio(String valor){

        boolean resultado = (TextUtils.isEmpty(valor)) || valor.trim().isEmpty();
        return resultado;
    }

    private boolean isEmailValido(String valor){

        boolean resultado = (!isCampoVazio(valor) && Patterns.EMAIL_ADDRESS.matcher(valor).matches());
        return resultado;
    }

    private boolean IsFoneVazio(String valor){

        boolean resultado = (!isCampoVazio(valor) && Patterns.PHONE.matcher(valor).matches());
        return resultado;
    }

}
