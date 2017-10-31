package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shreya on 2/9/17.
 */

public class body_fragment extends Fragment {

    static final String LIST = "mimageids";
    static final String INDEX = "listitem";
    private List<Integer> mImageIds;
    private int mlistIndex;
    public body_fragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.body_fragment,container,false);

        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_fragment_image_view);
        if(mImageIds !=null) {
            imageView.setImageResource(mImageIds.get(mlistIndex));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mlistIndex < mImageIds.size()) {
                        mlistIndex++;
                        imageView.setImageResource(mImageIds.get(mlistIndex));
                    } else {
                        mlistIndex = 0;
                    }
                }


            });
        }


        return rootView;
    }

    public void setmImageIds(List<Integer> imageIds){
        mImageIds=imageIds;
    }

    public void setMlistIndex(int index){mlistIndex = index;}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(LIST,(ArrayList<Integer>) mImageIds);
        outState.putInt(INDEX,mlistIndex);
    }
}
