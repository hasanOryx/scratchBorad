package tk.zillion.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Home on 2017-02-10.
 */

public class FirstRun extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_run);

        Button closeSelf = (Button) findViewById(R.id.btn_close_firstRun);
        closeSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstRun.this, MainActivity.class));
                finish();
            }
        });
    }
}
