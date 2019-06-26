package com.ag.studies;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class AlreadyExistsException extends Exception {

    public AlreadyExistsException(String fieldName, Class clazz, Object existingEntity) {
        super(AlreadyExistsException.generateMessage(fieldName, clazz.getSimpleName(), existingEntity));
    }

    private static String generateMessage(String fieldname, String entity, Object existingEntity) {
        return "Record with " + fieldname + ": " + existingEntity +
                " already exists in " +
                StringUtils.capitalize(entity) + ". Please enter a different " + fieldname + ".";
    }

    private static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }

}