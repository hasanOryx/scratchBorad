package oryx.tecna.locateme;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.width;

/**
 * Created by Hasan Yousef on 27/04/2017.
 */

public class Card extends RelativeLayout {
    private String mShowText = "Yap, Hi, and Welcome to my first app";
    private TextView otv;
    private ViewManager vm;

    public Card(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public Card(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }
    public Card(Context context) {
        super(context);
        init(context);
    }
    private void init(Context context) {
        final RelativeLayout cardLayout = new RelativeLayout(this.getContext());

    //    cardLayout.setMinimumWidth(width);
    //    cardLayout.setMinimumHeight(LayoutParams.WRAP_CONTENT);

        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedLayout= inflater.inflate(R.layout.parents, null, false);
        Button closeCard = (Button) inflatedLayout.findViewById(R.id.btn_card);
    //    Button closeCard = (Button) findViewById(R.id.btn_card);
        vm = (ViewManager) closeCard.getParent();
        closeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vm.removeView(vm);

                cardLayout.removeAllViews();
                otv.setText(mShowText);
            }
        });


        // TextView
        otv = (TextView) inflatedLayout.findViewById(R.id.tv);
        String formattedText = "This <i>is</i> a <b>test</b> of <a href='http://foo.com'>html</a>";
// or getString(R.string.htmlFormattedText);
  //      otv.setText(Html.fromHtml(formattedText,Html.FROM_HTML_MODE_LEGACY));
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(getResources().getString(R.string.htmlFormattedText),Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(getResources().getString(R.string.htmlFormattedText));
        }
     //   otv.setText(result);
          otv.setText(mShowText);

        cardLayout.addView(inflatedLayout);
        this.addView(cardLayout);

        RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                //  ViewGroup.LayoutParams.WRAP_CONTENT
                240);
        this.setLayoutParams(p1);
    }

    public void setShowText(String showText) {
        mShowText = showText;
        otv.setText(mShowText);
    }
}
