package com.jejefcgb.homelights;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Server> list;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final int NB_COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView = findViewById(R.id.my_recycler_view); //TODO : to bind
        mRecyclerView.addItemDecoration(
                new GridSpacingItemDecoration(
                        NB_COLUMNS,
                        getResources().getDimensionPixelSize(R.dimen.default_margin),
                        true,
                        0));

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this,NB_COLUMNS);


        mRecyclerView.setLayoutManager(mLayoutManager);

        setData();


        mAdapter = new MyAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setData() {
        // specify an adapter (see also next example)
        list = new ArrayList<>();

        Server meubleTv = new Server();
        meubleTv.setName("Meuble TV");
        meubleTv.setIcon(R.mipmap.ic_object_tv);
        list.add(meubleTv);

        Server lit = new Server();
        lit.setName("Lit");
        lit.setIcon(R.mipmap.ic_object_bed);
        list.add(lit);

        Server bar = new Server();
        bar.setName("Bar");
        bar.setIcon(R.mipmap.ic_object_bar);
        list.add(bar);

    }

   /* @OnClick(R.id.home_color_picker)
    void openColorPicker(){
        ColorPickerDialogBuilder
                .with(MainActivity.this)
                .setTitle(getString(R.string.color_title))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .showAlphaSlider(false)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast.makeText(MainActivity.this, Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(getString(R.string.validate), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        Toast.makeText(MainActivity.this, Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }*/

}
