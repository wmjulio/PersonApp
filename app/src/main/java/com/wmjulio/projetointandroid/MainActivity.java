package com.wmjulio.projetointandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.wmjulio.projetointandroid.adapter.PersonAdapter;
import com.wmjulio.projetointandroid.model.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton newBtn;

    private ListView myList;

     SwipeRefreshLayout swipeRefresh;

     ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Carregando...");
        progressDialog.setCancelable(false);


        this.newBtn = findViewById(R.id.idNewBtn);

        this.newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(i);
            }
        });

        swipeRefresh=findViewById(R.id.swiperefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadList(MainActivity.this);

            }
        });


        myList = findViewById(R.id.idItemsList);

        registerForContextMenu(myList);

//        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Person p;
//
//                p = (Person) parent.getItemAtPosition(position);
//
//                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//                intent.putExtra("selectedPerson", p);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onResume() {
        progressDialog.show();
        loadList(MainActivity.this);
        super.onResume();
    }

    public void loadList(final Activity activity){

        Call<List<Person>> call = new RetrofitConfig().getPersonService().findPersons();

        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {

                swipeRefresh.setRefreshing(false);
                progressDialog.cancel();
                List<Person> people = response.body();

                PersonAdapter personAdapter = new PersonAdapter(people, activity);
                ListView myList = findViewById(R.id.idItemsList);
                myList.setAdapter(personAdapter);
                personAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e("Erro", "Erro ao listar as pessoas:" + t.getMessage());
            }
        });

    }


}
