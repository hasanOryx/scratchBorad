package oryx.tecna.locateme;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class HUD extends Service {

    private WindowManager windowManager;
    private ImageView close;
    private RelativeLayout chatheadView;
    private FrameLayout content;

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
/*
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this) // getApplicationContext()
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Support us by clicking an Ad")
                        .setContentText("[Optional] Help us with one ad.")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("[Optional] Help us with one ad."))
                        .setAutoCancel(true);
        Intent adIntent = new Intent(this, AdActivity.class);   // getApplicationContext()
        adIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent =
                PendingIntent.getActivity(this, 0, adIntent, 0);   // getApplicationContext()
        mBuilder.setContentIntent(pIntent);
        int mNotificationId = 002;

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
*/
        return START_STICKY;
    }
        /*
    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);



        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        chatheadView = (RelativeLayout) inflater.inflate(R.layout.activity_alert_dialog, null);
        close=(ImageView)chatheadView.findViewById(R.id.close);
        content=(FrameLayout)chatheadView.findViewById(R.id.content);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                windowManager.removeView(chatheadView);
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://saravananandroid.blogspot.in/"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                stopService(new Intent(getApplicationContext(), HUD.class));
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  windowManager.removeView(chatheadView);
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://saravananandroid.blogspot.in/"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                stopService(new Intent(getApplicationContext(), HUD.class));
                // startService(new Intent(getApplicationContext(), HUD.class));
            }
        });

        windowManager.addView(chatheadView, params);
    }
*/
    @Override
    public void onDestroy() {
        super.onDestroy();
       /* if (chatheadView != null) windowManager.removeView(chatheadView);*/
    }
}

