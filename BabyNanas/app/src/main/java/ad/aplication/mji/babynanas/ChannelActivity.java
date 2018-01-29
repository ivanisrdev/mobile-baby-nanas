package ad.aplication.mji.babynanas;

import ad.aplication.mji.babynanas.adapters.CustomListAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChannelActivity extends AppCompatActivity {

  ListView lvVideo;
  ArrayList<VideoDetails> videoDetailsArrayList;
  CustomListAdapter customListAdapter;
  String searchName;
  String TAG = "ChannelActivity";
  String URL = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyC_wZKJcHfx3jgHTtGU0ermO7uptkiANnY"
      + "&channelId=UCtWuHtjRPM6CZnQn0cLvoSw&part=snippet&maxResults=50";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_channel);
    lvVideo = findViewById(R.id.videoList);
    videoDetailsArrayList = new ArrayList<>();
    customListAdapter = new CustomListAdapter(ChannelActivity.this,videoDetailsArrayList);
    showVideo();
  }
  private void showVideo() {
    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
    StringRequest stringRequest=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        try {
          JSONObject jsonObject=new JSONObject(response);
          JSONArray jsonArray=jsonObject.getJSONArray("items");
          for(int i=0;i<jsonArray.length();i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            JSONObject jsonVideoId = jsonObject1.getJSONObject("id");
            boolean isVideo = jsonVideoId.getString("kind").equals("youtube#video");
            if (isVideo) {
              JSONObject jsonsnippet = jsonObject1.getJSONObject("snippet");
              JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails")
                  .getJSONObject("medium");
              VideoDetails videoDetails = new VideoDetails();
              String videoid = jsonVideoId.getString("videoId");
              Log.e(TAG, " New Video Id" + videoid);
              videoDetails.setURL(jsonObjectdefault.getString("url"));
              videoDetails.setVideoName(jsonsnippet.getString("title"));
              videoDetails.setVideoDesc(jsonsnippet.getString("description"));
              videoDetails.setVideoId(videoid);
              videoDetailsArrayList.add(videoDetails);
            }
          }
          lvVideo.setAdapter(customListAdapter);
          customListAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        NetworkResponse networkResponse = error.networkResponse;
        error.printStackTrace();
      }
    })
    {

      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        HashMap<String,String> hashMap= new HashMap<>();
        hashMap.put("key","AIzaSyBadk6TBj3YYh2zXN8qMiQMLRwR1klzaVQ");
        hashMap.put("part","snippet");
        hashMap.put("channelId","UC1NF71EwP41VdjAU1iXdLkw");
        hashMap.put("maxResult","50");
        return  hashMap;
      }

      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        // do not add anything here
        return headers;
      }
      @Override
      public String getBodyContentType() {
        return "application/json";
      }

    };
    int socketTimeout = 30000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    stringRequest.setRetryPolicy(policy);
    requestQueue.add(stringRequest);
  }
}
