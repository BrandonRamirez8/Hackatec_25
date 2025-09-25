package com.example.xalli;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class ResetPassword extends AppCompatActivity {

    private EditText etEmail;
    private MaterialButton btnSendEmail;

    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();
    private final String SUPABASE_URL = "https://boozaqbrhfoqmanfykpp.supabase.co";
    private final String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJvb3phcWJyaGZvcW1hbmZ5a3BwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTg3MzIxMDQsImV4cCI6MjA3NDMwODEwNH0.BUaJLCk19QKo9y2KS2D3qm4YsPa_TrJKtcB0ZS8Lw44";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        ImageView btnBack = findViewById(R.id.iv_back_arrow);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        etEmail = findViewById(R.id.et_email);
        btnSendEmail = findViewById(R.id.btn_send_email);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                sendResetPasswordEmail(email);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void sendResetPasswordEmail(String email) {
        if (email.isEmpty()) {
            Toast.makeText(ResetPassword.this, "Por favor, ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String jsonPayload = gson.toJson(new ResetPasswordRequest(email));

        RequestBody body = RequestBody.create(jsonPayload, JSON);
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/auth/v1/recover")
                .post(body)
                .addHeader("apikey", SUPABASE_ANON_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(ResetPassword.this, "Error de red: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(ResetPassword.this, "Si el correo existe, se ha enviado un enlace para restablecer la contraseña.", Toast.LENGTH_LONG).show();
                        finish(); // Opcional: regresar a la pantalla de Login
                    });
                } else {
                    String errorBody = response.body().string();
                    runOnUiThread(() -> {
                        Toast.makeText(ResetPassword.this, "Error al enviar correo de recuperación: " + response.code() + " - " + errorBody, Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    private static class ResetPasswordRequest {
        String email;

        public ResetPasswordRequest(String email) {
            this.email = email;
        }
    }
}