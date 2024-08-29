package com.example.MyMangaList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MyMangaList.Database.UserRepository;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;
    private TextView signInTextView;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        userRepository = new UserRepository(getApplication());

        usernameEditText = findViewById(R.id.username_field);
        passwordEditText = findViewById(R.id.password_registration);
        confirmPasswordEditText = findViewById(R.id.confirm_pass);
        registerButton = findViewById(R.id.buttonSignUp);
        signInTextView = findViewById(R.id.textViewSignIn); // Associa la variabile al TextView

        registerButton.setOnClickListener(v -> registerUser());

        // Imposta un OnClickListener per il TextView "Sign-in"
        signInTextView.setOnClickListener(v -> {
            // Avvia la LoginActivity
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Chiude la RegistrationActivity in modo che l'utente non possa tornare indietro con il tasto back
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Username, password, and confirm password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se le due password coincidono
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se l'username è già preso
        if (userRepository.isUsernameTaken(username)) {
            Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crea un nuovo oggetto User e lo registra nel database
        User newUser = new User(username, password);
        userRepository.registerUser(newUser);

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

        // Avvia la HomeActivity
        Intent intent = new Intent(RegistrationActivity.this, HomeFragment.class);
        startActivity(intent);

        // Opzionale: chiudi l'activity di registrazione per prevenire il ritorno con il pulsante back
        finish();
    }
}
