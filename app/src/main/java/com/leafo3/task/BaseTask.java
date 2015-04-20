package com.leafo3.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto Rubalcaba on 4/11/2015.
 */
public abstract class BaseTask extends AsyncTask<Void, Void, Void> {
    private static List<HttpMessageConverter<?>> converters;

    static{
        converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new GsonHttpMessageConverter());
    }
    protected HttpClient client;
    protected HttpResponse response;
    protected HttpPost post;
    protected HttpGet get;

    protected Context context;
    private ProgressDialog progressDialog;
    private String message;
    private String title;

    public BaseTask(Context context, String message, String title){
        this.context = context;
        //init progress dialog
        this.client = new DefaultHttpClient();
        this.message = message;
        this.title = title;
    }

    @Override
    protected void onPreExecute(){
        showProgressDialog();
    }

    protected void showProgressDialog(){
        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setTitle(title);
        this.progressDialog.setMessage(message);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    protected void dismissProgressDialog(){
        if(this.progressDialog.isShowing())
            this.progressDialog.dismiss();
    }
}
