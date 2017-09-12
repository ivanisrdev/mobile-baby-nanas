package ad.aplication.mji.babynanas.adapters;

import ad.aplication.mji.babynanas.MainActivity;
import ad.aplication.mji.babynanas.R;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ohoussein.playpause.PlayPauseView;
import io.realm.RealmResults;
import java.io.IOException;
import realmBD.Music;

/**
 * Created by j2agm on 23/02/2017.
 */

public class MusicRecyclerAdapter  extends RecyclerView.Adapter<MusicRecyclerAdapter.ViewHolder> {

  private RealmResults<Music> mItems;
  private MainActivity mActivity;
  private boolean isPlaying = false;
  private ViewHolder holderOld;

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
  public void onBindViewHolder(final ViewHolder viewHolder, int i) {
    final Music item = mItems.get(i);
    viewHolder.mTextView.setText(item.getTitle());
    Resources resources = mActivity.getApplication().getResources();
    final int resourceId = resources.getIdentifier(item.getImageName(), "drawable",
            mActivity.getApplication().getPackageName());
    Glide.with(mActivity)
        .load(resourceId)
        .into(viewHolder.mImageView);
    //viewHolder.mImagePlayView.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);

    viewHolder.mImagePlayView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        //mActivity.playAudio(JcAudio.createFromAssets(item.getTitle(), item.getTitle()+".mp3"));
        viewHolder.mImagePlayView.toggle(true);
        if (holderOld != null) {
          if (!holderOld.mTextView.getText().equals(viewHolder.mTextView.getText())) {
            holderOld.mImagePlayView.change(true);
          }
        }

        holderOld = viewHolder;

        try {
          isPlaying = mActivity.playOrPauseMusic(item);
          /*if (isPlaying) {
            viewHolder.mImagePlayView.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
          } else {
            viewHolder.mImagePlayView.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);
          }*/
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;
    private final ImageView mImageView;
    private PlayPauseView mImagePlayView;

    ViewHolder(View v) {
      super(v);
      mTextView = v.findViewById(R.id.info_text);
      mImageView = v.findViewById(R.id.image_music);
      mImagePlayView = v.findViewById(R.id.image_play);
    }
  }
}
