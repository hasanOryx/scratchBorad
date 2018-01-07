package tk.zillion.app1.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import tk.zillion.app1.R;

/**
 * Created by Home on 2017-02-03.
 */
public class Card extends RelativeLayout {
    private boolean mShowText = false;
    private String title = "click me guy";
    private int mTextPos = TEXTPOS_LEFT;
    public static final int TEXTPOS_LEFT = 0;
    private Button btn = new Button(this.getContext());
    public Card(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public Card(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Card,
                0, 0);

        try {
            mShowText = a.getBoolean(R.styleable.Card_showText, false);
            mTextPos = a.getInteger(R.styleable.Card_labelPosition, 0);
            title = a.getString(R.styleable.Card_title);
        } finally {
            a.recycle();
        }
        init(context);
    }
    public Card(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context) {
        WindowManager windowmanager = (WindowManager) this.getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        Display display = windowmanager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        int height = size.y;

        // 1. Define the main layout of the CARD class
        RelativeLayout cardLayout = new RelativeLayout(this.getContext());

        // 2. (Optional) set the require specs for this Layout
        cardLayout.setBackground(context.getDrawable(R.drawable.layer_card_background));
        cardLayout.setMinimumWidth(width);

        // 3. Inflate the custom view
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout= inflater.inflate(R.layout.view_customs, null, false);

        // 4. Set ID for the inflated view
        inflatedLayout.setId(View.generateViewId());

        // 5. (Optional) pick and change the elements in the custom view
        TextView otv = (TextView) inflatedLayout.findViewById(R.id.tv);
        otv.setText("Welcome again to my first app");

        // 6. (Optional) create new elements
      //  Button btn = new Button(this.getContext());
        this.btn.setId(View.generateViewId());   //   btn.setId(R.id.titleId);
        this.btn.setText(this.title);
        this.btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int duration = Toast.LENGTH_SHORT;
                CharSequence text = "Hello from another toast!";
                Toast toast = Toast.makeText(v.getContext(), text, duration);
                toast.show();
            }
        });

        TextView one = new TextView(this.getContext());
        one.setText("Device width is: "+String.valueOf(width)+", " +
                 "\n Device height is: "+String.valueOf(height));
        one.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        // 7. Locate the relative locations between the inflated element and the main Layout
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
                       ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.BELOW, inflatedLayout.getId());
        cardLayout.setLayoutParams(p);

        // 8. In the same way as of 7, locate the relative locations of the elements inside the main Layout itself.
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                         LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, this.btn.getId());

        // 9. Add the inflated Layout, and all other elements to the main Layout
        cardLayout.addView(inflatedLayout);
        cardLayout.addView(this.btn);
        cardLayout.addView(one, lp);

        // 10. Add the main Layout to the class
        this.addView(cardLayout);

        // 11. (Optional) In the same way, you can locate the returned CARD
        // in relative location in the view that calling it
        //RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(
        //                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //p1.addRule(RelativeLayout.BELOW, R.id.checkBox);
        //this.setLayoutParams(p1);
    }

    public void setTitle(String title) {
      //  this.title = title;
        this.btn.setText(title);
    }

    public String Title() {
        return this.title;
    }
}
