package com.example.xalli;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class Mapa extends Fragment {

    private WebView webView;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean isRequestingLocationUpdates = false;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permiso concedido, ahora sí iniciamos la solicitud de ubicación
                    startLocationUpdates();
                } else {
                    Toast.makeText(getContext(), "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Definimos el callback para recibir las actualizaciones de ubicación
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult.getLastLocation() != null) {
                    double lat = locationResult.getLastLocation().getLatitude();
                    double lon = locationResult.getLastLocation().getLongitude();

                    // Una vez que tenemos una ubicación, la enviamos al WebView
                    String script = String.format("javascript:updateLocation(%f, %f)", lat, lon);
                    if (webView != null) {
                        webView.evaluateJavascript(script, null);
                    }

                    // IMPORTANTE: Dejamos de pedir actualizaciones para ahorrar batería
                    stopLocationUpdates();
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapa, container, false);
        webView = view.findViewById(R.id.webview_map);
        setupWebView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cuando el fragmento es visible, verificamos permisos y empezamos a buscar la ubicación
        checkLocationPermissionAndStartUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Cuando el fragmento ya no es visible, dejamos de buscar ubicación para ahorrar batería
        stopLocationUpdates();
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        webView.loadUrl("file:///android_asset/maps.html");
    }

    private void checkLocationPermissionAndStartUpdates() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void startLocationUpdates() {
        if (isRequestingLocationUpdates) return; // Si ya estamos pidiendo, no hacemos nada

        try {
            isRequestingLocationUpdates = true;
            LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000) // 10 segundos
                    .setMinUpdateIntervalMillis(5000) // 5 segundos
                    .build();

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper());
        } catch (SecurityException e) {
            isRequestingLocationUpdates = false;
            e.printStackTrace();
        }
    }

    private void stopLocationUpdates() {
        if (!isRequestingLocationUpdates) return; // Si no estamos pidiendo, no hacemos nada
        fusedLocationClient.removeLocationUpdates(locationCallback);
        isRequestingLocationUpdates = false;
    }
}
