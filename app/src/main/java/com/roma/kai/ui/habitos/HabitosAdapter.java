package com.roma.kai.ui.habitos;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.roma.kai.databinding.ItemHabitoBinding;
import com.roma.kai.model.dto.UserHabitResponse;
import com.roma.kai.utils.ImageUi;
import java.util.List;
import java.util.Objects;

public class HabitosAdapter extends ListAdapter<UserHabitResponse, HabitosAdapter.HabitoViewHolder> {

    private final OnHabitoClickListener listener;

    public interface OnHabitoClickListener {
        void onHabitoClick(UserHabitResponse habito);
    }

    public HabitosAdapter(OnHabitoClickListener listener) {

        super(DIFF_CALLBACK);

        this.listener = listener;
    }

    // DIFFUTIL
    private static final DiffUtil.ItemCallback<UserHabitResponse> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserHabitResponse>() {

                @Override
                public boolean areItemsTheSame(@NonNull UserHabitResponse oldItem, @NonNull UserHabitResponse newItem) {
                    // Comparás IDs únicos
                    return Objects.equals(oldItem.getHabitoUsuarioId(), newItem.getHabitoUsuarioId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull UserHabitResponse oldItem, @NonNull UserHabitResponse newItem) {
                    // Comparás contenido
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHabitoBinding binding = ItemHabitoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new HabitoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        UserHabitResponse habito = getItem(position);
        holder.bind(habito, listener);
    }

    // VIEW HOLDER
    static class HabitoViewHolder extends RecyclerView.ViewHolder {
        private final ItemHabitoBinding binding;

        public HabitoViewHolder(ItemHabitoBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(UserHabitResponse habito, OnHabitoClickListener listener) {

            binding.txtHabitoNombre.setText(habito.getNombre());

            binding.txtHabitoCategoria.setText(habito.getCategoria());

            binding.txtHabitoRacha.setText("Racha: " + habito.getRachaActual() + " días");

            // Icono con arquitectura centralizada ImageUi
            // Priorizamos la categoría para que todos los hábitos de la misma categoría tengan la misma imagen
            String categoryKey = habito.getCategoria();
            String imgData = habito.getImagenHabito();
            
            if (imgData != null && imgData.startsWith("http")) {
                // Es URL remota
                Glide.with(itemView.getContext())
                        .load(imgData)
                        .placeholder(com.roma.kai.R.drawable.ic_gallery_black_24dp)
                        .into(binding.imgHabitoIcon);
            } else {
                // Usamos el resolver con el nombre de la categoria para consistencia
                Glide.with(itemView.getContext())
                        .load(ImageUi.getDrawable(categoryKey))
                        .into(binding.imgHabitoIcon);
            }

            itemView.setOnClickListener(v ->
                    listener.onHabitoClick(habito)
            );
        }
    }
}
