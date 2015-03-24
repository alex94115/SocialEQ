/* Add a new communication channel to the MessageStateChanges table */
CREATE TABLE CommunicationChannel ( 
	type VARCHAR(20) PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `CommunicationChannel` (`type`) VALUES ('TWEET'), ('FACEBOOK'), ('EMAIL');

ALTER TABLE `MessageStateChanges` add column `communicationChannel` varchar(255) DEFAULT NULL after `doesRequireSellerCommunication`;
ALTER TABLE `MessageStateChanges` ADD CONSTRAINT `fk_message_state_change_channel_CommunicationChannel_type` FOREIGN KEY (`communicationChannel`) REFERENCES `CommunicationChannel` (`type`);

/* Fill in the  column for historical rows */
UPDATE MessageStateChanges msc   INNER JOIN Messages m ON msc.message_id = m.id SET msc.communicationChannel = 'FACEBOOK' WHERE m.providerId = 'facebook';
UPDATE MessageStateChanges msc   INNER JOIN Messages m ON msc.message_id = m.id SET msc.communicationChannel = 'TWEET' WHERE m.providerId = 'twitter';

/* Simplify deleting users by spreading out the cascading deletes */
alter table `Addresses` add constraint `FK_Addresses_User_Users_id` foreign key (`user_id`) references `Users` (`id`) ON DELETE CASCADE;