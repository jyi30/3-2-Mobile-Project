package com.example.team_5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class PayActivity extends AppCompatActivity {

    static RequestQueue requestQueue;
    private String productName;
    private String productPrice;
    private HashMap<String, Object> oderMap;
    private FirebaseFirestore db;

    WebView webView;
    Gson gson;
    MyWebViewClient myWebViewClient;
    String tidPin;
    String pgToken;

    public PayActivity(){

    }

//    public PayActivity(String prdName, String prdPrice) {
//        PayActivity.productName = prdName;
//        PayActivity.productPrice = prdPrice;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

//        this.productName = getIntent().getStringExtra("name");
//        this.productPrice = getIntent().getStringExtra("price");
        this.oderMap = (HashMap<String, Object>) getIntent().getSerializableExtra("oder_map");
        this.productName = oderMap.get("show_name").toString();
        this.productPrice = oderMap.get("price").toString();
        Log.d("Pay", oderMap.toString());
        db = FirebaseFirestore.getInstance();
        // 초기화
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myWebViewClient = new MyWebViewClient();
        webView = findViewById(R.id.webView);
        gson = new Gson();

        // 웹 뷰 설정
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(myWebViewClient);

        // 실행 시 바로 결제 Http 통신 실행
        requestQueue.add(myWebViewClient.readyRequest);
    }

    public class MyWebViewClient extends WebViewClient {

        // 에러 - 통신을 받을 Response 변수
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Debug", "Error : " + error);
            }
        };

        // 결제 준비 단계 - 통신을 받을 Response 변수
        Response.Listener<String> readyResponse = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Debug", response);
                // 결제가 성공 했다면 돌려받는 JSON객체를 파싱함.
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(response);

                // get("받을 Key")로 Json 데이터를 받음
                // - 결제 요청에 필요한 next_redirect_mobile_url, tid를 파싱
                String url = element.getAsJsonObject().get("next_redirect_mobile_url").getAsString();
                String tid = element.getAsJsonObject().get("tid").getAsString();
                Log.e("Debug", "url : " + url);
                Log.e("Debug", "tid : " + tid);

                webView.loadUrl(url);
                tidPin = tid;
            }
        };

        // 결제 준비 단계 - 통신을 넘겨줄 Request 변수
        StringRequest readyRequest = new StringRequest(Request.Method.POST, "https://kapi.kakao.com/v1/payment/ready", readyResponse, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.e("Debug", "name : " + productName);
                Log.e("Debug", "price : " + productPrice);

                Map<String, String> params = new HashMap<>();
                params.put("cid", "TC0ONETIME"); // 가맹점 코드
                params.put("partner_order_id", "1001"); // 가맹점 주문 번호
                params.put("partner_user_id", "gorany"); // 가맹점 회원 아이디
                params.put("item_name", productName); // 상품 이름
                params.put("quantity", "1"); // 상품 수량
                params.put("total_amount", productPrice); // 상품 총액
                params.put("tax_free_amount", "0"); // 상품 비과세
                params.put("approval_url", "https://www.naver.com/success"); // 결제 성공시 돌려 받을 url 주소
                params.put("cancel_url", "https://www.naver.com/cancel"); // 결제 취소시 돌려 받을 url 주소
                params.put("fail_url", "https://www.naver.com/fali"); // 결제 실패시 돌려 받을 url 주소
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "KakaoAK " + "5337d822d3a020b18067157e5c803999");
                return headers;
            }
        };

        // 결제 요청 단계 - 통신을 받을 Response 변수
        Response.Listener<String> approvalResponse = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Debug", "OnApprovalResponse "+response);
                oderMap.put("cid", "TC0ONETIME");
                oderMap.put("tid", tidPin);
                oderMap.put("pg_token", pgToken);
                DocumentReference oderRef = db.collection("Order").document();
                oderMap.put("order_id",oderRef.getId());
                oderMap.put("order_time", FieldValue.serverTimestamp());
                oderRef.set(oderMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(@NonNull Void aVoid) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        };

        // 결제 요청 단계 - 통신을 넘겨줄 Request 변수
        StringRequest approvalRequest = new StringRequest(Request.Method.POST, "https://kapi.kakao.com/v1/payment/approve", approvalResponse, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cid", "TC0ONETIME");
                params.put("tid", tidPin);
                params.put("partner_order_id", "1001");
                params.put("partner_user_id", "gorany");
                params.put("pg_token", pgToken);
                params.put("total_amount", productPrice);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "KakaoAK " + "5337d822d3a020b18067157e5c803999");
                return headers;
            }
        };

        // URL 변경시 발생 이벤트
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.e("Debug", "url" + url);
            if (url != null && url.contains("pg_token=")) {
                Log.d("Debug","token_url");
                String pg_Token = url.substring(url.indexOf("pg_token=") + 9);
                pgToken = pg_Token;

                requestQueue.add(approvalRequest);
                view.loadUrl(url);
            } else if (url != null && url.startsWith("intent://")) {
                Log.d("Debug", "intent url");
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {

                        startActivity(intent);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                view.loadUrl(url);
            } else if(url.contains("success")){
                Log.d("Debug", "pay success url");

            }

            return false;
        }
    }
}