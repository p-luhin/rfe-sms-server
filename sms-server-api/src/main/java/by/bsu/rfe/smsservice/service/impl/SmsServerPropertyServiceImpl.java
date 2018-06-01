package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.entity.SmsServerPropertyEntity;
import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;
import by.bsu.rfe.smsservice.repository.SmsServerPropertyRepository;
import by.bsu.rfe.smsservice.service.SmsServerPropertyService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pluhin on 1/4/17.
 */
@Service
public class SmsServerPropertyServiceImpl implements SmsServerPropertyService {

  @Autowired
  private SmsServerPropertyRepository smsServerPropertyRepository;

  @Override
  public String findPropertyValue(SmsServerProperty smsServerProperty) {
    return returnPropertyValue(smsServerPropertyRepository.findOne(smsServerProperty));
  }

  @Override
  public Map<String, Map<String, String>> getAllProperties() {
    List<SmsServerPropertyEntity> properties = smsServerPropertyRepository.findAll();

    return propertyListToMap(properties);
  }

  @Override
  public Map<String, String> findGroupProperties(
      SmsServerProperty.SmsServerPropertyGroup propertyGroup) {
    return propertyListToMap(smsServerPropertyRepository.findPropertiesByGroup(propertyGroup))
        .get(propertyGroup.getDisplayValue());
  }

  @Override
  public void saveAllProperties(Map<String, Map<String, String>> properties) {
    List<SmsServerPropertyEntity> propertyEntities = new ArrayList<>();

    for (Map.Entry<String, Map<String, String>> propertyGroup : properties.entrySet()) {
      for (Map.Entry<String, String> property : propertyGroup.getValue().entrySet()) {
        propertyEntities.add(
            new SmsServerPropertyEntity(
                SmsServerProperty.getByDisplayName(property.getKey()),
                SmsServerProperty.SmsServerPropertyGroup.getByDisplayName(propertyGroup.getKey()),
                property.getValue()));
      }
    }

    smsServerPropertyRepository.save(propertyEntities);
  }

  private String returnPropertyValue(SmsServerPropertyEntity smsServerPropertyEntity) {
    return smsServerPropertyEntity.getValue();
  }

  private Map<String, Map<String, String>> propertyListToMap(
      List<SmsServerPropertyEntity> properties) {
    Map<String, Map<String, String>> propertiesMap = new HashMap<>();

    for (SmsServerPropertyEntity propertyEntity : properties) {
      if (!propertiesMap.containsKey(propertyEntity.getPropertyGroup().getDisplayValue())) {
        propertiesMap
            .put(propertyEntity.getPropertyGroup().getDisplayValue(), new LinkedHashMap<>());
      }

      propertiesMap.get(propertyEntity.getPropertyGroup().getDisplayValue())
          .put(propertyEntity.getPropertyKey().getDisplayValue(), propertyEntity.getValue());
    }

    return propertiesMap;
  }
}
