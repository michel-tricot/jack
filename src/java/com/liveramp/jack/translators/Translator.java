package com.liveramp.jack.translators;

import com.liveramp.jack.translators.definitions.DatabaseDefinition;

public interface Translator {

  class Exception extends java.lang.Exception {
    public Exception(String message) {
      super(message);
    }

    public Exception(String message, Throwable cause) {
      super(message, cause);
    }
  }

  DatabaseDefinition translate(String name) throws Exception;

}
