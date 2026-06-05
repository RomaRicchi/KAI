package com.roma.kai.ui.inicio;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.roma.kai.R;
import com.roma.kai.databinding.ItemHabitoBinding;
import com.roma.kai.databinding.ItemInicioHabitoBinding;
import com.roma.kai.model.dto.DailyHabitSummary;
import com.roma.kai.model.dto.UserHabitResponse;
import com.roma.kai.ui.habitos.HabitosAdapter;
import com.roma.kai.utils.ImageUi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InicioHabitosAdapter extends ListAdapter<DailyHabitSummary, InicioHabitosAdapter.InicioHabitoViewHolder> {
    private List<DailyHabitSummary> habitos = new ArrayList<>();

    private final OnInicioHabitoClickListener listener;

    public interface OnInicioHabitoClickListener {
        void onHabitoClick(DailyHabitSummary habito);
    }

    public InicioHabitosAdapter(OnInicioHabitoClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<DailyHabitSummary> DIFF_CALLBACK = new DiffUtil.ItemCallback<DailyHabitSummary>() {

        @Override
        public boolean areItemsTheSame(@NonNull DailyHabitSummary oldItem, @NonNull DailyHabitSummary newItem) {
            // Comparás IDs únicos
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DailyHabitSummary oldItem, @NonNull DailyHabitSummary newItem) {
            // Comparás contenido
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public InicioHabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInicioHabitoBinding binding = ItemInicioHabitoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new InicioHabitoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InicioHabitoViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }



    static class InicioHabitoViewHolder extends RecyclerView.ViewHolder {
        ItemInicioHabitoBinding binding;

        public InicioHabitoViewHolder(ItemInicioHabitoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DailyHabitSummary habito, OnInicioHabitoClickListener listener) {

            binding.tvHabitoNombre.setText(habito.getNombre());
            binding.tvHabitoCategoria.setText(habito.getCategoria());

            if(habito.isCompletado()) {
                binding.ivHabitoEstadoIcon.setVisibility(android.view.View.VISIBLE);
                binding.ivHabitoEstadoIcon.setImageResource(R.drawable.ic_check_circle_green_24dp);
            } else {
                binding.ivHabitoEstadoIcon.setVisibility(android.view.View.GONE);
            }

            // Icono con arquitectura centralizada ImageUi
            // Priorizamos la categoría para que todos los hábitos de la misma categoría tengan la misma imagen
            String categoryKey = habito.getCategoria();
            String imgData = habito.getImagenHabito();

            if (imgData != null && imgData.startsWith("http")) {
                // Es URL remota
                Glide.with(itemView.getContext())
                        .load(imgData)
                        .placeholder(com.roma.kai.R.drawable.ic_gallery_black_24dp)
                        .into(binding.ivHabitoIcon);
            } else {
                // Usamos el resolver con el nombre de la categoria para consistencia
                Glide.with(itemView.getContext())
                        .load(ImageUi.getDrawable(categoryKey))
                        .into(binding.ivHabitoIcon);
            }

            itemView.setOnClickListener(v ->
                    listener.onHabitoClick(habito)
            );
        }
    }
}
