package com.naturewallpaper.viedoplayerdemo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;

import com.naturewallpaper.viedoplayerdemo.R;
import com.naturewallpaper.viedoplayerdemo.adapter.ViedoAdapter;
import com.naturewallpaper.viedoplayerdemo.classes.Viedomodel;

import java.security.Provider;
import java.util.ArrayList;

public class ViedoActivity extends AppCompatActivity {
  RecyclerView ViedoRecycler;
public  static   ViedoAdapter viedoAdapter;
 public static ArrayList<Viedomodel> viedomodelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viedo);
        ViedoRecycler=findViewById(R.id.ViedoRecycler);
        ViedoRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
       getALLViedo();

    }
    public  void   getALLViedo(){
        viedomodelArrayList= new ArrayList<>();
        ContentResolver viedoResolver= getContentResolver();
        Uri ViedoUri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor ViedoCursor= viedoResolver.query(ViedoUri,null,null,null,null);
            if (ViedoCursor!=null && ViedoCursor.moveToFirst()) {
                int titleColumn = ViedoCursor.getColumnIndex(MediaStore.Video.Media.TITLE);
                int durationColumn = ViedoCursor.getColumnIndex(MediaStore.Video.Media.DURATION);
                int dataColumn = ViedoCursor.getColumnIndex(MediaStore.Video.Media.DATA);
         do {
             String thistitle= ViedoCursor.getString(titleColumn);
                 long thisduration= ViedoCursor.getLong(durationColumn);
                 String string=timeConversion(thisduration);
             String thisdata = ViedoCursor.getString(dataColumn);
             Uri uri = Uri.parse("file:///" + thisdata);
             viedomodelArrayList.add(new Viedomodel(thistitle,string,uri));

         } while (ViedoCursor.moveToNext());

            }
        viedoAdapter = new ViedoAdapter(this,viedomodelArrayList);
        ViedoRecycler.setAdapter(viedoAdapter);
        viedoAdapter.setOnItemClickListener(new ViedoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Intent intent = new Intent(getApplicationContext(),ViedoPlay.class);
                intent.putExtra("pos",pos);
                startActivity(intent);
            }
        });

    }
    public String timeConversion(long value) {
        String videoTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }
}