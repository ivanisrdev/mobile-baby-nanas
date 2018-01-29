package ad.aplication.mji.babynanas;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ohoussein.playpause.PlayPauseView;
import java.util.ArrayList;

/**
 * Created by j2agm on 29/01/2018.
 */

public class YoutubeChannelRecyclerAdapter extends RecyclerView.Adapter<YoutubeChannelRecyclerAdapter.ViewHolder> {

  Activity mActivity;
  private boolean isPlaying = false;
  ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
  private LayoutInflater inflater;
  ArrayList<VideoDetails> videoDetailsArrayList;
  private YoutubeChannelRecyclerAdapter.ViewHolder holderOld;

  public YoutubeChannelRecyclerAdapter(Activity activity, ArrayList<VideoDetails> videoDetailsArrayList) {
    this.mActivity = activity;
    this.videoDetailsArrayList = videoDetailsArrayList;
  }

  @Override
  public YoutubeChannelRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View v = LayoutInflater
        .from(viewGroup.getContext()).inflate(R.layout.card_youtube_video_row, viewGroup, false);
    return new YoutubeChannelRecyclerAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(final YoutubeChannelRecyclerAdapter.ViewHolder viewHolder, int i) {
    final VideoDetails item = this.videoDetailsArrayList.get(i);
    viewHolder.mTextView.setText(item.getVideoName());

    viewHolder.mImageView.setImageUrl(item.getURL(), this.imageLoader);

    viewHolder.mImagePlayView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        viewHolder.mImagePlayView.toggle(true);
        if (holderOld != null) {
          if (!holderOld.mTextView.getText().equals(viewHolder.mTextView.getText())) {
            holderOld.mImagePlayView.change(true);
          }
        }

        holderOld = viewHolder;
        Intent intent=new Intent(view.getContext(), VideoActivity.class);
        intent.putExtra("videoId",item.getVideoId());
        view.getContext().startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return this.videoDetailsArrayList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;
    private final NetworkImageView mImageView;
    private PlayPauseView mImagePlayView;

    ViewHolder(View v) {
      super(v);
      mTextView = v.findViewById(R.id.info_text);
      mImageView = v.findViewById(R.id.video_image);
      mImagePlayView = v.findViewById(R.id.image_play);
    }
  }
}
