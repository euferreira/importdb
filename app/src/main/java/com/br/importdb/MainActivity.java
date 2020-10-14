package com.br.importdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.br.importdb.dao.BancoDeDados;
import com.br.importdb.modelo.Pessoa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private BancoDeDados mBancoDeDados;
    private ListView lvPessoa;
    private List<Pessoa> pessoaList = new ArrayList<>();
    private ArrayAdapter<Pessoa> pessoaArrayAdapter;

    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        inicializarBancoDeDados();
        popularLista();
        button.setOnClickListener(this);
    }

    private void inicializarComponentes() {
        lvPessoa = (ListView) findViewById(R.id.lvPessoa);
        editText = findViewById(R.id.edtNome);
        button = findViewById(R.id.btninserir);
    }

    private void popularLista() {
        mBancoDeDados = new BancoDeDados(this);
        pessoaList.clear();
        pessoaList = mBancoDeDados.allPessoa();
        pessoaArrayAdapter = new ArrayAdapter<Pessoa>(this, android.R.layout.simple_list_item_1, pessoaList);
        lvPessoa.setAdapter(pessoaArrayAdapter);
    }

    private void inicializarBancoDeDados() {
        mBancoDeDados = new BancoDeDados(this);

        File database = getApplicationContext().getDatabasePath(BancoDeDados.NOMEDB);
        if (!database.exists()) {
            mBancoDeDados.getReadableDatabase();
            if (copiaBanco(this)) {
                alert("Banco copiado com sucesso!");
            } else {
                alert("Erro ao copiar banco de dados");
            }
        } else {
            mBancoDeDados.getReadableDatabase();
            /*if (copiaBanco(this)) {
                alert("Banco copiado com sucesso!");
            }else {
                alert("Erro ao copiar banco de dados");
            }*/
        }
    }

    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean copiaBanco(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(BancoDeDados.NOMEDB);
            String outFile = BancoDeDados.LOCALDB + BancoDeDados.NOMEDB;
            //String outFile = BancoDeDados.NOMEDB;

            OutputStream outputStream = new FileOutputStream(outFile);
            byte[] buff = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btninserir) {
            if (inserir()){
                alert("inseriu");
                popularLista();
            }
            else{
                alert("Nao inseriu");
            }
        }
    }

    private boolean inserir() {
        String nome = editText.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put("nomePessoa", nome);

        if (mBancoDeDados.inserirpessoa(cv) > 0) {
            return true;
        }
        return false;
    }
}
