package com.liveramp.jack.translators.definitions;

import org.joda.time.DateTime;

public enum Type {
  INTEGER("int", "Integer", "0"),
  LONG("long", "Long", "0L"),
  DOUBLE("double", "Double", "0.0"),
  STRING("String", "String", "\"\""),
  DATETIME("long", "Long", "0L"),
  DATE("long", "Long", "0L"),
  TIME("long", "Long", "0L"),
  BYTE_ARRAY("byte[]", "byte[]", "new byte[0]"),
  BOOLEAN("boolean", "Boolean", "true");

  private final String requiredType;
  private final String nullableType;
  private final String javaDefault;

  Type(String requiredType, String nullableType, String javaDefault) {
    this.requiredType = requiredType;
    this.nullableType = nullableType;
    this.javaDefault = javaDefault;
  }

  public DefaultValue createDefaultValue(String literalDefaultValue) {
    return new DefaultValue(literalDefaultValue);
  }

  public static void main(String[] args) {
    DateTime dateTime;
  }
}
