CREATE TABLE Subjects (
    ID int IDENTITY(1,1) PRIMARY KEY,
    [Name] nvarchar(50) NOT NULL,
	TeacherID int NOT NULL,
	FOREIGN KEY (TeacherID) REFERENCES Teachers(ID)
);

CREATE TABLE Teachers (
    ID int  IDENTITY(1,1) PRIMARY KEY,
    [Name] nvarchar(50) NOT NULL,
    Email nvarchar(50) NOT NULL,
    SubjectID int NOT NULL,
	Department nvarchar(50) NOT NULL,
	PhotoPath nvarchar(50),
	FOREIGN KEY (SubjectID) REFERENCES Subjects(ID)

);


CREATE TABLE Courses(
    ID int  IDENTITY(1,1) PRIMARY KEY,
    [Name] nvarchar(50) NOT NULL,

);


CREATE TABLE Students (
    ID int IDENTITY(1,1) PRIMARY KEY,
    [Name] nvarchar(50) NOT NULL,
    Email nvarchar(50) NOT NULL,
    CourseID int NOT NULL,
	Semester int NOT NULL,
	PhotoPath nvarchar(50),
	FOREIGN KEY (CourseID) REFERENCES Courses(ID)
);

CREATE TABLE ScheduleEntity(
    ID int IDENTITY(1,1) PRIMARY KEY,
    [WeekDay] nvarchar(50) NOT NULL,
	StartTime dateTime NOT NULL,
	EndTime dateTime NOT NULL,
	SubjectID int,
	FOREIGN KEY (SubjectID) REFERENCES Subjects(ID)
);

CREATE TABLE Records(
    ID int IDENTITY(1,1) PRIMARY KEY,
    StudentID int NOT NULL,
	ScheduleEntityID int NOT NULL,
	isPresent bit,
	[Date] datetime,
	FOREIGN KEY (StudentID) REFERENCES Students(ID),
	FOREIGN KEY (ScheduleEntityID) REFERENCES ScheduleEntity(ID)
);

CREATE TABLE ChangeRequests(
    RecordID int,
    [Status] bit,
	FOREIGN KEY (RecordID) REFERENCES Records(ID)
);

