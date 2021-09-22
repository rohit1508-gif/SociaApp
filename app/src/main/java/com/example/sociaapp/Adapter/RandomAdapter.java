package com.example.sociaapp.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sociaapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static androidx.core.app.ActivityCompat.requestPermissions;
import static java.security.AccessController.getContext;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {
    List<String> list;
    Context ctx;
    public RandomAdapter(List<String> list,Context ctx){
        this.list=list;
        this.ctx=ctx;
    }
    @NonNull
    @Override
    public RandomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_random, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RandomAdapter.ViewHolder holder, int position) {
        String l = list.get(position);
        Picasso.get().load(l).into(holder.img);
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(ctx,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Permission error","You have permission");
                        download(l);
                    } else {
                        Log.e("Permission error","You have asked for permission");
                        requestPermissions((Activity) ctx, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
                else { //you dont need to worry about these stuff below api level 23
                    Log.e("Permission error","You already have the permission");
                    download(l);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button b;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            b=itemView.findViewById(R.id.link);
            img=itemView.findViewById(R.id.img);
        }
    }
    public void download(String l){
        try{
            DownloadManager dm = (DownloadManager) ctx.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(l);
            long t=System.currentTimeMillis();
            String time= String.valueOf(t);
            String filename=time+"SociaApp";
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(filename)
                    .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,File.separator + filename + ".jpg");
            dm.enqueue(request);
            Toast.makeText(ctx, "Image download started.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.i("failed",e.toString());
            Toast.makeText(ctx, "Image download failed.", Toast.LENGTH_SHORT).show();
        }
    }
}
