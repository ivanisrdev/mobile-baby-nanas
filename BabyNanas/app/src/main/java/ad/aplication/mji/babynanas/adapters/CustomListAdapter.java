package ad.aplication.mji.babynanas.adapters;


import ad.aplication.mji.babynanas.MyApplication;
import ad.aplication.mji.babynanas.R;
import ad.aplication.mji.babynanas.VideoActivity;
import ad.aplication.mji.babynanas.VideoDetails;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.ArrayList;

/**
 * Created by j2agm on 22/01/2018.
 */

public class CustomListAdapter extends BaseAdapter {
  Activity activity;
  ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
  private LayoutInflater inflater;
  ArrayList<VideoDetails> singletons;
  public CustomListAdapter(Activity activity, ArrayList<VideoDetails> singletons) {
    this.activity = activity;
    this.singletons = singletons;
  }
  public int getCount() {
    return this.singletons.size();
  }
  public Object getItem(int i) {
    return this.singletons.get(i);
  }
  public long getItemId(int i) {
    return (long) i;
  }
  public View getView(int i, View convertView, ViewGroup viewGroup) {
    if (this.inflater == null) {
      this.inflater = this.activity.getLayoutInflater();
      // getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    if (convertView == null) {
      convertView = this.inflater.inflate(R.layout.videolist, null);
    }
    if (this.imageLoader == null) {
      this.imageLoader = MyApplication.getInstance().getImageLoader();
    }
    NetworkImageView networkImageView = convertView.findViewById(R.id.video_image);
    final TextView imgtitle = convertView.findViewById(R.id.video_title);
    final TextView imgdesc = convertView.findViewById(R.id.video_descriptio);
    final TextView tvURL = convertView.findViewById(R.id.tv_url);
    final  TextView tvVideoID = convertView.findViewById(R.id.tv_videoId);
    convertView.findViewById(R.id.asser).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent=new Intent(view.getContext(), VideoActivity.class);
        intent.putExtra("videoId",tvVideoID.getText().toString());
        view.getContext().startActivity(intent);
      }
    });
    VideoDetails singleton = this.singletons.get(i);
    networkImageView.setImageUrl(singleton.getURL(), this.imageLoader);
    tvVideoID.setText(singleton.getVideoId());
    imgtitle.setText(singleton.getVideoName());
    imgdesc.setText(singleton.getVideoDesc());
    return convertView;
  }
}
