package goulart.rafael.historicojogos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class mostra_todos extends AppCompatActivity{

    List<jogadores> dadosList;
    JogadoresAdapter jogadoresAdapter;
    SQLiteDatabase BDPlacar;
    ListView listViewDados;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostra_todos);

        listViewDados = findViewById(R.id.listarDadosView);

        dadosList = new ArrayList<>();

        BDPlacar = openOrCreateDatabase(MainActivity.BD_Placar, MODE_PRIVATE, null);

        visualizarDadosDatabase();
    }

    private void visualizarDadosDatabase() {
        Cursor cursorDados = BDPlacar.rawQuery("SELECT * FROM jogadores", null);

        if (cursorDados.moveToFirst()) {
            do {
                dadosList.add(new jogadores(
                        cursorDados.getInt(0),
                        cursorDados.getString(1),
                        cursorDados.getString(2),
                        cursorDados.getInt(3),
                        cursorDados.getInt(4),
                        cursorDados.getInt(5)
                ));
            } while (cursorDados.moveToNext());
        }
        cursorDados.close();

        jogadoresAdapter = new  JogadoresAdapter(this, R.layout.lista_view_jogadores, dadosList, BDPlacar);

        listViewDados.setAdapter(jogadoresAdapter);
    }
}