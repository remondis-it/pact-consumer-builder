package com.remondis.cdc.consumer.pactbuilder;

import static com.remondis.cdc.consumer.pactbuilder.ReflectionException.denyMultipleInteractions;
import static com.remondis.cdc.consumer.pactbuilder.ReflectionException.notAProperty;
import static com.remondis.cdc.consumer.pactbuilder.ReflectionException.zeroInteractions;
import static com.remondis.cdc.consumer.pactbuilder.ReflectionUtil.defaultValue;
import static com.remondis.cdc.consumer.pactbuilder.ReflectionUtil.hasReturnType;
import static com.remondis.cdc.consumer.pactbuilder.ReflectionUtil.invokeMethodProxySafe;
import static com.remondis.cdc.consumer.pactbuilder.ReflectionUtil.isGetter;
import static com.remondis.cdc.consumer.pactbuilder.ReflectionUtil.toPropertyName;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

/**
 * The {@link InvocationSensor} tracks get-method invocations on a proxy class and makes the invocation information
 * available to the {@link Mapper}.
 *
 * @author schuettec
 */
class InvocationSensor<T> {

  private T proxyObject;

  private List<String> propertyNames = new LinkedList<>();

  private Class<T> type;

  InvocationSensor(Class<T> superType) {
    this.type = superType;
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(superType);
    enhancer.setCallback(new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isGetter(method)) {
          denyNoReturnType(method);
          // schuettec - Get property name from method and mark this property as called.
          String propertyName = toPropertyName(method);
          propertyNames.add(propertyName);
          // schuettec - Then return an appropriate default value.
          return nullOrDefaultValue(method.getReturnType());
        } else if (isObjectMethod(method)) {
          // schuettec - 08.02.2017 : Methods like toString, equals or hashcode are redirected to this invocation
          // handler.
          return invokeMethodProxySafe(method, this, args);
        } else {
          throw ReflectionException.notAGetter(method);
        }
      }

    });
    proxyObject = superType.cast(enhancer.create());
  }

  /**
   * Returns the proxy object get-method calls can be performed on.
   *
   * @return The proxy.
   */
  T getSensor() {
    return proxyObject;
  }

  /**
   *
   * Expects a single interaction with a property.
   * 
   * @return Returns the {@link PropertyDescriptor} selected by the
   *         {@link InvocationSensor}.
   */
  public PropertyDescriptor getProperty() {
    if (hasTrackedProperties()) {
      List<String> trackedPropertyNames = getTrackedPropertyNames();
      denyMultipleInteractions(trackedPropertyNames);
      // get the property name
      String propertyName = trackedPropertyNames.get(0);
      // find the property descriptor or fail with an exception
      return getPropertyDescriptorOrFail(type, propertyName);
    } else {
      throw zeroInteractions(type);
    }
  }

  /**
   * Ensures that the specified property name is a property in the specified {@link Set} of {@link
   * PropertyDescriptor}s.
   *
   * @param target Defines if the properties are validated against source or target rules.
   * @param type
   *        The inspected type.
   * @param propertyName
   *        The property name
   */
  static PropertyDescriptor getPropertyDescriptorOrFail(Class<?> type, String propertyName) {
    Optional<PropertyDescriptor> property;
    property = Properties.getProperties(type)
        .stream()
        .filter(pd -> pd.getName()
            .equals(propertyName))
        .findFirst();
    if (property.isPresent()) {
      return property.get();
    } else {
      throw notAProperty(type, propertyName);
    }

  }

  /**
   * Returns the list of property names that were tracked by get calls.
   *
   * @return Returns the tracked property names.
   */
  List<String> getTrackedPropertyNames() {
    return Collections.unmodifiableList(propertyNames);
  }

  /**
   * Checks if there were any properties accessed by get calls.
   *
   * @return Returns <code>true</code> if there were at least one interaction with a property. Otherwise
   *         <code>false</code> is returned.
   */
  boolean hasTrackedProperties() {
    return !propertyNames.isEmpty();
  }

  /**
   * Resets all tracked information.
   */
  void reset() {
    propertyNames.clear();
  }

  private void denyNoReturnType(Method method) {
    if (!hasReturnType(method)) {
      throw ReflectionException.noReturnTypeOnGetter(method);
    }
  }

  private static Object nullOrDefaultValue(Class<?> returnType) {
    if (returnType.isPrimitive()) {
      return defaultValue(returnType);
    } else {
      return null;
    }
  }

  private static boolean isObjectMethod(Method method) {
    return method.getDeclaringClass() == Object.class;
  }

}
