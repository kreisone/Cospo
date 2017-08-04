package com.example.aviro.cospo;

import android.graphics.Color;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "test11";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "Training plan: " + e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://mysql.hostinger.com.ua/u548386781_cospo","u548386781_sem", "WXmXOcwe400H");
            return dbConnection;
        } catch (SQLException e) {
            Log.d(TAG, "Training plan1: " + e.getMessage());
        }
        return dbConnection;
    }

    public void showTrainingPlan (View view) {

        String selectTableSQL = "SELECT plan FROM cosposk_training_plan";
        Connection dbConnection = null;
        Statement statement = null;

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выбираем данные с БД
            ResultSet rs = statement.executeQuery(selectTableSQL);

            // И если что то было получено то цикл while сработает
            while (rs.next()) {
                String plan = rs.getString("plan");

                System.out.println("plan : " + plan);
                Log.d(TAG, "Plan: " + plan);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }







//        try {
//
//
//
//            InputStream in = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/.Cospo/table.xls");
//            // Внимание InputStream будет закрыт
//            // Если нужно не закрывающий см. JavaDoc по POIFSFileSystem :  http://goo.gl/1Auu7
//            HSSFWorkbook wb = new HSSFWorkbook(in);
//
//            Sheet sheet = wb.getSheetAt(0);
//
//            ScrollView scroll = new ScrollView(this);
//            HorizontalScrollView hscroll = new HorizontalScrollView(this);
//
//
//
//            TableLayout table = new TableLayout(this);
//
//
//            table.setStretchAllColumns(true);
//            table.setShrinkAllColumns(true);
//
//
//            Row row;
//            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
//
//                TableRow tableRow = new TableRow(this);
//                row = sheet.getRow(i);
//
//                Log.d(TAG, "showTrainingPlan: " + row.getPhysicalNumberOfCells());
//
//                Cell cell;
//                for (int j = 0; j < 40; j++) {
//                    cell = row.getCell(j);
//
//                    TextView title = new TextView(this);
//                    title.setText(cell.getStringCellValue());
//
//                    tableRow.addView(title, j);
//
//                }
//
//                table.addView(tableRow);
//
//            }
//
//            hscroll.addView(table);
//            scroll.addView(hscroll);
//
//            setContentView(scroll);
//
//        }
//        catch (Exception ex) {
//            return;
//        }
    }
}