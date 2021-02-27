package com.registro.componentes.ibjo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.registro.componentes.ibjo.entidade.Aluno;
import com.registro.componentes.ibjo.entidade.Chamada;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.registro.componentes.ibjo.FragmentChamada.getListAlunos;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentResumo extends Fragment {

    private static View view;
    private Button buttonExpande;
    private CardView cardView;
    private TableLayout tableLayout;

    private TextView textViewData, textViewMatriculados, textViewPresentes,
            textViewAusentes, textViewVisitante;

    private Date data;
    private DateFormat dateFormat;

    private static int matriculados, presentes, visitantes, ausentes, desativados;

    public FragmentResumo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_resumo, container, false);

        cardView = view.findViewById(R.id.cardview);
        tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);
        buttonExpande = view.findViewById(R.id.buttonExpandir);

        // mapeamento
        textViewData = view.findViewById(R.id.subtituloData);
        textViewMatriculados = view.findViewById(R.id.dadosMatriculados);
        textViewPresentes = view.findViewById(R.id.dadosPresentes);
        textViewAusentes = view.findViewById(R.id.dadosAusentes);
        textViewVisitante = view.findViewById(R.id.dadosVisitantes);

        //
        buttonExpande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificar se tablelayout está invisivel
                if (tableLayout.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    tableLayout.setVisibility(View.VISIBLE);

                    buttonExpande.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24px);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    tableLayout.setVisibility(View.GONE);
                    buttonExpande.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24px);
                }
            }
        });

        data = Calendar.getInstance().getTime();
        dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        resumoRelatórioChamadaBD(dateFormat.format(data));
        popularResumo();
        return view;
    }


    //seta os dados do resumo
    private void popularResumo() {

        //data
        textViewData.setText(dateFormat.format(data));

        // verificar perfis de desativados  cadastro de alunos
        for (int i = 0; i < getListAlunos().size(); i++) {
            Aluno aluno = getListAlunos().get(i);
            if (aluno.getStatus().equals("0"))
                desativados++;
        }

        // matriculados = todos menos os perfis desativados
        matriculados = getListAlunos().size();
        matriculados -= desativados;

        Log.d("Presentes", ""+presentes);

        // vincula dados do resumo na tela
        textViewMatriculados.setText(String.valueOf(matriculados));


    }

    //  consulta o registro de chamadas
    private void resumoRelatórioChamadaBD(String data) {

        ausentes = 0; presentes = 0; visitantes = 0; matriculados = 0;
        
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("chamada").child(data);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                     Chamada chamada = dataSnapshot.getValue(Chamada.class);
                    if (chamada.isPresente()) {
                        presentes++;
                        Log.d("Chamada Resumo Presente"+presentes, chamada.getIdUser());
                    } else {
                        ausentes++;
                        Log.d("Chamada Resumo Ausente"+ausentes, chamada.getIdUser());
                    }

                }

                textViewPresentes.setText(String.valueOf(presentes));
                textViewAusentes.setText(String.valueOf(ausentes));
                textViewVisitante.setText(String.valueOf(visitantes));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

