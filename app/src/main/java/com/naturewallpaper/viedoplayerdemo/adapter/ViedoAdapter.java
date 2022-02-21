package com.naturewallpaper.viedoplayerdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naturewallpaper.viedoplayerdemo.R;
import com.naturewallpaper.viedoplayerdemo.classes.Viedomodel;

import java.util.ArrayList;

public class ViedoAdapter extends RecyclerView.Adapter<ViedoAdapter.ViewHolder> {
    Context context;
    ArrayList<Viedomodel> viedomodelArrayList;
    public OnItemClickListener onItemClickListener;
    public ViedoAdapter(Context context, ArrayList<Viedomodel>viedomodelArrayList){
        this.context=context;
        this.viedomodelArrayList=viedomodelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater= LayoutInflater.from(parent.getContext());
      View view= inflater.inflate(R.layout.viedo_list,parent,false);
        return new ViedoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          holder.title.setText(viedomodelArrayList.get(position).getVideoTitle());
          holder.duration.setText(viedomodelArrayList.get(position).getVideoDuration());
    }

    @Override
    public int getItemCount() {
        return viedomodelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageVIEDO;
        TextView title,duration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageVIEDO=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            duration=itemView.findViewById(R.id.duration);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(),v);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}
