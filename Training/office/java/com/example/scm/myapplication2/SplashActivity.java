package com.example.scm.myapplication2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

   // private static final int UI_ANIMATION_DELAY = 300;
   // final Handler handler = new Handler();

  //  SharedPreferences sharedPreferences = getSharedPreferences("version", 0);
    SharedPreferences mPrefs;
    SharedPreferences mVersion; // = getSharedPreferences("version", 0);

    final String welcomeScreenShownPref = "welcomeScreenShown";
    final String welcomeVersion = "version";

    int appVershionCode = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                // if you are redirecting from a fragment then use getActivity() as the context.
              //  startActivity(new Intent(SplashActivity.this, MainActivity.class));
                mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                mVersion = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                // second argument is the default to use if the preference can't be found

                Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);
                int savedVersionCode = mVersion.getInt(welcomeVersion, 2); //getSharedPreferences("version", 0);

                startActivity(new Intent(SplashActivity.this, tabActivity.class));

                if (!welcomeScreenShown || savedVersionCode < 1) {
                    // here you can launch another activity if you like
                    // the code below will display a popup

          /*  String whatsNewTitle = "whatsNewTitle";
            String whatsNewText = "whatsNewText is"; //getResources().getString(R.string.whatsNewText);
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(whatsNewTitle).setMessage(whatsNewText).setPositiveButton(
                    R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            */
                    startActivity(new Intent(SplashActivity.this, tabActivity.class));
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putBoolean(welcomeScreenShownPref, true);
                    editor.commit(); // Very important to save the preference

                    SharedPreferences.Editor mVersionEditor = mVersion.edit();
                    mVersionEditor.putInt("VersionCode", appVershionCode);
                    mVersionEditor.commit();
                }





                finish();

            }
        };

        Handler h = new Handler();
        // The Runnable will be executed after the given delay time
        h.postDelayed(r, 1500); // will be delayed for 1.5 seconds
    }
}
