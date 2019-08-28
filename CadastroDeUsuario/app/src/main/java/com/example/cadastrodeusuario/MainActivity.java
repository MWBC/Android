package com.example.cadastrodeusuario;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import com.example.cadastrodeusuario.database.BancoController;
import com.example.cadastrodeusuario.database.UsuarioOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int INSERIR_USUARIO = 1;
    static final int CANCELAR = 2;
    private List<Usuario> listaContatos = new ArrayList<Usuario>();
    private MyAdapter adapter;
    private SQLiteDatabase conexao;
    private Repositorio repositorio;
    private RecyclerView recyclerView;
    private BancoController bancoController;

    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        constraintLayout = findViewById(R.id.contentMainLayout);
        bancoController = new BancoController();
        conexao = bancoController.criarConexao(constraintLayout, this);
        repositorio = new Repositorio(conexao);
        listaContatos = repositorio.buscarTodos();

        setupRecyclerView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, TelaCadastro.class);
                startActivityForResult(intent,INSERIR_USUARIO, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
    }

    public void setupRecyclerView(){

        recyclerView = findViewById(R.id.RVUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(this, listaContatos);
        recyclerView.setAdapter(adapter);

    }

    //método para tratar a resposta vinda da activity de cadastro
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode == INSERIR_USUARIO && requestCode == INSERIR_USUARIO){

            listaContatos = repositorio.buscarTodos();
            adapter  =new MyAdapter(this, listaContatos);
            recyclerView.setAdapter(adapter);
        }

        if(resultCode == CANCELAR){

            adapter.notifyDataSetChanged();
        }
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}