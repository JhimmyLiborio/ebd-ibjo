package com.registro.componentes.ibjo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCadastro extends Fragment {

    // itens para o Menu: "dropdown_menu_categoria_classe.xml"
    private String [] categoriaClasse =  {"Crianças", "Adolescente", "Jovens",
            "Mulheres", "Homens"};

    public FragmentCadastro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_fragment_cadastro, container,
                false );


        // Associa os itens do Array "categoriaClasse" ao layout dropdown_menu_categoria_classe.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.dropdown_menu_categoria_classe,
                categoriaClasse);

        //Seta a associação no compomente AutoCompleteTextView
        AutoCompleteTextView categoriasMenu =  view.findViewById(R.id.autoCompleteDropdownMenu);
        categoriasMenu.setAdapter(adapter);

        return view;
    }

}
