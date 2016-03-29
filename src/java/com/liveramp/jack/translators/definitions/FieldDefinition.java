package com.liveramp.jack.translators.definitions;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import com.google.common.base.Optional;

public class FieldDefinition {

  private final String name;
  private final Type type;
  private final Set<Attribute> attributes;
  private Optional<DefaultValue> defaultValue;

  public FieldDefinition(String name, Type type) {
    this.name = name;
    this.type = type;
    this.attributes = EnumSet.noneOf(Attribute.class);
    this.defaultValue = Optional.absent();
  }

  public String getName() {
    return name;
  }

  public Type getType() {
    return type;
  }

  public void setDefaultValue(DefaultValue defaultValue) {
    this.defaultValue = Optional.fromNullable(defaultValue);
  }

  public DefaultValue getDefaultValue() {
    return defaultValue.orNull();
  }

  public void addAttribute(Attribute attribute) {
    attributes.add(attribute);
  }

  public Set<Attribute> getAttributes() {
    return Collections.unmodifiableSet(attributes);
  }

  @Override
  public String toString() {
    return "FieldDefinition{" +
        "name='" + name + '\'' +
        ", type=" + type +
        ", defaultValue=" + defaultValue +
        ", attributes=" + attributes +
        '}';
  }
}
