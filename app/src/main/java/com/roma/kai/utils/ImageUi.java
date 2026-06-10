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
        resourceMap.put("atento", R.drawable.kai1);
        resourceMap.put("curioso", R.drawable.kai1);
        resourceMap.put("divertido", R.drawable.kai1);
        resourceMap.put("jugueton", R.drawable.kai1);

        // Claves específicas para la animación de AnimationKai
        resourceMap.put("kai_base", R.drawable.kai1);
        resourceMap.put("kai_ojos_cerrados", R.drawable.cerrado_frente);
        resourceMap.put("kai_boca_abierta", R.drawable.boca_frente);

        // --- Animación #2: Kai y la Luciérnaga ---
        resourceMap.put("anim1", R.drawable.anim1);
        resourceMap.put("anim2", R.drawable.anim2);
        resourceMap.put("anim3", R.drawable.anim3);
        resourceMap.put("anim4", R.drawable.anim4);
        resourceMap.put("anim5", R.drawable.anim5);
        resourceMap.put("anim6", R.drawable.anim6);
        resourceMap.put("anim7", R.drawable.anim7);
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
