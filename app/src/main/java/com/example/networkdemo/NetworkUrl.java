package com.example.networkdemo;

import android.provider.Settings;
import android.text.TextUtils;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUrl {
    public static String get(String urlPath){
        HttpURLConnection connection = null;
        InputStream is = null;
        try {
            //将url字符串转为URL对象
            URL url = new URL(urlPath);
            //获得HttpURLConnection对象
            connection = (HttpURLConnection) url.openConnection();
            //设置
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("Connection","Keep-Alive?ip=112.2.253.218");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36"+
                    "(KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36");
            connection.setDoInput(true);
            if ("https".equalsIgnoreCase(url.getProtocol())){
                ((HttpsURLConnection) connection).setSSLSocketFactory(
                        HttpsUtil.getSSLSocketFactory());
            }
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine())!= null){
                    response.append(line);
                }
                is.close();
                connection.disconnect();
                return response.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getParamString(List<NameValuePair> pairs)
            throws UnsupportedEncodingException{
        StringBuilder builder = new StringBuilder();
        for (NameValuePair pair : pairs){
            if (!TextUtils.isEmpty(builder)){
                builder.append("&");
            }
            builder.append(URLEncoder.encode(pair.getName(),"UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(pair.getValue(),"UTF-8"));
        }
        return builder.toString();
    }
    public static String post(String urlPath, List<NameValuePair> params){
        if (params == null || params.size() == 0){
            return  get(urlPath);
        }
        try {
            String body = getParamString(params);
            byte[] data = body.getBytes();
            //将url字符串转为URL对象
            URL url = new URL(urlPath);
            //获得HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("Connection","Keep-Alive?ip=112.2.253.218");
            connection.setRequestProperty("User-Agent","Mozilla/5.0(Windows NT 10.0;Win64;x64" +
                    "AppleWebKit/537.36(KHTML,like Gecko) Chrome/77.0.3865.120 Safari/537.36)");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            if ("https".equalsIgnoreCase(url.getProtocol())){
                ((HttpsURLConnection) connection).setSSLSocketFactory(
                        HttpsUtil.getSSLSocketFactory());
            }
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(data.length));
            OutputStream os =connection.getOutputStream();
            os.write(data);
            os.flush();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine())!= null){
                    response.append(line);
                }
                is.close();
                connection.disconnect();
                return response.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
