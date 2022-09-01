package goulart.rafael.historicojogos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class JogadoresAdapter extends ArrayAdapter<jogadores> {
    Context mCtx;
    int listaLayoutRes;
    List<jogadores> listaDados;
    SQLiteDatabase BDPlacar;

    //Construtor da classe
    public JogadoresAdapter(Context mCtx, int listaLayoutRes, List<jogadores> listaDados, SQLiteDatabase BDPlacar) {
        super(mCtx, listaLayoutRes, listaDados);

        this.mCtx = mCtx;
        this.listaLayoutRes = listaLayoutRes;
        this.listaDados = listaDados;
        this.BDPlacar = BDPlacar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listaLayoutRes, null);

        final jogadores jogadores = listaDados.get(position);

        TextView txtViewNome, txtViewDataEntrada, txtViewPife, txtViewBanco, txtViewUno;

        txtViewNome = view.findViewById(R.id.txtNomeViewJogador);
        txtViewPife = view.findViewById(R.id.txtPife);
        txtViewBanco = view.findViewById(R.id.txtBanco);
        txtViewUno = view.findViewById(R.id.txtUno);
        txtViewDataEntrada = view.findViewById(R.id.txtEntradaviewJogador);

        txtViewNome.setText(jogadores.getNome());
        txtViewPife.setText(String.valueOf(jogadores.getPife()));
        txtViewBanco.setText(String.valueOf(jogadores.getBanco()));
        txtViewUno.setText(String.valueOf(jogadores.getUno()));
        txtViewDataEntrada.setText(jogadores.getDataEntrada());

        Button btnExcluir, btnEditar;

        btnExcluir = view.findViewById(R.id.btnExcluirViewJogador);
        btnEditar = view.findViewById(R.id.btnEditarViewJogador);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarDados(jogadores);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Deseja excluir?");
                builder.setIcon(R.drawable.outline_cancel);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM jogadores WHERE id = ?";

                        BDPlacar.execSQL(sql, new Integer[]{jogadores.getId()});
                        recarregarJogadoresDB();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recarregarJogadoresDB();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    public void alterarDados(final jogadores jogadores) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.caixa_alterar_dados, null);
        builder.setView(view);

        final EditText txtEditarNome = view.findViewById(R.id.txtEditarNome);
        final EditText txtEditarPife = view.findViewById(R.id.txtEditarPife);
        final EditText txtEditarBanco = view.findViewById(R.id.txtEditarBanco);
        final EditText txtEditarUno = view.findViewById(R.id.txtEditarUno);

        txtEditarNome.setText(jogadores.getNome());
        txtEditarPife.setText(String.valueOf(jogadores.getPife()));
        txtEditarBanco.setText(String.valueOf(jogadores.getBanco()));
        txtEditarUno.setText(String.valueOf(jogadores.getUno()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnAlterarDados).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = txtEditarNome.getText().toString().trim();
                String pife = txtEditarPife.getText().toString().trim();
                String banco = txtEditarBanco.getText().toString().trim();
                String uno = txtEditarUno.getText().toString().trim();

                if (nome.isEmpty()) {
                    txtEditarNome.setError("Nome está em branco!");
                    txtEditarNome.requestFocus();
                    return;
                }

                if (pife.isEmpty()) {
                    txtEditarPife.setError("Pife está em branco!");
                    txtEditarPife.requestFocus();
                    return;
                }

                if (banco.isEmpty()) {
                    txtEditarBanco.setError("Banco está em branco!");
                    txtEditarBanco.requestFocus();
                    return;
                }

                if (uno.isEmpty()) {
                    txtEditarUno.setError("Uno está em branco!");
                    txtEditarUno.requestFocus();
                    return;
                }

                String sql = "UPDATE jogadores SET nome = ?, pife = ?, banco = ?, uno = ? WHERE id = ?";
                BDPlacar.execSQL(sql,
                        new String[]{nome, pife, banco, uno, String.valueOf(jogadores.getId())});
                Toast.makeText(mCtx, "Dados Alterados", Toast.LENGTH_LONG).show();

                recarregarJogadoresDB();

                dialog.dismiss();
            }
        });
    }

    public void recarregarJogadoresDB() {

        Cursor cursorDados = BDPlacar.rawQuery("SELECT * FROM jogadores", null);
        if (cursorDados.moveToFirst()) {
            listaDados.clear();
            do {
                listaDados.add(new jogadores(
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
        notifyDataSetChanged();
    }
}
