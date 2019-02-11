package com.kgluszczyk.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.kgluszczyk.myapplication.ItemFragment.OnListFragmentInteractionListener;
import com.kgluszczyk.myapplication.dummy.StaticContent;

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
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                                android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.lista_kontener, ItemFragment.newInstance(1))
                        .addToBackStack(null)
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
    public void onListFragmentInteraction(final StaticContent.BazowyListItem item) {
        Toast.makeText(MainActivity.this, "Klinkąłem na element: " + item.getItem(), Toast.LENGTH_SHORT).show();
    }
}
