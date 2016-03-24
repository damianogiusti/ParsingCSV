package it.damianogiusti.parsingcsv;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "ParsingCSV";

    private Button btnStart;
    private EditText txtGoogleKey;
    private ProgressBar progressBar;

    private String googlekey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGoogleKey = (EditText) findViewById(R.id.txtGoogleKey);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnStart = (Button) findViewById(R.id.btnStart);

        if (btnStart != null) {
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googlekey = txtGoogleKey.getText().toString();

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            startParsing();
                            return null;
                        }

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Finito", Toast.LENGTH_SHORT).show();
                        }
                    }.execute();
                }
            });
        }

    }

    private void startParsing() {

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.dati);
            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                printLine(line);
            }
        } catch (Exception e) {

        }
    }

    private void printLine(String[] line) {
        String out = "";
        for (String s : line) {
            out += s + ";";
        }
        Log.d(TAG, "printLine: " + out);
    }
}
