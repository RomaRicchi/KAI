package com.roma.kai.ui.habitos.selectCategoria;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.roma.kai.databinding.ItemCategoriaBinding;
import com.roma.kai.model.dto.CategoriaDto;
import com.roma.kai.utils.ImageUi;

import java.util.Objects;

public class CategoriaAdapter extends ListAdapter<CategoriaDto, CategoriaAdapter.CategoriaViewHolder> {

    private final OnCategoriaClickListener listener;

    public interface OnCategoriaClickListener {
        void onCategoriaClick(CategoriaDto categoria);
    }

    public CategoriaAdapter(OnCategoriaClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<CategoriaDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<CategoriaDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategoriaDto oldItem, @NonNull CategoriaDto newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategoriaDto oldItem, @NonNull CategoriaDto newItem) {
            return Objects.equals(oldItem.getNombre(), newItem.getNombre()) &&
                   Objects.equals(oldItem.getImagenCategoria(), newItem.getImagenCategoria());
        }
    };

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoriaBinding binding = ItemCategoriaBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoriaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoriaBinding binding;

        public CategoriaViewHolder(ItemCategoriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CategoriaDto categoria, OnCategoriaClickListener listener) {
            binding.txtCategoriaNombre.setText(categoria.getNombre());

            String categoryKey = categoria.getNombre();
            String imgData = categoria.getImagenCategoria();

            if (imgData != null && imgData.startsWith("http")) {
                // Es URL remota
                Glide.with(itemView.getContext())
                        .load(imgData)
                        .into(binding.imgCategoriaIcon);
            } else {
                // Usamos el resolver con el nombre de la categoria para consistencia total
                Glide.with(itemView.getContext())
                        .load(ImageUi.getDrawable(categoryKey))
                        .into(binding.imgCategoriaIcon);
            }

            itemView.setOnClickListener(v -> listener.onCategoriaClick(categoria));
        }
    }
}
