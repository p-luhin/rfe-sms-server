ALTER TABLE `sms_template` ADD enabled TINYINT NOT NULL DEFAULT true;

INSERT INTO `sms_template` (sms_type, uri_path, created_by, created_date, enabled) VALUES
  ('RegisterUserSMS', '/rest/sms/registerUser', 'SYSTEM', NOW(), FALSE);

UPDATE `email_template` SET sms_template=LAST_INSERT_ID() WHERE id=2;