drop table if exists Person;

CREATE TABLE `Person` (
  `PersonId` bigint NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Gender` enum ('M','F','N') not null,
  NickName varchar(255) null,
  PRIMARY KEY (`PersonId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
