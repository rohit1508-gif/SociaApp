package com.example.sociaapp.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sociaapp.Adapter.RandomAdapter;
import com.example.sociaapp.ModalClass.Random;
import com.example.sociaapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoFragment extends Fragment {
    RecyclerView recyclerView;
    RandomAdapter adapter;
    List<String> list;
    Context ctx;
    String breed;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        Bundle bundle = this.getArguments();
        breed=bundle.getString("breed");
        recyclerView = view.findViewById(R.id.recycler_view);
        ctx =getActivity();
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getreccyclerview();
        return view;
    }
    public void getreccyclerview(){
        String url="https://dog.ceo/api/breed/"+breed+"/images/random/10";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(ctx));
        ProgressDialog pd = ProgressDialog.show(ctx,null,"Please wait");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(pd!=null && pd.isShowing())
                        pd.dismiss();
                    JSONArray arr=response.getJSONArray("message");
                    for(int i=0;i<arr.length();i++) {
                        list.add(arr.getString(i));
                    }
                    adapter = new RandomAdapter(list,ctx);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(pd!=null && pd.isShowing())
                    pd.dismiss();
            }
        });
        requestQueue.add(request);
    }
}
