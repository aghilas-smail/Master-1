#!/bin/bash
# production des courbes rang/frequence pour les donnees de corpus_topic
# affiche les valeurs pour les rangs de 10 a 1000 et les frequences de 10 a 2000
# modifier le chemin pour l'acces au repertoire corpus_topic pour correspondre a vos donnees
# les courbes sont produites dans le fichier "zipf_topic.pdf"

for t in  CLT ECO INT SPO 
 do
 echo "- analyse du theme "$t
 declare -i n=1
 cat ../data/corpus_topic/$t/*.txt | \
	sed "s/[ \t.?'\!-\"]/\n/g" | grep -v '^$' | sort | uniq -c | sort -n -r | \
	while read freq mot
	 do
		 echo $n $freq
	 n+=1
	done > topic_$t.gp
 done

echo "set grid ; set logscale x ; set logscale y ; set terminal pdf ; set output 'zipf_topic.pdf' ; set xlabel 'rang' ; set ylabel 'freq' ; set xrange [10:1000] ; set yrange [10:2000] ; \
       	plot 'topic_CLT.gp' w lp t 'CLT' , 'topic_ECO.gp' w lp t 'ECO' , 'topic_INT.gp' w lp t 'INT' , 'topic_SPO.gp' w lp t 'SPO'" | gnuplot
 
