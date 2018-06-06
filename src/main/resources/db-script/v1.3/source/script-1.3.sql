ALTER TABLE person ADD COLUMN temporary BOOLEAN DEFAULT FALSE;

ALTER TABLE recipient_group ADD COLUMN temporary BOOLEAN DEFAULT FALSE;

ALTER TABLE sms_template DROP COLUMN uri_path;

ALTER TABLE sms_queue ADD COLUMN duplicate_email BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE sms_queue ADD COLUMN initiated_by VARCHAR(255) NOT NULL;

ALTER TABLE sms_queue ADD COLUMN parameters_json BLOB;

ALTER TABLE sms_queue DROP FOREIGN KEY sms_type_fk;
ALTER TABLE sms_queue DROP COLUMN sms_type_id ;
ALTER TABLE sms_queue ADD COLUMN sms_type VARCHAR(255);

ALTER TABLE person DROP INDEX phone_number;
ALTER TABLE person DROP INDEX email;