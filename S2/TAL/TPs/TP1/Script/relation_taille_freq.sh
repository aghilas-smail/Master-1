#!/bin/bash

# script permettant de produire les donnees taille de corpus / taille du lexique
# utilisation : bash ./relation_taille_freq.sh corpus.txt
# corpus.txt : c'est le corpus a analyser, remplacer par le corpus de votre choix
# dans le script, le corpus donne en parametre correspond a la variable $1

for t in 100 500 1000 1500 2000 4000 5000 10000 20000 30000 40000 50000 80000 100000 200000 500000 1000000 2000000 5000000
do
	echo $t $(cat $1 | sed 's/[ \t]/\n/g' | head -n $t | sort -u | wc -l)
done
 
