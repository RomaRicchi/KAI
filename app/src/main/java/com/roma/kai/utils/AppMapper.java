package com.roma.kai.utils;

import java.util.HashMap;
import java.util.Map;

public class AppMapper {

    private static final Map<String, String> CATEGORY_ATTRIBUTE_MAP = new HashMap<>();
    private static final Map<Integer, String> STATE_ENERGY_MAP = new HashMap<>();

    static {
        // Categoria por atributo
        CATEGORY_ATTRIBUTE_MAP.put("vitalidad", "Vitalidad");
        CATEGORY_ATTRIBUTE_MAP.put("fuerza", "Movimiento");
        CATEGORY_ATTRIBUTE_MAP.put("resistencia", "Coraje");
        CATEGORY_ATTRIBUTE_MAP.put("disciplina", "Disciplina");
        CATEGORY_ATTRIBUTE_MAP.put("sabiduria", "Sabiduría");
        CATEGORY_ATTRIBUTE_MAP.put("equilibrio", "Equilibrio");
        CATEGORY_ATTRIBUTE_MAP.put("conciencia", "Conexión");
        CATEGORY_ATTRIBUTE_MAP.put("vinculo", "Constancia");

        // Estado por energia
        STATE_ENERGY_MAP.put(20, "Muy baja");
        STATE_ENERGY_MAP.put(40, "Baja");
        STATE_ENERGY_MAP.put(60, "Normal");
        STATE_ENERGY_MAP.put(80, "Alta");
        STATE_ENERGY_MAP.put(100, "Excelente");
    }


    public static String getCategoryByAttribute(String attribute) {
        return CATEGORY_ATTRIBUTE_MAP.get(normalizeString(attribute));
    }

    public static String getEstadoByEnergy(int energia) {
        //desarrollar
        return  "Excelent";
    }

    public static String normalizeString(String text) {
        return text.toLowerCase()
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u");
    }
}
