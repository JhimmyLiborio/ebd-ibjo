package com.registro.componentes.ibjo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.registro.componentes.ibjo.entidade.Aluno;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.registro.componentes.ibjo.R.drawable.customizado_ripple;


public class ListaAlunosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<Aluno> listaAluno;
    private int qtdSelecionado = 0;

    private final Date data;
    private final DateFormat dataFormatada;


    // Construtor da Classe para Determinar a entrada obrigatóra de uma lista
    ListaAlunosAdapter(List<Aluno> lista){
        listaAluno = lista;
        Calendar calendar = Calendar.getInstance();
        data = calendar.getTime();
        dataFormatada = DateFormat.getDateInstance(DateFormat.MEDIUM);
    }


    // Inflar o Layout de Conteúdo "aluno_item_lista.xml" no Recycler View
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aluno_item_lista,
                parent, false);

        return new ViewHolder(view);
    }


    // Vincula ou atribue a Fonte de Dados "List<Aluno>" nos Elementos do Layout
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder itemHolder = (ViewHolder) holder;
        final Aluno aluno  = listaAluno.get(position);


        itemHolder.textIcon.setText( aluno.getNome().substring(0,1) );
        itemHolder.textNome.setText( aluno.getNome() );
        itemHolder.textClasse.setText( new StringBuilder("Classe: ").append( aluno.getClasse() ));
        itemHolder.textData.setText( dataFormatada.format(data).substring(0,10) );


        // click simples na lista
        itemHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                if( getQtdSelecionado() > 0 ){

                    aluno.setSelecionado(!aluno.isSelecionado());
                    // se a lista tiver itens selecionado(clique longo), realiza marcação
                    if( getQtdSelecionado() > 0 && aluno.isSelecionado())
                        marcarItemBackground( itemHolder, "#E1062E3E", "#64dd17" );
                    // se  o click for em um aluno selecionado, realiza a desmarcação do item
                    else
                        desmarcarItemBackground( itemHolder, aluno.getNome().substring(0, 1), "#008ecc" );
                }// implementar abertura de uma nova activity ao clicar em um ícone
            }
        });


        //  clique longo na lista
        itemHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                aluno.setSelecionado( !aluno.isSelecionado() );

                if(aluno.isSelecionado())
                    marcarItemBackground(itemHolder, "#E1062E3E", "#64dd17");
                else
                    desmarcarItemBackground(itemHolder, aluno.getNome().substring(0,1), "#008ecc" );
                return true;
            }
        });

    }

    //marca o otem da lista que foi selecionado
    private void marcarItemBackground(final  ViewHolder itemHolder, String corItem, String corIcone){
        itemHolder.itemView.setBackgroundColor(Color.parseColor(corItem));
        setIconBackground(itemHolder, "P", corIcone);
        qtdSelecionado++;
    }

    // desmarca
    private void desmarcarItemBackground(  final ViewHolder itemHolder, CharSequence letraIcon, String corIcone ){

        setIconBackground(itemHolder, letraIcon, corIcone);
        itemHolder.itemView.setPressed(false); // efeito ripple não aparece na desmarcação
        itemHolder.itemView.setBackgroundResource(customizado_ripple); // padrão lista
        qtdSelecionado--;
    }


    // altera cor e letra do TextIcon da lista ao selecionar/desmarcar um item da lista
    private void setIconBackground( final ViewHolder itemHolder, CharSequence letraIcon, String cor){
        itemHolder.textIcon.setText(letraIcon);
        itemHolder.textIcon.getBackground().setTint(Color.parseColor(cor));
    }


    // Tamanho da fonte de Dados
    @Override
    public int getItemCount() {
        return listaAluno.size();
    }


    // Classe interna, referencia os elementos do layout: aluno_item
    private static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textIcon, textNome, textClasse, textData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textIcon = itemView.findViewById(R.id.textIcon);
            textNome = itemView.findViewById(R.id.textNome);
            textClasse = itemView.findViewById(R.id.textClasse);
            textData = itemView.findViewById(R.id.textData);
        }
    }

    public int getQtdSelecionado() {
        return qtdSelecionado;
    }

    public void setQtdSelecionado(int qtdSelecionado) {
        this.qtdSelecionado = qtdSelecionado;
    }
}
