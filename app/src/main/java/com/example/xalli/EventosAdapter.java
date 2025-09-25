package com.example.xalli;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;

import java.util.List;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.EventoViewHolder> {
    private List<Evento> eventos;

    public EventosAdapter(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventos.get(position);

        holder.title.setText(evento.getNombreCultura());
        holder.description.setText(evento.getDescripcion());

        // Format the date string
        String dateText = "Fecha: " + evento.getFechaInicio();
        if (evento.getFechaFin() != null && !evento.getFechaFin().isEmpty()) {
            dateText += " al " + evento.getFechaFin();
        }
        holder.date.setText(dateText);

        // You can set an OnClickListener for the button here if needed
        // holder.detailsButton.setOnClickListener(v -> { ... });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    // Updated ViewHolder to match the new layout IDs
    static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date;
        Chip categoryChip;
        Button detailsButton;

        EventoViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_event_title);
            description = itemView.findViewById(R.id.text_event_description);
            date = itemView.findViewById(R.id.text_event_date);
            categoryChip = itemView.findViewById(R.id.chip_category);
            detailsButton = itemView.findViewById(R.id.button_details);
        }
    }
}