package com.registro.componentes.ibjo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.registro.componentes.ibjo.entidade.Aluno;

import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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


    // Construtor público vázio requerido para Firebase
    public FragmentCadastro() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_fragment_cadastro, container,
                false );

        inputLayoutNome = view.findViewById(R.id.textInputLayoutNome);
        textNome = view.findViewById(R.id.textInputNome);

        inputLayoutClasse = view.findViewById(R.id.textInputLayoutClasse);
        MaterialButton buttonCadastrar = view.findViewById(R.id.buttonCadastrar);

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
                checarCampos();
            }
        });

        return view;
    }


    // verifica se os campos estão preenchidos corretamente
    private void checarCampos(){

        setErroCampo(0, null, false);

        if ( Objects.requireNonNull(textNome.getText()).toString().isEmpty() )
            setErroCampo(1, "Digite o nome do Aluno!", true);

        verificarPadraoPreenchimentoNome();

        if ( textClasse.getText().toString().isEmpty() )
            setErroCampo(2, "Selecione a Classe do Aluno!", true);

        // sem erros para proseguir
        if(!inputLayoutClasse.isErrorEnabled()
                && !inputLayoutNome.isErrorEnabled()){
             cadastrarAluno();
             limparCampos();
        }
    }


    // mostrar erros nos campos sem preenchimento
    private void setErroCampo(int idCampo, String msgError, boolean encontrado){

        switch (idCampo){
            case 1:
                inputLayoutNome.setErrorEnabled(encontrado);
                inputLayoutNome.setError(msgError);
                break;
            case 2:
                inputLayoutClasse.setError(msgError);
                inputLayoutClasse.setErrorEnabled(encontrado);
                break;
            default: // deixa os campos sem informação de erro a cada verificação
                inputLayoutNome.setErrorEnabled(encontrado);
                inputLayoutClasse.setErrorEnabled(encontrado);
        }
    }


    // Não aceita números, caracteres especiais e menos de 8 letras
    private void verificarPadraoPreenchimentoNome(){

        Pattern padrao = Pattern.compile("[^a-zA-Z\\s]",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = padrao.matcher(Objects.requireNonNull(textNome.getText()).toString());

        if(matcher.find())
            setErroCampo(1,
                    "Digite o nome completo, utilize letras.", true);

        if (textNome.getText().toString().length() < 10)
            setErroCampo(1,
                    "Por favor, informe o nome completo.", true);
    }


    // limpar campo após realizar cadastro
    private void limparCampos(){

        textNome.setText(null);
        textClasse.setText(null);

        inputLayoutNome.setErrorEnabled(false);
        inputLayoutNome.setErrorEnabled(false);
    }


    // cadastrar aluno
    private void cadastrarAluno(){

        String id   = MainActivity.getBdReferencia().push().getKey();
        String nome = Objects.requireNonNull(textNome.getText()).toString();
        String classe = textClasse.getText().toString();
        String status = "1";
        int matricula = new Random().nextInt(9900)+100;

        Aluno aluno = new Aluno(id, matricula, nome, classe, status);

        assert id != null;
        MainActivity.getBdReferencia().child(id).setValue(aluno);
        limparCampos();

    }

    @Override
    public void onStart() {
        super.onStart();
    }


}
