package z90201.bgtask;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BGService extends Service {
    final String LOG_TAG = "myLogs";

    final int time = 1000;
    ExecutorService es;
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(1);
    }

    public void onDestroy() {
        Log.d(LOG_TAG, "MyService onDestroy");
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");
        PendingIntent pi = intent.getParcelableExtra(MainActivity.PARAM_PINTENT);

        MyRun mr = new MyRun(time, startId, pi);
        es.execute(mr);

        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyRun implements Runnable {

        int time;
        int startId;
        PendingIntent pi;
        int percentage;

        public MyRun(int time, int startId, PendingIntent pi) {
            this.time = time;
            this.startId = startId;
            this.pi = pi;
        }

        public void run() {
            try {
                Intent intent = new Intent().putExtra(MainActivity.PARAM_RESULT, percentage);
                while (percentage < 100) {
                    percentage += 2;
                    intent.putExtra(MainActivity.PARAM_RESULT, percentage);
                    pi.send(BGService.this, MainActivity.STATUS_RUNNING, intent);
                    Thread.sleep(1000);
                }
                percentage = 0;
                intent.putExtra(MainActivity.PARAM_RESULT, percentage);
                pi.send(BGService.this, MainActivity.STATUS_FINISH, intent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
