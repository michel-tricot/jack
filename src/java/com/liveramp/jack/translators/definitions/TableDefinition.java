package com.liveramp.jack.translators.definitions;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class TableDefinition {

  private final String name;
  private final List<FieldDefinition> fieldDefinitions;

  public TableDefinition(String name) {
    this.name = name;
    this.fieldDefinitions = Lists.newArrayList();
  }

  public String getName() {
    return name;
  }

  public void addFieldDefinition(FieldDefinition fieldDefinition) {
    fieldDefinitions.add(fieldDefinition);
  }

  public List<FieldDefinition> getFieldDefinitions() {
    return Collections.unmodifiableList(fieldDefinitions);
  }

  @Override
  public String toString() {
    return "TableDefinition{" +
        "name='" + name + '\'' +
        ", fieldDefinitions=" + fieldDefinitions +
        '}';
  }
}
