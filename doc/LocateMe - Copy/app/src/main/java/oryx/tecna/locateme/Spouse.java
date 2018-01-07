package oryx.tecna.locateme;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hasan Yousef on 28/04/2017.
 */

public class Spouse extends Fragment {

    private static int PICK_SPOUSE = 0;
    private TextView mName, mNumber;
    Button pickNumber, closeApp;
    CompoundButton rWRU;

    final String AuthorizedNamePref_spouse = "AuthorizedName_spouse";
    final String AuthorizedNumberPref_spouse = "AuthorizedNumber_spouse";

    private SharedPreferences.Editor editor;
    SharedPreferences mPrefs;

    public Spouse() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
      //  Toast.makeText(this.getActivity(), "No email client installed.",
      //          Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.parents, container, false);
        closeApp = (Button) rootView.findViewById(R.id.btn_close);
        pickNumber = (Button) rootView.findViewById(R.id.btn_PICK);
    //    rWRU = (CompoundButton) rootView.findViewById(R.id.wru);
        ((Switch) rootView.findViewById(R.id.wru)).setOnCheckedChangeListener(multiListener);
        mName = (TextView) rootView.findViewById(R.id.tv_name);
        mNumber = (TextView) rootView.findViewById(R.id.tv_number);

        String savedtrustedName_spouse =
                mPrefs.getString(AuthorizedNamePref_spouse, "Not selected");
        String savedtrustedNumber_spouse =
                mPrefs.getString(AuthorizedNumberPref_spouse, "");

        mName.setText(savedtrustedName_spouse);
        mNumber.setText(savedtrustedNumber_spouse);

        closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        pickNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_SPOUSE);
            }
        });
        return rootView;
    }

    CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()){
                case R.id.wru:
                    if(isChecked) {
                        Toast.makeText(getActivity(), "checked:", Toast.LENGTH_SHORT).show();
                        showError();
                    }
                    else
                        Toast.makeText(getActivity(), "Unchecked:", Toast.LENGTH_SHORT).show();
                    // Do something
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri contactUri = data.getData();

            String[] projection = {
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            };
            Cursor cursor = getActivity().getContentResolver() //  getContentResolver()
                    .query(contactUri, projection, null, null, null);
            cursor.moveToFirst();

            int numColumn =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameColumn =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            String name = cursor.getString(nameColumn);
            String s = cursor.getString(numColumn);
            String number = s.replaceAll("[\\s|\\u00A0]+", "");  // remove spaces

            Toast.makeText(getActivity().getBaseContext(),
                    "Number picked: "+s, Toast.LENGTH_SHORT).show();

            if(number.charAt(0) == '+'){
                mName.setText(name);
                mNumber.setText(number);
                editor = mPrefs.edit();
                editor.putString(AuthorizedNumberPref_spouse, number);
                editor.putString(AuthorizedNamePref_spouse, name);
                editor.commit();
            }
            else
                showError();
        }
    }

    private void showError() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle("Invalid number");

        // set dialog message
        alertDialogBuilder
                .setMessage("The selected number should start by '+' with international code, "+
                        "please go to your contacts and fix the number format:\n"+
                        "Example in Mexico, the number should start by: +52")
                .setCancelable(false)
                .setPositiveButton("CLOSE APP",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Pick another number",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener(){
            @Override
            public void onShow(DialogInterface dialog) {

                Button negative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negative.setFocusable(true);
                negative.setFocusableInTouchMode(true);
                negative.requestFocus();
            }
        });

        // show it
        alertDialog.show();
    }

}