package ad.aplication.mji.babynanas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import ad.aplication.mji.babynanas.helper.AboutHelper;

/**
 * Created by jrvansuita on 17/02/17.
 */

public class FragmentAboutActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LinearLayout(this));

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new FragmentSample()).commit();
    }

    public static class FragmentSample extends Fragment {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.about2_view, null);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setRetainInstance(true);
            AboutHelper.with(getActivity()).init().loadAbout();
        }
    }

}
