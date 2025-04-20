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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        t = findViewById(R.id.TEXTVIEW);

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/mc/test.php")
                .build();


        client.newCall(request).enqueue(new Callback() {
            final Handler mainHandler = new Handler(Looper.getMainLooper());

            public void onFailure(okhttp3.Call call, IOException e){
                e.printStackTrace();
            }

            //hello, this is a test
            //this is a test 2


            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    mainHandler.post(new Runnable(){
                        @Override
                        public void run(){t.setText(responseData);
                        }
                    });
                }
            }
        });
    }
}