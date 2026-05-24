package com.roma.kai.ui.habitos;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.roma.kai.databinding.ItemHabitoBinding;
import com.roma.kai.model.entity.Habito;
import java.util.List;

public class HabitosAdapter extends RecyclerView.Adapter<HabitosAdapter.HabitoViewHolder> {

    private final List<Habito> habitos;
    private final OnHabitoClickListener listener;

    public interface OnHabitoClickListener {
        void onHabitoClick(Habito habito);
    }

    public HabitosAdapter(List<Habito> habitos, OnHabitoClickListener listener) {
        this.habitos = habitos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHabitoBinding binding = ItemHabitoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new HabitoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        Habito habito = habitos.get(position);
        holder.binding.txtHabitoNombre.setText(habito.getNombre());
        holder.binding.txtHabitoCategoria.setText(habito.getCategoria());
        holder.binding.txtHabitoRacha.setText("Racha: " + habito.getRacha() + " días");
        
        // Cargar icono si existe
        if (habito.getIconResId() != 0) {
            holder.binding.imgHabitoIcon.setImageResource(habito.getIconResId());
        }

        holder.itemView.setOnClickListener(v -> listener.onHabitoClick(habito));
    }

    @Override
    public int getItemCount() {
        return habitos.size();
    }

    static class HabitoViewHolder extends RecyclerView.ViewHolder {
        ItemHabitoBinding binding;
        public HabitoViewHolder(ItemHabitoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
