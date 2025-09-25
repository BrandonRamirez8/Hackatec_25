package com.example.xalli;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.app.AlertDialog; // Importar AlertDialog
import com.example.xalli.XalliApplication; // Importar XalliApplication
import android.widget.Toast; // Importar Toast
import android.widget.Button; // Importar Button

import androidx.fragment.app.Fragment;

public class Explorar extends Fragment {
    private ImageButton btnPremium;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.explorar, container, false);

        ImageButton btnSettings = view.findViewById(R.id.btn_settings);
        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> {
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
                }
            });
        }

        btnPremium = view.findViewById(R.id.btn_premium);
        if (btnPremium != null) {
            btnPremium.setOnClickListener(v -> {
                if (getActivity() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater dialogInflater = requireActivity().getLayoutInflater();
                    View dialogView = dialogInflater.inflate(R.layout.dialog_premium_membership, null);

                    Button btnBecomePremium = dialogView.findViewById(R.id.btn_become_premium);
                    btnBecomePremium.setOnClickListener(v2 -> {
                        ((XalliApplication) requireActivity().getApplication()).setPremiumUser(true);
                        Toast.makeText(getContext(), "¡Eres usuario Premium ahora!", Toast.LENGTH_SHORT).show();
                        // Opcional: Cerrar el diálogo después de la acción
                        // dialog.dismiss();
                    });

                    builder.setView(dialogView)
                            .setTitle("Membresía Premium")
                            .setPositiveButton("Entendido", (dialog, id) -> {
                                // User clicked OK button
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        return view;
    }
}
