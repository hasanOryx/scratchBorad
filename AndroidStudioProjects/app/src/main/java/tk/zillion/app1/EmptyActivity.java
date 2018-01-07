package tk.zillion.app1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import tk.zillion.app1.CustomViews.Card;

public class EmptyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_empty);
     //   RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_empty);
        setContentView(R.layout.activity_empty_linear);
        LinearLayout rl = (LinearLayout) findViewById(R.id.activity_empty_linear);
        Card crd1 = new Card(this);
        crd1.setId(View.generateViewId());
        crd1.setTitle("Karam");
   //     RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(
   //                     ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
   //     p1.addRule(RelativeLayout.BELOW, R.id.checkBox);
   //     crd1.setLayoutParams(p1);
        Card crd2 = new Card(this);
       // crd2.isShowText(true);
        rl.addView(crd1);
   //     RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams(
   //             ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
   //     p2.addRule(RelativeLayout.BELOW, crd1.getId());
   //     crd2.setLayoutParams(p2);
        rl.addView(crd2);
    }
}
