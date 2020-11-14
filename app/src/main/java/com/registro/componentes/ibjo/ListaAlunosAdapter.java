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

import static android.graphics.Color.parseColor;
import static com.registro.componentes.ibjo.R.drawable.customizado_ripple;


public class ListaAlunosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Aluno> listaAluno;
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
        final Aluno aluno = listaAluno.get(position);


        itemHolder.textIcon.setText(aluno.getNome().substring(0,1));
        itemHolder.textNome.setText(aluno.getNome());
        itemHolder.textClasse.setText(new StringBuilder("Classe: ").append(aluno.getClasse()));
        itemHolder.textData.setText(dataFormatada.format(data).substring(0,10));

        //  clique longo na lista
        itemHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @SuppressLint({"NewApi", "ResourceAsColor"})
            @Override
            public boolean onLongClick(View v) {

                aluno.setSelecionado( !aluno.isSelecionado() );

                itemHolder.itemView.setBackgroundColor(Color.parseColor("#E1062E3E"));

                if (aluno.isSelecionado()) {
                    itemHolder.textIcon.setText("P");
                    itemHolder.textIcon.getBackground().setTint(Color.parseColor("#64dd17"));

                }else{

                    itemHolder.itemView.setPressed(false);
                    itemHolder.itemView.setBackgroundResource(customizado_ripple);

                    itemHolder.textIcon.setText(aluno.getNome().substring(0,1));
                    itemHolder.textIcon.getBackground().setTint(parseColor("#008ecc"));
                }

                return true;
            }
        });

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
