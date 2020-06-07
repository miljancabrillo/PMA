package com.pma.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.pma.utils.Utils;
import com.pma.dao.ActivityDao;
import com.pma.dao.Database;
import com.pma.dao.UserDao;
import com.pma.model.Activity;
import com.pma.model.User;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActivityDetectionReceiver extends BroadcastReceiver {

    private ActivityDao activityDao;
    private UserDao userDao;
    private  Context context;
    @Override
    public void onReceive(Context context, Intent intent) {

        activityDao = Database.getInstance(context).activityDao();
        userDao = Database.getInstance(context).userDao();
        this.context = context;

        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);
            for (ActivityTransitionEvent event : result.getTransitionEvents()) {
                if(event.getActivityType() == DetectedActivity.WALKING &&
                        event.getTransitionType() == ActivityTransition.ACTIVITY_TRANSITION_ENTER){


                    String userId = Utils.getCurrentUsername(context);
                    if(userId.equals("")) return;

                    Activity activity = new Activity();
                    NotificationUtils.sendNotification(context, "Walking started!");

                    long elapsedSeconds = (SystemClock.elapsedRealtimeNanos() - event.getElapsedRealTimeNanos())/1000000000;
                    activity.setDate(new Date());

                    Date startTime = new Date();
                    startTime.setTime(startTime.getTime() + elapsedSeconds*1000);
                    activity.setStartTime(startTime);

                    activity.setName("Walking");

                    //NotificationUtils.sendNotification(context, "Walking started!" + Long.toString(elapsedSeconds));

                    StartActivityTask task = new StartActivityTask();
                    task.execute(activity);


                }else if(event.getActivityType() == DetectedActivity.WALKING &&
                        event.getTransitionType() == ActivityTransition.ACTIVITY_TRANSITION_EXIT){

                    NotificationUtils.sendNotification(context, "Walking finished!");

                    long elapsedSeconds = (SystemClock.elapsedRealtimeNanos() - event.getElapsedRealTimeNanos())/1000000000;

                    Date endTime = new Date();
                    endTime.setTime(endTime.getTime() + elapsedSeconds*1000);

                    FinishActivityTask task = new FinishActivityTask();
                    task.execute(endTime);


                }
            }
        }
    }

    private class StartActivityTask extends AsyncTask<Activity, Void, Void>{

        @Override
        protected Void doInBackground(Activity... activities) {
            activityDao.insert(activities[0]);
            return null;
        }
    }

    private class FinishActivityTask extends AsyncTask<Date, Void, Void>{

        @Override
        protected Void doInBackground(Date... dates) {
            Activity activity = activityDao.getStartedWalkingActivity();
            if(activity == null) return null;

            String userId = Utils.getCurrentUsername(context);

            activity.setFinished(true);
            activity.setEndTime(dates[0]);
            activity.setName("Šetnja (automatski zabilježena)");
            activity.setUserId(userId);

            long diffInMillies = Math.abs(activity.getEndTime().getTime() - activity.getStartTime().getTime());
            long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
            float duration = diff;
            activity.setDuration(duration);

            User user = userDao.findUserByEmail(userId);
            activity.setKcalBurned((2.8f * 3.5f *user.getWeight()/200)*duration);

            activityDao.update(activity);
            return null;
        }
    }

}
