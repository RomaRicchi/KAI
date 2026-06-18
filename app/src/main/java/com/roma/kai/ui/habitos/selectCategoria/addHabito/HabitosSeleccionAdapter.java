package com.roma.kai.ui.habitos.selectCategoria.addHabito;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.roma.kai.databinding.ItemHabitoSeleccionBinding;
import com.roma.kai.model.dto.HabitoCatalogoDto;
import com.roma.kai.utils.ImageUi;
import java.util.List;

public class HabitosSeleccionAdapter extends RecyclerView.Adapter<HabitosSeleccionAdapter.ViewHolder> {

    private final List<HabitoCatalogoDto> habitos;
    private final OnHabitoSelectedListener listener;

    public interface OnHabitoSelectedListener {
        void onHabitoSelected(HabitoCatalogoDto habito);
    }

    public HabitosSeleccionAdapter(List<HabitoCatalogoDto> habitos, OnHabitoSelectedListener listener) {
        this.habitos = habitos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHabitoSeleccionBinding binding = ItemHabitoSeleccionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HabitoCatalogoDto habito = habitos.get(position);
        holder.binding.txtHabitoNombreSeleccion.setText(habito.getNombre());
        holder.binding.txtHabitoDificultad.setText("Dificultad: " + habito.getDificultad());
        
        String categoryKey = habito.getCategoria();
        String imgData = habito.getImagenHabito();
        
        if (imgData != null && imgData.startsWith("http")) {
            // Es URL remota
            Glide.with(holder.itemView.getContext())
                    .load(imgData)
                    .placeholder(com.roma.kai.R.drawable.ic_gallery_black_24dp)
                    .into(holder.binding.imgHabitoSeleccionIcon);
        } else {
            // Usamos el resolver con el nombre de la categoria para consistencia
            Glide.with(holder.itemView.getContext())
                    .load(ImageUi.getDrawable(categoryKey))
                    .into(holder.binding.imgHabitoSeleccionIcon);
        }

        // Evitar que el listener se dispare al scrollear
        holder.itemView.setOnClickListener(v -> {
            listener.onHabitoSelected(habito);
        });
    }

    @Override
    public int getItemCount() {
        return habitos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemHabitoSeleccionBinding binding;
        ViewHolder(ItemHabitoSeleccionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
