CREATE TABLE `themetable` (
	`themeName` VARCHAR(100) NOT NULL,
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`active` TINYINT(4) NULL DEFAULT '0',
	PRIMARY KEY (`id`)
)

CREATE TABLE `optionstable` (
	`idTheme` INT(11) NOT NULL,
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`optionName` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_themeTable` (`idTheme`),
	CONSTRAINT `FK_themeTable` FOREIGN KEY (`idTheme`) REFERENCES `themetable` (`id`)
)

CREATE TABLE `voicetable` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`idTheme` INT(11) NOT NULL,
	`idOption` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_idTheme` (`idTheme`),
	INDEX `FK_idOpt` (`idOption`),
	CONSTRAINT `FK_idOpt` FOREIGN KEY (`idOption`) REFERENCES `optionstable` (`id`),
	CONSTRAINT `FK_idTheme` FOREIGN KEY (`idTheme`) REFERENCES `themetable` (`id`)
)

