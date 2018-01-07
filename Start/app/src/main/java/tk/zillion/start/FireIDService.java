package tk.zillion.start;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by francesco on 13/09/16.
 */
public class FireIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String tkn = FirebaseInstanceId.getInstance().getToken();
        Log.d("Not","Token ["+tkn+"]");

    }
}