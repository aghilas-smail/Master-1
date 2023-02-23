# Extraction des differentes etiquettes d'entites pour chaque mot
# A utiliser par la ligne de commande :
# cat corpus_en_200k.train.txt | cut -f2,4 | sort -k1,1 | python3 ./extrait_mot_label.py
# le sort -k1,1 permet de trier par rapport a la 1ere colonne

import sys
    
ch=""; cat1=""; nb=0; l1=""; mot1=""; cat1=""; mot2=""; cat2=""
l2 = sys.stdin.readline()

while l2 :
    if ( l2!="\n" ) :
        t2=l2.split("\t")
        mot2=t2[0]
        if (len(t2[1])>2) :  # c'est une etiquette d'entite
            cat2=t2[1][2:-1] # on enleve le 'B-' ou 'I-' ainsi que le saut de ligne
        else:
            cat2="O"
        if (mot1==mot2) :
            if (ch == "") :
                ch=mot1+"\t"+cat1
                nb=1
            if (ch.find(cat2)==-1) :
                ch+=" "+cat2
                nb+=1
        else :
            if (ch != "") :
                print("{}\t{}".format(nb,ch), end="\n")
                ch="";
    l1 = l2; mot1=mot2; cat1=cat2
    l2 = sys.stdin.readline()
