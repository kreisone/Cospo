package com.example.aviro.cospo;

import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "test11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }



    public void showTrainingPlan (View view) {
        try {



            InputStream in = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/.Cospo/table.xls");
            // Внимание InputStream будет закрыт
            // Если нужно не закрывающий см. JavaDoc по POIFSFileSystem :  http://goo.gl/1Auu7
            HSSFWorkbook wb = new HSSFWorkbook(in);

            Sheet sheet = wb.getSheetAt(0);

            ScrollView scroll = new ScrollView(this);
            HorizontalScrollView hscroll = new HorizontalScrollView(this);



            TableLayout table = new TableLayout(this);


            table.setStretchAllColumns(true);
            table.setShrinkAllColumns(true);


            Row row;
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

                TableRow tableRow = new TableRow(this);
                row = sheet.getRow(i);

                Log.d(TAG, "showTrainingPlan: " + row.getPhysicalNumberOfCells());

                Cell cell;
                for (int j = 0; j < 40; j++) {
                    cell = row.getCell(j);

                    TextView title = new TextView(this);
                    title.setText(cell.getStringCellValue());

                    tableRow.addView(title, j);

                }

                table.addView(tableRow);

            }

            hscroll.addView(table);
            scroll.addView(hscroll);

            setContentView(scroll);

        }
        catch (Exception ex) {
            return;
        }
    }
}