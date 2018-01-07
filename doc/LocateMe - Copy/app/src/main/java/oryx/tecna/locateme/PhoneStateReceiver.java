package oryx.tecna.locateme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Hasan Yousef on 12/05/2017.
 */

public class PhoneStateReceiver extends BroadcastReceiver {
    SharedPreferences mPrefs;
    View mView;
    WindowManager mWindowManager;
    final String AuthorizedNumberPref = "AuthorizedNumber";
 //   final String AllowedNumberPref = "AllowedNumber";  // trusted number
 //   public final static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 11;

    @Override
    public void onReceive(Context context, Intent intent) {
        //  mPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        //  String savedAllowedNumber = mPrefs.getString(AllowedNumberPref, "");

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Toast.makeText(context, "Ringing State Number is -" + incomingNumber, Toast.LENGTH_SHORT).show();

            Toast.makeText(context,"Ready to be LOUD", Toast.LENGTH_SHORT).show();

            audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Toast.makeText(context,"I'm NORMAL now", Toast.LENGTH_SHORT).show();
            audio.setStreamVolume(AudioManager.STREAM_RING,
                    audio.getStreamMaxVolume(AudioManager.STREAM_RING),
                    audio.FLAG_ALLOW_RINGER_MODES| AudioManager.FLAG_PLAY_SOUND);

            Uri ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

            Ringtone r = RingtoneManager.getRingtone(context, ring);
            r.play();

        }
        if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {

            audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
        }
    }
}