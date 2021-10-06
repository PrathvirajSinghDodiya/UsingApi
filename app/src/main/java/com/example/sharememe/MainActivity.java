package com.example.sharememe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ImageButton button,button1;
    String ret;
    ScrollView scrollView;
    final  String  EXTRA_MESSAGE="com.example.sharememe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.meme);
        button = findViewById(R.id.button2);
        button1 = findViewById(R.id.button);
        scrollView = findViewById(R.id.scrollView);

        button.setOnClickListener(new View.OnClickListener() {//.....Share Button....
            @Override
            public void onClick(View v) {
                onLoad();
                Toast.makeText(MainActivity.this, "loading.....", Toast.LENGTH_SHORT).show();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,ret);
                sendIntent.setType("text/plain");

                startActivity(sendIntent);
            }
        });

    }
     void onLoad(){


         String url = "https://meme-api.herokuapp.com/gimme";
         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                 (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                     @Override
                     public void onResponse(JSONObject response) {
                         try {
                             //String url = response.getString("url");
                             ret = response.getString("url");

                             Glide.with(MainActivity.this).load(ret).into(imageView);


                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }, new Response.ErrorListener() {

                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(MainActivity.this, "Opps.....Please restart the app", Toast.LENGTH_LONG).show();

                     }
                 });
         MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
     }

    @Override
    protected void onStart() {
        super.onStart();
        onLoad();
    }
}