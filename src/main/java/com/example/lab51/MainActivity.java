package com.example.lab51;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView t;
    //Button Requestbutton;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        t = findViewById(R.id.TEXTVIEW);


        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fetchData();
    }

    private void fetchData() {
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2803899/cars.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> t.setText("Failed: " + e.getMessage()));
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();

                    runOnUiThread(() -> {
                        try {
                            JSONArray jsonArray = new JSONArray(jsonData);
                            StringBuilder result = new StringBuilder();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject car = jsonArray.getJSONObject(i);
                                result.append("Owner: ").append(car.getString("OWNER")).append("\n");
                                result.append("age: ").append(car.getString("AGE")).append("\n");
                                result.append("Brand: ").append(car.getString("BRAND")).append("\n");
                                result.append("Plate: ").append(car.getString("NUMBER")).append("\n");
                            }
                            t.setText(result.toString());
                        } catch (Exception e) {
                            t.setText("Parsing error" + e.getMessage());
                        }
                    });
                } else {
                    runOnUiThread(() -> t.setText("Server returned error"));
                }
            }
        });
    }
}