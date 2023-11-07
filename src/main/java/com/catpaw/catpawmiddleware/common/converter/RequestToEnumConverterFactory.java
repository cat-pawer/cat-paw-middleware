package com.catpaw.catpawmiddleware.common.converter;

import com.catpaw.catpawmiddleware.controller.request.enums.BaseEnumRequest;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Arrays;

public class RequestToEnumConverterFactory implements ConverterFactory<String, Enum<? extends BaseEnumRequest<?>>> {

    @NonNull
    @Override
    public <T extends Enum<? extends BaseEnumRequest<?>>> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
        return new StringToEnumsConverter<>(targetType);
    }

    private static final class StringToEnumsConverter<T extends Enum<? extends BaseEnumRequest<?>>> implements Converter<String, T> {

        private final Class<T> enumType;
        private final boolean baseEnum;

        public StringToEnumsConverter(Class<T> enumType) {
            this.enumType = enumType;
            this.baseEnum = Arrays.stream(enumType.getInterfaces()).anyMatch(inter -> inter == BaseEnumRequest.class);
        }

        @Override
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }

            T[] enumConstants = enumType.getEnumConstants();
            for (T c : enumConstants) {
                if (baseEnum) {
                    if (((BaseEnumRequest<?>) c).getCode().equals(source.trim())) {
                        return c;
                    }
                }
                else {
                    if (c.name().equals(source.trim())) {
                        return c;
                    }
                }
            }
            return null;
        }
    }

}
