package ad.aplication.mji.babynanas;

import ad.aplication.mji.babynanas.adapters.MusicRecyclerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import realmBD.Music;

public class MainActivity extends AppCompatActivity  {

  private static Realm realm;
  private static ViewPager viewPager;
  private DrawerLayout mDrawerLayout;
  private int stopPlayerTimer;
  private MediaPlayer mediaPlayer;
  private int length;
  private long itemPosition = -1;

  static String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    actionBar.setDisplayHomeAsUpEnabled(true);

    mDrawerLayout =  findViewById(R.id.drawer_layout);

    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView
        .setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            mDrawerLayout.closeDrawers();
            switch (menuItem.getItemId()) {
              case R.id.nanas_music:
                viewPager.setCurrentItem(0, true);
                menuItem.setChecked(true);
                return true;
              case R.id.relax_music:
                viewPager.setCurrentItem(1, true);
                menuItem.setChecked(true);
                return true;
              case R.id.classical_music:
                viewPager.setCurrentItem(2, true);
                menuItem.setChecked(true);
                return true;
              case R.id.pref:
                Intent i = new Intent(MainActivity.this, MyPreferencesActivity.class);
                startActivity(i);
                return true;
              case R.id.about:
                menuItem.setChecked(true);
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
              case R.id.youtube_channel:
                Intent youtubeChannelIntent = new Intent(MainActivity.this, ChannelActivity.class);
                startActivity(youtubeChannelIntent);
                return true;
              default:
                return true;
            }
          }
        });
    MusicTypePagerAdapter adapter = new MusicTypePagerAdapter(getSupportFragmentManager());
    viewPager = findViewById(R.id.viewpager);
    viewPager.setOffscreenPageLimit(2);
    viewPager.setAdapter(adapter);
    TabLayout tabLayout = findViewById(R.id.tabLayout);
    tabLayout.setupWithViewPager(viewPager);

    viewPager.addOnPageChangeListener(new SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        if (position == 0) {
          //RealmQuery<Music> query = realm.where(Music.class);
          //RealmResults<Music> resultsNana = query.equalTo(Music.TYPE, "Nana")
            //  .findAll();
          //ArrayList<JcAudio> jcAudiosNana = new ArrayList<>();
          //for (int i = 0; i < resultsNana.size(); i++) {
            //jcAudiosNana.add(JcAudio.createFromAssets(resultsNana.get(i).getTitle(),
              //  resultsNana.get(i).getTitle() + ".mp3"));
          //}
        }

        if (position == 1) {
         /// RealmQuery<Music> query = realm.where(Music.class);
          //RealmResults<Music> resultsRelax = query.equalTo(Music.TYPE, "Relax")
            //  .findAll();
          //ArrayList<JcAudio> jcAudiosRelax = new ArrayList<>();
          //for (int i = 0; i < resultsRelax.size(); i++) {
            //jcAudiosRelax.add(JcAudio.createFromAssets(resultsRelax.get(i).getTitle(),
              ///  resultsRelax.get(i).getTitle() + ".mp3"));
          //}

        }
        if (position == 2) {
          /*RealmQuery<Music> query = realm.where(Music.class);
          RealmResults<Music> resultsClassical = query.equalTo(Music.TYPE, "Classical")
              .findAll();*/
          //ArrayList<JcAudio> jcAudiosClassical = new ArrayList<>();
          /*for (int i = 0; i < resultsClassical.size(); i++) {
            jcAudiosClassical.add(JcAudio.createFromAssets(resultsClassical.get(i).getTitle(),
                resultsClassical.get(i).getTitle() + ".mp3"));
          }*/
        }

        super.onPageSelected(position);
      }
    });

    realm = Realm.getDefaultInstance();

    //load first tab music playlist
    /*RealmQuery<Music> query = realm.where(Music.class);
    RealmResults<Music> resultsNana = query.equalTo(Music.TYPE, "Nana")
        .findAll();
    ArrayList<JcAudio> jcAudiosNana = new ArrayList<>();
    for (int i = 0; i < resultsNana.size(); i++) {
      jcAudiosNana.add(JcAudio.createFromAssets(resultsNana.get(i).getTitle(),
          resultsNana.get(i).getTitle() + ".mp3"));
    }
    jcPlayerView.resetPlayerInfo();
    jcPlayerView.initPlaylist(jcAudiosNana);
    jcPlayerView.registerInvalidPathListener(MainActivity.this);*/

    // afegim el ads
    AdView mAdView = findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder().build();
    mAdView.loadAd(adRequest);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.mainmenu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    switch (id) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;

      case R.id.action_sleep:
        int stopPlayerTimerMinutes = stopPlayerTimer / 60000;
        Resources res = getResources();
        String message = String
            .format(res.getString(R.string.sleeping_message), stopPlayerTimerMinutes);
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show();

        // Initialize the CountDownClass
        CountDownTimer timer = new MyCountDown(stopPlayerTimer, 1000);
        timer.start();
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    String stopPlayMusicIntervalPref = prefs.getString("stopPlay", "15");
    stopPlayerTimer = Integer.valueOf(stopPlayMusicIntervalPref) * 60000;
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    stopPlaying();
    realm.close();
  }

  public boolean playOrPauseMusic(Music item) throws IOException {
    if (item.getId() == itemPosition) {
      if(mediaPlayer != null && mediaPlayer.isPlaying()){
        mediaPlayer.pause();
        length = mediaPlayer.getCurrentPosition();
        return false;
      } else {
        playMusic(item);
        return true;
      }
    } else {
      itemPosition = item.getId();
      stopPlaying();
      playMusic(item);
      return true;
    }
  }

  private void playMusic(Music item) throws IOException {
    AssetFileDescriptor descriptor;
    mediaPlayer = new MediaPlayer();
    descriptor = this.getAssets().openFd(item.getTitle()+".mp3");
    mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),descriptor.getLength());
    descriptor.close();
    mediaPlayer.prepare();
    mediaPlayer.seekTo(length);
    mediaPlayer.setLooping(true);
    mediaPlayer.setVolume(1f, 1f);
    mediaPlayer.start();
  }

  private void stopPlaying() {
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.release();
      mediaPlayer = null;
    }
  }


  public static class MusicTypeFragment extends Fragment {

    private static final String TAB_POSITION = "tab_position";
    ArrayList<VideoDetails> videoDetailsArrayList = new ArrayList<>();

    public MusicTypeFragment() {
    }

    public static MusicTypeFragment newInstance(int tabPosition) {
      MusicTypeFragment fragment = new MusicTypeFragment();
      Bundle args = new Bundle();
      args.putInt(TAB_POSITION, tabPosition);
      fragment.setArguments(args);
      return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
        Bundle savedInstanceState) {

      Bundle args = getArguments();
      int tabPosition = args.getInt(TAB_POSITION);

      if (tabPosition == 1) {
        /*RealmQuery<Music> query = realm.where(Music.class);
        RealmResults<Music> resultsNana = query.equalTo(Music.TYPE, "Nana")
            .findAll();*/
        final View v = inflater.inflate(R.layout.fragment_list_music, container, false);
        String URL = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyC_wZKJcHfx3jgHTtGU0ermO7uptkiANnY"
            + "&channelId=UCtWuHtjRPM6CZnQn0cLvoSw&part=snippet&maxResults=50";
        RequestQueue requestQueue= Volley.newRequestQueue((getActivity().getApplicationContext()));
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

              RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
              RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
              recyclerView.setItemAnimator(itemAnimator);
              recyclerView.setHasFixedSize(true);
              recyclerView.setItemViewCacheSize(20);
              recyclerView.setDrawingCacheEnabled(true);
              recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
              recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
              YoutubeChannelRecyclerAdapter youtubeChannelRecyclerAdapter = new YoutubeChannelRecyclerAdapter(
                  getActivity(), videoDetailsArrayList);
              recyclerView.setAdapter(youtubeChannelRecyclerAdapter);
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


        //musicRecyclerAdapter.notifyDataSetChanged();

        return v;
      } else {
        if (tabPosition == 2) {
          RealmQuery<Music> query = realm.where(Music.class);
          RealmResults<Music> resultsRelax = query.equalTo(Music.TYPE, "Relax")
              .findAll();
          View v = inflater.inflate(R.layout.fragment_list_music, container, false);
          RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
          RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
          recyclerView.setItemAnimator(itemAnimator);
          recyclerView.setHasFixedSize(true);
          recyclerView.setItemViewCacheSize(20);
          recyclerView.setDrawingCacheEnabled(true);
          recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
          recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
          MusicRecyclerAdapter musicRecyclerAdapter = new MusicRecyclerAdapter(
              (MainActivity) getActivity(), resultsRelax);
          recyclerView.setAdapter(musicRecyclerAdapter);
          //musicRecyclerAdapter.notifyDataSetChanged();

          return v;
        } else {
          if (tabPosition == 3) {
            RealmQuery<Music> query = realm.where(Music.class);
            RealmResults<Music> resultsClassical = query.equalTo(Music.TYPE, "Classical")
                .findAll();
            View v = inflater.inflate(R.layout.fragment_list_music, container, false);
            RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            recyclerView.setItemAnimator(itemAnimator);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(20);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            MusicRecyclerAdapter musicRecyclerAdapter = new MusicRecyclerAdapter(
                (MainActivity) getActivity(), resultsClassical);
            recyclerView.setAdapter(musicRecyclerAdapter);
            //musicRecyclerAdapter.notifyDataSetChanged();

            return v;
          }
        }
        return null;
      }

    }
  }

  public static class PrefsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Load the preferences from an XML resource
      addPreferencesFromResource(R.xml.preferences);
    }
  }

  // inner class
  private class MyCountDown extends CountDownTimer {

    MyCountDown(long millisInFuture, long countDownInterval) {
      super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish() {
      Toast.makeText(getBaseContext(), "Stop Music", Toast.LENGTH_SHORT)
          .show();
      stopPlaying();
    }

    @Override
    public void onTick(long duration) {
    }
  }

  private class MusicTypePagerAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{getString(R.string.nanas),
        getString(R.string.relax), getString(R.string.classical)};

    MusicTypePagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public int getCount() {
      return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
      return MusicTypeFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      // Generate title based on item position
      return tabTitles[position];
    }
  }
}
