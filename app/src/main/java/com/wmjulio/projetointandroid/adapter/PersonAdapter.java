package com.wmjulio.projetointandroid.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wmjulio.projetointandroid.DetailsActivity;
import com.wmjulio.projetointandroid.MainActivity;
import com.wmjulio.projetointandroid.R;
import com.wmjulio.projetointandroid.RetrofitConfig;
import com.wmjulio.projetointandroid.model.Person;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonAdapter extends BaseAdapter {

    private final List<Person> people;
    private final Activity activity;
    private final PersonAdapter adapter;

    ProgressDialog progressDialog;

    public PersonAdapter(List<Person> people, Activity activity) {
        this.people = people;
        this.activity = activity;

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Excluindo...");
        progressDialog.setCancelable(false);
        adapter = this;

    }

    @Override
    public int getCount() {
        return people.size();
    }

    @Override
    public Object getItem(int position) {
        return people.get(position);
    }

    @Override
    public long getItemId(int position) {
        return people.get(position).getId();
    }

    @Override
    public View getView(final int position, final View view, final ViewGroup viewGroup) {
        View line = view;


        final Person p = people.get(position);

        if(line==null){
            line = this.activity.getLayoutInflater().inflate(R.layout.cell_person, viewGroup, false);
        }

        TextView tvName = line.findViewById(R.id.idNameCell);
        TextView tvLast = line.findViewById(R.id.idLastCell);
        ImageView imageView = line.findViewById(R.id.imageView);
        LinearLayout linearLayout = line.findViewById(R.id.llPerson);


        // Editar
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, DetailsActivity.class);
                intent.putExtra("selectedPerson", people.get(position));
                activity.startActivity(intent);
            }
        });

        // EXCLUIR
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                //Person p = helper.pickPerson();

                Call<ResponseBody> call = new RetrofitConfig().getPersonService().deletePerson(p.getId());


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //people.remove(p);
                        people.remove(position);
                        adapter.notifyDataSetChanged();
                        progressDialog.cancel();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println(call);
                        progressDialog.cancel();
                        Log.e("Erro", "Erro  ao manipular pessoa:" + t.getMessage());
                    }
                });
            }
        });


        tvName.setText(p.getFirstName());
        tvLast.setText(p.getLastname());

        return line;

    }
}
