package oryx.tecna.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Slider extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button leftBtn, rightBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up);

        leftBtn = (Button) findViewById(R.id.left_btn);
        rightBtn= (Button) findViewById(R.id.right_btn);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // Add listners
        viewPager.addOnPageChangeListener(listener);
        leftBtn.setOnClickListener(mGlobal_OnClickListener);
        rightBtn.setOnClickListener(mGlobal_OnClickListener);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Tab1(),"");
        adapter.addFrag(new Tab2(),"");
        adapter.addFrag(new Tab3(),"");
        adapter.addFrag(new Tab4(),"");
        adapter.addFrag(new Tab5(),"");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
            int lastIdx = getCount() - 1;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void next_fragment(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }

    public void previous_fragment(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }

    public void start_app(View view) {
        startActivity(new Intent(Slider.this, AppSettings.class));
        finish();
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    leftBtn.setText("");
                    leftBtn.setEnabled(false);
                    rightBtn.setText("NEXT");
                    break;
                case 4:
                    leftBtn.setText("BACK");
                    rightBtn.setText("START");
                    break;
                default:
                    leftBtn.setText("BACK");
                    leftBtn.setEnabled(true);
                    rightBtn.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //Global On click listener for all views
    private View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch(v.getId()) {
                case R.id.left_btn:
                    if(viewPager.getCurrentItem() == 0) {
                    }else
                        previous_fragment(viewPager);
                    break;
                case R.id.right_btn:
                    if(viewPager.getCurrentItem() == 4) {

                        

                        startActivity(new Intent(Slider.this, AppSettings.class));
                        finish();
                    }else
                        next_fragment(viewPager);
                    break;
            }
        }
    };
}