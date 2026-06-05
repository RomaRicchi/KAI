package com.roma.kai.utils;

import com.roma.kai.R;
import java.util.HashMap;
import java.util.Map;

public class ImageUi {
    private static final Map<String, Integer> resourceMap = new HashMap<>();

    static {
        // Mapeo de Categorías
        resourceMap.put("vitalidad", R.drawable.vitalidad); // Swap: Vitalidad usa el runner
        resourceMap.put("sabiduria", R.drawable.sabiduria); // Swap: Sabiduría usa el libro
        resourceMap.put("coraje", R.drawable.coraje);
        resourceMap.put("disciplina", R.drawable.disciplina);
        resourceMap.put("constancia", R.drawable.constancia);
        resourceMap.put("equilibrio", R.drawable.equilibrio);
        resourceMap.put("movimiento", R.drawable.movimiento);
        resourceMap.put("conexion", R.drawable.conexion);

        // Mapeo de Dificultades
        resourceMap.put("facil", R.drawable.facil);
        resourceMap.put("medio", R.drawable.medio);
        resourceMap.put("dificil", R.drawable.dificil);
        
        // Mapeo de Estados de Kai (Usando versiones frontales para evitar saltos)
        resourceMap.put("dormido", R.drawable.bb_dormido);
        resourceMap.put("enojado", R.drawable.bb_enojado);
        resourceMap.put("feliz", R.drawable.kai1);
        resourceMap.put("atento", R.drawable.cerrado_frente);
        resourceMap.put("curioso", R.drawable.cerrado_frente);
        resourceMap.put("divertido", R.drawable.kai1);
        resourceMap.put("jugueton", R.drawable.kai1);
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
