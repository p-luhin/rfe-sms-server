ALTER DATABASE CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `credentials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL UNIQUE,
  `api_key` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  `default_credentials` int(11) DEFAULT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `def_creds_key` (`default_credentials`),
  CONSTRAINT `def_creds_key` FOREIGN KEY (`default_credentials`) REFERENCES `credentials` (`id`) ON DELETE SET NULL
);

INSERT INTO `user` (username, password, created_by, created_date) VALUES ('pavel.luhin@gmail.com', '42d8aa7cde9c78c4757862d84620c335', 'SYSTEM', NOW());

CREATE TABLE `authentication_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expires` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `token_user_fk` (`user_id`),
  CONSTRAINT `token_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `sms_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sms_type` varchar(255) NOT NULL UNIQUE,
  `template` longtext DEFAULT NULL,
  `uri_path` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `sms_template` (sms_type, uri_path, created_by, created_date) VALUES ('CustomSMS', '/rest/sms/custom', 'SYSTEM', NOW());

CREATE TABLE IF NOT EXISTS `email_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `content` longtext DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `sms_template` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `template_fk` (`sms_template`),
  CONSTRAINT `template_fk` FOREIGN KEY (`sms_template`) REFERENCES `sms_template` (`id`) ON DELETE CASCADE
);

INSERT INTO `email_template` (subject, content, created_by, created_date) VALUES ('Your account on SMS-Server was registered',
                                                                                               'Hello ${USERNAME},<br><br>Your account on SMS-Server was successfully registered.<br>
                                                                                               SMS-Server address is: ${SERVER.URL}<br>
                                                                                               User the following credentials to log in:<br>
                                                                                               <b>Username:</b> ${EMAIL},<br><b>Password</b>: ${PASSWORD}.', 'SYSTEM', NOW());

CREATE TABLE IF NOT EXISTS `external_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `application_name` varchar(255) NOT NULL UNIQUE,
  `authentication` varchar(255) NOT NULL,
  `default_credentials` tinyblob,
  PRIMARY KEY (`id`)
);

CREATE TABLE `users_credentials` (
  `credentials_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  KEY `user_user_fk` (`users_id`),
  KEY `user_cred_fk` (`credentials_id`),
  CONSTRAINT `user_cred_fk` FOREIGN KEY (`credentials_id`) REFERENCES `credentials` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_user_fk` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);

CREATE TABLE `applications_credentials` (
  `credentials_id` int(11) NOT NULL,
  `applications_id` int(11) NOT NULL,
  KEY `app_app_fk` (`applications_id`),
  KEY `app_cred_fk` (`credentials_id`),
  CONSTRAINT `app_app_fk` FOREIGN KEY (`applications_id`) REFERENCES `external_application` (`id`) ON DELETE CASCADE ,
  CONSTRAINT `app_cred_fk` FOREIGN KEY (`credentials_id`) REFERENCES `credentials` (`id`) ON DELETE CASCADE
) ;

CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `recipient_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `persons_have_groups` (
  `person_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  KEY `group_fk` (`group_id`),
  KEY `person_fk` (`person_id`),
  CONSTRAINT `person_fk` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE ,
  CONSTRAINT `group_fk` FOREIGN KEY (`group_id`) REFERENCES `recipient_group` (`id`) ON DELETE CASCADE
);

CREATE TABLE `statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `error` bit(1) DEFAULT NULL,
  `recipient` varchar(255) DEFAULT NULL,
  `recipient_type` varchar(255) DEFAULT NULL,
  `response` longtext,
  `sender` varchar(255) DEFAULT NULL,
  `sent_date` datetime DEFAULT NULL,
  `smsType` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);