package com.example.MyMangaList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        TextView signUpTextView = findViewById(R.id.textViewSignUp);

        // Testo completo con "sign-up" cliccabile
        String text = "Se non sei ancora registrato fai sign-up";
        SpannableString spannableString = new SpannableString(text);

        // Trova l'indice della parola "sign-up"
        int signUpStart = text.indexOf("sign-up");
        int signUpEnd = signUpStart + "sign-up".length();

        // Crea ClickableSpan
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Passa alla RegistrationActivity quando viene cliccato
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        };

        // Applica il ClickableSpan solo alla parola "sign-up"
        spannableString.setSpan(clickableSpan, signUpStart, signUpEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Opzionale: cambia il colore del testo "sign-up" per farlo sembrare un link
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_light)),
                signUpStart, signUpEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Imposta il testo modificato sul TextView
        signUpTextView.setText(spannableString);
        signUpTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
