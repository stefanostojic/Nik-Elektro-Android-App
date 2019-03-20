/*
package com.example.importingexcell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Pretraga_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga);
    }
}
*/

package com.example.importingexcell;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.Stream;


public class Pretraga_Activity extends AppCompatActivity {

    String[] elementiDirektorijuma= MainActivity.lastDirectory.split("/");
    String direktorijum="";
    public static Proizvod pronadjeniProizvod;
    private static final String TAG = "Pretraga_Activity123";
    public static File file;
    public int proslaKolicina,novaKolicina;
    public static MediaPlayer mp;
    public static int novaKolicinaZaLogovanje;

    public ArrayList<String> listaPromena= new ArrayList<String>();
    // SearchView svPretraga = (SearchView) findViewById(R.id.svPretraga);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga);
        toastMessage("Podaci su učitani");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        final Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        final Button btnPretraga = (Button) findViewById(R.id.btnPretraga);
        final TextView tvIme = (TextView) findViewById(R.id.tvIme);
        final SearchView searchViewPretraga = (SearchView) findViewById(R.id.etPretraga); // prethodno: editTextPretraga, takođe, sad se umesto getText() piše getQuery()
        final EditText editTextUvecajZa = (EditText) findViewById(R.id.editTextUvecajZa);
        final EditText editTextTrenutnaKolicina = (EditText) findViewById(R.id.editTextTrenutnaKolicina);
        final TextView textViewSabiranje = (TextView) findViewById(R.id.textViewSabiranje);
        final TextView textViewStaraKolicina = (TextView) findViewById(R.id.textViewStaraKolicina);

        final ListView listViewPrethodneIzmene = (ListView) findViewById(R.id.listViewPrethodneIzmene);
        final ArrayList<String> l = new ArrayList<String>();
        listViewPrethodneIzmene.setVisibility(View.VISIBLE);

        //postavlja veličinu teksta polja za pretragu
        LinearLayout linearLayout1 = (LinearLayout) searchViewPretraga.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(24);

        //sakriva polja o pronađenom proizvodu pri prvom paljenju aplikacije
        tvIme.setVisibility(View.INVISIBLE);
        editTextUvecajZa.setVisibility(View.INVISIBLE);
        editTextTrenutnaKolicina.setVisibility(View.INVISIBLE);
        textViewSabiranje.setVisibility(View.INVISIBLE);
        textViewStaraKolicina.setVisibility(View.INVISIBLE);


        for(int i = 0; i < elementiDirektorijuma.length - 1; i++)
        {
            direktorijum = direktorijum + elementiDirektorijuma[i] + "/";

        }

        promene();

        btnPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"Dugme \"Pretraga\" je kliknuto");

                Log.d(TAG,"ListView je prikazan");

                if (searchViewPretraga.getQuery().toString() != "") {
                    boolean uspesnoPronadjenProizvod = false;
                    for (Proizvod p : MainActivity.proizvodi) {
                        if (p.getId().equals(searchViewPretraga.getQuery().toString())) {
                            tvIme.setVisibility(View.VISIBLE);
                            editTextUvecajZa.setVisibility(View.VISIBLE);
                            editTextTrenutnaKolicina.setVisibility(View.VISIBLE);
                            textViewStaraKolicina.setVisibility(View.VISIBLE);
                            textViewSabiranje.setVisibility(View.VISIBLE);

                            editTextUvecajZa.requestFocus();
                            tvIme.setText(p.getIme());
                            /*editTextUvecajZa.setText(p.getKolicina());*/
                            editTextTrenutnaKolicina.setText(p.getKolicina());
                            textViewSabiranje.setText(p.getSabiranje());
                            textViewStaraKolicina.setText(p.getStaraKolicina());
                            pronadjeniProizvod=p;
                            uspesnoPronadjenProizvod = true;
                            toastMessage("Proizvod je pronađen");
                            Log.d(TAG,"Proizvod je pronadjen");
                            break;
                        }
                    }

                    if (!uspesnoPronadjenProizvod) {
                        toastMessage("Proizvod nije pronađen");
                        tvIme.setVisibility(View.INVISIBLE);
                        editTextUvecajZa.setVisibility(View.INVISIBLE);
                        editTextTrenutnaKolicina.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    toastMessage("Niste uneli ID za pretragu");
                }

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cuvamo indeks iz liste
                if(editTextUvecajZa.getText().toString().isEmpty())
                {
                    toastMessage("Polje za količinu je prazno");
                }
                else if (editTextUvecajZa.getText().toString() == pronadjeniProizvod.getKolicina())
                {
                    toastMessage("Polje za količinu je nepromenjeno");
                }
                else if(editTextTrenutnaKolicina.getText().toString().isEmpty())
                {
                    toastMessage("Proizvod nije odabran");
                }
                else
                {
                    AlertDialog al = new AlertDialog.Builder(Pretraga_Activity.this)
                            .setTitle("Sačuvaj?")
                            .setMessage("Da li ste sigurni da želite da sačuvate promenu?")
                            .setIcon(R.drawable.baseline_edit_black_36dp)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Upis upis = new Upis();
                                    upis.execute();

                                    toastMessage("Promena je sačuvana");

                                    searchViewPretraga.requestFocus();

                                    int uvecajOd = Integer.parseInt(editTextTrenutnaKolicina.getText().toString());
                                    int uvecajDo = uvecajOd + Integer.parseInt(editTextUvecajZa.getText().toString());
                                    editTextUvecajZa.setText("");

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            editTextTrenutnaKolicina.setTypeface(null, Typeface.NORMAL);
                                            searchViewPretraga.setQuery("",false);
                                            tvIme.setVisibility(View.INVISIBLE);
                                            editTextUvecajZa.setVisibility(View.INVISIBLE);
                                            editTextTrenutnaKolicina.setVisibility(View.INVISIBLE);
                                            textViewSabiranje.setVisibility(View.INVISIBLE);
                                            textViewStaraKolicina.setVisibility(View.INVISIBLE);

                                            /*l.add(0, "ID: " + pronadjeniProizvod.getId() + ", Ime: " + pronadjeniProizvod.getIme() + ", Kolicina: " + pronadjeniProizvod.getKolicina());
                                            ListView listViewPrethodneIzmene = (ListView) findViewById(R.id.listViewPrethodneIzmene);
                                            PrethodneIzmene_ListAdapter_v2 adapter = new PrethodneIzmene_ListAdapter_v2(Pretraga_Activity.this, R.layout.prethodneizmene_listadapter_layout_v2, l);
                                            listViewPrethodneIzmene.setAdapter(adapter);*/
                                        }
                                    }, 3000);

                                    animateEditText(uvecajOd, uvecajDo, ((EditText) findViewById(R.id.editTextTrenutnaKolicina)));
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });
    }

    public void animateEditText(int uvecajOd, int uvecajDo, final EditText editText) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(uvecajOd, uvecajDo);
        valueAnimator.setDuration(1250);

        editText.setTypeface(null, Typeface.BOLD);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                editText.setText(valueAnimator.getAnimatedValue().toString());

            }
        });

        valueAnimator.start();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemLogPromena:
                File fileLog= new File(direktorijum,"logPromena.txt");
                Uri path = Uri.fromFile(fileLog);
                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenintent.setDataAndType(path, "text/plain");
                try {
                    startActivity(pdfOpenintent);
                }
                catch (ActivityNotFoundException e) {

                }
                return true;
            /*case R.id.itemZnak:
                EditText editTextUvecaj = (EditText) findViewById(R.id.editTextUvecajZa);
                if (editTextUvecaj.getText().toString().isEmpty())
                    editTextUvecaj.setText("-");
                else if (editTextUvecaj.getText().toString().contains("-")){
                    editTextUvecaj.setText(editTextUvecaj.getText().toString().replace("-",""));
                }
                else {
                    editTextUvecaj.setText("-" + editTextUvecaj.getText());
                }
                editTextUvecaj.setText("-" + editTextUvecaj.getText());

                return true;*/
            case R.id.itemONama:
                showDialog(getCurrentFocus());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public class Upis extends AsyncTask {

        @Override
        protected String doInBackground(Object[] objects) {

            EditText editTextUvecajZa1 = (EditText) findViewById(R.id.editTextUvecajZa);
            int brojReda = MainActivity.proizvodi.indexOf(pronadjeniProizvod) + 1;
            int staraKolicina= Integer.parseInt(pronadjeniProizvod.getKolicina());
            int novaKolicina= Integer.parseInt(editTextUvecajZa1.getText().toString());
            novaKolicinaZaLogovanje=novaKolicina;

            pronadjeniProizvod.setKolicina(String.valueOf(staraKolicina + novaKolicina));
            if (pronadjeniProizvod.getSabiranje().equals("0")) {
                pronadjeniProizvod.setSabiranje(novaKolicina + "");
            }
            else if (novaKolicina >= 0){
                pronadjeniProizvod.setSabiranje(pronadjeniProizvod.getSabiranje() + "+" + novaKolicina);
            }
            else {
                pronadjeniProizvod.setSabiranje(pronadjeniProizvod.getSabiranje() + novaKolicina);
            }
//            pronadjeniProizvod.setSabiranje(pronadjeniProizvod.getSabiranje() + "+" + novaKolicina);
            writeSheet(pronadjeniProizvod.getKolicina(),pronadjeniProizvod.getSabiranje(),brojReda);

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            logovanje();
            promene();
        }
    }

    public static void writeSheet(String data,String data2, int i) {
        HSSFWorkbook workbook;

        try {
            String outFileName = "filebook.xls";

            File path = new File(MainActivity.lastDirectory);
            Log.d(TAG,MainActivity.lastDirectory);

            FileInputStream file = new FileInputStream(path);

            workbook = new HSSFWorkbook(file);
            Log.d(TAG,"napravio workbook");

            //Row row = workbook.getSheetAt(0).createRow(0);
            Cell cell = workbook.getSheetAt(0).getRow(i).getCell(6);
            cell.setCellValue(data);
            Cell cell2 = workbook.getSheetAt(0).getRow(i).getCell(7);
            cell2.setCellValue(data2);

            file.close();

            FileOutputStream outFile =new FileOutputStream(path);
            workbook.write(outFile);
            outFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void logovanje(){

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time

        file = new File(direktorijum,"logPromena.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        try {
           /* FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            writer.append(formattedDate + ": Sifra: " + pronadjeniProizvod.getId() + ", Ime:" + pronadjeniProizvod.getIme() + ", Kolicina:" + pronadjeniProizvod.getKolicina() + "\n");
            writer.close();
            fileOutputStream.close();*/
            RandomAccessFile f = new RandomAccessFile(file,"rw");
            f.seek(file.length());
            Log.d("kurac", "nova kolicina kurac"+ novaKolicinaZaLogovanje);
            f.write((formattedDate + ": Sifra: " + pronadjeniProizvod.getId() + ":, Ime:" + pronadjeniProizvod.getIme() + ":, Kolicina: " + (Integer.parseInt(pronadjeniProizvod.getKolicina())-novaKolicinaZaLogovanje) + "->"+pronadjeniProizvod.getKolicina()+ "\n").getBytes());
            f.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(View view) {
        ONamaDialog onamadialog = new ONamaDialog();
        onamadialog.show(getSupportFragmentManager(),"O nama");
    }

    public void promene() {
        try {
           /* FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            writer.append(formattedDate + ": Sifra: " + pronadjeniProizvod.getId() + ", Ime:" + pronadjeniProizvod.getIme() + ", Kolicina:" + pronadjeniProizvod.getKolicina() + "\n");
            writer.close();
            fileOutputStream.close();*/
            String promena;
            String [] niz;
            file = new File(direktorijum,"logPromena.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int lines = 0;
            String linija = "";
            ArrayList<String> listaLinija = new ArrayList<String>();
            while ((linija = reader.readLine()) != null) {
                Log.d("kurac1", linija);
                listaLinija.add(0, linija);
            }
            reader.close();
            listaPromena.clear();

            int counter = 0;
            for (String linija1: listaLinija) {
                if (counter == 3)
                    break;
                niz = linija1.split(":");
                listaPromena.add(niz[4]+" : "+niz[6]+" : "+niz[8]);
                counter++;
            }

            final ListView listViewPrethodneIzmene = (ListView) findViewById(R.id.listViewPrethodneIzmene);

            PrethodneIzmene_ListAdapter_v2 adapter = new PrethodneIzmene_ListAdapter_v2(this, R.layout.prethodneizmene_listadapter_layout_v2, listaPromena);
            listViewPrethodneIzmene.setAdapter(adapter);
            Log.d("kurac", "nova kolicina kurac"+ novaKolicinaZaLogovanje);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog al = new AlertDialog.Builder(this)
                .setTitle("Upozorenje")
                .setMessage("Da li ste sigurni?")
                .setIcon(R.drawable.baseline_close_black_36dp)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}
