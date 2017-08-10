package com.example.aviro.cospo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by aviro on 8/4/17.
 */

public class TrainingPlan {

    private static final String TAG = "test12";
    private static final String FILE = "file";
    private static final String INET = "inet";
    public static String[][] data = new String[50][200];






    public static void uploadPlan() {

        new MyTask().execute();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.d(TAG, "sleep: " + e.getMessage());
        }



    }




    public static class MyTask extends AsyncTask<Object, Object, Void> {

        String resultJson = "";



        @Override
        protected Void doInBackground(Object... voids) {

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

                            resultJson = resultJson.substring(9, resultJson.length() - 2);
                            resultJson = resultJson.replaceAll("\\\\\"", "'");


                            // Create & write training_plan.json
                            File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/.Cospo");
                            folder.mkdir();
                            try {
                                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/.Cospo/training_plan.json");
                                file.createNewFile();
                                OutputStream fr = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + "/.Cospo/training_plan.json"));
                                fr.write(resultJson.getBytes(), 0, resultJson.length());
                                Log.d(TAG, "hi");
                            } catch (IOException e) {
                                Log.d(TAG, "file1: " + e.getMessage());
                            }


                            JSONObject dataJsonObj = null;
                            try {


                                dataJsonObj = new JSONObject(resultJson);
//                            String[][] data = new String[dataJsonObj.length()][200];

                                Log.d(TAG, "length: " + dataJsonObj.length());
                                for (int i = 1; i <= dataJsonObj.length(); i++) {


                                    JSONObject plan1 = dataJsonObj.getJSONObject(String.valueOf(i));
                                    Log.d(TAG, "length1: " + plan1.length());
                                    for (int j = 0; j < plan1.length(); j++) {

                                        String tt = plan1.getString(String.valueOf(j));

                                        data[i - 1][j] = tt;

                                        Log.d(TAG, "training_plan22: " + (i - 1) + "-" + j + ": " + tt);

//
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }




                    } catch (Throwable cause) {




                        File fl = new File(Environment.getExternalStorageDirectory().getPath() + "/.Cospo/training_plan.json");
                        if (fl.exists()) {

                            try {

                                BufferedReader in = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getPath() + "/.Cospo/training_plan.json"));

                                StringBuffer buffer = new StringBuffer();
                                Log.d(TAG, "training_plan333: " + in.read());
                                String plan;
                                while ((plan = in.readLine()) != null) {
                                    buffer.append(plan);
                                }


                                resultJson = buffer.toString();
//                    resultJson = "{\"plan\":\"{" + resultJson + "}]";
                                resultJson = "{" + resultJson;

                                Log.d(TAG, "training_plan1rtyrt1: " + resultJson);




                                JSONObject dataJsonObj = null;
                                try {


                                    dataJsonObj = new JSONObject(resultJson);

                                    Log.d(TAG, "length: " + dataJsonObj.length());
                                    for (int i = 1; i <= dataJsonObj.length(); i++) {


                                        JSONObject plan1 = dataJsonObj.getJSONObject(String.valueOf(i));
                                        Log.d(TAG, "length1: " + plan1.length());
                                        for (int j = 0; j < plan1.length(); j++) {

                                            String tt = plan1.getString(String.valueOf(j));

                                            data[i - 1][j] = tt;

                                            Log.d(TAG, "training_planppp: " + (i - 1) + "-" + j + ": " + tt);

//
                                        }
                                    }
                                } catch (JSONException e) {
                                    Log.d(FILE, "file: " + e.getMessage());
                                }


                            } catch (IOException e) {
                                Log.d(TAG, "file: " + e.getMessage());
                            }


                        } else {
                            Log.d(INET, "Подключитесь к интернету");

                        }




                    }



            return null;
        }


        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
        }
    }

}
