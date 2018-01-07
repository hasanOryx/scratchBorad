package oryx.tecna.locateme;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsStateReciever extends BroadcastReceiver
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    protected GoogleApiClient mGoogleApiClient;  /* Provides the entry point to Google Play services. */
    protected Location mLastLocation;  /* Represents a geographical location. */
    protected Context context; /* Context fro the reciever. */

    SharedPreferences mPrefs;
    final String AuthorizedNumberPref = "AuthorizedNumber";
    final String AuthorizedNumberPref_dad = "AuthorizedNumber_dad";
    final String AuthorizedNumberPref_mum = "AuthorizedNumber_mum";

    public static final String SMS_BUNDLE = "pdus";
    int subId;
    String address, smsBody, nonAlphaNum;
    Pattern pattern;
    Matcher matcher;
    String regEx =
            "[\\w\\s]*"+    // or "\\s*[0-9a-zA-Z]*" + if only one word allowed
            "(" +
                    "((?i)\\s*(where|were|w)\\s*(are|r)\\s*(you|u)(?-i))" +
                    "|"+"(Dónde estás)"+
                    "|"+
                    "(وينك)|(وينكي)|(وينكم)|(فينك)|(فينكي)|(فينكم)|(أين أنت)|(اين انت)|(أين أنتم)|(اين انتم)"+
                    "|"+"(Ku jeni ju)|(Non zaude)|(Дзе вы знаходзіцеся)|(Gdje si ti)|(Къде си)|(On ets)"+
                    "|"+"(Gdje si)|(Kde jsi)|(Hvor er du)|(Waar ben je)|(Kus sa oled)|(Missä sinä olet)"+
                    "|"+"(Où es tu)|(Onde estás)|(Wo bist du)|(Που είσαι)|(Merre jársz)|(Hvar ertu)"+
                    "|"+"(Cá bhfuil tú)|(Dove sei)|(Kur tu esi)|(Kur tu esi)|(Каде си)|(Fejn int)"+
                    "|"+"(Hvor er du)|(Gdzie jesteś)|(Onde está você)|(Unde esti)|(Где ты)|(Где си)"+
                    "|"+"(Kde si)|(Kje si)|(Var är du)|(Де ти)|(Ble ydych chi)|(ווו זענען איר)"+
                    "|"+"(Որտեղ եք դուք)|(Haradasan)|(你在哪里)|(你在哪裡)|(სად ხარ)|(Ubi es)"+
                    "|"+"(Qhov twg yog koj)|(どこにいますか)|(Сен қайдасың)|(Та хаана байна вэ)|(Ту дар куҷо)"+
                    "|"+"(คุณอยู่ที่ไหน)|(اپ کہاں ہیں)|(Qayerdasiz)|(Bạn đang ở đâu)|(איפה אתה)|(کجایی)|(Neredesin)"+
                    "|"+"(Waar is jy)|(Muli kuti)|(Ina ku ke)|(Ebee ka ị nọ)|(U hokae)|(Xaggee baad joogtaa)"+
                    "|"+"(Uko wapi)|(Ibo lo wa)|(Ukuphi)|(Hain ka)|(Nasaan ka)|(Kamu di mana)"+
                    "|"+"(Aiza ianao)|(Di manakah anda)|(Kei hea koe)|(Kie vi estas)|(Ki kote w ye)"+
            ")\\s*" +
            "[\\w\\s]*";   // i for case non
    // http://www.freeformatter.com/java-regex-tester.html
    // http://www.indifferentlanguages.com/words/where_are_you%3F

    // \s* zero-or-more, \s? zero-or-one, \s+ one or more, \s{a,b} a or b times
    // [0-9a-zA-Z] letters or numbers, [^0-9a-zA-Z] anything except letters or numbers
   // String search = "Where are you";
   // Location location = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    //    String savedAllowedNumber = mPrefs.getString(AuthorizedNumberPref, "");
        String savedDadNumber = mPrefs.getString(AuthorizedNumberPref_dad, "");
        String savedMumNumber = mPrefs.getString(AuthorizedNumberPref_mum, "");

        this.context = context;
//        Toast.makeText(this.context, "Reciever started", Toast.LENGTH_SHORT).show();

        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            for (int i = 0; i < sms.length; ++i) {
                String format = intentExtras.getString("format");
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
                //    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);

                smsBody = smsMessage.getMessageBody().toString();
                address = smsMessage.getOriginatingAddress();

                int slot = intentExtras.getInt("slot", -1);
                subId = intentExtras.getInt("subscription", -1);

//                Toast.makeText(this.context, "MSG recieved from: "+address, Toast.LENGTH_SHORT).show();
            }

            nonAlphaNum = Pattern.compile("[\\p{P}\\p{S}]").matcher(smsBody).replaceAll("");  // [^0-9a-zA-Z\s]
            // [\p{P}\p{S}]: punctuations and special characters
//            Toast.makeText(this.context, nonAlphaNum, Toast.LENGTH_SHORT).show();
            pattern = Pattern.compile(regEx);
            matcher = pattern.matcher(nonAlphaNum);

//    Toast.makeText(this.context, String.format("Groups found: %b",
//                    matcher.matches()), Toast.LENGTH_SHORT).show();

    /*    Toast.makeText(this.context, String.format("Groups found: %d , %b, %s",
                   matcher.groupCount(), matcher.matches(), matcher.group(1).toString())
                    , Toast.LENGTH_SHORT).show();
    */    if (
                    (address.equals(savedDadNumber) || address.equals(savedMumNumber))
                    && matcher.matches()) //smsBody.toLowerCase().contains(search.toLowerCase()))
            {
                buildGoogleApiClient();
                mGoogleApiClient.connect();
            }

        }
    }

    protected synchronized void buildGoogleApiClient() {
//        Toast.makeText(this.context, "building Client", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
//        Toast.makeText(this.context, "Client built", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this.context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this.context,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this.context, "Something not fine", Toast.LENGTH_SHORT).show();
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null) {
//                Toast.makeText(this.context, "Location not found", Toast.LENGTH_LONG).show();
                UtilSMS.sendSMS(this.context, address, "Sorry, it looks GPS is off, no location found", subId);
            }
        else{
//                Toast.makeText(this.context, "Location found", Toast.LENGTH_SHORT).show();
                Date date = new Date(mLastLocation.getTime());
                //  SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yy");
          //  NumberFormat nf = NumberFormat.getInstance(Locale.US);
                String msg = "As of: "+date+", I am at: http://maps.google.com/?q=@" +
                Location.convert(mLastLocation.getLatitude(), Location.FORMAT_DEGREES).replace(',','.')
                + "," +
                Location.convert(mLastLocation.getLongitude(), Location.FORMAT_DEGREES).replace(',','.')
                + " my speed is: " + mLastLocation.getSpeed();

//                UtilSMS.sendSMS(this.context, address, msg, subId);

                ConnectivityManager connManager = (ConnectivityManager)
                                            this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
                //     NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                NetworkInfo net = connManager.getActiveNetworkInfo(); //ConnectivityManager.TYPE_WIFI).getState();
                //    NetworkInfo.State mData = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                //    NetworkInfo ni = conMan.getActiveNetworkInfo(); // .getNetworkInfo();  //   .isAvailable()
            if (net == null) {
//                Toast.makeText(this.context, "There is no active connection", Toast.LENGTH_LONG).show();
//                UtilSMS.sendSMS(this.context, address, "Sorry, There is no active connection to get the GeoLocation", subId);
                UtilSMS.sendSMS(this.context, address, msg, subId);
            }
            else{
                if (net.isConnected()) {
                    Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());
                    List<Address> _address = null;
                    try {
                        _address = geocoder.getFromLocation(mLastLocation.getLatitude(),
                                mLastLocation.getLongitude(), 1);
                        if (_address != null & _address.size() > 0) {
                            Address obj = _address.get(0);
                            String add = new String(); // = "Street Number, Area: "+obj.getAddressLine(0);

                            add += "\n Street : " + obj.getThoroughfare();
                            add += "\n Area: " + obj.getSubLocality();
                            add += "\n City: " + obj.getLocality();
                            // add += "\n Country: " + obj.getCountryName();
 //                           Toast.makeText(this.context, add, Toast.LENGTH_SHORT).show();
//                            UtilSMS.sendSMS(this.context, address, add, subId);

                            msg = "As of: "+date+", I am in "+obj.getLocality()+", at: http://maps.google.com/?q=@" +
                                    Location.convert(mLastLocation.getLatitude(), Location.FORMAT_DEGREES).replace(',','.')
                                    + "," +
                                    Location.convert(mLastLocation.getLongitude(), Location.FORMAT_DEGREES).replace(',','.')
                                    + " my speed is: " + mLastLocation.getSpeed();

                            UtilSMS.sendSMS(this.context, address, msg, subId);
                        }
                        else{
 //                           Toast.makeText(this.context, "Sorry, could not find the GeoLocation name", Toast.LENGTH_SHORT).show();
//                            UtilSMS.sendSMS(this.context, address, "Sorry, could not find the GeoLocation name", subId);
                            UtilSMS.sendSMS(this.context, address, msg, subId);
                        }
                    } catch (IOException e) {
//                        Toast.makeText(this.context, "No internet found to get the GeoLocation", Toast.LENGTH_SHORT).show();
//                        UtilSMS.sendSMS(this.context, address, "Sorry, No internet found to get the GeoLocation", subId);
                        UtilSMS.sendSMS(this.context, address, msg, subId);
                        //    e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this.context, "Connection failed", Toast.LENGTH_LONG).show();
    }
}
