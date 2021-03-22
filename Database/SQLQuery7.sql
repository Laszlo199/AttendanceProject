CREATE TABLE Subjects (
    ID int  NOT NULL,
    [Name] nvarchar(50) NOT NULL,
	TeacherID int NOT NULL,
    PRIMARY KEY (ID),
	FOREIGN KEY (TeacherID) REFERENCES Teachers(ID)
);

CREATE TABLE Teachers (
    ID int  NOT NULL,
    [Name] nvarchar(50) NOT NULL,
    Email nvarchar(50) NOT NULL,
    SubjectID int NOT NULL,
	Department nvarchar(50) NOT NULL,
	PhotoPath nvarchar(50),
	PRIMARY KEY (ID),
	FOREIGN KEY (SubjectID) REFERENCES Subjects(ID)
	
	
);


CREATE TABLE Courses(
    ID int  NOT NULL,
    [Name] nvarchar(50) NOT NULL,
    PRIMARY KEY (ID) 
);


CREATE TABLE Students (
    ID int  NOT NULL,
    [Name] nvarchar(50) NOT NULL,
    Email nvarchar(50) NOT NULL,
    CourseID int NOT NULL,
	Semester int NOT NULL,
	PhotoPath nvarchar(50),
	PRIMARY KEY (ID), 
	FOREIGN KEY (CourseID) REFERENCES Courses(ID)
);

CREATE TABLE ScheduleEntity(
    ID int  NOT NULL,
    [WeekDay] nvarchar(50) NOT NULL,
	StartTime dateTime NOT NULL,
	EndTime dateTime NOT NULL,
	SubjectID int,
    PRIMARY KEY (ID),
	FOREIGN KEY (SubjectID) REFERENCES Subjects(ID)
);

CREATE TABLE Records(
    ID int  NOT NULL,
    StudentID int NOT NULL,
	ScheduleEntityID int NOT NULL,
	isPresent bit,
	[Date] datetime,
    PRIMARY KEY (ID), 
	FOREIGN KEY (StudentID) REFERENCES Students(ID),
	FOREIGN KEY (ScheduleEntityID) REFERENCES ScheduleEntity(ID)
);

CREATE TABLE ChangeRequests(
    RecordID int,
    [Status] bit,
	FOREIGN KEY (RecordID) REFERENCES Records(ID)
);

