package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by shreya on 2/9/17.
 */

public class MasterListFragment extends Fragment {

    onImageClickListener  mcallbacks;

    public interface onImageClickListener {
       void OnImageSelected(int position);
    }

   public MasterListFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mcallbacks = (onImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.master_list_fragment,container,false);


        GridView gridview = (GridView) rootView.findViewById(R.id.images_grid_view);
        gridview.setAdapter(new MasterListAdapter(getContext(), AndroidImageAssets.getAll()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mcallbacks.OnImageSelected(i);
            }
        });

        return rootView;
    }
}
