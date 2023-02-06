# Traduction d'un corpus annote en entite nomme dans un format icsiboost
# cat corpus_en_200k.train.txt | python3 ./extrait_entite_nomme.py

# format:
# - en entrée 
# 14  à           prep    O
# 15  Bujumbura   np      B-geoloc
# 16  au          prep    O
# 17  Burundi     np      B-geoloc
#
# - en sortie : id , mot-2 , pos-2 , mot-1 , pos-1 , mot , pos , mot+1 , pos+1 , mot+2 , pos+2 , label.

import sys

tphrase=[];

def traite_phrase():
    chmot=""; chpos=""
    for x in range(0,len(tphrase)):
        print("{}".format(tphrase[x][0]),end="")
        print(",{},{}".format(tphrase[x-2][1],tphrase[x-2][2]),end="") if (x>=2) else print(",XX,XX",end="");
        print(",{},{}".format(tphrase[x-1][1],tphrase[x-1][2]),end="") if (x>=1) else print(",XX,XX",end="");
        print(",{},{}".format(tphrase[x][1],tphrase[x][2]),end="")
        print(",{},{}".format(tphrase[x+1][1],tphrase[x+1][2]),end="") if (x<(len(tphrase)-1)) else print(",XX,XX",end="");
        print(",{},{}".format(tphrase[x+2][1],tphrase[x+2][2]),end="") if (x<(len(tphrase)-2)) else print(",XX,XX",end="");
        print(",{}".format(tphrase[x][3]),end=".\n")

l1 = sys.stdin.readline()
while l1 :
    if (len(l1)>2): # pas de saut de ligne
        t1=l1[:-1].split("\t")
        if (t1[1]==','): t1[1]="VIRGULE"
        if (t1[1]=='.'): t1[1]="POINT"
        tphrase.append(t1.copy())
    else :
        traite_phrase()
        tphrase.clear();
    l1 = sys.stdin.readline()
traite_phrase();
