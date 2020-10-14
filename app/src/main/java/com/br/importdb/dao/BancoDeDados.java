package com.br.importdb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.ListView;

import com.br.importdb.modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados extends SQLiteOpenHelper {
    public static final String NOMEDB = "dbpessoa.db";
    ////public static final String LOCALDB = "/data/data/com.br.importdb/databases/";
    public static final String LOCALDB = "/data/data/com.br.importdb/databases/";
    public static final int VERSAO = 1;
    private Context mContext;
    private SQLiteDatabase mSqlLiteDatabase;

    public BancoDeDados(Context context) {
        super(context, NOMEDB, null, VERSAO);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(NOMEDB).getPath();
        if (mSqlLiteDatabase != null && mSqlLiteDatabase.isOpen()) {
            return;
        }
        mSqlLiteDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mSqlLiteDatabase != null) {
            mSqlLiteDatabase.close();
        }
    }

    public long inserirpessoa(ContentValues contentValues) {
        openDatabase();
        mSqlLiteDatabase = this.getWritableDatabase();
        long result = mSqlLiteDatabase.insert("Pessoa", null, contentValues);
        mSqlLiteDatabase.close();
        return result;
    }

    public List<Pessoa> allPessoa() {
        openDatabase();
        Cursor cursor = null;
        String sql = "";
        List<Pessoa> pessoaList = new ArrayList<>();
        try {
            mSqlLiteDatabase = this.getWritableDatabase();
            sql = "SELECT * FROM Pessoa ORDER BY nomePessoa ASC";
            cursor = mSqlLiteDatabase.rawQuery(sql, null);
            try {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Pessoa pessoa = new Pessoa();
                            pessoa.setIdPessoa(cursor.getInt(0));
                            pessoa.setNomePessoa(cursor.getString(1));
                            pessoa.setSobrenomePessoa(cursor.getString(2));
                            pessoaList.add(pessoa);
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        cursor.close();
        mSqlLiteDatabase.close();
        return pessoaList;
    }

}
