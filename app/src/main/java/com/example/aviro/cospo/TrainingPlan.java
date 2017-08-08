package com.example.aviro.cospo;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by aviro on 8/4/17.
 */

public class TrainingPlan {

    private static final String TAG = "test12";
    public static String[][] data = new String[50][200];

    public static void uploadPlan() {
//        Log.d(TAG, "yyyyyyyyyyyyyyyyyaaaaaaaaaaa");
        new MyTask().execute();


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
