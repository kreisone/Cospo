package com.example.aviro.cospo;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TrainingPlan1 extends AppCompatActivity {


    private static final String MESSAGE = "message";
    private static final String TAG = "test12";
    public static String[][] data = new String[50][200];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_plan1);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.training_plan1);

        RelativeLayout.LayoutParams param =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        new MyTask().execute();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.d(TAG, "sleep: " + e.getMessage());
        }


        Log.d(TAG, "plan123 " + this.data[1][2]);
        Log.d(TAG, "length2 " + this.data.length);


        try {
            ScrollView scroll = new ScrollView(this);
            HorizontalScrollView hscroll = new HorizontalScrollView(this);

            TableLayout tableLayout = new TableLayout(this);

            tableLayout.setStretchAllColumns(true);
//            tableLayout.setShrinkAllColumns(true);


            for (int i = 0; i < this.data.length; i++) {

                TableRow tableRow = new TableRow(this);
                tableRow.setBackgroundColor(Color.WHITE);

                for (int j = 0; j < this.data[i].length; j++) {

                    TextView title = new TextView(this);
                    title.setText(this.data[i][j]);
                    title.setTextColor(Color.BLACK);
                    title.setPadding(5,5,5,5);

                    Log.d(TAG, "plan[]: " + this.data[i][j]);


                    tableRow.addView(title, j);
                }


                tableLayout.addView(tableRow);

            }


            hscroll.addView(tableLayout);
            scroll.addView(hscroll);


            rl.addView(scroll);


//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//            new Drawer()
//                    .withActivity(this)
//                    .withToolbar(toolbar)
//                    .withActionBarDrawerToggle(true)
//                    .withHeader(R.layout.drawer_header)
//                    .addDrawerItems(
//                            new PrimaryDrawerItem().withName(R.string.training_plan).withIcon(FontAwesome.Icon.faw_table).withIdentifier(1)
//
//                    )
//                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//
//
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
////                            showTrainingPlan(view);
//                            Log.d(TAG, "test22");
//                        }
//                    })
//                    .build();



        } catch (Exception e) {
            Log.d(MESSAGE, e.getMessage());
        }




    }



    public static void uploadPlan() {
//        Log.d(TAG, "yyyyyyyyyyyyyyyyyaaaaaaaaaaa");
        new TrainingPlan.MyTask().execute();


    }



    public static class MyTask extends AsyncTask<Object, Object, Void> {

        String resultJson = "";



        @Override
        protected Void doInBackground(Object... voids) {


            try {
                // ПОТОМ ДОБАВИТЬ ПАРАМЕТРЫ С EMAIL, PASS,
                String query = "http://cospo.esy.es/andr.php?andr=download_plan";

                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL(query).openConnection();

                    connection.setRequestMethod("GET");
                    connection.setUseCaches(false);
                    connection.setConnectTimeout(250);
                    connection.setReadTimeout(250);

                    connection.connect();


                    if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {


                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        StringBuffer buffer = new StringBuffer();
                        Log.d(TAG, "training_plan333: " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ": " + in.read());
                        String plan;
                        while ((plan = in.readLine()) != null) {
                            buffer.append(plan);
                        }


                        resultJson = buffer.toString();

                        Log.d(TAG, "training_plan11: " + resultJson);

                        resultJson = resultJson.substring(9,resultJson.length() - 2);
                        resultJson = resultJson.replaceAll("\\\\\"", "'");
                        JSONObject dataJsonObj = null;

                        try {


                            dataJsonObj = new JSONObject(resultJson);
//                            String[][] data = new String[dataJsonObj.length()][200];

                            Log.d(TAG, "length: " +  dataJsonObj.length());
                            for (int i = 1; i <= dataJsonObj.length();i++) {


                                JSONObject plan1 = dataJsonObj.getJSONObject(String.valueOf(i));
                                Log.d(TAG, "length1: " +  plan1.length());
                                for (int j = 0; j < plan1.length(); j++) {

                                    String tt = plan1.getString(String.valueOf(j));

                                    data[i-1][j] = tt;

                                    Log.d(TAG, "training_plan22: " + (i-1) + "-" + j + ": " + tt);

//
                                }
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.d(TAG, "training_plan: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
                    }

                } catch (Throwable cause) {
                    cause.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }


        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
        }
    }



}
