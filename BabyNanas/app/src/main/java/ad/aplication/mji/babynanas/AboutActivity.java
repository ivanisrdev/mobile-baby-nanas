package ad.aplication.mji.babynanas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;
import com.vansuita.materialabout.views.AutoFitGridLayout.LayoutParams;

import ad.aplication.mji.babynanas.helper.AboutHelper;

public class AboutActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    AboutView view = AboutBuilder.with(this)
        .setPhoto(R.mipmap.profile_picture)
        .setCover(R.mipmap.profile_cover)
        .setName("MJI Aplication")
        .setSubTitle("Creadores de sueños")
        .setBrief("Acercamos las Apps a quines lo necesiten.")
        .setAppIcon(R.mipmap.ic_launcher)
        .setAppName(R.string.app_name)
        .addGooglePlayStoreLink("8002078663318221363")
        .addGitHubLink("user")
        .addFacebookLink("user")
        .addFiveStarsAction()
        .setVersionNameAsAppSubTitle()
        .addShareAction(R.string.app_name)
        .setWrapScrollView(true)
        .setLinksAnimated(true)
        .setShowAsCard(true)
        .build();

    addContentView(view, lp);

  }
}
