package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroLivros extends AppCompatActivity {

    private EditText titulo;
    private EditText autor;
    private EditText categ;
    private LivroDao dao;
    private Livro livro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livros);

        titulo = findViewById(R.id.edtTitulo);
        autor = findViewById(R.id.edtAutor);
        categ = findViewById(R.id.edtCateg);
        dao = new LivroDao(this);

        Intent it = getIntent();
        if (it.hasExtra("livro")){
            livro = (Livro) it.getSerializableExtra("livro");
            titulo.setText(livro.getTitulo());
            autor.setText(livro.getAutor());
            categ.setText(livro.getCateg());
        }

    }

    public void salvar(View view){

        if (livro == null) {
            livro = new Livro();
            livro.setTitulo(titulo.getText().toString());
            livro.setAutor(autor.getText().toString());
            livro.setCateg(categ.getText().toString());

            long id = dao.inserir(livro);

            Toast.makeText(this, "Livro inserido com id: " + id, Toast.LENGTH_SHORT).show();
            finish();
        }else {
            livro.setTitulo(titulo.getText().toString());
            livro.setAutor(autor.getText().toString());
            livro.setCateg(categ.getText().toString());
            dao.alterar(livro);
            Toast.makeText(this, "Livro foi alterado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
