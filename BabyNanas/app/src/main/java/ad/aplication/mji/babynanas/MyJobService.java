package ad.aplication.mji.babynanas;

/**
 * Created by j2agm on 02/06/2017.
 */

import android.util.Log;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {

  private static final String TAG = "MyJobService";

  @Override
  public boolean onStartJob(JobParameters jobParameters) {
    Log.d(TAG, "Performing long running task in scheduled job");
    // TODO(developer): add long running task here.
    return false;
  }

  @Override
  public boolean onStopJob(JobParameters jobParameters) {
    return false;
  }

}