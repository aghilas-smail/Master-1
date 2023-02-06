# Extraction des entites nommees avec les "patrons" d'etiquettes POS
# A utiliser par la ligne de commande :
# cat corpus_en_200k.train.txt | python3 ./extrait_entite_nomme.py
import sys

tmot=[]; tpos=[]; label="";

def traite_entite():
    chmot=""; chpos=""
    for x in range(0,len(tmot)):
        chmot+=tmot[x]+" "
        chpos+=tpos[x]+" "
    print("{}\t{}\t{}".format(chmot[:-1],chpos[:-1],label), end="\n")
    
l1 = sys.stdin.readline()
while l1 :
    if (len(l1)>2): # pas de saut de ligne
        t1=l1.split("\t")
        cat1=t1[3]
        if (len(cat1)>2): # c'est un label d'entite
            if (cat1.find("B-")==0): # on commence une nouvelle entite  
                if (len(tmot)>0):
                    traite_entite()
                    tmot=[]; tpos=[]
                label=cat1[2:-1]
            tmot.append(t1[1]); tpos.append(t1[2]);
        else:
            if (len(tmot)>0):
                traite_entite()
                tmot=[]; tpos=[]
    l1 = sys.stdin.readline()
