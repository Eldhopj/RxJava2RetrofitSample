package com.example.eldho.rxjava2sample.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eldho.rxjava2sample.Model.PostResponse;
import com.example.eldho.rxjava2sample.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<PostResponse> mListItems; // List
    private Context mContext;
    private OnItemClickListener mListener; // Listener for the OnItemClickListener interface

    public PostAdapter(List<PostResponse> mListItems, Context mContext) {
        this.mListItems = mListItems;
        this.mContext = mContext;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);

//                //NOTE : use this if items height and width not following the match_parent or wrap_content
//                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//                view.setLayoutParams(layoutParams);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostResponse listitem = mListItems.get(position);
        if ( listitem != null) {
            holder.headTv.setText(listitem.getTitle());
            holder.descTv.setText(listitem.getBody());
        }
    }

    @Override
    public int getItemCount() {
        if (mListItems == null) {
            return 0;
        }
        return mListItems.size();
    }

    //View Holder class caches these references that gonna modify in the adapter
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView headTv;
        TextView descTv;

        //create a constructor with itemView as a params
        ViewHolder(View itemView) { // with the help of "itemView" we ge the views from xml
            super(itemView);

            headTv = itemView.findViewById(R.id.title);
            descTv = itemView.findViewById(R.id.body);

            //Assigning on click listener on the item
            itemView.setOnClickListener(new View.OnClickListener() { // we can handle the click as like we do in normal
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition(); // Get the index of the view holder
                        if (position != RecyclerView.NO_POSITION) { // Makes sure this position is still valid
                            mListener.onItemClick(v,position); // we catch the click on the item view then pass it over the interface and then to our activity
                        }
                    }
                }
            });
        }
    }
}
