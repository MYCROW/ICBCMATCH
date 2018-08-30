package com.example.crow.goldsupplychainapp.network;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpURLConnectionNetworkTask extends NetworkTask{

    public static Integer READTIME = 5000;
    public static Integer CONNTIME = 5000;

    public HttpURLConnectionNetworkTask(String method){
        super(method);
    }

    @Override
    public String doGet(String httpUrl){
        String result;
        try{
            URL url = new URL(httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(READTIME);
            urlConnection.setConnectTimeout(CONNTIME);
            urlConnection.setRequestProperty("Charset", "UTF-8");
            if (urlConnection.getResponseCode() == 200) {
                InputStream is = urlConnection.getInputStream();
                result = readFromStream(is);
            } else {
                isSuccess = false;
                result = "Network Response Code != 200";
            }
        }
        catch (IOException e){
            isSuccess = false;
            result = "Network Access Error:"+e.getMessage();
        }
        return result;
    }

    @Override
    public String doPost(String ...params){
        // process params to
        // httpurl:String + paramMap:Map<String,String>
        String httpUrl = params[0];
        String[] strs = new String[params.length-1];
        System.arraycopy(params,1,strs,0,params.length-1);
        Map<String,String> paramMap = new HashMap<>();
        for(String s:strs){
            String[] ms = s.split("=");
            if(ms.length != 2)
                continue;
            paramMap.put(ms[0],ms[1]);
        }
        String result;
        try{
            URL url = new URL(httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(READTIME);
            urlConnection.setConnectTimeout(CONNTIME);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            // process paramMap
            // to Json -> String -> Byte
            JSONObject json = new JSONObject(paramMap);
            String jsonString = json.toString();
            byte[] jsonByte = jsonString.getBytes();
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonByte);
            os.flush();

            if (urlConnection.getResponseCode() == 200) {
                InputStream is = urlConnection.getInputStream();
                result = readFromStream(is);
            } else {
                isSuccess = false;
                result = "Network Response Code != 200:"+urlConnection.getResponseCode();
            }
        }
        catch (IOException e){
            isSuccess = false;
            result = "Network Access Error:"+e.getMessage();
        }
        return result;
    }

    /**
     * 输入流获取字符串
     *
     * @param is 输入流
     * @return String 返回的字符串
     * @throws IOException
     */
    public String readFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        String result = baos.toString();
        baos.close();
        return result;
    }
}
