package com.jejefcgb.homelights;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        Server desk = new Server();
        desk.setName("Bureau");
        desk.setIcon(R.mipmap.ic_object_desk);
        list.add(desk);

    }

    @OnClick(R.id.menu_color)
    void openColorPicker() {

        if (menu.isOpened()) {
            menu.close(true);
        }


        ColorPickerDialogBuilder
                .with(MainActivity.this)
                .setTitle(getString(R.string.color_title))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .showAlphaSlider(false)
                .setPositiveButton(getString(R.string.validate), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, Integer[] integers) {


                        ((MyAdapter) mAdapter).resetSelectedPos();
                        OkHttpClient client = new OkHttpClient();


                        String url  = String.format("http://192.168.1.41:8080/api/lights/%1$d/%2$d/%3$d",Color.red(i), Color.green(i), Color.blue(i));

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                    }
                })
                        // TODO Send commands
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                .build()
                .show();
    }

    @OnClick(R.id.menu_off)
    void shutDown(){
        ((MyAdapter)mAdapter).resetSelectedPos();
        OkHttpClient client = new OkHttpClient();


        String url  = String.format("http://192.168.1.41:8080/api/lights/0/0/0");

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

}
