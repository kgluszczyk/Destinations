package com.kgluszczyk.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.kgluszczyk.myapplication.ItemFragment.OnListFragmentInteractionListener;
import com.kgluszczyk.myapplication.dummy.DummyContent.DummyItem;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements OnListFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tytuł).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, ItemFragment.newInstance(1))
                        .commit();
            }
        });

        findViewById(R.id.wersja).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(MainActivity.this, WersjaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(final DummyItem item) {
        Toast.makeText(MainActivity.this, "Klinkąłem na element: " + item.id, Toast.LENGTH_SHORT).show();
    }
}
