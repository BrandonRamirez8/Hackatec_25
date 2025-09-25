package com.example.xalli;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// C:/Users/byrur/Documents/Hjasask/app/src/main/java/com/example/xalli/MainActivity.java

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    // 1. Declara tus fragmentos como variables de instancia
    final Fragment homeFragment = new Home();
    final Fragment explorarFragment = new Explorar();
    final Fragment mapaFragment = new Mapa();
    final Fragment eventosFragment = new Eventos();
    final Fragment iaFragment = new Ia();
    Fragment activeFragment = homeFragment; // Mantén una referencia al fragmento activo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 2. Añade todos los fragmentos al inicio, pero ocúltalos
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, iaFragment, "5").hide(iaFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, eventosFragment, "4").hide(eventosFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mapaFragment, "3").hide(mapaFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, explorarFragment, "2").hide(explorarFragment).commit();
        // Muestra el fragmento inicial (Home)
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment, "1").commit();


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            // 3. Usa show() y hide() para cambiar entre ellos
            if (itemId == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction().hide(activeFragment).show(homeFragment).commit();
                activeFragment = homeFragment;
            } else if (itemId == R.id.nav_explorar) {
                getSupportFragmentManager().beginTransaction().hide(activeFragment).show(explorarFragment).commit();
                activeFragment = explorarFragment;
            } else if (itemId == R.id.nav_mapa) {
                getSupportFragmentManager().beginTransaction().hide(activeFragment).show(mapaFragment).commit();
                activeFragment = mapaFragment;
            } else if (itemId == R.id.nav_eventos) {
                getSupportFragmentManager().beginTransaction().hide(activeFragment).show(eventosFragment).commit();
                activeFragment = eventosFragment;
            } else if (itemId == R.id.nav_ia) {
                getSupportFragmentManager().beginTransaction().hide(activeFragment).show(iaFragment).commit();
                activeFragment = iaFragment;
            }

            return true;
        });
    }
}
