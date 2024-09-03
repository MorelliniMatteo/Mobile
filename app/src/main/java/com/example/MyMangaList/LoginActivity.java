package com.example.MyMangaList;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.MyMangaList.Database.UserRepository;
import com.example.MyMangaList.Database.UserDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private UserRepository userRepository;

    private static final String CHANNEL_ID = "login_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        userRepository = new UserRepository(getApplication());

        usernameEditText = findViewById(R.id.username_field);
        passwordEditText = findViewById(R.id.password_registration); // Assicurati che l'ID sia corretto
        loginButton = findViewById(R.id.buttonSignIn);

        TextView signUpTextView = findViewById(R.id.textViewSignUp);

        // Crea il canale di notifica per il login
        createNotificationChannel();

        // Testo completo con "sign-up" cliccabile
        String text = "Don't have an account? sign-up";
        SpannableString spannableString = new SpannableString(text);

        int signUpStart = text.indexOf("sign-up");
        int signUpEnd = signUpStart + "sign-up".length();

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        };

        spannableString.setSpan(clickableSpan, signUpStart, signUpEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_light)),
                signUpStart, signUpEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        signUpTextView.setText(spannableString);
        signUpTextView.setMovementMethod(LinkMovementMethod.getInstance());

        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        userRepository.getUserByUsername(username, user -> {
            runOnUiThread(() -> {
                if (user == null) {
                    Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
                } else if (user.getPassword().equals(password)) {
                    // Credenziali corrette, invia la notifica e naviga verso la home
                    sendLoginSuccessNotification();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Login Channel";
            String description = "Channel for login notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendLoginSuccessNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_registration_not) // Usa un'icona appropriata
                .setContentTitle("Login Successful")
                .setContentText("You have successfully logged in to MyMangaList.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(2, builder.build());
    }
}
