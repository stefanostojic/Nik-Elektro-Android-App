package com.example.importingexcell;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    public static ArrayList<Proizvod> proizvodi = new ArrayList<Proizvod>();
    public static boolean finished=false;
    File file;

    Button btnUpDirectory,btnSDCard;

    ArrayList<String> pathHistory;
    public static String lastDirectory;
    int count = 0;

    ListView lvInternalStorage;
    //private static final int REQUEST_CODE = 1;

    private static final String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Odaberite Excel tabelu");
        setContentView(R.layout.activity_main);
//        getActionBar().setTitle("Odaberite fajl");

        verifyPermissions();
        lvInternalStorage=(ListView)findViewById(R.id.lvInternalStorage);
        btnUpDirectory=(Button)findViewById(R.id.btnUpDirectory);
        btnSDCard=(Button)findViewById(R.id.btnViewSDCard);
        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);
                    //Execute method for reading the excel data.

                    String[] nizLastDirectory = lastDirectory.split("\\.");

                    if (nizLastDirectory[nizLastDirectory.length - 1].equals("xls")) {

                        readExcel readExcel = new readExcel();

                        readExcel.execute();

                        toastMessage("Podaci se ucitavaju");



//                        Log.d(TAG, "ovo je IME: "+proizvodi.get(1).getIme());
                       // toastMessage("Podaci su ucitani");



                        /*Intent intentPretraga = new Intent(getApplicationContext(), Pretraga_Activity_V2.class);
                        intentPretraga.putExtra("filePath",lastDirectory);
                        startActivity(intentPretraga);*/
                    }
                    else
                        toastMessage("Fajl nije tabela");
                }
                else {
                    count++;
                    pathHistory.add(count,(String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                }
            }
        });

        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                } else {
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });

        //Opens the SDCard or phone memory
        btnSDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count,System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });
    }

    public class readExcel extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            Intent intentUcitavanje = new Intent(getApplicationContext(), Loading.class);
            startActivity(intentUcitavanje);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            readExcelData(lastDirectory);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("logwtf","izasao je ");
            Intent intentPretraga = new Intent(getApplicationContext(), Pretraga_Activity_V2.class);
            startActivity(intentPretraga);
            finish();
        }
    }

    private void readExcelData(String filePath) {

        File inputFile= new File(filePath);
        try{
            //pamti imputStream  fajla koji smo izabrali
            InputStream inputStream=  new FileInputStream(inputFile);
            HSSFWorkbook workbook= new HSSFWorkbook(inputStream);
            //za vise sheetova
            HSSFSheet sheet= workbook.getSheetAt(0);
            int rowsCount= sheet.getPhysicalNumberOfRows();
            //evalutor da bi znao kojeg tipa podatka je celija
            FormulaEvaluator formulaEvaluator= workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb= new StringBuilder();
            //r krece od 1 jer necemo prvi red
            for(int r=1;r<rowsCount;r++)
            {
                Row row=sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //celije krecu od 1 zbor jebenog plusa i cellCount -1 zbog zadnje prazne celije
                for(int c=1;c<cellsCount;c++)
                {
                    if (getCellAsString(row,6,formulaEvaluator).isEmpty())
                    {
                        if (c == 3 || c == 4 || c==6)
                        {
                            continue;
                        } else {
                            String value = getCellAsString(row, c, formulaEvaluator);
                            sb.append(value + "`");
                        }
                    } else
                        {
                            if (c == 3 || c == 4 || c==5)
                            {
                                continue;
                            } else {
                                String value = getCellAsString(row, c, formulaEvaluator);
                                sb.append(value + "`");
                            }
                        }
                }
                sb.append(";");
            }
            parseStringBuilder(sb);
            finished=true;
        }catch (FileNotFoundException e){
            //log
        }catch (IOException e){
            //log
        }
    }

    private void parseStringBuilder(StringBuilder sb) {
        Log.d(TAG, "started parsing");
        String[] rows= sb.toString().split(";");
        for(int i=0;i<rows.length;i++)
        {
            String[] column= rows[i].split("`");
            String id= column[0];
            String kolicina = column[2];
            String cellInfo= "(sifra,ime,kolicina):("+id+","+column[1]+","+kolicina+")";
            Log.d(TAG, "data from row"+cellInfo);

            proizvodi.add(new Proizvod(id,column[1],kolicina));
        }

        /*TextView tw = (TextView) findViewById(R.id.textView);
        tw.setText(proizvodi.get(1).getId());
        Log.d(TAG, "ovo je IME: "+proizvodi.get(1).getIme());*/
    }

    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {

        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = "" + cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
//                    double numericValue = cellValue.getNumberValue();
                    int numericValue = (int) cellValue.getNumberValue();
                    value = "" + numericValue;
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = "" + cellValue.getStringValue();
                    break;
                default:

            }
        }catch(Exception e){}
        return value;
    }

    private void checkInternalStorage(){
        try {
            if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                toastMessage("Nemate SD karticu!");
            }
            else{
                file = new File (pathHistory.get(count));
            }
            listFile = file.listFiles();
            FilePathStrings= new String[listFile.length];
            FileNameStrings= new String[listFile.length];
            for(int i=0;i< listFile.length;i++)
            {
                FilePathStrings[i]=listFile[i].getPath();
                FileNameStrings[i]=listFile[i].getName();
            }
            ArrayAdapter<String> adapter= new ArrayAdapter<String>
                    (MainActivity.this,android.R.layout.simple_list_item_1,FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        }catch(NullPointerException e){

        }

    }

    private void toastMessage(String message) {
        Toast.makeText(MainActivity.this,message, LENGTH_SHORT).show();
    }

    private void verifyPermissions(){

        //int permissionCallPhone = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);

        int permissionExternalMemory = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    STORAGE_PERMISSIONS,
                    1
            );
        }
    }
}