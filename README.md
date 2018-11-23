# Clasche Language Guidelines

Clasche Language is Domain Specific Language that used to generate schedule for classes.

# DSL Vocabolary

---

- **classroom_id** – Unique ID for each classroom, for example *R7606*.
- **capacity** – Capacity of a classroom, for example *100*.
- **facilities** – A list of tools or facility that exist in a classroom, for example *[ac,projector]*.
- **codename** *-* Codename for a lecturer, for example *IMK*.
- **schedule_time** - A number that show a specific time in schedule, for example *11*. First number show the day, and second (and third if available) number show the time of a day.
- **availability_schedule** *-* A list of schedule_time that the lecturer available to teach, for example *[11,12].*
- **class_code** *-* Unique code for each class, for example *IF4031K1*.
- **lecturers** - A list of codename of a lecturer, for example *[AP,IMK]*.
- **participant** *-* The number of students that take the class, for example *50*.
- **max_capacity** *-* The number of maximum students that take the class, for example *100*.
- **teaching_hours** - The number of hours that required by a class, for example *3*.

# DSL Grammar

---

**Classroom Definition**

Add classroom that are available in your school.

    ADD CLASSROOM ID <classroom_id> CAPACITY <capacity> FACILITIES <facilities>;

**Lecturer Definition**

Add lecturer.

    ADD Lecturer CODENAME <codename> AVAILABILITIES <availability_schedule>;

**Class Definition**

Add class.

    ADD CLASS CODE <class_code> LECTURERS <lecturers> PARTICIPANT <participant> 
    	MAX-CAPACITY <max_capacity> FACILITIES <facilities> 
    	TEACHING-HOURS <teaching_hours>; 

**Preference and Constraint Definition**

Add non-conflict preference/constraint

    ADD CONSTRAINT NON-CONFLICT [<class_code_1>:<class_code_2>,...];
    ADD PREFERENCE NON-CONFLICT [<class_code_1>:<class_code_2>,...]; 

Add fixed schedule preference/constraint

    ADD CONSTRAINT FIXED SCHEDULE <class_code> [<schedule_time>,...];
    ADD PREFERENCE FIXED SCHEDULE <class_code> [<schedule_time>,...];

Add unavailable schedule preference/constraint

    ADD CONSTRAINT UNAVAILABLE [<schedule_time>,...];
    ADD PREFERENCE UNAVAILABLE [<schedule_time>,...];

Add teaching duration limit for each lecturer

    ADD CONSTRAINT TEACHING DURATION LIMIT <teaching_hours>;
    ADD PREFERENCE TEACHING DURATION LIMIT <teaching_hours>;

**Scheduler**

Run scheduler

    GENERATE SCHEDULE;

# Development

---

Build project:

    ./gradlew build

Run project:

    ./gradlew run --args '<path_to_file>'
