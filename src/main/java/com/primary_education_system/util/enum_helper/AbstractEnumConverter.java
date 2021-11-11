package com.primary_education_system.util.enum_helper;

import javax.persistence.AttributeConverter;


public abstract class AbstractEnumConverter<EnumClazz extends Enum<EnumClazz> & ValueEnumInterface<TypeData>, TypeData>
        implements AttributeConverter<EnumClazz, TypeData> {

    private final Class<EnumClazz> enumCLass;

    public AbstractEnumConverter(Class<EnumClazz> enumCLass) {
        this.enumCLass = enumCLass;
    }

    @Override
    public TypeData convertToDatabaseColumn(EnumClazz attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public EnumClazz convertToEntityAttribute(TypeData value) {
        EnumClazz[] enums = enumCLass.getEnumConstants();
        for (EnumClazz type : enums) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value enum " + enumCLass.getName() + " with value " + value );
    }
}
