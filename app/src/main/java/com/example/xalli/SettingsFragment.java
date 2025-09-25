package com.example.xalli;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SettingsFragment extends Fragment {

    private TextInputEditText etNombreUsuario;
    private TextInputEditText etEmailConfig;
    private Button btnCambiarContrasena;
    private Button btnPoliticaPrivacidad;
    private Button btnCerrarSesion;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vincular los elementos de la UI
        etNombreUsuario = view.findViewById(R.id.et_nombre_usuario);
        etEmailConfig = view.findViewById(R.id.et_email_config);
        btnCambiarContrasena = view.findViewById(R.id.btn_cambiar_contrasena);
        btnPoliticaPrivacidad = view.findViewById(R.id.btn_politica_privacidad);
        btnCerrarSesion = view.findViewById(R.id.btn_cerrar_sesion);

        // Cargar datos del usuario (placeholder)
        // TODO: Implementar la carga de datos del usuario desde Supabase aquí.
        // Por ejemplo, SupabaseClient.getInstance().from("users").select("name, email").eq("id", userId).execute();
        cargarDatosUsuario();

        // Listeners para los botones
        // Para el nombre de usuario, puedes añadir un listener onFocusChange o un botón de guardar si lo prefieres.
        // Ejemplo de cómo manejar la actualización del nombre de usuario al perder el foco:
        etNombreUsuario.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                actualizarNombreUsuario(etNombreUsuario.getText().toString());
            }
        });

        btnCambiarContrasena.setOnClickListener(v -> cambiarContrasena());

        btnPoliticaPrivacidad.setOnClickListener(v -> verPoliticaPrivacidad());

        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());
    }

    private void cargarDatosUsuario() {
        // TODO: Implementar la carga de datos del usuario desde Supabase.
        // Aquí deberías obtener el nombre y el email del usuario logueado de Supabase
        // y establecerlos en etNombreUsuario y etEmailConfig.
        etNombreUsuario.setText("Nombre de Usuario Actual"); // Placeholder
        etEmailConfig.setText("correo.actual@example.com"); // Placeholder, este campo es no editable
    }

    private void actualizarNombreUsuario(String nuevoNombre) {
        // TODO: Implementar la lógica para actualizar el nombre de usuario en Supabase.
        // SupabaseClient.getInstance().from("users").update(new UserUpdate("name", nuevoNombre)).eq("id", userId).execute();
        Toast.makeText(getContext(), "Nombre de usuario actualizado a: " + nuevoNombre, Toast.LENGTH_SHORT).show();
    }

    private void cambiarContrasena() {
        // TODO: Implementar la lógica para cambiar la contraseña con Supabase.
        // Esto generalmente implica: 
        // 1. Abrir un nuevo diálogo o fragmento para que el usuario ingrese la contraseña actual y la nueva.
        // 2. Llamar a la API de Supabase para actualizar la contraseña.
        // SupabaseClient.getInstance().auth().updateUserPassword(newPassword);
        Toast.makeText(getContext(), "Abriendo pantalla para cambiar contraseña...", Toast.LENGTH_SHORT).show();
    }

    private void verPoliticaPrivacidad() {
        // TODO: Reemplazar con la URL real de tu política de privacidad.
        // Asegúrate de que la URL sea accesible.
        String url = "https://www.tusitioweb.com/politica-de-privacidad"; 
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void cerrarSesion() {
        // TODO: Implementar la lógica para cerrar la sesión con Supabase.
        // SupabaseClient.getInstance().auth().signOut();
        Toast.makeText(getContext(), "Cerrando sesión...", Toast.LENGTH_SHORT).show();
        // Después de cerrar sesión, el usuario debería ser redirigido a la pantalla de inicio de sesión.
        // Asegúrate de limpiar cualquier estado de usuario o token de sesión.
        // Intent intent = new Intent(getActivity(), LoginActivity.class); 
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpia la pila de actividades
        // startActivity(intent);
        // getActivity().finish();
    }
}
