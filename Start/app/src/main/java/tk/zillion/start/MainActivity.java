package tk.zillion.start;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Hasan A Yousef on 2017-02-09.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private boolean isFABOpen;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private LinearLayout fabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
      //  requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fabLayout = (LinearLayout) findViewById(R.id.fabLayout);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tkn = FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(MainActivity.this, "Current token ["+tkn+"]", Toast.LENGTH_LONG).show();
                Log.d("App", "Token ["+tkn+"]");

                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        if(isFABOpen){
            closeFABMenu();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        // Configure the search info and add any event listeners...
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        closeFabIfOpened();
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        closeFabIfOpened();
        int id = item.getItemId();
        RelativeLayout Layout = (RelativeLayout) findViewById(R.id.main_content);

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFABMenu(){
        isFABOpen=true;
        fab.animate().rotation(45);

        fabLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_205));
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));

        fabLayout.setVisibility(View.VISIBLE);
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab.animate().rotation(0);
        fabLayout.setVisibility(View.GONE);
        fabLayout.animate().translationY(0);
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
    }

    private void closeFabIfOpened() {
        if (isFABOpen) {
            closeFABMenu();
        }
    }
}
