package tk.zillion.start;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

/**
 * Created by Hasan A Yousef on 2017-02-10.
 */

public class Splash extends Activity {
    SharedPreferences mPrefs;

    final String settingScreenShownPref = "settingScreenShown";
    final String versionCheckedPref = "versionChecked";

    int appVershionCode = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.splash);

        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;

                mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = mPrefs.edit();

                Boolean settingScreenShown = mPrefs.getBoolean(settingScreenShownPref, false);
                int savedVersionCode = mPrefs.getInt(versionCheckedPref, 1);

                if (!settingScreenShown || savedVersionCode != versionCode) {
                    startActivity(new Intent(Splash.this, FirstRun.class));
                    editor.putBoolean(settingScreenShownPref, true);
                    editor.putInt(versionCheckedPref, versionCode);
                    editor.commit();
                }
                else
                    startActivity(new Intent(Splash.this, MainActivity.class));
                finish();

            }
        };

        h.postDelayed(r, 1500);
    }
}
