package com.example.androidassignments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    private ProgressBar progressBar;
    private String selectedCity = "Ottawa";  // Default city

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather_forecast);

        // Initialize progress bar
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the Spinner with city names
        Spinner citySpinner = findViewById(R.id.city_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.canadian_cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        // Set listener to detect city selection changes
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = parent.getItemAtPosition(position).toString();
                // Fetch and display weather for the selected city
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q="
                        + selectedCity + ",ca&APPID=a28f4a4d639650a4c5bcc3327e323cfb&mode=xml&units=metric";
                new ForecastQuery(WeatherForecast.this).execute(apiUrl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCity = "Ottawa";  // Default to Ottawa if nothing selected
            }
        });

        // Initial call to fetch weather for default city
        String defaultApiUrl = "https://api.openweathermap.org/data/2.5/weather?q=Ottawa,ca&APPID=a28f4a4d639650a4c5bcc3327e323cfb&mode=xml&units=metric";
        new ForecastQuery(WeatherForecast.this).execute(defaultApiUrl);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private Context context;
        private String minTemperature;
        private String maxTemperature;
        private String currentTemperature;
        private Bitmap weatherIcon;
        private String iconCode;

        public ForecastQuery(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];  // Use the URL passed to execute()
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    Log.e("ForecastQuery", "Failed to fetch data; HTTP response code: " + responseCode);
                    return null;
                }

                InputStream inputStream = connection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        String tagName = parser.getName();

                        if (tagName.equals("temperature")) {
                            // Add null checks for each attribute
                            String tempValue = parser.getAttributeValue(null, "value");
                            currentTemperature = (tempValue != null) ? tempValue : "N/A";
                            publishProgress(25);

                            String minValue = parser.getAttributeValue(null, "min");
                            minTemperature = (minValue != null) ? minValue : "N/A";
                            publishProgress(50);

                            String maxValue = parser.getAttributeValue(null, "max");
                            maxTemperature = (maxValue != null) ? maxValue : "N/A";
                            publishProgress(75);
                        }

                        if (tagName.equals("weather")) {
                            // Check if the icon attribute exists
                            String iconValue = parser.getAttributeValue(null, "icon");
                            iconCode = (iconValue != null) ? iconValue : "";
                        }
                    }
                    eventType = parser.next();
                }

                inputStream.close();
                connection.disconnect();

                if (iconCode != null && !iconCode.isEmpty()) {
                    String iconUrl = "https://openweathermap.org/img/w/" + iconCode + ".png";
                    weatherIcon = downloadImage(iconUrl);
                    Log.e("ForecaseQuery","Successfully downloaded Image from iconURL");
                    publishProgress(100);
                }
            } catch (Exception e) {
                Log.e("ForecastQuery", "Error in doInBackground: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (progressBar.getVisibility() != View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE);
            }
            progressBar.setProgress(values[0]);
        }

        private Bitmap downloadImage(String urlString) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                Log.e("ForecastQuery", "Error downloading image: " + e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView currentTempView = findViewById(R.id.current_temp);
            TextView minTempView = findViewById(R.id.min_temp);
            TextView maxTempView = findViewById(R.id.max_temp);

            currentTempView.setText("Current Temperature: " + currentTemperature + "°C");
            minTempView.setText("Min Temperature: " + minTemperature + "°C");
            maxTempView.setText("Max Temperature: " + maxTemperature + "°C");

            ImageView weatherImageView = findViewById(R.id.weather_image);
            if (weatherIcon != null) {
                Glide.with(context)
                        .load(weatherIcon)
                        .into(weatherImageView);
            } else {
                weatherImageView.setImageResource(R.drawable.p01d); // Placeholder in case of missing icon
            }

            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
