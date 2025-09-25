// C:/Users/byrur/Documents/Hjasask/app/src/main/java/com/example/xalli/Home.java
package com.example.xalli;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide; // Glide ya está en tu proyecto, lo usamos

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Home extends Fragment {

    // Vistas de la UI
    private ImageButton btnSettings;
    private ImageButton btnPremium;
    private ImageView imagePopularDestination;
    private TextView textPopularDestinationName;
    private TextView textPopularDestinationDescription;

    // Credenciales de Supabase (las mismas que usas en Eventos.java)
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJvb3phcWJyaGZvcW1hbmZ5a3BwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTg3MzIxMDQsImV4cCI6MjA3NDMwODEwNH0.BUaJLCk19QKo9y2KS2D3qm4YsPa_TrJKtcB0ZS8Lw44";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Inicializar todas las vistas
        initializeViews(view);

        // 2. Configurar los listeners de los botones (tu código existente)
        setupButtonListeners();

        // 3. Cargar los datos del destino popular desde Supabase
        fetchPopularDestination();
    }

    private void initializeViews(View view) {
        btnSettings = view.findViewById(R.id.btn_settings);
        btnPremium = view.findViewById(R.id.btn_premium);
        imagePopularDestination = view.findViewById(R.id.image_popular_destination);
        textPopularDestinationName = view.findViewById(R.id.text_popular_destination_name);
        textPopularDestinationDescription = view.findViewById(R.id.text_popular_destination_description);
    }

    private void fetchPopularDestination() {
        OkHttpClient client = new OkHttpClient();

        // URL para la tabla Pueblos_Magicos.
        // `select=*,Imagenes_Pueblos_Magicos(url_imagen)` trae el pueblo y la URL de su imagen relacionada.
        // `limit=1` trae solo el primer resultado.
        String url = "https://boozaqbrhfoqmanfykpp.supabase.co/rest/v1/Pueblos_Magicos?select=*,Imagenes_Pueblos_Magicos(url_imagen)&limit=1";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", SUPABASE_API_KEY)
                .addHeader("Authorization", "Bearer " + SUPABASE_API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Error de red al cargar destino", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseBody);

                        if (jsonArray.length() > 0) {
                            // Obtenemos solo el primer objeto del array
                            JSONObject destinationObject = jsonArray.getJSONObject(0);

                            // Extraemos los datos
                            String name = destinationObject.optString("Nombre", "Destino no encontrado");
                            String description = destinationObject.optString("Descripcion", "Descubre este increíble lugar.");
                            String imageUrl = "";

                            // La imagen viene en un array anidado, lo procesamos
                            JSONArray imagesArray = destinationObject.optJSONArray("Imagenes_Pueblos_Magicos");
                            if (imagesArray != null && imagesArray.length() > 0) {
                                imageUrl = imagesArray.getJSONObject(0).optString("url_imagen");
                            }

                            // Actualizamos la UI en el hilo principal para evitar errores
                            String finalImageUrl = imageUrl;
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(() -> {
                                    textPopularDestinationName.setText(name);
                                    textPopularDestinationDescription.setText(description);

                                    // Usamos Glide para cargar la imagen desde la URL
                                    if (getContext() != null && !finalImageUrl.isEmpty()) {
                                        Glide.with(getContext())
                                                .load(finalImageUrl)
                                                .centerCrop()
                                                .into(imagePopularDestination);
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        // Manejar error si el JSON es inválido
                    }
                }
            }
        });
    }

    // --- Tu código existente para los botones (sin cambios) ---
    private void setupButtonListeners() {
        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> {
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (btnPremium != null) {
            btnPremium.setOnClickListener(v -> showPremiumDialog());
        }
    }

    private void showPremiumDialog() {
        if (getContext() == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater dialogInflater = requireActivity().getLayoutInflater();
        View dialogView = dialogInflater.inflate(R.layout.dialog_premium_membership, null);

        Button btnBecomePremium = dialogView.findViewById(R.id.btn_become_premium);
        btnBecomePremium.setOnClickListener(v2 -> {
            ((XalliApplication) requireActivity().getApplication()).setPremiumUser(true);
            Toast.makeText(getContext(), "¡Eres usuario Premium ahora!", Toast.LENGTH_SHORT).show();
        });

        builder.setView(dialogView)
                .setTitle("Membresía Premium")
                .setPositiveButton("Entendido", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}