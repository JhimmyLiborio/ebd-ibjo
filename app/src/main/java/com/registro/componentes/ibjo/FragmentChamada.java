package com.registro.componentes.ibjo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.registro.componentes.ibjo.entidade.Aluno;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChamada extends Fragment {

    private List<Aluno> listAlunos;



    public FragmentChamada() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        listAlunos = new ArrayList<>();

        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_fragment_chamada, container,
                false );

        // Instancia RecyclerView, componente que posicionará a lista na tela
        final RecyclerView meuRecyclerView = view.findViewById(R.id.myRecyclerView);


        // Ler dados da referência e detectar alterações
        MainActivity.getAlunosBD().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Aluno aluno = dataSnapshot.getValue(Aluno.class);
                    listAlunos.add(aluno);
                }

                // Classe responsável de associar os item da lista a view correspondente
                ListaAlunosAdapter listAlunosAdapter = new ListaAlunosAdapter(listAlunos);


                // Exibir Recycler View
                meuRecyclerView.setAdapter(listAlunosAdapter);

                Log.d("Tamanho TAB!", String.valueOf(listAlunos.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

}
