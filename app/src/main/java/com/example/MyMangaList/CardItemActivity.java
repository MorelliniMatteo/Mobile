package com.example.MyMangaList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CardItemActivity extends AppCompatActivity {

    private CardItem cardItem;  // Supponendo che tu abbia già un'istanza di CardItem da eliminare

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_layout);  // Il layout della tua activity

        Button deleteButton = findViewById(R.id.delete_button);  // Supponendo che il pulsante "delete" abbia questo ID

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        // Creare una nuova AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?");

        // Impostare il bottone "Yes"
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Chiamare il metodo per eliminare l'oggetto
                deleteItem();
            }
        });

        // Impostare il bottone "No"
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Chiudere il dialog senza fare nulla
                dialog.dismiss();
            }
        });

        // Creare e mostrare la dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem() {
        // Logica per eliminare l'oggetto dal database
        // Per esempio:
        // myDatabase.cardItemDao().delete(cardItem);

        // Mostrare un messaggio di conferma, se necessario
        // Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();

        // Terminare l'activity o aggiornare la UI
        finish();  // Chiude l'activity corrente e torna alla precedente
    }
}
