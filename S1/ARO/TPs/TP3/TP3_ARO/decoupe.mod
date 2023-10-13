/* Variable definitions */
var a1 >= 0, integer;
var a2 >= 0, integer;
var a3 >= 0, integer;
var a4 >= 0, integer;

/* Objective function */
maximize obj: (1/2) * a1 + (1/2) * a2 + (1/3) * a3 + (1/7) * a4;

/* Constraints */
R1: a1 * 45 + a2 * 36 + a3 * 31 + a4 * 14 <= 100;

solve;

