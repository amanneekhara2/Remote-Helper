package com.boomerdroid.remotehelper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boomerdroid.remotehelper.Adapter.RecentRecyclerViewAdapter;
import com.boomerdroid.remotehelper.ModelData.RecentContent;


public class RecentFragment extends Fragment {

    public RecentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            recyclerView.setAdapter(new RecentRecyclerViewAdapter(RecentContent.ITEMS));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
