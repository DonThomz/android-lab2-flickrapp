package efrei.android.lab2.flickrapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JsonOperation {

    public static JSONObject extractJsonResult(String result) {
        try {
            // clean string
            String preMatch = result.split("jsonFlickrFeed[(]")[1];
            preMatch = preMatch.substring(0, preMatch.length() - 1);
            return new JSONObject(preMatch);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String extractImageURL(int itemNumber, JSONObject object) {
        try{
            JSONArray items = object.getJSONArray("items");
            JSONObject item = items.getJSONObject(itemNumber);
            JSONObject media = item.getJSONObject("media");
            return (String) media.get("m");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> extractAllURL(JSONObject object) {
        try{
            JSONArray items = object.getJSONArray("items");
            return IntStream.range(0, items.length()).mapToObj(i -> {
                try {
                    return items.getJSONObject(i).getJSONObject("media").get("m").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "";
                }
            }).collect(Collectors.toList());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Read an input stream and return string
     *
     * @param in the input stream
     * @return the input stream reading
     * @throws IOException throw if error during reading
     */
    public static String readStream(InputStream in) throws IOException {
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining());
    }

}
