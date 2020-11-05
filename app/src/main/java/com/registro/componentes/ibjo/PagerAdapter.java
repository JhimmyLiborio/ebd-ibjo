package com.registro.componentes.tablayout_viewpager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PagerAdapter extends FragmentPagerAdapter {


    private  final int  numeroDeTabs;

   PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super( fm, behavior );
        this.numeroDeTabs = behavior;
    }


    /*public PagerAdapter(@NonNull FragmentManager fm, int  numeroDeTabs) {
        super( fm );
        this.numeroDeTabs = numeroDeTabs;
    }*/


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                Log.d("Fragment", "Fragment Chamada");
                return new FragmentChamada();

            case 1:
                Log.d("Fragment", "Fragment Cadastro");
                return  new FragmentCadastro();

            case 2:
                Log.d("Fragment", "Fragment Resumo");
                return new FragmentResumo();

            default: return  null;
        }
    }




    @Override
    public int getCount() {
        return numeroDeTabs;
    }
}
