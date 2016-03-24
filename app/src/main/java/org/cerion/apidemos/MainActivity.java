package org.cerion.apidemos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.cerion.apidemos.design.DrawerActivity;
import org.cerion.apidemos.design.ToolbarActivity;
import org.cerion.apidemos.service.CustomIntentServiceActivity;
import org.cerion.apidemos.service.MessageServiceActivity;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "received", Toast.LENGTH_SHORT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new
                        //Intent(MainActivity.this,RecyclerViewActivity.class);
                //Intent intent = new Intent(MainActivity.this,FragmentActivity.class);
                        //Intent(MainActivity.this,ImageGridActivity.class);
                //Intent intent = new Intent(MainActivity.this,ServiceActivity.class);
                //Intent intent = new Intent(MainActivity.this,DrawerActivity.class);
                //Intent intent = new Intent(MainActivity.this,ToolbarActivity.class);
                        //Intent(MainActivity.this, CustomIntentServiceActivity.class);
                        Intent(MainActivity.this, MessageServiceActivity.class);

                startActivity(intent);
                //finish();

                //IntentFilter filter = new IntentFilter();
                //filter.addAction(SimpleIntentService.ACTION_BROADCAST);
                //registerReceiver(receiver, filter);


                //Intent intent = new Intent(MainActivity.this,SimpleIntentService.class);
                //startService(intent);
            }
        });


        fab.callOnClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {



            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
