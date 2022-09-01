package goulart.rafael.historicojogos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.view.Menu;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public static final String BD_Placar = "dbPlacar.db";

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MyFragmentAdapter adapter;

    SQLiteDatabase BDPlacar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);

        tabLayout.addTab(tabLayout.newTab().setText("Pife"));
        tabLayout.addTab(tabLayout.newTab().setText("Banco"));
        tabLayout.addTab(tabLayout.newTab().setText("Uno"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MyFragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        BDPlacar = openOrCreateDatabase(BD_Placar, MODE_PRIVATE, null);
        criarTabelaBanco();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adicionar:
                startActivity(new Intent(getApplicationContext(), AdicionarActivity.class));
                break;
            case R.id.listaJogadores:
                startActivity(new Intent(getApplicationContext(), mostra_todos.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void criarTabelaBanco() {
        BDPlacar.execSQL(
                "CREATE TABLE IF NOT EXISTS jogadores (" +
                        "id integer PRIMARY KEY AUTOINCREMENT," +
                        "nome varchar(200) NOT NULL," +
                        "dataEntrada datetime NOT NULL," +
                        "pife integer NOT NULL," +
                        "banco integer NOT NULL," +
                        "uno integer NOT NULL);"
        );
    }
}