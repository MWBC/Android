package com.example.cadastrodeusuario;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cadastrodeusuario.database.BancoController;

public class TelaVisualizacao extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private ConstraintLayout constraintLayout;
    private Repositorio repositorio;
    private TextView nome, telefone, email, endereço;
    private ImageView imagem;
    private BancoController bancoController;
    private Button btnEditar, btnDeletar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao);
        Intent intent = getIntent();
        constraintLayout = findViewById(R.id.containerCad);
        bancoController = new BancoController();
        conexao = bancoController.criarConexao(constraintLayout, this);
        repositorio = new Repositorio(conexao);
        Usuario usuario;
        nome = findViewById(R.id.lblNomeValor);
        telefone = findViewById(R.id.lblTelefoneValor);
        email = findViewById(R.id.lblEmailValor);
        endereço = findViewById(R.id.lblEndereçoValor);
        imagem = findViewById(R.id.imgContatoVisualizacao);
        final Bundle bundle = intent.getExtras();
        usuario = repositorio.buscarCliente(bundle.getInt("codigo"));
        nome.setText(usuario.getNome());
        telefone.setText(usuario.getTelefone());
        email.setText(usuario.getEmail());
        endereço.setText(usuario.getEndereço());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Contato");

        btnEditar = findViewById(R.id.btnOK);
        btnEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intentUpdate = new Intent(TelaVisualizacao.this, TelaUpdate.class);
                intentUpdate.putExtras(bundle);
                startActivity(intentUpdate);
            }
        });

        btnDeletar = findViewById(R.id.btnDeletar);
        btnDeletar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                repositorio.excluir(bundle.getInt("codigo"));
                Intent intentMain = new Intent(TelaVisualizacao.this, MainActivity.class);
                startActivityForResult(intentMain, 98);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == 0){


        }
    }
}