import sys

phrase = []
patrons = []

def traiter_phrase() :
    for mot in phrase : mot.append("O")
    for i in range(len(phrase)) :
        if (phrase[i][1][0]>='A' and phrase[i][1][0]<='Z') :
            phrase[i][4] = "hyp"
        print("{}\t{}\t{}\t{}\t{}".format(phrase[i][0],phrase[i][1],phrase[i][2],phrase[i][3],phrase[i][4]))

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