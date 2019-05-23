package com.remondis.cdc.consumer.pactbuilder;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Util class to get a list of all properties of a class.
 *
 * @author schuettec
 */
class Properties {

  /**
   * Returns a {@link Set} of properties with read and write access.
   *
   * @param inspectType
   *        The type to inspect.
   * @return Returns the list of {@link PropertyDescriptor}s that grant read and write access.
   * @throws ReflectionException
   *         Thrown on any introspection error.
   */
  static Set<PropertyDescriptor> getProperties(Class<?> inspectType) {
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(inspectType);
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      return new HashSet<>(Arrays.asList(propertyDescriptors)
          .stream()
          .filter(pd -> !pd.getName()
              .equals("class"))
          .collect(Collectors.toList()));
    } catch (IntrospectionException e) {
      throw new RuntimeException(String.format("Cannot introspect the type %s.", inspectType.getName()));
    }
  }

  /**
   * A readable string representation for a {@link PropertyDescriptor}.
   *
   * @param pd
   *        The pd
   * @return Returns a readable string.
   */
  static String asStringWithType(PropertyDescriptor pd) {
    return asStringWithType(pd, false);
  }

  /**
   * A readable string representation for a {@link PropertyDescriptor}.
   *
   * @param pd
   *        The pd
   * @param detailed
   *        If <code>false</code> simple names should be used. If
   *        <code>true</code> fully qualified names should be used.
   * @return Returns a readable string.
   */
  static String asStringWithType(PropertyDescriptor pd, boolean detailed) {
    Class<?> clazz = Properties.getPropertyClass(pd);
    return String.format("Property '%s' (%s) in %s", pd.getName(), pd.getPropertyType()
        .getName(), (detailed ? clazz.getName() : clazz.getSimpleName()));
  }

  /**
   * A readable string representation for a {@link PropertyDescriptor}.
   *
   * @param pd
   *        The pd
   * @return Returns a readable string.
   */
  static String asString(PropertyDescriptor pd) {
    return asString(pd, false);
  }

  /**
   * A readable string representation for a {@link PropertyDescriptor}.
   *
   * @param pd
   *        The pd
   * @param detailed
   *        If <code>false</code> simple names should be used. If
   *        <code>true</code> fully qualified names should be used.
   * @return Returns a readable string.
   */
  static String asString(PropertyDescriptor pd, boolean detailed) {
    Class<?> clazz = Properties.getPropertyClass(pd);
    return String.format("Property '%s' in %s", pd.getName(), (detailed ? clazz.getName() : clazz.getSimpleName()));
  }

  /**
   * Returns the class declaring the property.
   *
   * @param propertyDescriptor the {@link PropertyDescriptor}
   * @return Returns the declaring class.
   */
  static Class<?> getPropertyClass(PropertyDescriptor propertyDescriptor) {
    return propertyDescriptor.getReadMethod()
        .getDeclaringClass();
  }
}
