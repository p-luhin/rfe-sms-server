UPDATE `sms_template`
SET
  enabled = FALSE
WHERE
  `sms_type` = 'CustomSMS';