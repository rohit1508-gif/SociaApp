package com.example.sociaapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sociaapp.Fragment.InfoFragment;
import com.example.sociaapp.ModalClass.Home;
import com.example.sociaapp.ModalClass.Random;
import com.example.sociaapp.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    List<String> list;
    Context ctx;
    public HomeAdapter(List<String> list,Context ctx){
        this.list=list;
        this.ctx=ctx;
    }
    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        String l=list.get(position);
        holder.b.setText(l);
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new InfoFragment();
                FragmentManager fragmentManager = ((FragmentActivity) ctx).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container1, someFragment );
                transaction.addToBackStack(null);
                Bundle bundle = new Bundle();
                bundle.putString("breed",l);
                someFragment.setArguments(bundle);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button b;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            b=itemView.findViewById(R.id.name);
        }
    }
}
