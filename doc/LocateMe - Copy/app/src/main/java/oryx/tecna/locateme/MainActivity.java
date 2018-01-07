package oryx.tecna.locateme;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;
import static android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION;
import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class MainActivity extends Activity {
    private static final int PERMISSION_ALL = 0;
    private Handler h;
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  Toast.makeText(this, "staring", Toast.LENGTH_SHORT).show();

        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
             //   Toast.makeText(getApplicationContext(),
             //           "LocateMe started", Toast.LENGTH_SHORT).show();
                // (OPTIONAL) these lines to check if the `First run` ativity is required
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                Context context = getApplicationContext(); // getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
       //         SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
       //         SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPref.edit();

                Boolean introductionScreenShown =
                        sharedPref.getBoolean(getString(R.string.introduction_shown),
                                Boolean.FALSE);
                Boolean introductionScreenChanged =
                        sharedPref.getBoolean(getString(R.string.introduction_changed),
                                Boolean.FALSE);
                int savedVersionCode = sharedPref.getInt(getString(R.string.saved_version),
                                                            R.integer.saved_version_default);

                if (!introductionScreenShown || (
                        getResources().getBoolean(R.bool.introduction_changed_default) &&
                        savedVersionCode != versionCode &&
                        savedVersionCode <= R.integer.las_introduction_version)
                        ) {
                    startActivity(new Intent(MainActivity.this, Slider.class));

                    editor.putBoolean(getString(R.string.introduction_shown),
                            Boolean.TRUE);
                    editor.putInt(getString(R.string.saved_version), versionCode);
                    editor.commit();

                    /* Notification about FaceBook */
                    NotificationCompat.Builder mBuilderFB =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle("Join us at FaceBook")
                                    .setContentText("For hints, updates and illustrations")
                                    .setStyle(new NotificationCompat.BigTextStyle()
                                            .bigText("For hints, updates and illustrations"))
                                    .setAutoCancel(true);
                    //    Log.d(TAG, "onCreate: " + "Device ID");

                    Intent intentFB = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/AppLocateMe"));  // fb.me/AppLocateMe
                    //      Intent intent = new Intent(getApplicationContext(), AdActivity.class);
                    PendingIntent pIntentFB =
                            PendingIntent.getActivity(getApplicationContext(), 0, intentFB, 0);

                    mBuilderFB.setContentIntent(pIntentFB);

                    // Sets an ID for the notification
                    int mNotificationIdFB = 1;
                    // Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgrFB =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
                    mNotifyMgrFB.notify(mNotificationIdFB, mBuilderFB.build());


                    /*  One time Ad*/

                    Intent intent = new Intent(getApplicationContext(), AdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent,
                            PendingIntent.FLAG_ONE_SHOT);

                    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder notificationBuilder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("Support us")
                            .setContentText("[Optional] Help us with one Ad.")
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

                }
                else
                    startActivity(new Intent(MainActivity.this, AppSettings.class));
              //  startService(new Intent(getApplicationContext(), HUD.class));
                finish();
            }
        };

//        final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                Uri.parse("package:" +  getPackageName()));
//        startActivityForResult(intent, 10);

        String[] PERMISSIONS = {
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
                READ_SMS,
                READ_CONTACTS,
                ACCESS_NETWORK_STATE
        };

        if(!UtilPermissions.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        else
            h.postDelayed(r, 1500);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        int index = 0;
        Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
        for (String permission : permissions){
            PermissionsMap.put(permission, grantResults[index]);
            index++;
        }

        if((PermissionsMap.get(ACCESS_FINE_LOCATION) != 0)
                || PermissionsMap.get(READ_SMS) != 0){
            Toast.makeText(this, "Location and permissions are a must", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
            h.postDelayed(r,1500);
    }
}