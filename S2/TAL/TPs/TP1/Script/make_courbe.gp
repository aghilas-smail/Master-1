set grid ; set logscale x ; set logscale y ; set xlabel 'taille corpus' ; set ylabel 'taille lexique' ; plot 'wiki.data' w lp t 'ecrit' , 'orfeo.data' w lp t 'oral'
set terminal pdf ; set output 'taille_lexique.pdf' ; replot
