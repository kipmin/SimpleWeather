package com.kipmin.simpleweather.Utility;

import android.util.Log;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yzl91 on 2017/9/17.
 * okhttp
 */

public class HttpUtil {

    private static OkHttpClient client;

    public static void sendOkHttpRequest(String address, final Callback callback) {
//        OkHttpClient client = new OkHttpClient();
        client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(address)
//                .build();
//        client.newCall(request).enqueue(callback);
//        String run(String address) throws IOException {
//            Request request = new Request.Builder().url(address).build();
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                return response.body().string();
//            } else {
//                throw new IOException("Unexpected code " + response);
//            }
//        }
        final Request request=new Request.Builder()
                .get()
                .url(address)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    client.newCall(request).enqueue(callback);
                    if (response.isSuccessful()) {
                        Log.d("Kipmin","打印GET响应的数据：" + response.body().string());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
