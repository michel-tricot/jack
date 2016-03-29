package com.liveramp.jack.translators.rails;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liveramp.jack.translators.Translator;
import com.liveramp.jack.translators.definitions.Attribute;
import com.liveramp.jack.translators.definitions.DatabaseDefinition;
import com.liveramp.jack.translators.definitions.FieldDefinition;
import com.liveramp.jack.translators.definitions.TableDefinition;
import com.liveramp.jack.translators.definitions.Type;

public class RailsProjectTranslator implements Translator {
  private static Logger LOG = LoggerFactory.getLogger(RailsProjectTranslator.class);

  public static final String NULL_ATTRIBUTE = "null";
  public static final String LIMIT_ATTRIBUTE = "limit";
  public static final String DEFAULT_ATTRIBUTE = "default";

  private static final Pattern CREATE_TABLE_EXTRACTOR = Pattern.compile("^create_table \"(\\w+)\".*");
  private static final Pattern COLUMN_EXTRACTOR = Pattern.compile("^\\w+\\.(\\w+) \"(\\w+)\"(?:, (.*))?");
  private static final Map<String, Type> RAILS_TYPE_MAPPING = ImmutableMap.<String, Type>builder().
      put("string", Type.STRING).
      put("text", Type.STRING).
      put("integer", Type.INTEGER).
      put("float", Type.DOUBLE).
      put("decimal", Type.DOUBLE).
      put("datetime", Type.DATETIME).
      put("time", Type.TIME).
      put("date", Type.DATE).
      put("binary", Type.BYTE_ARRAY).
      put("bytes", Type.BYTE_ARRAY).
      put("varbinary", Type.BYTE_ARRAY).
      put("boolean", Type.BOOLEAN).
      build();

  private final File schema;

  public static class Options {
  }

  public RailsProjectTranslator(File schema, Options options) {
    this.schema = schema;
  }

  @Override
  public DatabaseDefinition translate(String name) throws Exception {
    DatabaseDefinition databaseDefinition = new DatabaseDefinition(name);

    try (BufferedReader reader = new BufferedReader(new FileReader(schema))) {
      String line;
      TableDefinition currentTable = null;
      while ((line = sanitizeLine(reader.readLine())) != null) {
        Matcher tableMatcher = CREATE_TABLE_EXTRACTOR.matcher(line);
        if (tableMatcher.find()) {
          currentTable = new TableDefinition(tableMatcher.group(1));
        }

        Matcher columnMatcher = COLUMN_EXTRACTOR.matcher(line);
        if (currentTable != null && columnMatcher.find()) {
          String columnName = columnMatcher.group(2);
          Map<String, String> attributes = parseAttributes(columnMatcher.group(3));
          Type type = parseType(columnMatcher.group(1), attributes.get(LIMIT_ATTRIBUTE));

          FieldDefinition fieldDefinition = new FieldDefinition(columnName, type);
          if (attributes.containsKey(NULL_ATTRIBUTE) && !Boolean.parseBoolean(attributes.get(NULL_ATTRIBUTE))) {
            fieldDefinition.addAttribute(Attribute.NOT_NULLABLE);
          }
          if (attributes.containsKey(DEFAULT_ATTRIBUTE)) {
            fieldDefinition.setDefaultValue(type.createDefaultValue(attributes.get(DEFAULT_ATTRIBUTE)));
          }
          currentTable.addFieldDefinition(fieldDefinition);
        }

        if (line.equals("end")) {
          databaseDefinition.addTableDefinition(currentTable);
        }
      }
    } catch (IOException e) {
      throw new Exception("Error while reading schema.rb", e);
    }
    System.out.println(databaseDefinition);
    return databaseDefinition;
  }

  private static Map<String, String> parseAttributes(String railsAttributes) {
    Map<String, String> results = Maps.newHashMap();
    if (StringUtils.isNotBlank(railsAttributes)) {
      for (String kv : StringUtils.split(railsAttributes, ",")) {
        String[] splitKv = StringUtils.split(kv, "=>");
        results.put(splitKv[0].trim().replace(":", ""), splitKv[1].trim());
      }
    }
    return results;
  }

  private static Type parseType(String railsTypeName, String limitValue) throws Exception {
    Type type = RAILS_TYPE_MAPPING.get(railsTypeName);
    if (type == null) {
      throw new Exception("Unsupported Rails type: " + railsTypeName);
    }
    if (type == Type.INTEGER && StringUtils.isNotBlank(limitValue) && Integer.parseInt(limitValue) > 4) {
      type = Type.LONG;
    }
    return type;
  }

  private static String sanitizeLine(String line) {
    return StringUtils.isBlank(line) ? line : line.trim().replaceAll(" +", " ");
  }

  public static void main(String[] args) throws Exception {
    new RailsProjectTranslator(
        new File("/Users/michel/Documents/Work/db_schemas/databases/rldb/db/schema.rb"),
        new Options()
    ).translate("rldb");
  }

}
