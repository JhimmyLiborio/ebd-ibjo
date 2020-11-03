package com.registro.componentes.tablayout_viewpager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.registro.componentes.tablayout_viewpager.entidade.Aluno;

import java.util.List;

public class ListaAlunosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Aluno> listaAluno;


    // Construtor da Classe para Determinar a entrada obrigatóra de uma lista
    ListaAlunosAdapter(List<Aluno> lista){
        listaAluno = lista;
    }

    // Inflar o Layout de Conteúdo "aluno_item_lista.xml" no Recycler View
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflar o Layout "aluno_item_lista" dentro do  Recycler View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aluno_item_lista,
                parent, false);
        return new ViewHolder(view);
    }


    // Vincula ou atribue a Fonte de Dados "List<Aluno>" nos Elementos do Layout
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder itemHolder = (ViewHolder) holder;

        itemHolder.textIcon.setText(listaAluno.get(position).getNome().substring(0,1));
        itemHolder.textNome.setText(listaAluno.get(position).getNome());
        itemHolder.textData.setText("03 nov.");
        itemHolder.textClasse.setText(listaAluno.get(position).getClasse());

        // set Color
        itemHolder.textNome.setTextColor( Color.WHITE );
        itemHolder.textClasse.setTextColor( Color.LTGRAY );
        itemHolder.textData.setTextColor(Color.LTGRAY);
    }

    // Tamanho da fonte de Dados
    @Override
    public int getItemCount() {
        return listaAluno.size();
    }


    // Classe interna, referencia os elementos do layout: aluno_item
    public  static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textIcon, textNome, textClasse, textData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textIcon = itemView.findViewById(R.id.textIcon);
            textNome = itemView.findViewById(R.id.textNome);
            textClasse = itemView.findViewById(R.id.textClasse);
            textData = itemView.findViewById(R.id.textData);
        }
    }
}
