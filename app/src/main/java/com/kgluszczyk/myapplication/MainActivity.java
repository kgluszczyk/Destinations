package com.kgluszczyk.myapplication;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.kgluszczyk.myapplication.ItemFragment.OnListFragmentInteractionListener;
import com.kgluszczyk.myapplication.dummy.StaticContent;
import com.kgluszczyk.myapplication.dummy.StaticContent.ZabytekItem;

public class MainActivity extends AppCompatActivity implements OnListFragmentInteractionListener {

    public static final int REQUEST_CODE_MEDIA_PICKER = 1;
    public static final int REQUEST_CODE_CAMERA = 2;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    ItemFragment fragment;
    private ZabytekItem itemSelectedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tytuł).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                fragment = ItemFragment.newInstance(1);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                                android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.lista_kontener, fragment)
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
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (itemSelectedPhoto != null) {
                if (requestCode == REQUEST_CODE_MEDIA_PICKER) {
                    itemSelectedPhoto.setUri(data.getData());
                } else if (requestCode == REQUEST_CODE_CAMERA) {
                    if (data != null && data.getExtras() != null) {
                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                        itemSelectedPhoto.setBitmap(imageBitmap);
                    }
                }
                if (fragment != null && fragment.getAdapter() != null) {
                    fragment.getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    openMediaPicker();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onListFragmentInteraction(final StaticContent.BazowyListItem item) {
        if (item instanceof ZabytekItem) {
            itemSelectedPhoto = (ZabytekItem) item;
            askForPermission();
        } else {
            Toast.makeText(MainActivity.this, "Klinkąłem na element: " + item.getItem(), Toast.LENGTH_SHORT).show();
        }
    }

    private void askForPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asrynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_CAMERA is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            openCamera();
        }
    }

    private void openMediaPicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), REQUEST_CODE_MEDIA_PICKER);
    }

    private void openCamera() {
        startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CODE_CAMERA);
    }
}
