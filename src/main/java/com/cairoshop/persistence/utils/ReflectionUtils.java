package com.cairoshop.persistence.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Component
public class ReflectionUtils {

    public void getFields(List<String> fields, Class<?> current) {
        if (current.equals(Object.class)) return;

        fields.addAll(Stream.of(current.getDeclaredFields())
                            .map(Field::getName)
                            .collect(Collectors.toList()));
        getFields(fields, current.getSuperclass());
    }

}
