package com.liveramp.jack.generator;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.resolver.ClasspathResolver;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liveramp.jack.translators.definitions.DatabaseDefinition;

public class ModelGenerator {
  private static Logger LOG = LoggerFactory.getLogger(ModelGenerator.class);

  public static class Options {
    private String packageName;

    public Options() {
      this.packageName = "com.liveramp.jack.generated";
    }

    public Options setPackageName(String packageName) {
      this.packageName = packageName;
      return this;
    }
  }

  public ModelGenerator(Options options) {
  }

  public void generate(DatabaseDefinition databaseDefinition) {

  }

  public static void main(String[] args) throws IOException {
    System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("/templates/test.ms"));
    System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/test.ms"));
    System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/liveramp/jack/generator/templates/test.ms"));
    System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("/com/liveramp/jack/generator/templates/test.ms"));
    System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("java/com/liveramp/jack/generator/templates/test.ms"));
    System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("/java/com/liveramp/jack/generator/templates/test.ms"));

    DefaultMustacheFactory factory = new DefaultMustacheFactory(new ClasspathResolver("java/com/liveramp/jack/generator/templates"));
    //MustacheFactory factory = new DefaultMustacheFactory(new ClasspathResolver());
    Mustache template = factory.compile("model.mustache");
    Writer writer = template.execute(new PrintWriter(System.out), ImmutableMap.of("toto", "you"));
    writer.flush();
  }
}
