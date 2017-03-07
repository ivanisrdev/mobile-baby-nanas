package ad.aplication.mji.babynanas.adapters;

import ad.aplication.mji.babynanas.MainActivity;
import ad.aplication.mji.babynanas.R;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jean.jcplayer.JcAudio;
import io.realm.RealmResults;
import realmBD.Music;

/**
 * Created by j2agm on 23/02/2017.
 */

public class MusicRecyclerAdapter  extends RecyclerView.Adapter<MusicRecyclerAdapter.ViewHolder> {

  private RealmResults<Music> mItems;
  private MainActivity mActivity;

  public MusicRecyclerAdapter(MainActivity activity, RealmResults<Music> items) {
    mItems = items;
    mActivity = activity;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_music_row, viewGroup, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int i) {
    final Music item = mItems.get(i);
    viewHolder.mTextView.setText(item.getTitle());
    //TODO ad image dynamically
    Resources resources = mActivity.getApplication().getResources();
    final int resourceId = resources.getIdentifier(item.getImageName(), "drawable",
            mActivity.getApplication().getPackageName());
    viewHolder.mImageView.setImageDrawable(ContextCompat.getDrawable(mActivity, resourceId));
    viewHolder.mImagePlayView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mActivity.playAudio(JcAudio.createFromAssets(item.getTitle(), item.getTitle()+".mp3"));
      }
    });
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;
    private final ImageView mImageView;
    private final ImageView mImagePlayView;

    ViewHolder(View v) {
      super(v);
      mTextView = (TextView) v.findViewById(R.id.info_text);
      mImageView = (ImageView) v.findViewById(R.id.image_music);
      mImagePlayView = (ImageView) v.findViewById(R.id.image_play);
    }
  }
}
