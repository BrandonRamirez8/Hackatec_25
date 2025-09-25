package com.example.xalli;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
// import androidx.annotation.Nullable;
// import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
// import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView; // Usar TextView en lugar de TextInputEditText
import android.widget.Toast;

// import com.google.android.material.textfield.TextInputEditText; // Ya no necesario

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.xalli.XalliApplication; // Importar XalliApplication

public class SettingsActivity extends AppCompatActivity {

    private TextView etNombreUsuario; // Cambiado a TextView
    private TextView etEmailConfig;   // Cambiado a TextView
    private Button btnCambiarContrasena;
    private Button btnPoliticaPrivacidad;
    private Button btnCerrarSesion;
    private TextView tvPremiumStatus; // Declarar TextView

    // Constructor vacío ya no es necesario para una actividad
    // public SettingsFragment() {
    // Required empty public constructor
    // }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Ajustes");
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // Vincular los elementos de la UI
        etNombreUsuario = findViewById(R.id.et_nombre_usuario);
        etEmailConfig = findViewById(R.id.et_email_config);
        btnCambiarContrasena = findViewById(R.id.btn_cambiar_contrasena);
        btnPoliticaPrivacidad = findViewById(R.id.btn_politica_privacidad);
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);
        tvPremiumStatus = findViewById(R.id.tv_premium_status); // Inicializar TextView

        // Cargar datos del usuario (placeholder)
        cargarDatosUsuario();

        // Actualizar estado Premium
        updatePremiumStatus();

        // Listeners para los botones
        // etNombreUsuario.setOnFocusChangeListener((v, hasFocus) -> {
        //     if (!hasFocus) {
        //         actualizarNombreUsuario(etNombreUsuario.getText().toString());
        //     }
        // }); // Eliminado ya que no es un campo editable

        btnCambiarContrasena.setOnClickListener(v -> cambiarContrasena());
        btnPoliticaPrivacidad.setOnClickListener(v -> verPoliticaPrivacidad());
        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePremiumStatus(); // Actualizar el estado Premium cada vez que la actividad se reanuda
    }

    private void updatePremiumStatus() {
        boolean isPremium = ((XalliApplication) getApplication()).isPremiumUser();
        if (isPremium) {
            tvPremiumStatus.setText("Estado Premium: Sí");
        } else {
            tvPremiumStatus.setText("Estado Premium: No");
        }
    }

    private void cargarDatosUsuario() {
        // Estos datos se cargarán de Supabase en una implementación real.
        etNombreUsuario.setText("Nombre de Usuario Actual"); // Placeholder
        etEmailConfig.setText("correo.actual@example.com"); // Placeholder, este campo es no editable
    }

    private void actualizarNombreUsuario(String nuevoNombre) {
        // Este método ya no es relevante si los campos son de solo lectura, pero lo dejo por si acaso
        Toast.makeText(this, "Nombre de usuario actualizado a: " + nuevoNombre, Toast.LENGTH_SHORT).show();
    }

    private void cambiarContrasena() {
        Intent intent = new Intent(this, ResetPassword.class);
        startActivity(intent);
    }

    private void verPoliticaPrivacidad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_privacy_policy, null);
        TextView tvPrivacyPolicy = dialogView.findViewById(R.id.tv_privacy_policy);

        String privacyPolicyText = "Política de Privacidad de Xalli \n" +
                "Última actualización: 24 de septiembre 2025 \n" +
                "En Xalli respetamos y protegemos la privacidad de nuestros usuarios. Esta Política de \n" +
                "Privacidad describe cómo recopilamos, usamos y protegemos su información personal \n" +
                "cuando utiliza nuestra aplicación móvil. \n" +
                "1. Quiénes somos \n" +
                "Xalli es una aplicación móvil que ofrece una guía de turismo para los Pueblos Mágicos de \n" +
                "Jalisco, brindando información cultural, histórica y de servicios para los visitantes. \n" +
                "2. Información que recopilamos \n" +
                "Al utilizar Xalli podemos recopilar: \n" +
                "● Datos personales básicos: nombre, correo electrónico y/o número de teléfono, si \n" +
                "decides registrarte o ponerte en contacto con nosotros. \n" +
                "● Datos de ubicación: solo si otorgas permiso, para mostrarte información turística \n" +
                "cercana. \n" +
                "● Datos técnicos: como tipo de dispositivo, sistema operativo, versión de la app y \n" +
                "dirección IP (de manera anónima para fines estadísticos). \n" +
                "No recopilamos más información de la necesaria para que la app funcione correctamente. \n" +
                "3. Uso de la información \n" +
                "La información recopilada se utiliza exclusivamente para: \n" +
                "● Brindar los servicios y funcionalidades de la aplicación (mostrar información de \n" +
                "Pueblos Mágicos, mapas, rutas). \n" +
                "● Mejorar la experiencia de usuario y la calidad del servicio. \n" +
                "● Atender solicitudes, comentarios o consultas que nos envíes. \n" +
                "4. Compartición de información \n" +
                "En Xalli no vendemos, rentamos ni compartimos tu información personal con terceros para \n" +
                "fines comerciales. \n" +
                "Solo podremos revelar datos personales si estamos legalmente obligados a hacerlo o si es \n" +
                "necesario para proteger nuestros derechos o la seguridad de otros usuarios. \n" +
                "5. Derechos de los usuarios \n" +
                "En cualquier momento puedes: \n" +
                "● Solicitar acceso a tus datos personales. \n" +
                "● Solicitar la corrección o eliminación de tus datos. \n" +
                "● Revocar el consentimiento para el uso de tus datos de ubicación. \n" +
                "Puedes ejercer estos derechos escribiendo a servicio_tecnico@xalliapp.com \n" +
                "6. Seguridad de la información \n" +
                "Tomamos las medidas técnicas y organizativas necesarias para proteger tu información \n" +
                "personal contra pérdida, uso indebido, acceso no autorizado, divulgación y destrucción. \n" +
                "7. Cambios en esta política \n" +
                "Podremos actualizar esta Política de Privacidad ocasionalmente. Si realizamos cambios \n" +
                "significativos, te notificaremos dentro de la aplicación y actualizaremos la fecha de “Última \n" +
                "actualización” en la parte superior de este documento. \n" +
                "8. Contacto \n" +
                "Si tienes preguntas o comentarios sobre esta Política de Privacidad, puedes escribirnos a: \n" +
                "servicio_tecnico@xalliapp.com";
        tvPrivacyPolicy.setText(privacyPolicyText);

        builder.setView(dialogView)
                .setTitle("Política de Privacidad")
                .setPositiveButton("Cerrar", (dialog, id) -> {
                    // User clicked OK button
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void cerrarSesion() {
        Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
        // Restablecer el estado premium a false al cerrar sesión
        ((XalliApplication) getApplication()).setPremiumUser(false);
        // Después de cerrar sesión, el usuario debería ser redirigido a la pantalla de inicio de sesión.
        Intent intent = new Intent(this, Login.class); // Asegúrate de tener LoginActivity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpia la pila de actividades
        startActivity(intent);
        finish();
    }
}
