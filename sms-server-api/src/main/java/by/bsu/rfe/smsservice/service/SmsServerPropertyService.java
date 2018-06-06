package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;
import java.util.Map;

/**
 * Created by pluhin on 1/4/17.
 */
public interface SmsServerPropertyService {

  String findPropertyValue(SmsServerProperty smsServerProperty);

  Map<String, Map<String, String>> getAllProperties();

  Map<String, String> findGroupProperties(SmsServerProperty.SmsServerPropertyGroup propertyGroup);

  void saveAllProperties(Map<String, Map<String, String>> properties);
}
