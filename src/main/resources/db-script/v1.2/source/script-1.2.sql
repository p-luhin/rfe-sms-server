CREATE TABLE IF NOT EXISTS `sms_queue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recipient` VARCHAR(255) NOT NULL ,
  `recipient_type` ENUM('PERSON', 'NUMBER', 'GROUP') NOT NULL ,
  `message` VARCHAR(255) NOT NULL ,
  `sms_type_id` INT(11) NOT NULL ,
  `credentials_id` INT(11) NOT NULL ,
  `created_by` VARCHAR(255) NOT NULL ,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sms_type_fk` (`sms_type_id`),
  KEY `creds_fk` (`credentials_id`),
  CONSTRAINT `sms_type_fk` FOREIGN KEY (`sms_type_id`) REFERENCES `sms_template` (`id`) ON DELETE CASCADE,
  CONSTRAINT `creds_fk` FOREIGN KEY (`credentials_id`) REFERENCES `credentials` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `sms_server_property` (
  `property_key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NOT NULL,
  UNIQUE KEY `key` (`property_key`)
);

INSERT INTO `sms_server_property` (`property_key`, `value`) VALUES ('MUTE_ENABLED', 'false');
INSERT INTO `sms_server_property` (`property_key`, `value`) VALUES ('MUTE_START_TIME', '00:00');
INSERT INTO `sms_server_property` (`property_key`, `value`) VALUES ('MUTE_END_TIME', '00:00');