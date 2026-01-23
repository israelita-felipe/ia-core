package com.ia.core.security.model.functionality;

public enum OperationEnum implements Operation {
  CREATE {
    @Override
    public String value() {
      return CREATE_VALUE;
    }
  },
  DELETE {
    @Override
    public String value() {
      return DELETE_VALUE;
    }
  },
  READ {
    @Override
    public String value() {
      return READ_VALUE;
    }
  },
  UPDATE {
    @Override
    public String value() {
      return UPDATE_VALUE;
    }
  };

  public static final String CREATE_VALUE = "CREATE";
  public static final String DELETE_VALUE = "DELETE";
  public static final String READ_VALUE = "READ";
  public static final String UPDATE_VALUE = "UPDATE";
  public static final String OTHER_VALUE = "OTHER";

  @Override
  public String toString() {
    return value();
  }

}
