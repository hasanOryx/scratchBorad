package oryx.tecna.locateme;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsManager;
import android.widget.Toast;

public class UtilSMS {
    public static void sendSMS(Context context, String phoneNo, String msg, int subId) {

        SmsManager smsManager = SmsManager.getDefault();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            smsManager = SmsManager.getSmsManagerForSubscriptionId(subId);
        }


        PendingIntent sentPI;
        String SENT = "SMS_SENT";

        sentPI = PendingIntent.getBroadcast(context, 0,new Intent(SENT), 0);

        try {


        //  smsManager.sendTextMessage(phoneNo, null, msg, null, null);
        // OR

            smsManager.sendTextMessage(phoneNo, null, msg, sentPI, null);
//            Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context,ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
