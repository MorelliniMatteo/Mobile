package com.example.MyMangaList;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends Fragment {

    private SwitchCompat themeSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflata il layout per questo fragment
        return inflater.inflate(R.layout.settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recupera le SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("theme_prefs", requireContext().MODE_PRIVATE);
        boolean isDarkTheme = sharedPreferences.getBoolean("dark_theme", false);

        // Imposta il tema
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        themeSwitch = view.findViewById(R.id.dark_theme_switch);

        // Imposta lo stato dello switch
        themeSwitch.setChecked(isDarkTheme);

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("dark_theme", true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("dark_theme", false);
            }
            editor.apply();
        });
    }
}
