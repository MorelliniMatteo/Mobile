package com.example.MyMangaList;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class FilterDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Filter Options")
                .setMessage("Choose your filter options")
                .setPositiveButton("OK", (dialog, id) -> {
                    // Implementa l'azione per il pulsante "OK"
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    // Chiudi il dialog senza fare nulla
                    dialog.dismiss();
                });
        return builder.create();
    }
}
