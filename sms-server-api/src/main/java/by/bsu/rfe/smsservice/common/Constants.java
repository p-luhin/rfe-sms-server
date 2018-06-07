package by.bsu.rfe.smsservice.common;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public interface Constants {
  String GENERATED_GROUP_NAME_PREFIX = "GROUP_";
  String GENERATED_NAME = "GENERATED";
  String EXAMPLE_EMAIL_POSTFIX = "@example.com";

  String BULK_SMS_TYPE = "BULK";
  String CUSTOM_SMS_TYPE = "CUSTOM";

  String REGISTER_USER_SMS_TYPE = "RegisterUserSMS";

  String PROFILE_LOCAL = "local";
  String PROFILE_PROD = "prod";

  String ROLE_USER = "ROLE_USER";
  String ROLE_APPLICATION = "ROLE_APPLICATION";

  GrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority(ROLE_USER);
  GrantedAuthority APPLICATION_AUTHORITY = new SimpleGrantedAuthority(ROLE_APPLICATION);
}
