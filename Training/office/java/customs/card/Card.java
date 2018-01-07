package customs.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.scm.myapplication2.R;

/**
 * Created by SCM on 2017-02-07.
 */

public class Card extends RelativeLayout {
    public Card(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context) {
        // 1. Inflate the custom view
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout= inflater.inflate(R.layout.frament_two, null, false);
        this.addView(inflatedLayout);
    }
}
