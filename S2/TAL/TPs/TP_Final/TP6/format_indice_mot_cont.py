# modifie les indices en premiere colonne
#5743    le  det O
#5744    réalisateur nc  O
#5745    Brad    np  B-person
#5746    Mirman  np  I-person
#5747    a   v   O
#5748    en_effet    adv O
#5749    réussi  v   O
#5750    à   prep    O
#5751    réunir  v   O
#5752    face_à  prep    O
#5753    Harvey  np  B-person
#5754    Keitel  np  I-person
# utilisation : cat corpus.txt | python3 ./format_indice_mot.py

import sys

tphrase=[];
nbphrase=0;
indice=0;

def traite_phrase():
    global tphrase,indice
    chmot=""; chpos=""
    for x in range(0,len(tphrase)):
        print("{}\t{}\t{}\t{}".format(indice,tphrase[x][1],tphrase[x][2],tphrase[x][3]),end="\n")
        indice+=1

l1 = sys.stdin.readline()
while l1 :
    if (len(l1)>2): # pas de saut de ligne
        t1=l1[:-1].split("\t")
        tphrase.append(t1.copy())
    else :
        if (nbphrase>0):
            print(end="\n")
        traite_phrase()
        tphrase.clear();
        nbphrase+=1
        indice+=1
    l1 = sys.stdin.readline()
traite_phrase();
