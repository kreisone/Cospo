package com.example.aviro.cospo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import io.fabric.sdk.android.Fabric;



public class MainActivity extends ActionBarActivity {

    private static final String TAG = "test11";
    private static final String MESSAGE = "message";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        final Intent intent = new Intent(this, TrainingPlan1.class);
        new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.training_plan).withIcon(FontAwesome.Icon.faw_table).withIdentifier(1)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {


                        showTrainingPlan(view);
                        Log.d(TAG, "test22");
                    }
                })
                .build();





    }



    public int showTrainingPlan (View view) {

        TrainingPlan training_plan = new TrainingPlan();


        // Тренировочный план
        training_plan.uploadPlan();


        Log.d(TAG, "plan123 " + training_plan.data[1][2]);
        Log.d(TAG, "length2 " + training_plan.data.length);
//return 0;


        try {
            ScrollView scroll = new ScrollView(this);
            HorizontalScrollView hscroll = new HorizontalScrollView(this);

            TableLayout tableLayout = new TableLayout(this);

            tableLayout.setStretchAllColumns(true);
//            tableLayout.setShrinkAllColumns(true);
            tableLayout.setPadding(0,160,0,0);


            for (int i = 0; i < training_plan.data.length; i++) {

                TableRow tableRow = new TableRow(this);
                tableRow.setBackgroundColor(Color.WHITE);

                for (int j = 0; j < training_plan.data[i].length; j++) {

                    TextView title = new TextView(this);
                    title.setText(training_plan.data[i][j]);
                    title.setTextColor(Color.BLACK);
                    title.setPadding(5,5,5,5);

                    Log.d(TAG, "plan[]: " + training_plan.data[i][j]);


                    tableRow.addView(title, j);
                }


                tableLayout.addView(tableRow);

            }


            hscroll.addView(tableLayout);
            scroll.addView(hscroll);

            RelativeLayout rl = (RelativeLayout) findViewById(R.id.main_activity);

            RelativeLayout.LayoutParams param =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


            rl.addView(scroll);
//            setContentView(R.layout.activity_main);

//            container.addView(scroll);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            new Drawer()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .withActionBarDrawerToggle(true)
                    .withHeader(R.layout.drawer_header)
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName(R.string.training_plan).withIcon(FontAwesome.Icon.faw_table).withIdentifier(1)

                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {


                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                            showTrainingPlan(view);
                            Log.d(TAG, "test22");
                        }
                    })
                    .build();



        } catch (Exception e) {
            return Log.d(MESSAGE, e.getMessage());
        }

        return 0;
    }
}