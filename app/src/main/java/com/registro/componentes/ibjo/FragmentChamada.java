package com.registro.componentes.ibjo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.registro.componentes.ibjo.entidade.Aluno;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class FragmentChamada extends Fragment  {

    private static View view;
    private static RecyclerView meuRecyclerView;
    private static List<Aluno> listAlunos;
    private static ListaAlunosAdapter listAlunosAdapter;
    private int contador;

    //ActionMode para Toolbar
    private ActionMode actionMode;


    public FragmentChamada() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_chamada, container,
                false);
        popularListaView();
        //cliquesLista();
        return view;
    }


    // popular a lista com RecyclerView
    private void popularListaView() {

        // RecyclerView, componente que posicionará o conteúdo da lista na tela
        meuRecyclerView = view.findViewById(R.id.myRecyclerView);

        // Dado da lista
        listAlunos = new ArrayList<>();

        // Conexão com o BD e leitura de dados
        MainActivity.getBdReferencia().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAlunos.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    final  Aluno aluno = dataSnapshot.getValue(Aluno.class);
                    listAlunos.add(aluno);
                }
                // Associa os dados ao items correspondentes
                listAlunosAdapter = new ListaAlunosAdapter(listAlunos, getActivity());

                // Posiciona a lista
                meuRecyclerView.setAdapter(listAlunosAdapter);
                meuRecyclerView.setHasFixedSize(true);
                listAlunosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public static List< Aluno > getListAlunos() {
        return listAlunos;
    }
}
