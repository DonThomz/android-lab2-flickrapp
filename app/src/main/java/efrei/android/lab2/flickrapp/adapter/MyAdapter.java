package efrei.android.lab2.flickrapp.adapter;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

import efrei.android.lab2.flickrapp.MySingleton;
import efrei.android.lab2.flickrapp.R;

public class MyAdapter extends BaseAdapter {

    Vector<String> vector = new Vector<>();

    public void add(String url) {
        vector.add(url);
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int i) {
        return vector.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            // get the singleton queue
            RequestQueue queue = MySingleton.getInstance(viewGroup.getContext()).getRequestQueue();

            // inflate the bitmap layout
            view = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bitmaplayout, viewGroup, false);
            ImageView t = view.findViewById(R.id.url_image);
            // add image downloaded to ImageView
            Response.Listener<Bitmap> rep_listener = t::setImageBitmap;

            // create image request
            ImageRequest imageRequest = new ImageRequest(this.getItem(i).toString(),
                    rep_listener,
                    0,
                    0,
                    ImageView.ScaleType.CENTER_CROP,
                    Bitmap.Config.RGB_565,
                    error -> {
                        error.printStackTrace();
                        Log.e("JFL", error.getMessage());
                    });

            // add image request to request queue
            queue.add(imageRequest);
            // update the view with the url
            /*view = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.textviewlayout, viewGroup, false);
            TextView t = view.findViewById(R.id.text_view_url);
            t.setText(this.getItem(i).toString());*/
        }
        // the updated view
        return view;
    }
}
