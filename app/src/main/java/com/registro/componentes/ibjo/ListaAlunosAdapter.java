package com.registro.componentes.ibjo;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.registro.componentes.ibjo.entidade.Aluno;
import com.registro.componentes.ibjo.entidade.Chamada;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.parseColor;
import static com.registro.componentes.ibjo.R.drawable;
import static com.registro.componentes.ibjo.R.id;
import static com.registro.componentes.ibjo.R.layout;


public class ListaAlunosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<Aluno> alunosCadastrados;

    private final Date data;
    private final DateFormat dataFormatada;

    public  Activity activity;
    private androidx.appcompat.view.ActionMode actionMode;

    // armazena os índices da lista dos alunos que foram selecionados, até que uma ação ocorra.
    private SparseBooleanArray indiceValorSelecaoTmp;

    private final  SparseBooleanArray indiceValorFinal;

    private final Map<String, Object> folhaDeChamada = new HashMap();


    // Construtor, determina as entradas e iniciaçizões necessárias
    // act: activity atual
    // lista: conjunto de dados
    ListaAlunosAdapter(List<Aluno> lista, Activity act){

        alunosCadastrados = lista;
        Calendar calendar = Calendar.getInstance();
        data = calendar.getTime();
        dataFormatada = DateFormat.getDateInstance(DateFormat.MEDIUM);
        activity =  act;
        indiceValorSelecaoTmp = new SparseBooleanArray();
        indiceValorFinal = new SparseBooleanArray();
    }


    // Inflar o Layout de Conteúdo "aluno_item_lista.xml" no Recycler View
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout.aluno_item_lista,
                parent, false);

        return new ViewHolder(view);
    }

    // Vincula a Fonte de Dados "List<Aluno>" nos Elementos do Layout

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder itemHolder = (ViewHolder) holder;
        final Aluno aluno  = alunosCadastrados.get(position);


        //itemHolder.textIcon.setText( aluno.getNome().substring(0,1) );

        itemHolder.textIcon.setText(indiceValorSelecaoTmp.get(position)? "P" :
                aluno.getNome().substring(0,1) );
        itemHolder.textNome.setText( aluno.getNome() );
        itemHolder.textClasse.setText( new StringBuilder("Classe: ").append( aluno.getClasse() ));
        itemHolder.textData.setText( dataFormatada.format(data).substring(0,10) );




        // muda a cor do item caso seja selecionado ou não.
        itemHolder.itemView.setBackgroundColor(indiceValorSelecaoTmp.get(position)?
                Color.parseColor("#E1062E3E") : Color.TRANSPARENT);

        itemHolder.textIcon.getBackground().setTint(indiceValorSelecaoTmp.get(position)?
                parseColor("#64dd17"): parseColor("#008ecc"));

        // aplicar efeito riple na lista
        if (getTotalSelecao() <= 0){
            itemHolder.itemView.setBackgroundResource(drawable.customizado_ripple);
        }


        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aceita seleção após ter algum item selecionado por longclick
                if( getTotalSelecao() > 0 )
                    selecaoItemLista( position );
            }
        });

        itemHolder.itemView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selecaoItemLista( position );
                return true;
            }
        });
    }

    // Classe interna, referencia os elementos do layout: aluno_item
    private static class ViewHolder extends RecyclerView.ViewHolder{


        TextView textIcon, textNome, textClasse, textData;
        FloatingActionButton fab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textIcon = itemView.findViewById(id.textIcon);
            textNome = itemView.findViewById(id.textNome);
            textClasse = itemView.findViewById(id.textClasse);
            textData = itemView.findViewById(id.textData);
            fab = itemView.findViewById(id.btfinalizar);
        }
    }


    // Selecao de item da lista
    private void selecaoItemLista(int posicao){

        alternarSelecao(posicao);

        //verifica se algum item já estão selecionados ou não.
        boolean haItemSelecionado = getTotalSelecao() > 0;

        if(haItemSelecionado && actionMode ==  null){
            //Existe itens selecionados. Inicializa ActionMode (o menu superior com opções para a lista)
            actionMode = ((AppCompatActivity) activity).startSupportActionMode(callbackMenu);

        }else if (!haItemSelecionado && actionMode != null){
            // não há seleção de itens, finaliza o actionMode
            actionMode.finish();
        }
        if (actionMode != null)
            actionMode.setTitle(String.valueOf(getTotalSelecao()));
    }


    /*
     * Alterna a seleção baseado no índice da lista
     * Se um item não selecionado é clicado, ocorre o mapeamento do índice/valor
     * Se um item selecionado é clicado, ocorre a desseleção e remove o mapeamento
     */
    public void alternarSelecao(int posicao) {
        selecionarPosicao(posicao, !indiceValorSelecaoTmp.get(posicao));
    }

    // Mapeia e Remove o mapeamento das posições selecionadas ou desselecionadas
    public void selecionarPosicao(int position, boolean value) {
        if (value)
            indiceValorSelecaoTmp.put(position, value);
        else
            indiceValorSelecaoTmp.delete(position);

        notifyDataSetChanged();
    }

    // Remove todos as posições e valores mapeadas
    public void removeSelecao() {
        indiceValorSelecaoTmp.clear();
        notifyDataSetChanged();
    }

    // retorna o total itens selecionados da lista
    public int getTotalSelecao(){
        return indiceValorSelecaoTmp.size();
    }

    @Override
    public int getItemCount() {
        return alunosCadastrados.size();
    }


    // action mode, meu de opções para ações na lista
    ActionMode.Callback callbackMenu = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            if (item.getItemId() == id.aplicarPresenca) {

                prepararFolhaDeChamada(true);
                actionMode.finish();
            }

            if (item.getItemId() == id.aplicarFalta) {
               prepararFolhaDeChamada(false);
               actionMode.finish();
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            removeSelecao();
        }
    };


    // verifica os presentes e os faltantes e adiciona-os à folha de chamada
    private void prepararFolhaDeChamada(boolean isPresente){

        for (int chave = 0; chave < alunosCadastrados.size(); chave++) {

            String idUser = alunosCadastrados.get(chave).getKey();

            /* mapea na folha de chamada o id do aluno e seu respectivo valor de presença,
               true ou false; true na ação aplicar presença, false na ação  retirar presença
             */
            if (indiceValorSelecaoTmp.get(chave)) {
                folhaDeChamada.put(idUser, isPresente);
            }

            // os que não estão selecionados/presentes são mapeados  na folha de registro com false
            if (!folhaDeChamada.containsKey(idUser))
                folhaDeChamada.put(idUser, false);
        }

        salvarFolhaDeChamada(folhaDeChamada);
    }

    // conecta o banco de dados e salva a folha de registro d
    private void salvarFolhaDeChamada(Map registroChamada){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("chamada").child(dataFormatada.format(data));
        databaseReference.setValue(registroChamada);

    }

    // verificar ainda o bug da lista quando termina finaliza o app no mesmo dia e faz outra chamada
    private void consultarListaDeChamada(){
        FirebaseDatabase.getInstance()
                .getReference("chamada").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    final Chamada chamada  = dataSnapshot.getValue(Chamada.class);
                    folhaDeChamada.put(chamada.getIdUser(), chamada.isPresente());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
