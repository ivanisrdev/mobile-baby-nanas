package ad.aplication.mji.babynanas;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jean.jcplayer.JcAudio;
import com.example.jean.jcplayer.JcPlayerService;
import com.example.jean.jcplayer.JcPlayerView;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import java.util.ArrayList;
import realmBD.Music;

public class MainActivity extends AppCompatActivity implements
    JcPlayerService.OnInvalidPathListener {

  private DrawerLayout mDrawerLayout;
  private JcPlayerView jcPlayerView;
  private Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    assert actionBar != null;
    actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    actionBar.setDisplayHomeAsUpEnabled(true);

    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
    navigationView
        .setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
            return true;
          }
        });

    jcPlayerView = (JcPlayerView) findViewById(R.id.jcPlayerView);

    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    jcAudios.add(JcAudio.createFromAssets("Relaxing_Music_Sleep", "Relaxing_Music_Sleep.mp3"));
    jcPlayerView.initPlaylist(jcAudios);
    jcPlayerView.registerInvalidPathListener(this);

    MusicTypePagerAdapter adapter = new MusicTypePagerAdapter(getSupportFragmentManager(),
        this.getApplicationContext(), jcAudios);
    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
    viewPager.setAdapter(adapter);
    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    tabLayout.setupWithViewPager(viewPager);

    realm = Realm.getDefaultInstance();

    RealmQuery<Music> query = realm.where(Music.class);
    RealmResults<Music> results = query.findAll();

    Music musciTemp = results.get(1);

    String titletemp = musciTemp.getTitle();

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
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onPause() {
    super.onPause();
    jcPlayerView.createNotification();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    jcPlayerView.kill();
    realm.close();
  }

  @Override
  public void onPathError(JcAudio jcAudio) {
    Toast.makeText(this, jcAudio.getPath() + " with problems", Toast.LENGTH_LONG).show();
//        player.removeAudio(jcAudio);
//        player.next();
  }

  public void playAudio(JcAudio jcAudio) {
    jcPlayerView.playAudio(jcAudio);
    Toast.makeText(this, jcPlayerView.getCurrentAudio().getOrigin().toString(), Toast.LENGTH_SHORT)
        .show();
  }

  public static class MusicTypeFragment extends Fragment {

    private static final String TAB_POSITION = "tab_position";
    private static final String PLAY_LIST = "PlayList";

    public MusicTypeFragment() {
    }

    public static MusicTypeFragment newInstance(int tabPosition, ArrayList<JcAudio> jcAudioList) {
      MusicTypeFragment fragment = new MusicTypeFragment();
      Bundle args = new Bundle();
      args.putInt(TAB_POSITION, tabPosition);
      args.putSerializable(PLAY_LIST, jcAudioList);
      fragment.setArguments(args);
      return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      Bundle args = getArguments();
      int tabPosition = args.getInt(TAB_POSITION);
      final ArrayList<JcAudio> jcAudioList = (ArrayList<JcAudio>) args.getSerializable(PLAY_LIST);
      if (tabPosition == 1) {
        View v = inflater.inflate(R.layout.card_music_view, container, false);
        ImageView playButton = (ImageView) v.findViewById(R.id.image_play);
        playButton.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            JcAudio JcAudio = jcAudioList != null ? jcAudioList.get(0) : null;
            ((MainActivity) getActivity()).playAudio(JcAudio);
          }
        });
        return v;
      } else {
        TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("Text in Tab #" + tabPosition);
        return tv;
      }
    }
  }

  class MusicTypePagerAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{getString(R.string.nanas),
        getString(R.string.relax), getString(R.string.classical)};
    private Context context;
    private ArrayList<JcAudio> jcAudioList;

    MusicTypePagerAdapter(FragmentManager fm, Context context, ArrayList<JcAudio> jcAudioList) {
      super(fm);
      this.context = context;
      this.jcAudioList = jcAudioList;
    }

    @Override
    public int getCount() {
      return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
      return MusicTypeFragment.newInstance(position + 1, jcAudioList);
    }

    @Override
    public CharSequence getPageTitle(int position) {
      // Generate title based on item position
      return tabTitles[position];
    }
  }
}
