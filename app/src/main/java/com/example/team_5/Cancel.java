package com.example.team_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Cancel extends AppCompatActivity {

    static RequestQueue requestQueue;
    static String tid;
    static String price;
    WebView webView;
    Gson gson;
    MyWebViewClient myWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        Intent intent = getIntent();
        tid = intent.getStringExtra("tid");
        price = intent.getStringExtra("price");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myWebViewClient = new Cancel.MyWebViewClient();
        webView = findViewById(R.id.webView);
        gson = new Gson();

        // 웹 뷰 설정
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(myWebViewClient);

        // 실행 시 바로 결제 Http 통신 실행
        requestQueue.add(myWebViewClient.cancelRequest);
    }

    public class MyWebViewClient extends WebViewClient {
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Debug", "Error : " + error);
            }
        };

        Response.Listener<String> cancelResponse = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Debug", "결제취소 response" + response);

                // get("받을 Key")로 Json 데이터를 받음
                Log.e("Debug", "결제취소 response tid : " + tid);
            }
        };

        StringRequest cancelRequest = new StringRequest(Request.Method.POST, "https://kapi.kakao.com/v1/payment/cancel", cancelResponse, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.e("Debug", "cancel_tid : "+ tid);

                Map<String, String> params = new HashMap<>();
                params.put("cid", "TC0ONETIME"); // 가맹점 코드
                params.put("tid", tid); // 가맹점 코드
                params.put("cancel_amount", price);
                params.put("cancel_tax_free_amount", "0");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "KakaoAK " + "5337d822d3a020b18067157e5c803999");
                return headers;
            }
        };
    }

}