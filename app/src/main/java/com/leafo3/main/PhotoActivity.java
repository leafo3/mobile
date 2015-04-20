package com.leafo3.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.leafo3.main.R;
import com.leafo3.task.ImageTask;
import com.leafo3.util.ImageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PhotoActivity extends ActionBarActivity {

    public static final String PATH_PARAM = "path";
    private String path;
    private ImageView imageView;
    private Button button;
    private Bitmap bitmap;
    private LocationManager locationManager;
    private String locationString;
    private EditText titleEditText;
    private EditText commentEditText;
    private String compressedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        init();
        //get path
        path = getIntent().getStringExtra(PATH_PARAM);
        //load image
        loadImage();
    }

    private void init(){
        this.imageView = (ImageView)findViewById(R.id.activity_photo_picture_image_view);
        this.button = (Button)findViewById(R.id.activity_photo_send_button);
        this.titleEditText = (EditText)findViewById(R.id.activity_photo_title);
        this.commentEditText = (EditText)findViewById(R.id.activity_photo_comment);

        this.button.setOnClickListener(onClickListener);
        this.locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locationListener);
        this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5, locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            locationString = location.getLatitude() + "," + location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //validate
            String title = titleEditText.getText().toString();
            String comment = commentEditText.getText().toString();
            if(title == null || title.isEmpty()){
                Toast.makeText(PhotoActivity.this, "Must specify a title", Toast.LENGTH_LONG).show();
                return;
            }else if(comment == null || comment.isEmpty()){
                Toast.makeText(PhotoActivity.this, "Any comment or description for this leaf?", Toast.LENGTH_LONG).show();
                return;
            }
            if(locationString == null || locationString.isEmpty()){
                Toast.makeText(PhotoActivity.this, "We're trying to get your location in order to upload your leaf image", Toast.LENGTH_LONG).show();
                return;
            }
            ImageTask task = new ImageTask(PhotoActivity.this, compressedImage, locationString, title, comment);
            task.execute();
        }
    };

    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 if(fromUser){
                     Bitmap resultBitmap = ImageUtil.transform(progress, bitmap);
                     //show image
                     imageView.setImageBitmap(resultBitmap);
                 }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void loadImage(){
        File file = new File(this.path);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        //rotate bitmap
        Bitmap rotated = Bitmap.createBitmap(scaled, 0, 0, scaled.getWidth(), scaled.getHeight(), matrix, true);
        this.bitmap = rotated;
        this.compressedImage = this.path.replace(".jpg", "_compressed.jpg");
        //compress the image
        OutputStream stream = null;
        try{
            stream = new FileOutputStream(this.compressedImage);

            rotated.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        }catch(IOException ex){
            //could not compress image
        }

        this.imageView.setImageBitmap(rotated);
    }
}
