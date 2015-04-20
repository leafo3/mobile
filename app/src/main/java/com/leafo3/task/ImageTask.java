package com.leafo3.task;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.leafo3.data.DBHelper;
import com.leafo3.model.Leaf;
import com.leafo3.model.NewResourceResponse;
import com.leafo3.util.CustomRestTemplate;
import com.leafo3.util.EnvironmentUtils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto Rubalcaba on 4/12/2015.
 */
public class ImageTask extends BaseTask {

    private static final String PATH = "/rest/leafs/create";
    private Bitmap bitmap;
    private String jsonResponse;
    private String imagePath;
    private String location;
    private String email;
    private boolean hasError;
    private String errorMessage;
    private String title;
    private String comment;
    private int damageClass;

    private NewResourceResponse response;
    public ImageTask(Context context, String imagePath, String location, String title, String comment){
        super(context, "Uploading image", "Please, wait");
        this.imagePath = imagePath;
        this.location = location;
        this.email = EnvironmentUtils.getUserEmail(context);
        this.bitmap = BitmapFactory.decodeFile(this.imagePath);
        this.title = title;
        this.comment = comment;
    }


    @Override
    protected Void doInBackground(Void... params){
        String url = "";
        if(EnvironmentUtils.TEST)
            url = String.format(EnvironmentUtils.TEST_URL + PATH, location, EnvironmentUtils.getUserId(context));
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.setCharset(Charset.forName("UTF8"));

        RestTemplate template = new CustomRestTemplate();
        template.getMessageConverters().add( formHttpMessageConverter );
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("location", location);
        map.add("file", new FileSystemResource(imagePath));
        map.add("email", email);
        map.add("title", title);
        map.add("comment", comment);

        HttpHeaders imageHeaders = new HttpHeaders();
        imageHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        ResponseEntity<NewResourceResponse> responseEntity = null;
        try{
            HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<MultiValueMap<String, Object>>(map, imageHeaders);
            responseEntity = template.exchange(url, HttpMethod.POST, imageEntity, NewResourceResponse.class);
        }catch (Exception ex){
            hasError = true;
            errorMessage = ex.getMessage();
        }
        if(! hasError){
            this.response = responseEntity.getBody();
            this.damageClass = this.response.getDamageClass();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void param){
        if(hasError){
            dismissProgressDialog();
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            dismissProgressDialog();
            //save the task
            Leaf leaf = new Leaf();
            leaf.setTitle(title);
            leaf.setComment(comment);
            leaf.setImageUrl(imagePath);
            leaf.setDamageClass(damageClass);
            leaf.setLocation(location);
            DBHelper helper = new DBHelper(context);
            helper.insertLeaf(leaf);
            Toast.makeText(context, "Thanks for sharing with us, leafer! :)", Toast.LENGTH_LONG).show();
            ((Activity)context).finish();
        }
    }
}
