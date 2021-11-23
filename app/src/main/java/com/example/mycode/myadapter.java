package com.example.mycode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<model_class,myadapter.VHolder> {


    public myadapter(@NonNull FirebaseRecyclerOptions<model_class> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VHolder holder, int position, @NonNull model_class model) {
        holder.textView.setText(model.getTitle());
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);

        return  new VHolder(view);

    }

    class VHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public VHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.titile);
        }
    }


}
