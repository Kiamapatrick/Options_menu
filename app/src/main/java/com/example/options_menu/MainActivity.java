package com.example.options_menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etRecordName;
    private TextView tvStatus;
    private List<String> records = new ArrayList<>();

    private static final String KEY_RECORDS = "records_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Calculate action bar height
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(androidx.appcompat.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        final int abHeight = actionBarHeight;
        final int dp16 = (int) (16 * getResources().getDisplayMetrics().density);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left + dp16, bars.top + abHeight + dp16, bars.right + dp16, bars.bottom + dp16);
            return insets;
        });

        etRecordName = findViewById(R.id.etRecordName);
        tvStatus = findViewById(R.id.tvStatus);

        // Rotation: restore the list from savedInstanceState if available
        if (savedInstanceState != null) {
            ArrayList<String> saved = savedInstanceState.getStringArrayList(KEY_RECORDS);
            if (saved != null) {
                records = new ArrayList<>(saved);
            }
        }
        updateStatus();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Rotation: save the list so it survives configuration changes
        outState.putStringArrayList(KEY_RECORDS, new ArrayList<>(records));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML resource
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Debug logging for menu selection
        Log.d("OptionsMenu", "Selected item id=" + item.getItemId() + " title=" + item.getTitle());
        // Handle menu item clicks by stable ID (never by title)
        int id = item.getItemId();
        if (id == R.id.action_add_record) {
            String name = etRecordName.getText().toString().trim();
            Log.d("OptionsMenu", "Add record name='" + name + "' size before=" + records.size());
            if (name.isEmpty()) {
                // Show error on the EditText (stays visible, not a Toast)
                etRecordName.setError(getString(R.string.error_enter_record_name));
            } else {
                records.add(name);
                etRecordName.setText("");
                updateStatus();
                Toast.makeText(this, R.string.toast_record_added, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.action_view_records) {
            // Start RecordsActivity and pass the list as a String array extra
            Intent intent = new Intent(this, RecordsActivity.class);
            intent.putExtra(RecordsActivity.EXTRA_RECORDS, records.toArray(new String[0]));
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(this, R.string.toast_about, Toast.LENGTH_SHORT).show();
            return true;
        }
        // Unknown items: let the system handle them
        return super.onOptionsItemSelected(item);
    }

    private void updateStatus() {
        tvStatus.setText(getString(R.string.tv_status_format, records.size()));
    }
}