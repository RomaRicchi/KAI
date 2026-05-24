package com.roma.kai.ui.habitos;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.roma.kai.databinding.ItemHabitoSeleccionBinding;
import com.roma.kai.model.entity.Habito;
import java.util.List;

public class HabitosSeleccionAdapter extends RecyclerView.Adapter<HabitosSeleccionAdapter.ViewHolder> {

    private final List<Habito> habitos;
    private final OnHabitoSelectedListener listener;

    public interface OnHabitoSelectedListener {
        void onHabitoSelected(Habito habito, boolean isChecked);
    }

    public HabitosSeleccionAdapter(List<Habito> habitos, OnHabitoSelectedListener listener) {
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
        Habito habito = habitos.get(position);
        holder.binding.txtHabitoNombreSeleccion.setText(habito.getNombre());
        
        // Evitar que el listener se dispare al scrollear
        holder.binding.cbHabitoSeleccion.setOnCheckedChangeListener(null);
        holder.binding.cbHabitoSeleccion.setChecked(false); // O el estado real si se guardara

        holder.binding.cbHabitoSeleccion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onHabitoSelected(habito, isChecked);
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
