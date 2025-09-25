package com.example.xalli;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Eventos extends Fragment {

    private RecyclerView recyclerView;
    private EventosAdapter adapter;
    private List<Evento> eventosList = new ArrayList<>();

    // Cambia estos valores por los de tu proyecto Supabase
    private static final String SUPABASE_URL = "https://boozaqbrhfoqmanfykpp.supabase.co/rest/v1/Cultura";
    private static final String SUPABASE_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJvb3phcWJyaGZvcW1hbmZ5a3BwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTg3MzIxMDQsImV4cCI6MjA3NDMwODEwNH0.BUaJLCk19QKo9y2KS2D3qm4YsPa_TrJKtcB0ZS8Lw44";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewEventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EventosAdapter(eventosList);
        recyclerView.setAdapter(adapter);

        cargarEventosSupabase();

        return view;
    }

    private void cargarEventosSupabase() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(SUPABASE_URL)
                .addHeader("apikey", SUPABASE_API_KEY)
                .addHeader("Authorization", "Bearer " + SUPABASE_API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error de red al cargar eventos", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseBody);
                        eventosList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String nombreCultura = obj.optString("Nombre_Cultura");
                            String fechaInicio = obj.optString("fecha_inicio");
                            String fechaFin = obj.optString("fecha_fin");
                            String descripcion = obj.optString("Descripcion");
                            eventosList.add(new Evento(nombreCultura, fechaInicio, fechaFin, descripcion));
                        }
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "Eventos recibidos: " + eventosList.size(), Toast.LENGTH_LONG).show();
                            adapter.notifyDataSetChanged();
                        });
                    } catch (Exception e) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Error al procesar eventos", Toast.LENGTH_SHORT).show()
                        );
                    }
                } else {
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "Error al cargar eventos", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}