package ad.aplication.mji.babynanas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
  YouTubePlayerView youTubePlayerView;
  String API_KEY="AIzaSyBadk6TBj3YYh2zXN8qMiQMLRwR1klzaVQ";
  private static final int RECOVERY_REQUEST = 1;
  String TAG="VideoActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video);
    youTubePlayerView= findViewById(R.id.youtubeview);
    youTubePlayerView.initialize(API_KEY, this);
  }
  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
    Bundle bundle = getIntent().getExtras();
    String showVideo = bundle.getString("videoId");
    Log.e(TAG,"Video" +showVideo);
    youTubePlayer.cueVideo(showVideo);
  }
  @Override
  public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
    if (youTubeInitializationResult.isUserRecoverableError()) {
      youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
    } else {
      Toast.makeText(this, "Error Intializing Youtube Player", Toast.LENGTH_LONG).show();
    }
  }
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == RECOVERY_REQUEST) {
      getYouTubePlayerProvider().initialize(API_KEY, this);
    }
  }
  protected YouTubePlayer.Provider getYouTubePlayerProvider() {
    return youTubePlayerView;
  }
}
