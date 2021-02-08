package efrei.android.lab2.flickrapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import efrei.android.lab2.flickrapp.MySingleton;
import efrei.android.lab2.flickrapp.R;
import efrei.android.lab2.flickrapp.adapter.MyAdapter;
import efrei.android.lab2.flickrapp.task.AsyncFlickrJSONData;
import efrei.android.lab2.flickrapp.task.AsyncFlickrJSONDataForList;

public class ListActivity extends AppCompatActivity {

    static String URL = "https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json";

    ListView urlList;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this.urlList = (ListView) findViewById(R.id.list);
        this.myAdapter = new MyAdapter();

        Thread thread = new Thread(() -> {
            AsyncTask<String, Void, JSONObject> taskFetch = new AsyncFlickrJSONDataForList(myAdapter);
            taskFetch.execute(URL);
        });
        thread.start();


        //  Link adapter to url list

        this.urlList.setAdapter(myAdapter);
    }
}