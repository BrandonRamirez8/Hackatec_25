package com.example.xalli;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    // Eliminada la declaración de Button fabCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // Cambiado a home.xml

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Eliminada la inicialización de fabCenter = findViewById(R.id.button);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new Home();
            } else if (itemId == R.id.nav_explorar) {
                selectedFragment = new Explorar();
            } else if (itemId == R.id.nav_mapa) {
                selectedFragment = new Mapa();
            } else if (itemId == R.id.nav_eventos) {
                selectedFragment = new Eventos();
            } else if (itemId == R.id.nav_ia) {
                selectedFragment = new Ia();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Eliminado el OnClickListener de fabCenter

        if (savedInstanceState == null) {
            // Cargar el Home Fragment por defecto
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Home())
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }
}