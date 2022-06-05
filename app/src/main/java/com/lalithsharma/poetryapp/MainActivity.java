package com.lalithsharma.poetryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lalithsharma.poetryapp.Adapter.PoetryAdapter;
import com.lalithsharma.poetryapp.Api.ApiClient;
import com.lalithsharma.poetryapp.Api.ApiInterface;
import com.lalithsharma.poetryapp.Models.PoetryModel;
import com.lalithsharma.poetryapp.Poetry.AddPoetry;
import com.lalithsharma.poetryapp.Response.GetPoetryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PoetryAdapter poetryAdapter;
    List<PoetryModel> poetryModelList = new ArrayList<>();
    ApiInterface apiInterface;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



      /*  poetryModelList.add(new PoetryModel(1,"Shaam ka dhalta sooraj hu main\n" +
                "Halki iss aag mei sulagta hu main\n" +
                "Raat ka andhera jab utar jaye\n" +
                "Mere iss tootey hue badan me\n" +
                "Sharaab ki botal mei doobti tishnagi sa jhoomta hu main\n" +
                "Iss geley kagaz per likhe sabad karte to ha wafa mujhse\n" +
                "Naa jaane kyun fir zindagi se khafa hu main… ","me","31-05-2022"));*/

        initialization();
        setSupportActionBar(toolbar);
        getdata();
        setadapter(poetryModelList);

    }

    private void initialization() {
        recyclerView = findViewById(R.id.poetry_recyclerView);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        toolbar = findViewById(R.id.main_toolbar);
    }

    private void setadapter(List<PoetryModel> poetryModels) {
        poetryAdapter = new PoetryAdapter(this, poetryModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(poetryAdapter);
    }

    private void getdata() {

        apiInterface.getpoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response) {

                try {
                    if (response != null) {
                        if (response.body().getStatus().equals("1")) {
                            setadapter(response.body().getData());
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_poetry:
                // Toast.makeText(this, "Add Poetry Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,AddPoetry.class);
                startActivity(intent);
                return true;

            default:
            return super.onOptionsItemSelected(item);
        }
    }
}