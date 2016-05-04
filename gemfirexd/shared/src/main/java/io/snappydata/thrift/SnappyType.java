/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package io.snappydata.thrift;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum SnappyType implements org.apache.thrift.TEnum {
  BOOLEAN(1),
  TINYINT(2),
  SMALLINT(3),
  INTEGER(4),
  BIGINT(5),
  FLOAT(6),
  REAL(7),
  DOUBLE(8),
  DECIMAL(9),
  CHAR(10),
  VARCHAR(11),
  LONGVARCHAR(12),
  DATE(13),
  TIME(14),
  TIMESTAMP(15),
  BINARY(16),
  VARBINARY(17),
  LONGVARBINARY(18),
  BLOB(19),
  CLOB(20),
  SQLXML(21),
  ARRAY(22),
  MAP(23),
  STRUCT(24),
  NULLTYPE(25),
  JSON(26),
  JAVA_OBJECT(27),
  OTHER(28);

  private final int value;

  private SnappyType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static SnappyType findByValue(int value) { 
    switch (value) {
      case 1:
        return BOOLEAN;
      case 2:
        return TINYINT;
      case 3:
        return SMALLINT;
      case 4:
        return INTEGER;
      case 5:
        return BIGINT;
      case 6:
        return FLOAT;
      case 7:
        return REAL;
      case 8:
        return DOUBLE;
      case 9:
        return DECIMAL;
      case 10:
        return CHAR;
      case 11:
        return VARCHAR;
      case 12:
        return LONGVARCHAR;
      case 13:
        return DATE;
      case 14:
        return TIME;
      case 15:
        return TIMESTAMP;
      case 16:
        return BINARY;
      case 17:
        return VARBINARY;
      case 18:
        return LONGVARBINARY;
      case 19:
        return BLOB;
      case 20:
        return CLOB;
      case 21:
        return SQLXML;
      case 22:
        return ARRAY;
      case 23:
        return MAP;
      case 24:
        return STRUCT;
      case 25:
        return NULLTYPE;
      case 26:
        return JSON;
      case 27:
        return JAVA_OBJECT;
      case 28:
        return OTHER;
      default:
        return null;
    }
  }
}