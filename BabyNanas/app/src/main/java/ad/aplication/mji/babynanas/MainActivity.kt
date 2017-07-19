package ad.aplication.mji.babynanas

import ad.aplication.mji.babynanas.adapters.MusicRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.Toast
import com.example.jean.jcplayer.JcAudio
import com.example.jean.jcplayer.JcPlayerService
import com.example.jean.jcplayer.JcPlayerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import io.realm.Realm
import realmBD.Music
import java.util.*

class MainActivity : AppCompatActivity(), JcPlayerService.OnInvalidPathListener {
    private var mDrawerLayout: DrawerLayout? = null
    private var stopPlayerTimmer: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar!!
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
        actionBar.setDisplayHomeAsUpEnabled(true)

        mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView
                .setNavigationItemSelectedListener { menuItem ->
                    mDrawerLayout!!.closeDrawers()
                    when (menuItem.itemId) {
                        R.id.nanas_music -> {
                            viewPager!!.setCurrentItem(0, true)
                            menuItem.isChecked = true
                            true
                        }
                        R.id.relax_music -> {
                            viewPager!!.setCurrentItem(1, true)
                            menuItem.isChecked = true
                            true
                        }
                        R.id.classical_music -> {
                            viewPager!!.setCurrentItem(2, true)
                            menuItem.isChecked = true
                            true
                        }
                        R.id.pref -> {
                            val i = Intent(this@MainActivity, MyPreferencesActivity::class.java)
                            startActivity(i)
                            true
                        }
                        R.id.about -> {
                            menuItem.isChecked = true
                            val aboutIntent = Intent(this@MainActivity, AboutActivity::class.java)
                            startActivity(aboutIntent)
                            true
                        }

                        else -> true
                    }
                }

        jcPlayerView = findViewById<View>(R.id.jcPlayerView) as JcPlayerView

        val adapter = MusicTypePagerAdapter(supportFragmentManager)
        viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        viewPager!!.adapter = adapter
        val tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout
        tabLayout.setupWithViewPager(viewPager)

        viewPager!!.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    val query = realm!!.where(Music::class.java)
                    val resultsNana = query.equalTo(Music.TYPE, "Nana")
                            .findAll()
                    val jcAudiosNana = ArrayList<JcAudio>()
                    for (i in resultsNana.indices) {
                        jcAudiosNana.add(JcAudio.createFromAssets(resultsNana[i].title,
                                resultsNana[i].title + ".mp3"))
                    }
                    jcPlayerView!!.kill()
                    jcPlayerView!!.resetPlayerInfo()
                    jcPlayerView!!.initPlaylist(jcAudiosNana)
                    jcPlayerView!!.registerInvalidPathListener(this@MainActivity)
                }

                if (position == 1) {
                    val query = realm!!.where(Music::class.java)
                    val resultsRelax = query.equalTo(Music.TYPE, "Relax")
                            .findAll()
                    val jcAudiosRelax = ArrayList<JcAudio>()
                    for (i in resultsRelax.indices) {
                        jcAudiosRelax.add(JcAudio.createFromAssets(resultsRelax[i].title,
                                resultsRelax[i].title + ".mp3"))
                    }
                    jcPlayerView!!.kill()
                    jcPlayerView!!.resetPlayerInfo()
                    jcPlayerView!!.initPlaylist(jcAudiosRelax)
                    jcPlayerView!!.registerInvalidPathListener(this@MainActivity)
                }
                if (position == 2) {
                    val query = realm!!.where(Music::class.java)
                    val resultsClassical = query.equalTo(Music.TYPE, "Classical")
                            .findAll()
                    val jcAudiosClassical = ArrayList<JcAudio>()
                    for (i in resultsClassical.indices) {
                        jcAudiosClassical.add(JcAudio.createFromAssets(resultsClassical[i].title,
                                resultsClassical[i].title + ".mp3"))
                    }
                    jcPlayerView!!.kill()
                    jcPlayerView!!.resetPlayerInfo()
                    jcPlayerView!!.initPlaylist(jcAudiosClassical)
                    jcPlayerView!!.registerInvalidPathListener(this@MainActivity)
                }

                super.onPageSelected(position)
            }
        })

        realm = Realm.getDefaultInstance()

        //load first tab music playlist
        val query = realm!!.where(Music::class.java)
        val resultsNana = query.equalTo(Music.TYPE, "Nana")
                .findAll()
        val jcAudiosNana = ArrayList<JcAudio>()
        for (i in resultsNana.indices) {
            jcAudiosNana.add(JcAudio.createFromAssets(resultsNana[i].title,
                    resultsNana[i].title + ".mp3"))
        }
        jcPlayerView!!.resetPlayerInfo()
        jcPlayerView!!.initPlaylist(jcAudiosNana)
        jcPlayerView!!.registerInvalidPathListener(this@MainActivity)

        // afegim el ads
        val mAdView = findViewById<View>(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        when (id) {
            android.R.id.home -> {
                mDrawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }

            R.id.action_sleep -> {
                val stopPlayerTimerMinutes = stopPlayerTimmer / 60000
                val res = resources
                val message = String
                        .format(res.getString(R.string.sleeping_message), stopPlayerTimerMinutes)
                Toast.makeText(this, message, Toast.LENGTH_LONG)
                        .show()

                // Initialize the CountDownClass
                val timer = MyCountDown(stopPlayerTimmer.toLong(), 1000)
                timer.start()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val stopPlayMusicIntervalPref = prefs.getString("stopPlay", "15")
        stopPlayerTimmer = Integer.valueOf(stopPlayMusicIntervalPref)!! * 60000
    }

    public override fun onPause() {
        super.onPause()
        jcPlayerView!!.createNotification()
    }

    override fun onDestroy() {
        super.onDestroy()
        jcPlayerView!!.kill()
        realm!!.close()
    }

    override fun onPathError(jcAudio: JcAudio) {
        Toast.makeText(this, jcAudio.path + " with problems", Toast.LENGTH_SHORT).show()
        //        player.removeAudio(jcAudio);
        //        player.next();
    }

    fun playAudio(jcAudio: JcAudio) {
        jcPlayerView!!.playAudio(jcAudio)
        Toast.makeText(this, jcPlayerView!!.currentAudio.title, Toast.LENGTH_SHORT)
                .show()
    }

    class MusicTypeFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {

            val args = arguments
            val tabPosition = args.getInt(TAB_POSITION)

            if (tabPosition == 1) {
                val query = realm!!.where(Music::class.java)
                val resultsNana = query.equalTo(Music.TYPE, "Nana")
                        .findAll()
                val v = inflater!!.inflate(R.layout.fragment_list_music, container, false)
                val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
                val itemAnimator = DefaultItemAnimator()
                recyclerView.itemAnimator = itemAnimator
                recyclerView.setHasFixedSize(true)
                recyclerView.setItemViewCacheSize(20)
                recyclerView.isDrawingCacheEnabled = true
                recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                recyclerView.layoutManager = GridLayoutManager(activity, 2)
                val musicRecyclerAdapter = MusicRecyclerAdapter(
                        activity as MainActivity, resultsNana)
                recyclerView.adapter = musicRecyclerAdapter
                //musicRecyclerAdapter.notifyDataSetChanged();

                return v
            } else {
                if (tabPosition == 2) {
                    val query = realm!!.where(Music::class.java)
                    val resultsRelax = query.equalTo(Music.TYPE, "Relax")
                            .findAll()
                    val v = inflater!!.inflate(R.layout.fragment_list_music, container, false)
                    val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
                    val itemAnimator = DefaultItemAnimator()
                    recyclerView.itemAnimator = itemAnimator
                    recyclerView.setHasFixedSize(true)
                    recyclerView.setItemViewCacheSize(20)
                    recyclerView.isDrawingCacheEnabled = true
                    recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                    recyclerView.layoutManager = GridLayoutManager(activity, 2)
                    val musicRecyclerAdapter = MusicRecyclerAdapter(
                            activity as MainActivity, resultsRelax)
                    recyclerView.adapter = musicRecyclerAdapter
                    //musicRecyclerAdapter.notifyDataSetChanged();

                    return v
                } else {
                    if (tabPosition == 3) {
                        val query = realm!!.where(Music::class.java)
                        val resultsClassical = query.equalTo(Music.TYPE, "Classical")
                                .findAll()
                        val v = inflater!!.inflate(R.layout.fragment_list_music, container, false)
                        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
                        val itemAnimator = DefaultItemAnimator()
                        recyclerView.itemAnimator = itemAnimator
                        recyclerView.setHasFixedSize(true)
                        recyclerView.setItemViewCacheSize(20)
                        recyclerView.isDrawingCacheEnabled = true
                        recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        recyclerView.layoutManager = GridLayoutManager(activity, 2)
                        val musicRecyclerAdapter = MusicRecyclerAdapter(
                                activity as MainActivity, resultsClassical)
                        recyclerView.adapter = musicRecyclerAdapter
                        //musicRecyclerAdapter.notifyDataSetChanged();

                        return v
                    }
                }
                return null
            }
        }

        companion object {

            private val TAB_POSITION = "tab_position"

            fun newInstance(tabPosition: Int): MusicTypeFragment {
                val fragment = MusicTypeFragment()
                val args = Bundle()
                args.putInt(TAB_POSITION, tabPosition)
                fragment.arguments = args
                return fragment
            }
        }
    }

    class PrefsFragment : PreferenceFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences)
        }
    }

    // inner class
    private inner class MyCountDown internal constructor(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            Toast.makeText(baseContext, "Stop Music", Toast.LENGTH_SHORT)
                    .show()
            jcPlayerView!!.pause()
        }

        override fun onTick(duration: Long) {}
    }

    private inner class MusicTypePagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        internal val PAGE_COUNT = 3
        private val tabTitles = arrayOf(getString(R.string.nanas), getString(R.string.relax), getString(R.string.classical))

        override fun getCount(): Int {
            return PAGE_COUNT
        }

        override fun getItem(position: Int): Fragment {
            return MusicTypeFragment.newInstance(position + 1)
        }

        override fun getPageTitle(position: Int): CharSequence {
            // Generate title based on item position
            return tabTitles[position]
        }
    }

    companion object {

        private var jcPlayerView: JcPlayerView? = null
        private var realm: Realm? = null
        private var viewPager: ViewPager? = null
    }
}
