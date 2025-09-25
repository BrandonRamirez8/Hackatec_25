package com.example.xalli;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
// import com.example.xalli.MainActivity; // Importar MainActivity
import android.content.Intent;
import android.app.AlertDialog; // Importar AlertDialog
import com.example.xalli.XalliApplication; // Importar XalliApplication
import android.widget.Toast; // Importar Toast
import android.widget.Button; // Importar Button

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Eventos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Eventos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Eventos() {
        // Required empty public constructor
    }


    public static Eventos newInstance(String param1, String param2) {
        Eventos fragment = new Eventos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        ImageButton btnSettings = view.findViewById(R.id.btn_settings);
        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> {
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
                }
            });
        }

        ImageButton btnPremium = view.findViewById(R.id.btn_premium);
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