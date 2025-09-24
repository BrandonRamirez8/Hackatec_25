package com.example.xalli;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment; // No es necesario inicializarlo a null
            int itemId = item.getItemId();

            // Usamos if-else if en lugar de switch
            if (itemId == R.id.nav_home) {
                selectedFragment = new Home();
            } else if (itemId == R.id.nav_explorar || itemId == R.id.nav_mapa) {
                // Combinamos los casos que llevan al mismo fragmento
                selectedFragment = new Explorar();
            } else {
                // Es una buena práctica tener un caso por defecto
                // para evitar que la app crashee si se añade un ítem sin lógica
                selectedFragment = new Home();
            }

            // Realizamos la transacción del fragmento
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();

            return true;
        });

        // --- MEJORA IMPORTANTE ---
        // Para cargar el fragmento inicial, simplemente selecciona el ítem del menú.
        // Esto ejecutará el listener de arriba automáticamente, evitando duplicar código.
        // El 'if' evita que se recargue al rotar la pantalla.
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }
}