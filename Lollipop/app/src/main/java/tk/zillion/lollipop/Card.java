package tk.zillion.lollipop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Home on 2017-02-03.
 */
public class Card extends View {
    public Card(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
      //  Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
