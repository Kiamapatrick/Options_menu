package com.example.options_menu;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecordsActivity extends AppCompatActivity {

    public static final String EXTRA_RECORDS = "extra_records";

    private ListView lvRecords;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        // Calculate action bar height
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(androidx.appcompat.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        final int abHeight = actionBarHeight;
        final int dp16 = (int) (16 * getResources().getDisplayMetrics().density);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.records_root), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + dp16, bars.top + abHeight + dp16, bars.right + dp16, bars.bottom + dp16);
            return insets;
        });

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