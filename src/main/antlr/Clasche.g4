grammar Clasche;
@header {
package com.rplsd.clasche.parser;
}

room_id: (NUMBER | WORD | ALPHANUMERIC);
class_name: ALPHANUMERIC;
capacity: NUMBER;
facility: WORD;
lecturer_name: WORD;
teaching_hour: NUMBER;
attendees_count: NUMBER;
duration: NUMBER;
class_1: class_name;
class_2: class_name;
max_capacity: NUMBER;

array_of_lecturers: (OPEN_BRACKET (lecturer_name COMMA WHITESPACE*)? lecturer_name CLOSE_BRACKET | lecturer_name);
array_of_facilities: OPEN_BRACKET (facility COMMA WHITESPACE*)* facility CLOSE_BRACKET;
array_of_teaching_hours: OPEN_BRACKET (teaching_hour COMMA WHITESPACE*)* teaching_hour CLOSE_BRACKET;

fixed_schedule: FIXED SCHEDULE class_name WHITESPACE* array_of_teaching_hours;
pair_of_class_name: class_1 COLON class_2;
non_conflict: NONCONFLICT OPEN_BRACKET (pair_of_class_name COMMA WHITESPACE*)* pair_of_class_name CLOSE_BRACKET;
unavailable: UNAVAILABLE array_of_teaching_hours;
teaching_duration_limit: TEACHING DURATION LIMIT duration;


defineClassroom
    : ADD CLASSROOM WHITESPACE* ID WHITESPACE* room_id WHITESPACE* CAPACITY WHITESPACE* capacity WHITESPACE* FACILITIES WHITESPACE* array_of_facilities SEMICOLON
    ;
defineLecturer
    : ADD LECTURER WHITESPACE* CODENAME WHITESPACE* lecturer_name WHITESPACE* AVAILABILITIES WHITESPACE* array_of_teaching_hours SEMICOLON
    ;
defineClass
    : ADD CLASS WHITESPACE* CODE WHITESPACE* class_name WHITESPACE* (LECTURER | LECTURERS) WHITESPACE* array_of_lecturers WHITESPACE* PARTICIPANT WHITESPACE* attendees_count WHITESPACE* (MAXCAPACITY WHITESPACE* max_capacity)? WHITESPACE* FACILITIES WHITESPACE* array_of_facilities WHITESPACE* (TEACHINGHOUR | TEACHINGHOURS) WHITESPACE* duration SEMICOLON
    ;
defineConstraint
    : ADD CONSTRAINT (fixed_schedule | non_conflict | unavailable | teaching_duration_limit) SEMICOLON
    ;
definePreference
    : ADD PREFERENCE (fixed_schedule | non_conflict | unavailable | teaching_duration_limit) SEMICOLON
    ;
startSchedule
    : GENERATE SCHEDULE SEMICOLON
    ;
eval
	:	((defineClassroom | defineLecturer | defineClass | defineConstraint | definePreference | startSchedule) WHITESPACE*)* EOF
	;

fragment A : ('A' | 'a');
fragment B : ('B' | 'b');
fragment C : ('C' | 'c');
fragment D : ('D' | 'd');
fragment E : ('E' | 'e');
fragment F : ('F' | 'f');
fragment G : ('G' | 'g');
fragment H : ('H' | 'h');
fragment I : ('I' | 'i');
fragment J : ('J' | 'j');
fragment K : ('K' | 'k');
fragment L : ('L' | 'l');
fragment M : ('M' | 'm');
fragment N : ('N' | 'n');
fragment O : ('O' | 'o');
fragment P : ('P' | 'p');
fragment Q : ('Q' | 'q');
fragment R : ('R' | 'r');
fragment S : ('S' | 's');
fragment T : ('T' | 't');
fragment U : ('U' | 'u');
fragment V : ('V' | 'v');
fragment W : ('W' | 'w');
fragment X : ('X' | 'x');
fragment Y : ('Y' | 'y');
fragment Z : ('Z' | 'z');
fragment LOWERCASE : [a-z];
fragment UPPERCASE : [A-Z];
fragment DIGIT : '0'..'9';
fragment DASH : '-';
OPEN_BRACKET : '[';
CLOSE_BRACKET : ']';
COMMA : ',';
COLON : ':';
SEMICOLON : ';';
WHITESPACE : (' ' | '\r' | '\n')+ -> skip;

CLASSROOM : C L A S S R O O M;
LECTURER : L E C T U R E R;
LECTURERS : L E C T U R E R S;
CLASS : C L A S S;
PREFERENCE : P R E F E R E N C E;
CONSTRAINT : C O N S T R A I N T;
SCHEDULE : S C H E D U L E;
GENERATE : G E N E R A T E;
FIXED : F I X E D;
NONCONFLICT : N O N DASH C O N F L I C T;
UNAVAILABLE : U N A V A I L A B L E;
TEACHING: T E A C H I N G;
HOUR : H O U R;
HOURS : HOUR S;
TEACHINGHOUR: TEACHING DASH HOUR;
TEACHINGHOURS: TEACHING DASH HOURS;
LIMIT: L I M I T;
DURATION: D U R A T I O N;
ADD: A D D;
ID: I D;
CAPACITY: C A P A C I T Y;
FACILITIES: F A C I L I T I E S;
CODENAME: C O D E N A M E;
AVAILABILITIES: A V A I L A B I L I T I E S;
CODE: C O D E;
PARTICIPANT: P A R T I C I P A N T;
MAXCAPACITY: M A X DASH C A P A C I T Y;

NUMBER : DIGIT+;
WORD : (LOWERCASE | UPPERCASE)+;
ALPHANUMERIC : (LOWERCASE | UPPERCASE | DIGIT)+;



