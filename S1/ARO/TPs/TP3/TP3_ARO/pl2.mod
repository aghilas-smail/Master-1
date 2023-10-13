/* Variable definitions */
var x1 >= 0;
var x2 >= 0;
var x3 >= 0;
var x4 >= 0;
var x5 >= 0;

/* Objective function */
minimize obj: x1 + x2 + x3 + x4 + x5;

/* Constraints */
R1: 2 * x1 = 97;
R2: 2 * x2 + 2 * x5 = 610;
R3: 3 * x3 = 395;
R4: 7 * x4 + 2 * x5 = 211;
solve;
display R1.dual, R2.dual, R3.dual, R4.dual;
