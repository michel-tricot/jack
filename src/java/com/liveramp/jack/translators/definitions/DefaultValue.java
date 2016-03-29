package com.liveramp.jack.translators.definitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultValue {
  private static Logger LOG = LoggerFactory.getLogger(DefaultValue.class);
  private final String defaultValue;

  public DefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  @Override
  public String toString() {
    return "DefaultValue{" +
        "defaultValue='" + defaultValue + '\'' +
        '}';
  }
}
