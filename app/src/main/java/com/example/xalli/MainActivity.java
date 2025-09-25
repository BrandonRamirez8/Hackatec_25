package com.example.xalli;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.NonNull;
import android.view.View; // Importar View para gestionar visibilidad

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.SharedPreferences; // Importar SharedPreferences
import android.content.Context; // Importar Context

// C:/Users/byrur/Documents/Hjasask/app/src/main/java/com/example/xalli/MainActivity.java

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private AdView mAdView; // Declarar AdView

    Fragment homeFragment;
    Fragment explorarFragment;
    Fragment mapaFragment;
    Fragment eventosFragment;
    Fragment iaFragment;
    Fragment activeFragment;

    private static final String TAG_ACTIVE_FRAGMENT = "active_fragment";
    private static final String TAG_HOME_FRAGMENT = "home_fragment";
    private static final String TAG_EXPLORAR_FRAGMENT = "explorar_fragment";
    private static final String TAG_MAPA_FRAGMENT = "mapa_fragment";
    private static final String TAG_EVENTOS_FRAGMENT = "eventos_fragment";
    private static final String TAG_IA_FRAGMENT = "ia_fragment";
    // public static final String TAG_SETTINGS_FRAGMENT = "settings_fragment"; // Hice public para acceso desde fragmentos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            homeFragment = new Home();
            explorarFragment = new Explorar();
            mapaFragment = new Mapa();
            eventosFragment = new Eventos();
            iaFragment = new Ia();

            fm.beginTransaction().add(R.id.fragment_container, homeFragment, TAG_HOME_FRAGMENT).commit();
            fm.beginTransaction().add(R.id.fragment_container, explorarFragment, TAG_EXPLORAR_FRAGMENT).hide(explorarFragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, mapaFragment, TAG_MAPA_FRAGMENT).hide(mapaFragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, eventosFragment, TAG_EVENTOS_FRAGMENT).hide(eventosFragment).commit();
            fm.beginTransaction().add(R.id.fragment_container, iaFragment, TAG_IA_FRAGMENT).hide(iaFragment).commit();
            activeFragment = homeFragment;
        } else {
            homeFragment = fm.findFragmentByTag(TAG_HOME_FRAGMENT);
            explorarFragment = fm.findFragmentByTag(TAG_EXPLORAR_FRAGMENT);
            mapaFragment = fm.findFragmentByTag(TAG_MAPA_FRAGMENT);
            eventosFragment = fm.findFragmentByTag(TAG_EVENTOS_FRAGMENT);
            iaFragment = fm.findFragmentByTag(TAG_IA_FRAGMENT);
            
            activeFragment = fm.getFragment(savedInstanceState, TAG_ACTIVE_FRAGMENT);
            if (activeFragment == null) {
                activeFragment = homeFragment;
            }
            // Asegurarse de que el fragmento activo se muestre y los demás estén ocultos
            FragmentTransaction ft = fm.beginTransaction();
            if (homeFragment != null && homeFragment != activeFragment) ft.hide(homeFragment);
            if (explorarFragment != null && explorarFragment != activeFragment) ft.hide(explorarFragment);
            if (mapaFragment != null && mapaFragment != activeFragment) ft.hide(mapaFragment);
            if (eventosFragment != null && eventosFragment != activeFragment) ft.hide(eventosFragment);
            if (iaFragment != null && iaFragment != activeFragment) ft.hide(iaFragment);
            if (activeFragment != null) ft.show(activeFragment);
            ft.commit();

            // Asegurar que el elemento de la barra de navegación inferior esté seleccionado correctamente
            if (activeFragment == homeFragment) {
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
            } else if (activeFragment == explorarFragment) {
                bottomNavigationView.setSelectedItemId(R.id.nav_explorar);
            } else if (activeFragment == mapaFragment) {
                bottomNavigationView.setSelectedItemId(R.id.nav_mapa);
            } else if (activeFragment == eventosFragment) {
                bottomNavigationView.setSelectedItemId(R.id.nav_eventos);
            } else if (activeFragment == iaFragment) {
                bottomNavigationView.setSelectedItemId(R.id.nav_ia);
            }
        }

        // Lógica de AdMob
        mAdView = findViewById(R.id.adView);
        boolean isPremium = ((XalliApplication) getApplication()).isPremiumUser();

        if (isPremium) {
            mAdView.setVisibility(View.GONE); // Ocultar el anuncio si el usuario es premium
        } else {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            mAdView.setVisibility(View.VISIBLE); // Asegurarse de que el anuncio sea visible
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Fragment selectedFragment = null;
            String tag = null;
            if (itemId == R.id.nav_home) {
                selectedFragment = homeFragment;
                tag = TAG_HOME_FRAGMENT;
            } else if (itemId == R.id.nav_explorar) {
                selectedFragment = explorarFragment;
                tag = TAG_EXPLORAR_FRAGMENT;
            } else if (itemId == R.id.nav_mapa) {
                selectedFragment = mapaFragment;
                tag = TAG_MAPA_FRAGMENT;
            } else if (itemId == R.id.nav_eventos) {
                selectedFragment = eventosFragment;
                tag = TAG_EVENTOS_FRAGMENT;
            } else if (itemId == R.id.nav_ia) {
                selectedFragment = iaFragment;
                tag = TAG_IA_FRAGMENT;
            }

            if (selectedFragment != null && selectedFragment != activeFragment) {
                showMainFragment(selectedFragment, tag);
            }
            return true;
        });
    }

    private void showMainFragment(Fragment fragmentToShow, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (activeFragment != null) {
            ft.hide(activeFragment);
        }

        // Si el fragmento ya existe, solo muéstralo
        if (fm.findFragmentByTag(tag) != null) {
            ft.show(fm.findFragmentByTag(tag));
        } else {
            // Si no, añádelo
            ft.add(R.id.fragment_container, fragmentToShow, tag);
        }
        ft.commit();
        activeFragment = fragmentToShow;
    }

    // public void navigateToSettings() {
    //     FragmentManager fm = getSupportFragmentManager();
    //     FragmentTransaction ft = fm.beginTransaction();

    //     if (activeFragment != null) {
    //         ft.hide(activeFragment);
    //     }

    //     ft.replace(R.id.fragment_container, new SettingsFragment(), TAG_SETTINGS_FRAGMENT);
    //     ft.addToBackStack(TAG_SETTINGS_FRAGMENT);
    //     ft.commit();
    // }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (activeFragment != null) {
            getSupportFragmentManager().putFragment(outState, TAG_ACTIVE_FRAGMENT, activeFragment);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
