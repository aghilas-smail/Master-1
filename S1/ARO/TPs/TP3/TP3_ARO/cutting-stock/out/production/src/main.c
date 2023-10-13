#include <stdio.h>
#include <stdlib.h>
#include <glpk.h>

void main(void){
    glp_prob *lp;
    int row[1+1000], column[1+1000], coef[1+1000];

    lp = glp_create_prob();
    glp_set_prob_name(lp, "pl_reduit");
    glp_set_obj_dir(lp, GLP_MIN);

    glp_add_rows(lp, 4);
    glp_set_row_name(lp, 1, "R1");
    glp_set_row_bnds(lp, 1, GLP_FX, 97, 97);
    glp_set_row_name(lp, 2, "R2");
    glp_set_row_bnds(lp, 2, GLP_FX, 610, 610);
    glp_set_row_name(lp, 3, "R4");
    glp_set_row_bnds(lp, 3, GLP_FX, 395, 395);
    glp_set_row_name(lp, 4, "R4");
    glp_set_row_bnds(lp, 4, GLP_FX, 211, 211);

    glp_add_cols(lp, 4)
    glp_set_col_name(lp, 1, "x1");
    glp_set_col_bnds(lp, 1, GLP_LO, 0.0, 0.0);
    glp_set_obj_coef(lp, 1, 1);
    glp_set_col_name(lp, 2, "x2");
    glp_set_col_bnds(lp, 2, GLP_LO, 0.0, 0.0);
    glp_set_obj_coef(lp, 2, 1);
    glp_set_col_name(lp, 3, "x3");
    glp_set_col_bnds(lp, 3, GLP_LO, 0.0, 0.0);
    glp_set_obj_coef(lp, 3, 1);
    glp_set_col_name(lp, 4, "x4");
    glp_set_col_bnds(lp, 4, GLP_LO, 0.0, 0.0);
    glp_set_obj_coef(lp, 4, 1);

    column[1] = 1; row[1] = 1; coef[1] = 2;
    column[2] = 1; row[2] = 2; coef[1] = 2;
    column[3] = 1; row[3] = 3; coef[1] = 3;
    column[4] = 1; row[4] = 4; coef[1] = 7;
    glp_load_matrix(lp, 4, column, row, coef);
    glp_simplex(lp, NULL);
    printf("dual.R1 = %f; dual.R2 = %f; dual.R3 = %f; dual.R4 = %f;", glp_get_row_dual(lp, 1), glp_get_row_dual(lp, 2), glp_get_row_dual(lp, 3), glp_get_row_dual(lp, 4));
}