ALTER TABLE `recipient_group` ADD UNIQUE (`name`);
ALTER TABLE `person` ADD UNIQUE (`phone_number`);
ALTER TABLE `person` ADD UNIQUE (`email`);