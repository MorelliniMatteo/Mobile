package com.example.MyMangaList;

import static com.example.MyMangaList.Utilities.REQUEST_IMAGE_CAPTURE;
import static com.example.MyMangaList.Utilities.REQUEST_IMAGE_TAKEN;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.MyMangaList.ViewModel.AddViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AddViewModel addViewModel;
    ArrayList barrArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            Utilities.insertFragment(this, new CategoryFragment(), CategoryFragment.class.getSimpleName());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.app_bar_finances) {
            Intent intent = new Intent(this, SettingActivity.class);
            this.startActivity(intent);
            return true;
        }
        return false;
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
