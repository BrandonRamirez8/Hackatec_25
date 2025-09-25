package com.example.xalli;

import android.content.Intent;
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

public class Register extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etCity;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private MaterialButton btnRegister;

    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();
    private final String SUPABASE_URL = "https://boozaqbrhfoqmanfykpp.supabase.co";
    private final String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJvb3phcWJyaGZvcW1hbmZ5a3BwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTg3MzIxMDQsImV4cCI6MjA3NDMwODEwNH0.BUaJLCk19QKo9y2KS2D3qm4YsPa_TrJKtcB0ZS8Lw44";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ImageView btnBack = findViewById(R.id.iv_back_arrow);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        // Referenciar los elementos de la UI
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etCity = findViewById(R.id.ac_city);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (password.equals(confirmPassword)) {
                    registerUser(name, email, city, password);
                } else {
                    Toast.makeText(Register.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser(String name, String email, String city, String password) {
        if (name.isEmpty() || email.isEmpty() || city.isEmpty() || password.isEmpty()) {
            Toast.makeText(Register.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String jsonPayload = gson.toJson(new RegisterRequest(email, password, name, city));

        RequestBody body = RequestBody.create(jsonPayload, JSON);
        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/auth/v1/signup")
                .post(body)
                .addHeader("apikey", SUPABASE_ANON_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(Register.this, "Error de red: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    // Supabase retorna la sesión directamente después de un registro exitoso
                    RegisterResponse registerResponse = gson.fromJson(responseBody, RegisterResponse.class);
                    runOnUiThread(() -> {
                        if (registerResponse != null && registerResponse.getUser() != null) {
                            Toast.makeText(Register.this, "Registro exitoso para: " + registerResponse.getUser().getEmail(), Toast.LENGTH_SHORT).show();
                            // Redirigir al usuario a la pantalla de Login después de un registro exitoso
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Respuesta de Supabase inválida.", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    String errorBody = response.body().string();
                    runOnUiThread(() -> {
                        Toast.makeText(Register.this, "Error al registrar: " + response.code() + " - " + errorBody, Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    // Clases auxiliares para el JSON de la solicitud y respuesta
    private static class RegisterRequest {
        String email;
        String password;
        @SerializedName("data")
        UserData data;

        public RegisterRequest(String email, String password, String name, String city) {
            this.email = email;
            this.password = password;
            this.data = new UserData(name, city);
        }
    }

    private static class UserData {
        String name;
        String city;

        public UserData(String name, String city) {
            this.name = name;
            this.city = city;
        }
    }

    private static class RegisterResponse {
        @SerializedName("access_token")
        String accessToken;
        @SerializedName("refresh_token")
        String refreshToken;
        @SerializedName("user")
        User user;

        public User getUser() {
            return user;
        }

        private static class User {
            @SerializedName("id")
            String id;
            @SerializedName("email")
            String email;
            @SerializedName("user_metadata")
            UserData userMetadata;

            public String getEmail() {
                return email;
            }
        }
    }
}