package com.roma.kai.ui.inicio;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roma.kai.databinding.ItemHabitoBinding;
import com.roma.kai.model.dto.DailyHabitSummary;

import java.util.ArrayList;
import java.util.List;

public class InicioHabitosAdapter extends RecyclerView.Adapter<InicioHabitosAdapter.InicioHabitoViewHolder>{
    private List<DailyHabitSummary> habitos = new ArrayList<>();


    @NonNull
    @Override
    public InicioHabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHabitoBinding binding = ItemHabitoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InicioHabitosAdapter.InicioHabitoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InicioHabitoViewHolder holder, int position) {
        DailyHabitSummary habito = habitos.get(position);

        holder.binding.txtHabitoNombre.setText(habito.getNombre());
        holder.binding.txtHabitoCategoria.setText(habito.getCategoria());
        // agregar si fue completado o no
    }

    @Override
    public int getItemCount() { return habitos.size(); }

    public void setHabitos(List<DailyHabitSummary> habitos) {
        this.habitos = habitos;

        notifyDataSetChanged();
    }

    static class InicioHabitoViewHolder extends RecyclerView.ViewHolder {
        ItemHabitoBinding binding;

        public InicioHabitoViewHolder(ItemHabitoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
