package com.jejefcgb.homelights;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Server> list;
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.menu)
    FloatingActionMenu menu;

    @BindView(R.id.menu_color)
    FloatingActionButton menuColor;

    @BindView(R.id.menu_on)
    FloatingActionButton menuOn;

    @BindView(R.id.menu_off)
    FloatingActionButton menuOff;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final int NB_COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        mLayoutManager = new GridLayoutManager(this, NB_COLUMNS);


        mRecyclerView.setLayoutManager(mLayoutManager);

        setData();


        mAdapter = new MyAdapter(list, cb);

        mRecyclerView.setAdapter(mAdapter);

    }

    private Callback cb = new Callback() {
        @Override
        public void update(List<Integer> value) {

            int menuHeight = menu.getHeight() / (menu.getChildCount() + 1);

            if (value.size() > 0 && menu.getVisibility() == View.GONE) {
                ObjectAnimator in = ObjectAnimator.ofFloat(menu, "translationY", menuHeight + 100, 0);
                in.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        menu.setVisibility(View.VISIBLE);
                    }
                });
                in.start();
            } else if (value.isEmpty()) {

                ObjectAnimator out = ObjectAnimator.ofFloat(menu, "translationY", 0, menuHeight + 100);
                out.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        menu.setVisibility(View.GONE);
                    }
                });
                out.start();
            }

        }
    };

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
