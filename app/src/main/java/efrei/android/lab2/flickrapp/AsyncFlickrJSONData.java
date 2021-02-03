package efrei.android.lab2.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {

    JSONObject result;

    @Override
    protected JSONObject doInBackground(String... strings) {
        this.result = this.fetchData(strings[0]);
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        Log.i("JFL", String.valueOf(result));

        // get first image url
        Log.i("JFL", JsonOperation.extractImageURL(0, jsonObject));
    }

    /**
     * Fetch data from an url and log back with JFL key
     * WITH AUTHORIZATION (username:password)
     *
     * @param urlPath the url to fetch
     */
    private JSONObject fetchData(String urlPath) {

        URL url;
        try {
            url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = JsonOperation.readStream(in);
                // get json information
                return JsonOperation.extractJsonResult(s);
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
