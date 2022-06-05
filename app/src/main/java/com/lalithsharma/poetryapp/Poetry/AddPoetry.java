package com.lalithsharma.poetryapp.Poetry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lalithsharma.poetryapp.Api.ApiClient;
import com.lalithsharma.poetryapp.Api.ApiInterface;
import com.lalithsharma.poetryapp.R;
import com.lalithsharma.poetryapp.Response.DeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {

    Toolbar toolbar;
    EditText poetName,poetryData;
    AppCompatButton submitBtn;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poetry);

        intialization();
        setUpToolbar();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetryDataString = poetryData.getText().toString();
                String poetryNameString = poetName.getText().toString();

                if(poetryDataString.equals("")){
                    poetryData.setError("Field is empty");
                }
                else {
                    if (poetryNameString.equals("")) {
                        poetName.setError("Field is empty");
                    }
                    else {
                        Toast.makeText(AddPoetry.this, "Call Api", Toast.LENGTH_SHORT).show();
                        callApi(poetryDataString,poetryNameString);

                    }
                }


            }
        });

    }

    private void intialization(){
        toolbar = findViewById(R.id.add_poetry_toolbar);
        poetName = findViewById(R.id.add_poetry_name_edittext);
        poetryData = findViewById(R.id.add_poetry_data_edittext);
        submitBtn = findViewById(R.id.submit_data_btn);
        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);


    }

    private void setUpToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(AddPoetry.this, "Activity Finish", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void callApi(String poetryData,String poetName){
        apiInterface.addpoetry(poetryData,poetName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(AddPoetry.this, "Edit successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AddPoetry.this, "Edit not successfully", Toast.LENGTH_SHORT).show();
                    }
                }  catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }




}