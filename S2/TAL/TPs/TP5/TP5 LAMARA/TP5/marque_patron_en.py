# Marquage des candidats entites nommees a partir de patrons de POS
# cat corpus_en_200k.train.txt | python3 ./marque_patron_en.py patron_en.txt
#
# format:
# - en entrée 
# 14  à           prep    O
# 15  Bujumbura   np      B-geoloc
# 16  au          prep    O
# 17  Burundi     np      B-geoloc
#
# - en sortie
# 14  à           prep    O          O
# 15  Bujumbura   np      B-geoloc   hyp
# 16  au          prep    O          O
# 17  Burundi     np      B-geoloc   hyp
#
# le fichier patron_en.txt contient par exemple :
# np
# adj np

import sys

tpatron=[]; tphrase=[];

def traite_phrase():
    chmot=""; chpos=""
    for x in range(0,len(tphrase)): tphrase[x].append("O");
    for x in range(0,len(tphrase)):
        for y in range(0,len(tpatron)):
            z=0
            while (z<len(tpatron[y])) and ((z+x)<len(tphrase)) and (tphrase[x+z][2]==tpatron[y][z]):
                z+=1
            if (z==len(tpatron[y])): # on a trouve un patron qui match
                for z in range(0,len(tpatron[y])): tphrase[x+z][4]="hyp"
        print("{}\t{}\t{}\t{}\t{}".format(tphrase[x][0],tphrase[x][1],tphrase[x][2],tphrase[x][3],tphrase[x][4]), end="\n")
 
# lecture des patrons
file = open(sys.argv[1])
l = file.readline()
n=0
while l :
    t=l[:-1].split(" ")
    tpatron.append(t.copy())
    n+=1
    l = file.readline()
print('Nb de patrons lus={}'.format(len(tpatron)), file=sys.stderr)

# lecture du corpus annote
l = sys.stdin.readline()
while l :
    if (len(l)>2): # pas de saut de ligne
        t=l[:-1].split("\t")
        tphrase.append(t.copy())
    else :
        traite_phrase()
        tphrase.clear();
        print(end="\n")
    l = sys.stdin.readline()
traite_phrase();
