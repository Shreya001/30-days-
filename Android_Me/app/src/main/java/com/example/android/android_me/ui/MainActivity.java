package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by shreya on 2/9/17.
 */

public class MainActivity extends AppCompatActivity implements MasterListFragment.onImageClickListener{

    int headindex;
    int bodyindex;
    int legindex;
    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;

            Button nextButtn = (Button)findViewById(R.id.next);
            nextButtn.setVisibility(View.GONE);

            GridView gridView = (GridView)findViewById(R.id.images_grid_view);
            gridView.setNumColumns(3);

            if(savedInstanceState == null) {
                body_fragment headFragment = new body_fragment();

                FragmentManager manager = getSupportFragmentManager();

                headFragment.setmImageIds(AndroidImageAssets.getHeads());

                manager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                body_fragment bodyFragment = new body_fragment();

                bodyFragment.setmImageIds(AndroidImageAssets.getBodies());

                manager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                body_fragment legFragment = new body_fragment();

                legFragment.setmImageIds(AndroidImageAssets.getLegs());

                manager.beginTransaction()
                        .add(R.id.legs_container, legFragment)
                        .commit();
            }

        }else{
            mTwoPane = false;
        }

    }

    @Override
    public void OnImageSelected(int position) {
        int number = position/12;
        int listIndex = position - 12*number;

        if(mTwoPane){
            body_fragment newFragment = new body_fragment();

            switch (number){
                case 0:
                    newFragment.setmImageIds(AndroidImageAssets.getHeads());
                    newFragment.setMlistIndex(listIndex);
                    getSupportFragmentManager().beginTransaction().replace(R.id.head_container,newFragment).commit();
                    break;
                    case 1:
                    newFragment.setmImageIds(AndroidImageAssets.getBodies());
                    newFragment.setMlistIndex(listIndex);
                    getSupportFragmentManager().beginTransaction().replace(R.id.body_container,newFragment).commit();
                     break;
                    case 2:
                    newFragment.setmImageIds(AndroidImageAssets.getLegs());
                    newFragment.setMlistIndex(listIndex);
                    getSupportFragmentManager().beginTransaction().replace(R.id.legs_container,newFragment).commit();
                     break;
            }
        }

        switch(number){
            case 0:
                headindex = listIndex;
                break;
            case 1:
                bodyindex = listIndex;
                break;
            case 2:
                legindex = listIndex;
                break;
        }

        Bundle b = new Bundle();
        b.putInt("Headindex",headindex);
        b.putInt("Bodyindex",bodyindex);
        b.putInt("Legindex",legindex);

        final Intent intent= new Intent(this,AndroidMeActivity.class);
        intent.putExtras(b);

        Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                                                                                                                                                                                        }
        });
    }
}
