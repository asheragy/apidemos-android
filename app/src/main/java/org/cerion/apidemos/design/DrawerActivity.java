package org.cerion.apidemos.design;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.cerion.apidemos.R;


public class DrawerActivity extends AppCompatActivity {

    //private static final String TAG = DrawerActivity.class.getSimpleName();
    private ActionBarDrawerToggle mDrawerToggle;

    private static final String TITLE = "Navigation Drawer Activity";
    private static final String DRAWER_TITLE = "Navigation Drawer Menu";
    private static final String ITEM_POSITION = "itemPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);
        setTitle(TITLE);

        String items[] = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_item, items));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer, R.string.close_drawer) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(TITLE);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(DRAWER_TITLE);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);


        //This activity is launching instances of itself to simulate navigation changes
        int position = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            position = extras.getInt(ITEM_POSITION);
        ((TextView)findViewById(R.id.text)).setText("Item " + (position + 1));
        mDrawerList.setItemChecked(position, true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState(); //Change actionBar icon to nav drawer
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig); //Documentation says to do this
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(DrawerActivity.this, "selected item" + (position+1), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DrawerActivity.this, DrawerActivity.class);
            intent.putExtra(ITEM_POSITION, position);
            startActivity(intent);
            finish();
            //mDrawerLayout.closeDrawer(mDrawerList);
        }
    }




}
