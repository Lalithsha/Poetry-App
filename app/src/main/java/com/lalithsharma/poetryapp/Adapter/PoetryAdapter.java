package com.lalithsharma.poetryapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.lalithsharma.poetryapp.Api.ApiClient;
import com.lalithsharma.poetryapp.Api.ApiInterface;
import com.lalithsharma.poetryapp.Models.PoetryModel;
import com.lalithsharma.poetryapp.Poetry.UpdatePoetry;
import com.lalithsharma.poetryapp.R;
import com.lalithsharma.poetryapp.Response.DeleteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.ViewHolder> {

    Context context;
    List<PoetryModel> poetryModels;

    ApiInterface apiInterface;

    public PoetryAdapter(Context context, List<PoetryModel> poetryModels) {
        this.context = context;
        this.poetryModels = poetryModels;
        Retrofit retrofit = ApiClient.getClient();
        apiInterface= retrofit.create(ApiInterface.class);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poetry_list_design,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.poetName.setText(poetryModels.get(position).getPoet_name());
        holder.poetry.setText(poetryModels.get(position).getPoetry_data());
        holder.date_time.setText(poetryModels.get(position).getDate_time());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletepoetry(poetryModels.get(position).getId()+"",position);
            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, poetryModels.get(position).getId()+"\n"+ poetryModels.get(position).getPoetry_data(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, UpdatePoetry.class);
                intent.putExtra("p_id",poetryModels.get(position).getId());
                intent.putExtra("p_data",poetryModels.get(position).getPoetry_data());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return poetryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView poetName,poetry,date_time;
        AppCompatButton updateBtn,deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poetName = itemView.findViewById(R.id.textView_poetName);
            poetry = itemView.findViewById(R.id.textView_poetryData);
            date_time = itemView.findViewById(R.id.textView_poetryDataAndTime);

            updateBtn = itemView.findViewById(R.id.update_btn);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

        }
    }

    private void deletepoetry(String id,int position){
        apiInterface.deletepoetry(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                try {

                    if(response!=null){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if(response.body().getStatus().equals("1")){
                            poetryModels.remove(position);
                            notifyDataSetChanged();
                        }
                    }

                } catch (Exception e){
                    Log.e("exp",e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }

}
