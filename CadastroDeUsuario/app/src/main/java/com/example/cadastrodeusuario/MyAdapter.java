package com.example.cadastrodeusuario;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

    private List<Usuario> lista;
    private List<Usuario> listaCompleta;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<Usuario> lista){

        this.lista = lista;
        this.listaCompleta = new ArrayList<>(lista);
        this.layoutInflater = LayoutInflater.from(context);
    }

    //transforma o xml em view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.linha_usuario, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    //define os atributos que serão exibidos
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Usuario usuario = lista.get(position);
        holder.nome.setText(usuario.getNome());
        holder.telefone.setText(usuario.getTelefone());
        holder.img.setImageResource(R.drawable.user);
        holder.codigo = usuario.getCodigo();
    }

    //retorna a quantidade de elementos na lista
    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public Filter getFilter() {
        return filtro;
    }

    private Filter filtro = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Usuario> listaFiltrada = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){

                listaFiltrada.addAll(listaCompleta);

            }else{

                String parametroDoFiltro = charSequence.toString().toLowerCase().trim();

                for(Usuario usuario: listaCompleta){

                    if(usuario.getNome().toLowerCase().contains(parametroDoFiltro)){

                        listaFiltrada.add(usuario);
                    }
                }
            }

            FilterResults resultado = new FilterResults();
            resultado.values = listaFiltrada;
            return resultado;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults resultado) {

            lista.clear();
            lista.addAll((List)resultado.values);
            notifyDataSetChanged();
        }
    };

    //classe que guarda a view
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nome, telefone;
        ImageView img;
        int codigo;

        public ViewHolder(@NonNull View itemView, final Context context) {

            super(itemView);
            nome = itemView.findViewById(R.id.lblNome);
            telefone = itemView.findViewById(R.id.lblTelefone);
            img = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, TelaVisualizacao.class);
                Bundle parametros = new Bundle();
                parametros.putInt("codigo", codigo);
                intent.putExtras(parametros);
                ((AppCompatActivity)context).startActivityForResult(intent, 0);
            }
        });
        }

    }
}
