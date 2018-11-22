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

defineClassroom
    : CLASSROOM WHITESPACE* room_id WHITESPACE* capacity WHITESPACE* array_of_facilities SEMICOLON
    ;
defineLecturer
    : LECTURER WHITESPACE* lecturer_name WHITESPACE* array_of_teaching_hours SEMICOLON
    ;
defineClass
    : CLASS WHITESPACE* class_name WHITESPACE* array_of_lecturers WHITESPACE* attendees_count  WHITESPACE* max_capacity? WHITESPACE* array_of_facilities WHITESPACE* duration SEMICOLON
    ;
eval
	:	((defineClassroom | defineLecturer | defineClass) WHITESPACE*)* EOF
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
CLASS : C L A S S;

NUMBER : DIGIT+;
WORD : (LOWERCASE | UPPERCASE)+;
ALPHANUMERIC : (LOWERCASE | UPPERCASE | DIGIT)+;



