package com.example.MyMangaList;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.MyMangaList.Database.UserRepository;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;
    private TextView signInTextView;

    private UserRepository userRepository;

    private static final String CHANNEL_ID = "registration_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        userRepository = new UserRepository(getApplication());

        usernameEditText = findViewById(R.id.username_field);
        passwordEditText = findViewById(R.id.password_registration);
        confirmPasswordEditText = findViewById(R.id.confirm_pass);
        registerButton = findViewById(R.id.buttonSignUp);
        signInTextView = findViewById(R.id.textViewSignIn);

        registerButton.setOnClickListener(v -> registerUser());

        // Crea il canale di notifica
        createNotificationChannel();

        // Testo completo con "sign-in" cliccabile
        String signInText = "Do you already have an account? sign-in";
        SpannableString spannableString = new SpannableString(signInText);

        // Trova l'indice della parola "sign-in"
        int signInStart = signInText.indexOf("sign-in");
        int signInEnd = signInStart + "sign-in".length();

        // Crea ClickableSpan
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Avvia la LoginActivity quando viene cliccato
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Chiude la RegistrationActivity in modo che l'utente non possa tornare indietro con il tasto back
            }
        };

        // Applica il ClickableSpan solo alla parola "sign-in"
        spannableString.setSpan(clickableSpan, signInStart, signInEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Cambia il colore del testo "sign-in" per farlo sembrare un link
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_light)),
                signInStart, signInEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Imposta il testo modificato sul TextView
        signInTextView.setText(spannableString);
        signInTextView.setMovementMethod(LinkMovementMethod.getInstance());
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
        userRepository.isUsernameTaken(username, isTaken -> {
            runOnUiThread(() -> {
                if (isTaken) {
                    Toast.makeText(this, "Username already taken", Toast.LENGTH_SHORT).show();
                } else {
                    // Crea un nuovo oggetto User e lo registra nel database
                    User newUser = new User(username, password);
                    userRepository.registerUser(newUser);

                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Invia una notifica di successo
                    sendRegistrationSuccessNotification();

                    // Avvia la HomeActivity
                    Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                    startActivity(intent);

                    // Chiudi l'activity di registrazione per prevenire il ritorno con il pulsante back
                    finish();
                }
            });
        });
    }

    private void createNotificationChannel() {
        // Creazione del canale di notifica per Android 8.0 e versioni successive
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Registration Channel";
            String description = "Channel for registration notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Registra il canale nel sistema
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendRegistrationSuccessNotification() {
        // Costruisce la notifica
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_registration_not) // L'icona della notifica
                .setContentTitle("Registration Successful")
                .setContentText("You have successfully registered to MyMangaList.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Mostra la notifica
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }
}
