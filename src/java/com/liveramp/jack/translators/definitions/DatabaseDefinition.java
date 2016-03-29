package com.liveramp.jack.translators.definitions;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class DatabaseDefinition {

  private final String name;
  private final List<TableDefinition> tableDefinitions;

  public DatabaseDefinition(String name) {
    this.name = name;
    this.tableDefinitions = Lists.newArrayList();
  }

  public String getName() {
    return name;
  }

  public void addTableDefinition(TableDefinition tableDefinition) {
    tableDefinitions.add(tableDefinition);
  }

  public List<TableDefinition> getTableDefinitions() {
    return Collections.unmodifiableList(tableDefinitions);
  }

  @Override
  public String toString() {
    return "DatabaseRepr{" +
        "name='" + name + '\'' +
        ", tableDefinitions=" + tableDefinitions +
        '}';
  }
}
