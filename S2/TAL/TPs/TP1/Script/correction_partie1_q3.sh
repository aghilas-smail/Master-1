#!/bin/bash

# Correction de la question 3 de la partie 1

# on calcule les fichiers contenant les valeurs tailles corpus / taille lexique pour
# les corpus frwiki-sample.txt et orfeo_dump.txt

echo "Traitement du corpus frwiki-sample.txt"
bash ./relation_taille_freq.sh ./frwiki-sample.txt > wiki.data

echo "Traitement du corpus orfeo_dump.txt"
bash ./relation_taille_freq.sh ./orfeo_dump.txt > orfeo.data

echo "Production de la courbe dans le fichier: taille_lexique.pdf"
cat make_courbe.gp | gnuplot

echo "termine"

