package com.registro.componentes.ibjo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        // Lista Dinâmica na Memoria
        listAlunos = new ArrayList<>();
        Aluno aluno = new Aluno("1234", 1234, "Joel Jhimmy Ramos Liborio Neri",
                "Homens", "1");
        Aluno aluno2 = new Aluno("1234", 1234, "Mirella Epifânio Mesquita",
                "Mulheres", "1");

        Aluno aluno3 = new Aluno("1234", 1234, "Nicoloe Luane",
                "Crianças", "1");

        listAlunos.add(aluno);
        listAlunos.add(aluno2);
        listAlunos.add(aluno3);
        listAlunos.add(aluno);
        listAlunos.add(aluno2);

        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_fragment_chamada, container,
                false );

        // Instancia RecyclerView, componente que posicionará a lista na tela
        RecyclerView meuRecyclerView = view.findViewById(R.id.myRecyclerView);

        // Classe responsável de associar os item da lista a view correspondente
        ListaAlunosAdapter listAlunosAdapter = new ListaAlunosAdapter(listAlunos);


        // Exibir Recycler View
        meuRecyclerView.setAdapter(listAlunosAdapter);


        return view;
    }

}
