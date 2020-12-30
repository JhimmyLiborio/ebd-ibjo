package com.registro.componentes.ibjo;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

import com.google.android.material.card.MaterialCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentResumo extends Fragment {

    private static View view;
    private Button buttonExpande;
    private CardView cardView;
    private TableLayout tableLayout;

    public FragmentResumo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_fragment_resumo, container, false);

        cardView = view.findViewById(R.id.cardview);
        tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);
        buttonExpande = view.findViewById(R.id.buttonExpandir);

        buttonExpande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificar se tablelayout está invisivel
                if(tableLayout.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    tableLayout.setVisibility(View.VISIBLE);

                    buttonExpande.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_24px);
                }else{
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    tableLayout.setVisibility(View.GONE);
                    buttonExpande.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_24px);
                }
            }
        });




        return  view;
    }



}
