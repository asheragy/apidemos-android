package org.cerion.apidemos.design;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.cerion.apidemos.R;

public class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ToolbarAppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //Replacing title with nav spinner
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //show back button
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        //Add navigation droplist
        Spinner spinner = (Spinner)toolbar.findViewById(R.id.spinner);
        final String[] items = new String[] { "Item 1", "Item 2", "Item 3" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.toolbar_spinner_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ToolbarActivity.this, items[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else
            Toast.makeText(ToolbarActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}
