package com.wmjulio.projetointandroid.service;

import android.widget.EditText;

import com.wmjulio.projetointandroid.DetailsActivity;
import com.wmjulio.projetointandroid.R;
import com.wmjulio.projetointandroid.model.Person;

public class DetailsHelper {

    private DetailsActivity da;

    private EditText etName;
    private EditText etLast;


    private Person person;

    public DetailsHelper(DetailsActivity da) {
        this.person = new Person();

        this.etName = da.findViewById(R.id.idName);
        this.etLast = da.findViewById(R.id.idLast);
    }

    public Person pickPerson(){
        person.setFirstName(etName.getText().toString());
        person.setLastname(etLast.getText().toString());

        return person;
    }

    public void putPerson(Person person){
        etName.setText(person.getFirstName());
        etLast.setText(person.getLastname());

        this.person = person;
    }


}
