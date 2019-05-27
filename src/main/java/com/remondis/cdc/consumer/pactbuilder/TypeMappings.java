package com.remondis.cdc.consumer.pactbuilder;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Map;

import com.remondis.cdc.consumer.pactbuilder.types.BooleanMapping;
import com.remondis.cdc.consumer.pactbuilder.types.DecimalMapping;
import com.remondis.cdc.consumer.pactbuilder.types.IntegerMapping;
import com.remondis.cdc.consumer.pactbuilder.types.NumberMapping;
import com.remondis.cdc.consumer.pactbuilder.types.StringMapping;

/**
 * This is the is of default out-of-the-box type mappings.
 */
class TypeMappings {

  private static final Map<Class<?>, PactDslModifier<?>> defaultTypeMappings = new Hashtable<>();

  static {
    NumberMapping numberMapping = new NumberMapping();
    DecimalMapping decimalMapping = new DecimalMapping();
    StringMapping stringMapping = new StringMapping();
    BooleanMapping booleanMapping = new BooleanMapping();
    @SuppressWarnings("rawtypes")
    IntegerMapping integerMapping = new IntegerMapping();

    defaultTypeMappings.put(String.class, stringMapping);

    defaultTypeMappings.put(byte.class, numberMapping);
    defaultTypeMappings.put(short.class, numberMapping);
    defaultTypeMappings.put(int.class, integerMapping);
    defaultTypeMappings.put(long.class, integerMapping);
    defaultTypeMappings.put(float.class, numberMapping);
    defaultTypeMappings.put(double.class, decimalMapping);
    defaultTypeMappings.put(boolean.class, booleanMapping);

    defaultTypeMappings.put(Byte.class, numberMapping);
    defaultTypeMappings.put(Short.class, numberMapping);
    defaultTypeMappings.put(Integer.class, integerMapping);
    defaultTypeMappings.put(Long.class, integerMapping);
    defaultTypeMappings.put(Float.class, numberMapping);
    defaultTypeMappings.put(Double.class, decimalMapping);
    defaultTypeMappings.put(Boolean.class, booleanMapping);

    defaultTypeMappings.put(BigDecimal.class, decimalMapping);
  }

  public static Map<Class<?>, PactDslModifier<?>> getDatatypeMappings() {
    return new Hashtable<>(defaultTypeMappings);
  }

}
