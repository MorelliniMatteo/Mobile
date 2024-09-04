package com.example.MyMangaList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.MyMangaList.ViewModel.AddViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.MyMangaList.Utilities.REQUEST_IMAGE_CAPTURE;
import static com.example.MyMangaList.Utilities.REQUEST_IMAGE_TAKEN;

public class MainActivity extends AppCompatActivity {
    private AddViewModel addViewModel;
    ArrayList barrArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Recupera le SharedPreferences per il tema
        SharedPreferences sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE);
        boolean isDarkTheme = sharedPreferences.getBoolean("dark_theme", false);

        // Imposta il tema prima di creare l'activity
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura il BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.menu_profile:
                        Log.d("MainActivity", "ProfileFragment selected");
                        selectedFragment = new ProfileFragment();
                        break;
                    case R.id.menu_add_manga:
                        Log.d("MainActivity", "AddFragment selected");
                        selectedFragment = new AddFragment();
                        break;
                    case R.id.menu_settings:
                        Log.d("MainActivity", "SettingsFragment selected");
                        selectedFragment = new SettingsFragment();
                        break;
                }

                if (selectedFragment != null) {
                    Log.d("MainActivity", "Loading fragment: " + selectedFragment.getClass().getSimpleName());
                    loadFragment(selectedFragment);
                    return true;
                }

                Log.d("MainActivity", "No fragment selected");
                return false;
            }
        });


        // Carica il fragment di default (ad esempio, HomeFragment)
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.menu_profile);
        }

        addViewModel = new ViewModelProvider(this).get(AddViewModel.class);

        // Imposta l'AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Crea l'intento di notifica
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(
                this,
                100,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Imposta il calendario per l'allarme
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);

        // Imposta un allarme esatto
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }

    private void loadFragment(Fragment fragment) {
        Log.d("MainActivity", "Loading fragment: " + fragment.getClass().getSimpleName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                loadFragment(new ProfileFragment());
                return true;
            case R.id.menu_add_manga:
                loadFragment(new AddFragment());
                return true;
            case R.id.menu_settings:
                loadFragment(new SettingsFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            addViewModel.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_IMAGE_TAKEN && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            Bitmap selectedImageBitmap = null;
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(),
                        selectedImageUri
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            addViewModel.setImageBitmap(selectedImageBitmap);
        }
    }
}
