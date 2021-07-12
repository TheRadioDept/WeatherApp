package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText user_filed;
    private Button mainButton;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_filed = findViewById(R.id.user_field);
        mainButton = findViewById(R.id.mainButton);
        result = findViewById(R.id.result);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_filed.getText().toString().trim().equals(""))
                    Toast.makeText(MainActivity.this, R.string.no_usr_intput, Toast.LENGTH_LONG).show();
                else {
                    String city = user_filed.getText().toString();
                    String IPkey = "53ce7b49b53964f2a1a92ee28f9944d9";
                    String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + IPkey + "&units=metric&lang=ru";
//создаем Объект по методу getURLdata
                    new GetURLData().execute(url);
                }
            }

        });
    }

    //URL обработка
    private class GetURLData extends AsyncTask<String, String, String> {

        private void opPreExecute() {
            //срабатывает при отправление данных по URL адресу
            super.onPreExecute();
            result.setText("Ожидайте...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection Connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]); //объкет для обращения по адресу
                Connection = (HttpURLConnection) url.openConnection(); //открывает соединение
                Connection.connect();
                InputStream stream = Connection.getInputStream(); //считывает поток
                reader = new BufferedReader(new InputStreamReader(stream)); //передаем поток полученный при URL

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while((line=reader.readLine()) != null){
                    buffer.append(line).append("\n");

                    return buffer.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(Connection != null)
                    Connection.disconnect();
                if(reader!=null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
       return null; }
       @Override
        protected void onPostExecute(String result_info){
            super.onPostExecute(result_info);

            result.setText(result_info);
       }
    }
}