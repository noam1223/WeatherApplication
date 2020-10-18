package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView cityTextView, tempTextView, descTextView;
    EditText cityEditText;
    ImageView weatherImageView;
    FloatingActionButton searchFlotingActionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityTextView = findViewById(R.id.town);
        tempTextView = findViewById(R.id.temp);
        descTextView = findViewById(R.id.date);

        cityEditText = findViewById(R.id.search_bar);
        weatherImageView = findViewById(R.id.image_wheather);
        searchFlotingActionBtn = findViewById(R.id.floating_action_btn);

        searchFlotingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    apiKey(cityEditText.getText().toString());
                }



            }
        });

    }


    private void apiKey(final String cityName) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(getString(R.string.startLink)+ cityName +"&" + getString(R.string.appId) + "&" + getString(R.string.units))
                .get()
                .build();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {

            Response response = client.newCall(request).execute();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    String responseData = response.body().string();

                    try {

                        JSONObject jsonObject = new JSONObject(responseData);

                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        String description = jsonObject1.getString("description");
                        String icons = jsonObject1.getString("icon");


                        JSONObject temp1 = jsonObject.getJSONObject("main");
                        double temperature = temp1.getDouble("temp");

                        setText(cityTextView, cityName);

                        String temp = Math.round(temperature) + " °C";
                        setText(tempTextView, temp);
                        setText(descTextView, description);
                        setImage(weatherImageView, icons);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setText(final TextView textView, final String name){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(name);
            }
        });

    }


    private void setImage(final ImageView image,final String name) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                switch (name){
                    case "01d": image.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                        break;
                    case "01n": image.setImageDrawable(getResources().getDrawable(R.drawable.d01d));
                        break;
                    case "02d": image.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "02n": image.setImageDrawable(getResources().getDrawable(R.drawable.d02d));
                        break;
                    case "03d": image.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                        break;
                    case "03n": image.setImageDrawable(getResources().getDrawable(R.drawable.d03d));
                        break;
                    case "04d": image.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                        break;
                    case "04n": image.setImageDrawable(getResources().getDrawable(R.drawable.d04d));
                        break;
                    case "09d": image.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                        break;
                    case "09n": image.setImageDrawable(getResources().getDrawable(R.drawable.d09d));
                        break;
                    case "10d": image.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                        break;
                    case "10n": image.setImageDrawable(getResources().getDrawable(R.drawable.d10d));
                        break;
                    case "11d": image.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                        break;
                    case "11n": image.setImageDrawable(getResources().getDrawable(R.drawable.d11d));
                        break;
                    case "13d": image.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                        break;
                    case "13n": image.setImageDrawable(getResources().getDrawable(R.drawable.d13d));
                        break;
                    default:
                        image.setImageDrawable(getResources().getDrawable(R.drawable.wheather));

                }


            }
        });

    }
}