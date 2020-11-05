package com.registro.componentes.ibjo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    // inicialização de variáveis
    private TabLayout tabLayout;
    private TabItem  tabItemCadastro, tabItemChamada, tabItemResumo;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        //Recupera uma instancia do Banco de Dados
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference minhaReferencia =  database.getReference("pessoas");
        Log.d("REFERENCIA BANCO", minhaReferencia.getDatabase().toString());

        // referência UI Elementos
        tabLayout = findViewById( R.id.tabLayout );
        tabItemCadastro = findViewById( R.id.tabItemCadastro );
        tabItemChamada = findViewById( R.id.tabItemChamada );
        tabItemResumo = findViewById( R.id.tabItemResumo );
        viewPager = findViewById( R.id.viewPageMain );


        // Prepara e retorna um Fragment
        final PagerAdapter pagerAdapter = new PagerAdapter( getSupportFragmentManager(),
                tabLayout.getTabCount() );


        // Configura o Fragment no Elemento ViewPager
        viewPager.setAdapter(pagerAdapter);


        // altera as visualizações das tabs quando a tab é selecionada ou clicada
        tabLayout.addOnTabSelectedListener( new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem( tab.getPosition() );

                Log.d("TAB SELECIONADA", String.valueOf(tab.getPosition()));

                if(tab.getPosition() == 0)
                    pagerAdapter.notifyDataSetChanged();
                if(tab.getPosition() == 1)
                    pagerAdapter.notifyDataSetChanged();
                if(tab.getPosition() == 2)
                    pagerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                tabLayout));
    }
}
