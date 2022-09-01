package goulart.rafael.historicojogos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdicionarActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String BD_Placar = "dbPlacar.db";

    EditText txtNomeJogador;

    Button btnAdicionarJogador;

    SQLiteDatabase BDPlacar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        txtNomeJogador = findViewById(R.id.txtNomeNovoJogador);

        btnAdicionarJogador = findViewById(R.id.btnAdicionarJogador);

        btnAdicionarJogador.setOnClickListener(this);

        BDPlacar = openOrCreateDatabase(BD_Placar, MODE_PRIVATE, null);

        criarTabelaBanco();
    }

    private boolean verificarEntrada(String nome) {
        if (nome.isEmpty()) {
            txtNomeJogador.setError("Por favor insira o Nome");
            txtNomeJogador.requestFocus();
            return false;
        }
        return true;
    }

    private void AdicionarJogador() {

        String nomeJog = txtNomeJogador.getText().toString().trim();
        String mandaZero = "0";

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dataEntrada = simpleDateFormat.format(calendar.getTime());

        if (verificarEntrada(nomeJog)) {

            String insertSQL = "INSERT INTO jogadores (" +
                    "nome, " +
                    "dataEntrada, " +
                    "pife, " +
                    "banco, " +
                    "uno)" +
                    "VALUES(?, ?, ?, ?, ?);";

            BDPlacar.execSQL(insertSQL, new String[]{nomeJog, dataEntrada, mandaZero, mandaZero, mandaZero});

            Toast.makeText(getApplicationContext(), "Jogador Adicionado!", Toast.LENGTH_SHORT).show();

            limparCadastro();
        }
    }

    public void limparCadastro() {
        txtNomeJogador.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdicionarJogador:
                AdicionarJogador();
                break;
        }
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