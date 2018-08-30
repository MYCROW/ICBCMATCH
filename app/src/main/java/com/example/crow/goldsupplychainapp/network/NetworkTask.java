package com.example.crow.goldsupplychainapp.network;

import android.os.AsyncTask;
import android.util.Log;

public abstract class NetworkTask extends AsyncTask<String, Integer, String>{
    public static final String TAG = "NetworkTask";

    public static String GET = "GET",POST = "POST";
    private String mRequestMethod;//access method
    protected Boolean isSuccess = true;

    //Constructor
    public NetworkTask(String method){
        this.mRequestMethod = method;
    }

    @Override
    protected  String doInBackground(String... params) {
        String data;
        if(GET.equals(mRequestMethod)) {
            data = doGet(params[0]);
        } else if(POST.equals(mRequestMethod)) {
            data = doPost(params);
        } else {
            throw new RuntimeException("Request mode can only be GET or POST!");
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        if(mResponseListener != null){
            if(isSuccess){
                mResponseListener.onSuccess(result);
            }
            else{
                mResponseListener.onError(result);
            }
        }
        else
            Log.i(TAG,"mResponseListener:ResponseListener == null");
    }

    //Access Network by Get
    public abstract String doGet(String url);

    //Access Network by Post
    public abstract String doPost(String ...params);

    //监听回调接口
    private ResponseListener mResponseListener;

    public void setResponseListener(ResponseListener r){
        this.mResponseListener = r;
    }

    public interface ResponseListener {
        void onSuccess(String result);
        void onError(String error);
    }
}
