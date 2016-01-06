/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */

package com.pivotal.gemfirexd.thrift;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum ServiceFeatureParameterized implements org.apache.thrift.TEnum {
  TRANSACTIONS_SUPPORT_ISOLATION(1),
  RESULTSET_TYPE(2),
  RESULTSET_CONCURRENCY_READ_ONLY(3),
  RESULTSET_CONCURRENCY_UPDATABLE(4),
  RESULTSET_OWN_UPDATES_VISIBLE(5),
  RESULTSET_OWN_DELETES_VISIBLE(6),
  RESULTSET_OWN_INSERTS_VISIBLE(7),
  RESULTSET_OTHERS_UPDATES_VISIBLE(8),
  RESULTSET_OTHERS_DELETES_VISIBLE(9),
  RESULTSET_OTHERS_INSERTS_VISIBLE(10),
  RESULTSET_UPDATES_DETECTED(11),
  RESULTSET_DELETES_DETECTED(12),
  RESULTSET_INSERTS_DETECTED(13);

  private final int value;

  private ServiceFeatureParameterized(int value) {
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
  public static ServiceFeatureParameterized findByValue(int value) { 
    switch (value) {
      case 1:
        return TRANSACTIONS_SUPPORT_ISOLATION;
      case 2:
        return RESULTSET_TYPE;
      case 3:
        return RESULTSET_CONCURRENCY_READ_ONLY;
      case 4:
        return RESULTSET_CONCURRENCY_UPDATABLE;
      case 5:
        return RESULTSET_OWN_UPDATES_VISIBLE;
      case 6:
        return RESULTSET_OWN_DELETES_VISIBLE;
      case 7:
        return RESULTSET_OWN_INSERTS_VISIBLE;
      case 8:
        return RESULTSET_OTHERS_UPDATES_VISIBLE;
      case 9:
        return RESULTSET_OTHERS_DELETES_VISIBLE;
      case 10:
        return RESULTSET_OTHERS_INSERTS_VISIBLE;
      case 11:
        return RESULTSET_UPDATES_DETECTED;
      case 12:
        return RESULTSET_DELETES_DETECTED;
      case 13:
        return RESULTSET_INSERTS_DETECTED;
      default:
        return null;
    }
  }
}