package ad.aplication.mji.babynanas;

import ad.aplication.mji.babynanas.adapters.MusicRecyclerAdapter;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

  private static JcPlayerView jcPlayerView;
  private static Realm realm;
  private DrawerLayout mDrawerLayout;

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

    MusicTypePagerAdapter adapter = new MusicTypePagerAdapter(getSupportFragmentManager());
    ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
    viewPager.setAdapter(adapter);
    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
    tabLayout.setupWithViewPager(viewPager);

    realm = Realm.getDefaultInstance();

    //create elemnts en BBDD
    /*realm.beginTransaction();

    // Add a music
    Music music = realm.createObject(Music.class, 15L);
    music.setTitle("Angel_Feliz");
    music.setType("Nana");
    music.setCount(0);
    music.setImageName("baby5");

    Music music2 = realm.createObject(Music.class, 16L);
    music2.setTitle("Nana_Arrorro");
    music2.setType("Nana");
    music2.setCount(0);
    music2.setImageName("baby6");

    Music music3 = realm.createObject(Music.class, 17L);
    music3.setTitle("Nana_Duermete_nino");
    music3.setType("Nana");
    music3.setCount(0);
    music3.setImageName("baby7");

    Music music4 = realm.createObject(Music.class,18L);
    music4.setTitle("Nana_Estrellita_donde_estas");
    music4.setType("Nana");
    music4.setCount(0);
    music4.setImageName("baby8");

    Music music5 = realm.createObject(Music.class,19L);
    music5.setTitle("Nana_Mama_naturaleza");
    music5.setType("Nana");
    music5.setCount(0);
    music5.setImageName("baby9");

    Music music6 = realm.createObject(Music.class,20L);
    music6.setTitle("Nana_Muñequita_linda");
    music6.setType("Nana");
    music6.setCount(0);
    music6.setImageName("baby10");

    Music music7 = realm.createObject(Music.class,7L);
    music7.setTitle("Sonido_Pajaros");
    music7.setType("Relax");
    music7.setCount(0);
    music7.setImageName("baby11");

    Music music8 = realm.createObject(Music.class,8L);
    music8.setTitle("Sonido_Viento");
    music8.setType("Relax");
    music8.setCount(0);
    music8.setImageName("baby12");

    Music music9 = realm.createObject(Music.class,9L);
    music9.setTitle("Sonido_Vientre");
    music9.setType("Relax");
    music9.setCount(0);
    music9.setImageName("baby13");

   /* realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<Music> result = realm.where(Music.class).equalTo(Music.ID,15L).findAll();
        result.deleteAllFromRealm();
      }
    });*/
    //realm.commitTransaction();

    //delete all
//    realm.close();
//    Realm.deleteRealm(realm.getConfiguration());
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
    Toast.makeText(this, jcPlayerView.getCurrentAudio().getTitle(), Toast.LENGTH_SHORT)
        .show();
  }

  public static class MusicTypeFragment extends Fragment {

    private static final String TAB_POSITION = "tab_position";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

      Bundle args = getArguments();
      int tabPosition = args.getInt(TAB_POSITION);
      if (tabPosition == 1) {
        RealmQuery<Music> query = realm.where(Music.class);
        RealmResults<Music> results = query.equalTo(Music.TYPE, "Nana")
            .findAll();
        View v = inflater.inflate(R.layout.fragment_list_music, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        MusicRecyclerAdapter musicRecyclerAdapter = new MusicRecyclerAdapter(
            (MainActivity) getActivity(), results);
        recyclerView.setAdapter(musicRecyclerAdapter);
        //musicRecyclerAdapter.notifyDataSetChanged();

        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
          jcAudios.add(JcAudio.createFromAssets(results.get(i).getTitle(),
              results.get(i).getTitle() + ".mp3"));
        }
        jcPlayerView.initPlaylist(jcAudios);
        jcPlayerView.registerInvalidPathListener((MainActivity) getActivity());

        return v;
      } else {
        if (tabPosition == 2) {
          RealmQuery<Music> query = realm.where(Music.class);
          RealmResults<Music> results = query.equalTo(Music.TYPE, "Relax")
              .findAll();
          View v = inflater.inflate(R.layout.fragment_list_music, container, false);
          RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
          RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
          recyclerView.setItemAnimator(itemAnimator);
          recyclerView.setHasFixedSize(true);
          recyclerView.setItemViewCacheSize(20);
          recyclerView.setDrawingCacheEnabled(true);
          recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
          recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
          MusicRecyclerAdapter musicRecyclerAdapter = new MusicRecyclerAdapter(
              (MainActivity) getActivity(), results);
          recyclerView.setAdapter(musicRecyclerAdapter);
          //musicRecyclerAdapter.notifyDataSetChanged();

          ArrayList<JcAudio> jcAudios = new ArrayList<>();
          for (int i = 0; i < results.size(); i++) {
            jcAudios.add(JcAudio.createFromAssets(results.get(i).getTitle(),
                results.get(i).getTitle() + ".mp3"));
          }
          jcPlayerView.initPlaylist(jcAudios);
          jcPlayerView.registerInvalidPathListener((MainActivity) getActivity());

          return v;
        } else {
          if (tabPosition == 3) {
            RealmQuery<Music> query = realm.where(Music.class);
            RealmResults<Music> results = query.equalTo(Music.TYPE, "Classical")
                .findAll();
            View v = inflater.inflate(R.layout.fragment_list_music, container, false);
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            recyclerView.setItemAnimator(itemAnimator);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(20);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            MusicRecyclerAdapter musicRecyclerAdapter = new MusicRecyclerAdapter(
                (MainActivity) getActivity(), results);
            recyclerView.setAdapter(musicRecyclerAdapter);
            //musicRecyclerAdapter.notifyDataSetChanged();

            ArrayList<JcAudio> jcAudios = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
              jcAudios.add(JcAudio.createFromAssets(results.get(i).getTitle(),
                  results.get(i).getTitle() + ".mp3"));
            }
            jcPlayerView.initPlaylist(jcAudios);
            jcPlayerView.registerInvalidPathListener((MainActivity) getActivity());

            return v;
          }
        }
        return null;
      }
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
