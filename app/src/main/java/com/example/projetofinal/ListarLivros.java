package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarLivros extends AppCompatActivity {



    private ListView listView;
    private LivroDao dao;
    private List<Livro> livros;
    private List<Livro> livrosFiltrados = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_livros);

        listView = findViewById(R.id.lstLivro);
        dao = new LivroDao(this);
        livros = dao.obterTodos();
        livrosFiltrados.addAll(livros);

       // ArrayAdapter<Livro> adaptador = new ArrayAdapter<Livro>(this, android.R.layout.simple_list_item_1, livrosFiltrados);
        LivroAdaptater adaptador = new LivroAdaptater(this, livrosFiltrados);
        listView.setAdapter(adaptador);

        registerForContextMenu(listView);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraLivro(s);
                return false;
            }
        });
        return true;
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procuraLivro(String titulo){
        livrosFiltrados.clear();
        for(Livro livro : livros){
            if(livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())){
                livrosFiltrados.add(livro);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Livro livroExcluir = livrosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Atenção").setMessage("Deseja realmente excluir esse livro ?").setNegativeButton("Não", null).setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                livrosFiltrados.remove(livroExcluir);
                livros.remove(livroExcluir);
                dao.excluir(livroExcluir);
                listView.invalidateViews();
            }
        }).create();
        dialog.show();

    }

    public void alterar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Livro livroAlterar = livrosFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, CadastroLivros.class);
        it.putExtra("livro", livroAlterar);
        startActivity(it);
    }


    public void cadastrar(MenuItem item){

        Intent it = new Intent(this, CadastroLivros.class);
        startActivity(it);
    }

    public void onResume(){
        super.onResume();
        livros = dao.obterTodos();
        livrosFiltrados.clear();
        livrosFiltrados.addAll(livros);
        listView.invalidateViews();
    }

}
