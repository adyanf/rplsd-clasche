grammar Clasche;
@header {
package com.rplsd.clasche.parser;
}

room_id: (NUMBER | WORD | ALPHANUMERIC);
class_id: ALPHANUMERIC;
capacity: NUMBER;
facility: WORD;
lecturer_name: WORD;
teaching_hour: NUMBER;
attendees_count: NUMBER;
duration: NUMBER;
class_1: class_id;
class_2: class_id;

array_of_lecturers: (OPEN_PARENTHESIS (lecturer_name COMMA WHITESPACE*)? lecturer_name CLOSE_PARENTHESIS | lecturer_name);
array_of_facilities: OPEN_PARENTHESIS (facility COMMA WHITESPACE*)* facility CLOSE_PARENTHESIS;
array_of_teaching_hours: OPEN_PARENTHESIS (teaching_hour COMMA WHITESPACE*)* teaching_hour CLOSE_PARENTHESIS;

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

NUMBER : DIGIT+;
WORD : (LOWERCASE | UPPERCASE)+;
ALPHANUMERIC : (LOWERCASE | UPPERCASE | DIGIT)+;


