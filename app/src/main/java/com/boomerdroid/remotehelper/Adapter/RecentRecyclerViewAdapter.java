package com.boomerdroid.remotehelper.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boomerdroid.remotehelper.ModelData.RecentContent.RecentItem;
import com.boomerdroid.remotehelper.R;

import java.util.List;


public class RecentRecyclerViewAdapter extends RecyclerView.Adapter<RecentRecyclerViewAdapter.ViewHolder> {

    private final List<RecentItem> mValues;

    public RecentRecyclerViewAdapter(List<RecentItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//       holder.requester.setText(mValues.get(position).requester);
//       holder.date.setText(mValues.get(position).date);
//       holder.request.setText(mValues.get(position).request);
//       holder.details.setText(mValues.get(position).details);
//       holder.status.setText(mValues.get(position).status);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //public final TextView requester,date,request,status,details;
        public ViewHolder(View view) {
            super(view);
//            requester = view.findViewById(R.id.requesterName);
//            date = view.findViewById(R.id.requestedOn);
//            request = view.findViewById(R.id.request);
//            status = view.findViewById(R.id.status);
//            details = view.findViewById(R.id.details);
        }

    }
}
