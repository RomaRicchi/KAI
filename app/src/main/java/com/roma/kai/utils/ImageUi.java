package com.roma.kai.utils;

import com.roma.kai.R;
import java.util.HashMap;
import java.util.Map;

public class ImageUi {
    private static final Map<String, Integer> resourceMap = new HashMap<>();

    static {
        // Mapeo de Categorías
        resourceMap.put("vitalidad", R.drawable.categoria_vitalidad); // Swap: Vitalidad usa el runner
        resourceMap.put("sabiduria", R.drawable.categoria_sabiduria); // Swap: Sabiduría usa el libro
        resourceMap.put("coraje", R.drawable.categoria_coraje);
        resourceMap.put("disciplina", R.drawable.categoria_disciplina);
        resourceMap.put("constancia", R.drawable.categoria_constancia);
        resourceMap.put("equilibrio", R.drawable.categoria_equilibrio);
        resourceMap.put("movimiento", R.drawable.categoria_movimiento);
        resourceMap.put("conexion", R.drawable.categoria_conexion);

        // Mapeo de Dificultades
        resourceMap.put("facil", R.drawable.nivel_facil);
        resourceMap.put("medio", R.drawable.nivel_medio);
        resourceMap.put("dificil", R.drawable.nivel_dificil);
        
        // Mapeo de Estados de Kai (Usando versiones frontales para evitar saltos)
        resourceMap.put("kai_bebe_dormido", R.drawable.kai_bebe_dormido_1);
        resourceMap.put("kai_bebe_enojado", R.drawable.kai_bebe_enojado_1);
        resourceMap.put("kai_bebe_feliz", R.drawable.kai_bebe_feliz_1);
        resourceMap.put("kai_bebe_atento", R.drawable.kai_bebe_feliz_1);
        resourceMap.put("kai_bebe_curioso", R.drawable.kai_bebe_curioso_7);
        resourceMap.put("kai_bebe_divertido", R.drawable.kai_bebe_feliz_1);
        resourceMap.put("kai_bebe_jugueton", R.drawable.kai_bebe_feliz_1);

        resourceMap.put("firefly_a", R.drawable.firefly_a);
        resourceMap.put("firefly_b", R.drawable.firefly_b);
    }

    /**
     * Resuelve un recurso drawable a partir de una clave descriptiva.
     * @param key Clave descriptiva (ej: "vitalidad", "facil", "dormido")
     * @return ID del recurso drawable o icono por defecto si no se encuentra.
     */
    public static int getDrawable(String key) {
        if (key == null || key.isEmpty()) return R.drawable.ic_gallery_black_24dp;
        
        // Normalización de la clave
        String cleanKey = key.toLowerCase().trim();
        
        // Quitar extensión si existe (.webp, .png, etc)
        if (cleanKey.contains(".")) {
            cleanKey = cleanKey.substring(0, cleanKey.lastIndexOf('.'));
        }
        
        // Normalizar tildes (ej: conexión -> conexion)
        cleanKey = java.text.Normalizer.normalize(cleanKey, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        Integer resId = resourceMap.get(cleanKey);
        
        // Fallback: si no está en el mapa, devolvemos un icono genérico
        return (resId != null) ? resId : R.drawable.ic_gallery_black_24dp;
    }
}
