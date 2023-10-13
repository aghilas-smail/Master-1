#include <stdio.h>
#include <stdlib.h>
#include <glpk.h>

int main(void){
    glp_prob *lp;
    glp_prob *lpd;
    int row[1+1000], column[1+1000];
    double coef[1+1000];
    int rowD[1+1000], columnD[1+1000];
    double coefD[1+1000];
    double dual[4];
    double obj = 10.0;
    int mat[37][4];
    int nbDecoupes = 4;
    // sélectionner un sous-ensemble initial de colonnes.
    mat[0][0] = 2; mat[0][1] = 0; mat[0][2] = 0; mat[0][3] = 0;
    mat[1][0] = 0; mat[1][1] = 2; mat[1][2] = 0; mat[1][3] = 0;
    mat[2][0] = 0; mat[2][1] = 0; mat[2][2] = 3; mat[2][3] = 0;
    mat[3][0] = 0; mat[3][1] = 0; mat[3][2] = 0; mat[3][3] = 7;

    do{
        //résoudre le programme linéaire réduit
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

        glp_add_cols(lp, nbDecoupes * 4);
        for(int i = 1; i<=nbDecoupes; i++){
            glp_set_col_bnds(lp, i, GLP_LO, 0.0, 0.0);
            glp_set_obj_coef(lp, i, 1);
        }
        int pos = 1;
        for(int i=1; i<=4; i++){
            for(int j=0; j<nbDecoupes; j++){
                row[pos] = i;
                column[pos] = j+1;
                coef[pos] = mat[j][i-1];
                pos++;
            }
        }

        glp_load_matrix(lp, nbDecoupes * 4, row, column, coef);
        glp_simplex(lp, NULL);

        //récupère la solution duale optimale
        dual[0] = glp_get_row_dual(lp, 1);
        dual[1] = glp_get_row_dual(lp, 2);
        dual[2] = glp_get_row_dual(lp, 3);
        dual[3] = glp_get_row_dual(lp, 4);
        printf("dual.R1 = %f; dual.R2 = %f; dual.R3 = %f; dual.R4 = %f; \n\n", dual[0], dual[1], dual[2], dual[3]);

        //résoudre le problème de sac à dos
        lpd = glp_create_prob();
        glp_set_prob_name(lpd, "decoupe");
        glp_set_obj_dir(lpd, GLP_MAX);

        glp_add_rows(lpd, 1);
        glp_set_row_name(lpd, 1, "R1");
        glp_set_row_bnds(lpd, 1, GLP_UP, 0.0, 100.0);

        glp_add_cols(lpd, 4);
        for(int i = 1; i<=4; i++){
            glp_set_col_bnds(lpd, i, GLP_LO, 0, 0);
            glp_set_col_kind(lpd, i, GLP_IV);
            glp_set_obj_coef(lpd, i, dual[i-1]);
        }

        rowD[1] = 1; columnD[1] = 1; coefD[1] = 45;
        rowD[2] = 1; columnD[2] = 2; coefD[2] = 36;
        rowD[3] = 1; columnD[3] = 3; coefD[3] = 31;
        rowD[4] = 1; columnD[4] = 4; coefD[4] = 14;

        glp_load_matrix(lpd, 4, rowD, columnD, coefD);

        glp_iocp param;
        glp_init_iocp(&param);
        param.presolve = GLP_ON;

        glp_intopt(lpd, &param);
        obj = glp_mip_obj_val(lpd);

        if(obj > 1.0){
            //ajouter la colonne au programme linéaire
            for(int i = 0; i<4; i++){
                mat[nbDecoupes][i] = glp_mip_col_val(lpd, i+1);
            }
            nbDecoupes++;
        }
    }
    while(obj>1.0);

    //afficher le résultat
    printf("[45,36,31,14]\n");
    for(int i = 0; i<nbDecoupes; i++){
        printf("[");
        for(int j = 0; j<4; j++){
            printf(" %d,", mat[i][j]);
        }
        printf(" ] -> %g fois\n", glp_get_col_prim(lp, i+1));
    }

}