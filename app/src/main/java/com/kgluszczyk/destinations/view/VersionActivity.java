package com.kgluszczyk.destinations.view;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import com.kgluszczyk.destinations.R;
import com.kgluszczyk.destinations.databinding.ActivityVersionBinding;

public class VersionActivity extends AppCompatActivity {

    public final static String DELIMITER_NEW_LINE = "\n";
    public final static String DELIMITER_NEW_LINE_HTML = "<br>";

    /**
     * @param source
     * @return A Spanned text with \n replaced with <br>
     */
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        source = source.replace(DELIMITER_NEW_LINE, DELIMITER_NEW_LINE_HTML);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return Html.fromHtml(source);
        } else {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityVersionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_version);
        Toolbar myToolbar = binding.myToolbar;
        myToolbar.setTitle(getResources().getString(R.string.version_name));
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.aboutApp.setText(fromHtml(getString(R.string.version_dsc)));
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.info) {
            Toast.makeText(VersionActivity.this, "Created by: Krzysztof Głuszczyk", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_info, menu);
        return true;
    }
}
