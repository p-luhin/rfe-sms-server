DROP TABLE `applications_credentials`;

ALTER TABLE `external_application` MODIFY `default_credentials` INT(11) NULL;
ALTER TABLE `external_application` ADD CONSTRAINT `app_default_creds` FOREIGN KEY (default_credentials) REFERENCES credentials(id) ON DELETE SET NULL;