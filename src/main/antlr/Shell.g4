grammar Shell;


program: shellCommand EOF;

shellCommand: assignment | pipe | command |;

pipe: command (PIPE command)+;
command: cat | echo | wc | pwd | exit | grep | external | ls | cd;

pwd: PWD;
exit: EXIT;
ls: LS literal?;
cd: CD literal?;
cat: CAT literal?;
wc: WC literal?;
echo: ECHO literal*;
grep: GREP literal+;
assignment: id ASSIGN literal;
external: literal+;


literal: fullQuoting | weakQuoting | id | variable;
weakQuoting: WeakQuotedString;
fullQuoting: FullQuotedString;
id: ID;
variable: VAR_ID;

CAT: 'cat';
LS: 'ls';
CD: 'cd';
ECHO: 'echo';
WC: 'wc';
PWD: 'pwd';
EXIT: 'exit';
GREP: 'grep';
ASSIGN: '=';
PIPE: '|';
ID: Id;
VAR_ID: Var;
DOUBLE_QUOTE: '"';
SINGLE_QUOTE: '\'';
FullQuotedString :  SINGLE_QUOTE CharFull  SINGLE_QUOTE;
WeakQuotedString : DOUBLE_QUOTE CharWeak DOUBLE_QUOTE;

fragment OpChar: ~[a-zA-Z0-9()[\]{};, \r\t\n'"$_=];
fragment Id: ([a-zA-Z_0-9] | OpChar+)+;
fragment Var: '$' Id;
fragment CharFull: .*?;
fragment CharWeak: .*?;
WS: [ \t] -> skip;
