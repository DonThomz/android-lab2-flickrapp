package efrei.android.lab2.flickrapp.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import efrei.android.lab2.flickrapp.R;
import efrei.android.lab2.flickrapp.activity.MainActivity;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {


    private final WeakReference<ImageView> imageViewWeakReference;

    public AsyncBitmapDownloader(ImageView imageView) {
        imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadImage(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);


        if (isCancelled()){
            bitmap = null;
        }

        ImageView imageView = imageViewWeakReference.get();
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }

        Log.i("JSL", String.valueOf(bitmap));
    }

    @Override
    protected void onPreExecute() {
        ImageView imageView = imageViewWeakReference.get();
        if (imageView != null) {
            imageView.setImageResource(R.drawable.ic_question_mark);
        }
    }

    private Bitmap downloadImage(String urlPath) {
        URL url;
        try {
            // simulate downloading
            Thread.sleep(1000);
            url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                // get bitmap
                return BitmapFactory.decodeStream(in);
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
