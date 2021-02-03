package efrei.android.lab2.flickrapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

public class GetImageOnClickListener implements View.OnClickListener {

    static String URL = "https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json";

    private final WeakReference<ImageView> imageView;

    public GetImageOnClickListener(ImageView imageView) {
        this.imageView = new WeakReference<>(imageView);
    }

    @Override
    public void onClick(View view) {
        try {
            AsyncTask<String, Void, JSONObject> taskFetch = new AsyncFlickrJSONData();
            taskFetch.execute(URL);
            String url = JsonOperation.extractImageURL(0, taskFetch.get());
            Log.i("JFL", url);
            AsyncTask<String, Void, Bitmap> taskDownload = new AsyncBitmapDownloader(imageView.get());
            taskDownload.execute(url);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
