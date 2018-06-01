package by.bsu.rfe.smsservice.common.enums;

public enum SmsParams {
  SERVER_URL("${SERVER.URL}"),
  EMAIL("${EMAIL}"),
  USERNAME("${USERNAME}"),
  PASSWORD("${PASSWORD}"),
  FIRST_NAME("${FIRST_NAME}"),
  LAST_NAME("${LAST_NAME}"),
  PHONE_NUMBER("${PHONE_NUMBER}"),
  GROUP_NAME("${GROUP_NAME}");

  private String key;

  SmsParams(String key) {
    this.key = key;
  }

  /**
   * Gets key.
   *
   * @return key
   */
  public String getKey() {
    return key;
  }
}
