package ad.aplication.mji.babynanas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;
import com.vansuita.materialabout.views.AutoFitGridLayout.LayoutParams;

public class AboutActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    AboutView view = AboutBuilder.with(this)
        .setPhoto(R.drawable.logo_mji)
        .setCover(R.mipmap.profile_cover)
        .setName("MJI Aplication")
        .setSubTitle("Creadores de sueños")
        .setBrief("Acercamos las Apps a quines lo necesiten.")
        .setAppIcon(R.mipmap.ic_launcher)
        .setLinksColumnsCount(4)
        .setDividerDashGap(13)
        .setAppName(R.string.app_name)
        .addFiveStarsAction()
        .setVersionNameAsAppSubTitle()
        .addShareAction(R.string.app_name)
        .setWrapScrollView(true)
        .setLinksAnimated(true)
        .setShowAsCard(true)
        .addFeedbackAction("mji.aplication@gmail.com")
        .build();

    addContentView(view, lp);

  }
}
