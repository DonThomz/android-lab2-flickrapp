package efrei.android.lab2.flickrapp.task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import efrei.android.lab2.flickrapp.JsonOperation;
import efrei.android.lab2.flickrapp.adapter.MyAdapter;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    JSONObject result;

    MyAdapter myAdapter;

    public AsyncFlickrJSONDataForList(MyAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        this.result = this.fetchData(strings[0]);
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        // add each url to the adapter
        List<String> urls = JsonOperation.extractAllURL(jsonObject);
        if (urls != null) {
            urls.forEach(url -> {
                myAdapter.add(url);
                Log.i("JFL", "Adding to adapter url : " + url);
            });
            myAdapter.notifyDataSetChanged();

        }

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
