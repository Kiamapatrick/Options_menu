package com.example.options_menu;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordsActivity extends AppCompatActivity {

    public static final String EXTRA_RECORDS = "extra_records";

    private ListView lvRecords;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        lvRecords = findViewById(R.id.lvRecords);
        tvEmpty = findViewById(R.id.tvEmpty);

        // Enable Up navigation (back arrow in the action bar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Read the String array from the Intent
        String[] records = getIntent().getStringArrayExtra(EXTRA_RECORDS);
        if (records != null && records.length > 0) {
            // Fill the ListView with a simple ArrayAdapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    records
            );
            lvRecords.setAdapter(adapter);
            lvRecords.setVisibility(ListView.VISIBLE);
            tvEmpty.setVisibility(TextView.GONE);
        } else {
            // Show empty state
            lvRecords.setVisibility(ListView.GONE);
            tvEmpty.setVisibility(TextView.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle Up arrow (android.R.id.home) by finishing this activity
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}