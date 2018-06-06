ALTER TABLE `sms_server_property`
    ADD COLUMN `property_group` VARCHAR(255) NOT NULL DEFAULT 'UNASSIGNED';

UPDATE `sms_server_property`
  SET `property_group` = 'MUTE_MODE' WHERE property_key = 'MUTE_ENABLED';
UPDATE `sms_server_property`
  SET `property_group` = 'MUTE_MODE' WHERE property_key = 'MUTE_END_TIME';
UPDATE `sms_server_property`
  SET `property_group` = 'MUTE_MODE' WHERE property_key = 'MUTE_START_TIME';