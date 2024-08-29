package com.example.MyMangaList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MyMangaList.Database.UserRepository;
import com.example.MyMangaList.Database.UserDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        userRepository = new UserRepository(getApplication());

        usernameEditText = findViewById(R.id.username_field);
        passwordEditText = findViewById(R.id.password_registration);
        loginButton = findViewById(R.id.buttonSignIn);

        TextView signUpTextView = findViewById(R.id.textViewSignUp);

        // Testo completo con "sign-up" cliccabile
        String text = "Don't have an account? sign-up";
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

        // Cambia il colore del testo "sign-up" per farlo sembrare un link
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_light)),
                signUpStart, signUpEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Imposta il testo modificato sul TextView
        signUpTextView.setText(spannableString);
        signUpTextView.setMovementMethod(LinkMovementMethod.getInstance());

        // Aggiungi la logica di login al pulsante
        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Verifica se i campi sono vuoti
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Controlla se l'username esiste nel database e se la password è corretta
        User user = userRepository.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Credenziali corrette, naviga verso la home
            Intent intent = new Intent(LoginActivity.this, HomeFragment.class); // Dovrai creare questa HomeActivity
            startActivity(intent);
            finish(); // Chiude la LoginActivity in modo che non venga riaperta con il tasto back
        } else {
            // Credenziali errate, mostra un messaggio di errore
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
