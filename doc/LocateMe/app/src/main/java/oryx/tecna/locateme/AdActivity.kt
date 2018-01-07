package oryx.tecna.locateme

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewManager
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

/**
 * Created by Hasan Yousef on 22/04/2017.
 */

class AdActivity : AppCompatActivity() {

    private var mAdView: AdView? = null
    private var progressBar: ProgressBar? = null
    private var imageButton: ImageButton? = null
    private var textView: TextView? = null
    private var vm: ViewManager? = null
    private val TAG: String? = null

    protected fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad)

        val adRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //  .addTestDevice("911488800187432")
                .addTestDevice("6612F2AEECD2A4ADBD699CFC349AB01A")  // Obi
                //    .addTestDevice("054AD1123545E3EE9B170FE2A309CB49")   // Moto X
                //    .addTestDevice("024EEEF6F11D445D5C9A152916619640")  // Moto G
                .build()

        /*    WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 100;
        params.width = 550;
        params.y = -10;

        this.getWindow().setAttributes(params);
*/
        mAdView = findViewById(R.id.adView) as AdView
        progressBar = findViewById(R.id.progressBar) as ProgressBar
        imageButton = findViewById(R.id.closeAdView) as ImageButton
        textView = findViewById(R.id.LoadingAd) as TextView
        vm = progressBar!!.parent as ViewManager
        imageButton!!.setOnClickListener { finish() }

        mAdView!!.setAdListener(object : AdListener() {
            // Called when an ad is loaded.
            fun onAdLoaded() {
                Log.e(TAG, "Google onAdLoaded")
                vm!!.removeView(progressBar)
                vm!!.removeView(imageButton)
                vm!!.removeView(textView)
                // or (ViewGroup) instead of (ViewManager)
                //      RelativeLayout lp= (RelativeLayout) findViewById(R.id.formLayout); lp.removeAllViews();
                object : CountDownTimer(6000, 1000) {   // CountDownTimer(long millisInFuture, long countDownInterval)

                    override fun onTick(millisUntilFinished: Long) {}

                    override fun onFinish() {

                        finish()
                    }
                }.start()

            }

            // Called when an ad failed to load.
            fun onAdFailedToLoad(error: Int) {
                val message = "Google onAdFailedToLoad: " + getErrorReason(error)
                Log.e(TAG, message)
                finish()
            }

            // Called when an Activity is created in front of the app
            // (e.g. an interstitial is shown, or an ad is clicked and launches a new Activity).
            fun onAdOpened() {
                Log.e(TAG, "Google onAdOpened")
            }

            // Called when an ad is clicked and about to return to the application.
            fun onAdClosed() {
                Log.e(TAG, "Google onAdClosed")
                finish()
            }

            // Called when an ad is clicked and going to start a new Activity that will leave the application
            // (e.g. breaking out to the Browser or Maps application).
            fun onAdLeftApplication() {
                Log.d(TAG, "Google onAdLeftApplication")
                finish()
            }
        })



        mAdView!!.loadAd(adRequest)

    }

    private fun getErrorReason(errorCode: Int): String {
        // Gets a string error reason from an error code.
        var errorReason = ""
        when (errorCode) {
            AdRequest.ERROR_CODE_INTERNAL_ERROR -> errorReason = "Internal error"
            AdRequest.ERROR_CODE_INVALID_REQUEST -> errorReason = "Invalid request"
            AdRequest.ERROR_CODE_NETWORK_ERROR -> errorReason = "Network Error"
            AdRequest.ERROR_CODE_NO_FILL -> errorReason = "No fill"
        }
        return errorReason
    }
}
