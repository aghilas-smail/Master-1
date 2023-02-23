import sys

dic = {}
file = open(sys.argv[1])
line = file.readline()
while line :
    word = line[:-1].split("\t")
    if((word[0]) not in dic) : dic[word[0]] = [0,0,0,0]
    if (word[1] == "LOC") : dic[word[0]][0] += int(word[2])
    elif (word[1] == "ORG") : dic[word[0]][1] += int(word[2])
    elif (word[1] == "PERS") : dic[word[0]][2] += int(word[2])
    elif (word[1] == "PROD") : dic[word[0]][3] += int(word[2])
    line = file.readline()
indice = 0
tphrase=[]
def traite_phrase():
    global indice, tphrase
    for x in range(0,len(tphrase)):
        if (tphrase[x][4]=="hyp"):
            if(tphrase[x][1] in dic) : en = dic[tphrase[x][1]]
            else : en = [0,0,0,0]
            print("{}".format(tphrase[x][0]),end="")
            print(",{} {}".format(tphrase[x-2][1],tphrase[x-2][2]),end="") if (x>=2) else print(",XX XX",end="");
            print(",{} {}".format(tphrase[x-1][1],tphrase[x-1][2]),end="") if (x>=1) else print(",XX XX",end="");
            print(",{},{}".format(tphrase[x][1],tphrase[x][2]),end="")
            print(",{},{},{},{}".format(en[0],en[1],en[2],en[3]),end="")
            print(",{} {}".format(tphrase[x+1][1],tphrase[x+1][2]),end="") if (x<(len(tphrase)-1)) else print(",XX XX",end="")
            print(",{} {}".format(tphrase[x+2][1],tphrase[x+2][2]),end="") if (x<(len(tphrase)-2)) else print(",XX XX",end="");
            print(",{}".format(tphrase[x][3]),end=".\n")
        indice += 1
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
        indice += 1
    l1 = sys.stdin.readline()
traite_phrase()