package com.wmjulio.projetointandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wmjulio.projetointandroid.model.Person;
import com.wmjulio.projetointandroid.service.DetailsHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsActivity extends AppCompatActivity {

    private Button btnSave;
    private Person person;
    private DetailsHelper helper;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        helper = new DetailsHelper(this);

        progressDialog = new ProgressDialog(DetailsActivity.this);
        progressDialog.setMessage("Salvando...");
        progressDialog.setCancelable(false);

        Intent intent = this.getIntent();
        this.person = (Person) intent.getSerializableExtra("selectedPerson");

        if(this.person != null){
            this.helper.putPerson(this.person);
        }

        btnSave = findViewById(R.id.idSaveBtn);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Person p = helper.pickPerson();

                Call<Person> call;

                if(p.getId() == null){
                    call = new RetrofitConfig().getPersonService().addPerson(p);
                }else{
                    //Atualizar
                    call = new RetrofitConfig().getPersonService().attPerson(p.getId(), p);
                }

                call.enqueue(new Callback<Person>() {
                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {
                        progressDialog.cancel();
                        onBackPressed();
//                        Intent i = new Intent(DetailsActivity.this, MainActivity.class);
//                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {
                        progressDialog.cancel();
                        Log.e("Erro", "Erro  ao manipular pessoa:" + t.getMessage());
                    }
                });
            }
        });


    }
}
