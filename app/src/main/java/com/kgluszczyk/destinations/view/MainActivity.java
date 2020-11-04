package com.kgluszczyk.destinations.view;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import com.kgluszczyk.destinations.IntentServiceTigerBroadcastReceiver;
import com.kgluszczyk.destinations.R;
import com.kgluszczyk.destinations.databinding.ActivityMainBinding;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.BaseListItem;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.Country;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.DestinationListItem;
import com.kgluszczyk.destinations.view.DestinationsFragment.OnListFragmentInteractionListener;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements OnListFragmentInteractionListener {

    public static final int REQUEST_CODE_MEDIA_PICKER = 1;
    public static final int REQUEST_CODE_CAMERA = 2;

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final String CHANNEL_ID = "TravelChannel";
    public static final int NOTIFICATION_ID = 0;
    DestinationsFragment fragment;
    private Country itemSelectedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fragment = DestinationsFragment.newInstance(1);
        createNotificationChannel();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.list_container, fragment)
                    .commit();

        }
        binding.version.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VersionActivity.class);
            startActivity(intent);
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
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    openMediaPicker();
                }
                return;
            }
        }
    }

    @Override
    public void onListFragmentInteraction(final BaseListItem item) {
        if (item instanceof Country) {
            itemSelectedPhoto = (Country) item;
            askForPermission();
        }
    }

    @Override
    public void onLongClickListener(final BaseListItem item) {
        if (item instanceof DestinationListItem) {
            DestinationListItem destinationListItem = (DestinationListItem) item;

            Intent snoozeIntent = new Intent(this, IntentServiceTigerBroadcastReceiver.class);
            snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, NOTIFICATION_ID);

            PendingIntent snoozePendingIntent =
                    PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, ShowMovieActivity.class), 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_place_black_24dp)
                    .setContentTitle(destinationListItem.content)
                    .setContentText(destinationListItem.details)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .addAction(R.drawable.ic_place_black_24dp, "Trigger broadcast",
                            snoozePendingIntent);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Destinations notifications";
            String description = "For travel lovers";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        } else {
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
