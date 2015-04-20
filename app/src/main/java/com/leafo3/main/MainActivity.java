package com.leafo3.main;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leafo3.main.R;
import com.leafo3.util.EnvironmentUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity {
    private static final int CAMERA_CODE = 100;
    private ImageButton reportButton, cameraButton, instructionsButton;
    private static final String CAMERA_PATH = "";
    private String currentPhotoPath;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init(){
        reportButton = (ImageButton)findViewById(R.id.activity_main_report_image_view);
        cameraButton = (ImageButton)findViewById(R.id.activity_main_camera_image_view);
        instructionsButton = (ImageButton)findViewById(R.id.activity_main_instructions_image_view);
        textView = (TextView)findViewById(R.id.activity_main_email_text_view);
        textView.setText(EnvironmentUtils.getUserEmail(MainActivity.this));
        //listeners
        reportButton.setOnClickListener(reportClickListener);
        cameraButton.setOnClickListener(cameraClickListener);
        instructionsButton.setOnClickListener(instructionsClickListener);
    }



    private View.OnClickListener reportClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, LeafRecordActivity.class);
            startActivity(intent);
            //go to reports
        }
    };

    private View.OnClickListener cameraClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO: dialog to select between camera and gallery

            //start camera intent
            final String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/leafo3/";
            currentPhotoPath = directory + "leaf_" + new SimpleDateFormat("MM-dd-yyyy HH-mm").format(new Date()) + ".jpg";
            File file = new File(currentPhotoPath);
            try{
                file.createNewFile();
            }catch(IOException ex){
                //error
            }

            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_CODE);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_CODE && resultCode == RESULT_OK){
            //send path to PhotoActivity
            Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
            intent.putExtra(PhotoActivity.PATH_PARAM, currentPhotoPath);

            startActivity(intent);
        }
    }

    private View.OnClickListener instructionsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
            startActivity(intent);
        }
    };
}
