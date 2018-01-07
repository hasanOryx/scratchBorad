package tk.zillion.app1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.duration;

public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rl;
        rl = (RelativeLayout)findViewById(R.id.activity_main);
        Button btn = new Button(this);
        btn.setText(R.string.button_2);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                /*
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text = "Hello toast!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                */
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.custom_toast_container));

                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("This is a custom toast");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
        rl.addView(btn);

     //   final PieChart pie = (PieChart) this.findViewById(R.id.Pie);
    }

    /** Called when the user clicks the Send button */
    public void showActivity(View view) {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
