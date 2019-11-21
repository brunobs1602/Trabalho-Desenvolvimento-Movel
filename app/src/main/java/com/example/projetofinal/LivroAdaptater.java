package com.example.projetofinal;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LivroAdaptater extends BaseAdapter {

    private List<Livro> livros;
    private Activity activity;

    public LivroAdaptater(Activity activity, List<Livro> livros) {
        this.activity = activity;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int i) {
        return livros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return livros.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = activity.getLayoutInflater().inflate(R.layout.item, viewGroup,false );
        TextView titulo = v.findViewById(R.id.txtTitulo);
        TextView autor = v.findViewById(R.id.txtAutor);
        TextView categoria = v.findViewById(R.id.txtCategoria);
        Livro livro = livros.get(i);
        titulo.setText(livro.getTitulo());
        autor.setText(livro.getAutor());
        categoria.setText(livro.getCateg());

        return v;
    }
}
