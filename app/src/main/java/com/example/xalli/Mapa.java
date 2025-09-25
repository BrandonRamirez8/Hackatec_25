package com.example.xalli;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class Mapa extends Fragment {

    private WebView webView;
    private FusedLocationProviderClient fusedLocationClient;

    // Lanzador para solicitar permisos de ubicación
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permiso concedido, obtenemos la ubicación
                    getCurrentLocationAndSendToWebView();
                } else {
                    // Permiso denegado
                    Toast.makeText(getContext(), "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapa, container, false);

        webView = view.findViewById(R.id.webview_map);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        setupWebView();
        checkLocationPermission();

        return view;
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true); // Fundamental para la API de Geolocalización de HTML5

        webView.setWebViewClient(new WebViewClient());

        // Necesitas un WebChromeClient para gestionar los permisos de geolocalización de la página
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                // Concede permiso al origen de la página para usar la geolocalización
                callback.invoke(origin, true, false);
            }
        });

        webView.loadUrl("file:///android_asset/maps.html");
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // Ya tienes permiso, obtén la ubicación
            getCurrentLocationAndSendToWebView();
        } else {
            // No tienes permiso, solicítalo
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void getCurrentLocationAndSendToWebView() {
        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            // Ubicación obtenida, la pasamos al WebView
                            double lat = location.getLatitude();
                            double lon = location.getLongitude();

                            // Ejecuta una función JavaScript en el WebView
                            String script = String.format("javascript:updateLocation(%f, %f)", lat, lon);
                            webView.evaluateJavascript(script, null);
                        } else {
                            Toast.makeText(getContext(), "No se pudo obtener la ubicación.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (SecurityException e) {
            // Esto no debería pasar si ya verificaste el permiso, pero es buena práctica tenerlo
            e.printStackTrace();
        }
    }
}