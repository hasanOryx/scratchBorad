package custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.RelativeLayout;
import tk.zillion.start.R;

/**
 * Created by Hasan A Yousef on 2017-02-09.
 */

public class Card extends RelativeLayout {
    public Card(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context) {
        // 1. Inflate the custom view
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout= inflater.inflate(R.layout.content_fragment_two, null, false);
        this.addView(inflatedLayout);
    }
}
