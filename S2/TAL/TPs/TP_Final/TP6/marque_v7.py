import sys

phrase = []
patrons = []

def traiter_phrase() :
    for mot in phrase : mot.append("O")
    for i in range(len(phrase)) :
        for j in range(len(patrons)) :
            k=0
            while (k<len(patrons[j])) and ((k+i)<len(phrase)) and (phrase[i+k][2]==patrons[j][k]) :
                k+=1
            if (k==len(patrons[j])) :
                if (len(patrons[j])>3) :
                    for k in range(len(patrons[j])) : phrase[i+k][4]="hyp"
                elif (phrase[i][1][0].isupper() or phrase[i][2][0].isupper()) : 
                    for k in range(len(patrons[j])) : phrase[i+k][4]="hyp"
        print("{}\t{}\t{}\t{}\t{}".format(phrase[i][0],phrase[i][1],phrase[i][2],phrase[i][3],phrase[i][4]))

file = open(sys.argv[1])
line = file.readline()
while line :
    patron = line[:-1].split(" ")
    patrons.append(patron.copy())
    line = file.readline()

line = sys.stdin.readline()
while line :
    if (len(line)>2) :
        mot = line[:-1].split("\t")
        phrase.append(mot.copy())
    else :
        traiter_phrase()
        phrase.clear();
        print(end="\n")
    line = sys.stdin.readline()
traiter_phrase()