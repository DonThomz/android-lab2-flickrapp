package efrei.android.lab2.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    /*
        Exercise 12
        Remove the "jsonFlickrFeed("

        Exercise 13
        Because with have :
            String: type of params to be send at execution
            Void: type of object to notify the progression (so here no progression type)
            JSONObject: type of result
     */

    Button imageBtn;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageBtn = (Button) findViewById(R.id.main_btn_download_image);
        this.imageView = (ImageView) findViewById(R.id.main_image);
        this.imageBtn.setOnClickListener(new GetImageOnClickListener(this.imageView));

    }
}