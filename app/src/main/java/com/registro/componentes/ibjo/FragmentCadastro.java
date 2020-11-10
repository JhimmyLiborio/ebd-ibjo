package com.registro.componentes.ibjo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCadastro extends Fragment {

    // itens para o Menu: "dropdown_menu_categoria_classe.xml"
    private final String [] categoriaClasse =  {"Crianças", "Adolescente", "Jovens",
            "Mulheres", "Homens"};

    // campos de informação: nome  e classe
    private TextInputLayout inputLayoutNome;
    private TextInputLayout inputLayoutClasse;

    private TextInputEditText textNome;
    private AutoCompleteTextView textClasse;

    private MaterialButton buttonCadastrar;


    public FragmentCadastro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view =  inflater.inflate( R.layout.fragment_fragment_cadastro, container,
                    false );

            inputLayoutNome = view.findViewById(R.id.textInputLayoutNome);
            inputLayoutClasse = view.findViewById(R.id.textInputLayoutClasse);
            buttonCadastrar = view.findViewById(R.id.buttonCadastrar);

            // Associa os itens do Array "categoriaClasse" ao layout dropdown_menu_categoria_classe.xml
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    R.layout.dropdown_menu_categoria_classe,
                    categoriaClasse);

            //Seta a associação no compomente AutoCompleteTextView
            textClasse =  view.findViewById(R.id.autoCompleteDropdownMenu);
            textClasse.setAdapter(adapter);

            
            //dispara ação do click "botão cadastrar"
            buttonCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make( v.getRootView(), R.string.tab3, BaseTransientBottomBar.LENGTH_LONG)
                            .show();
                }
            });

            return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }


}
