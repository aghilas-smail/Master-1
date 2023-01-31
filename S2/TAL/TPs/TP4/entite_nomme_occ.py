import sys

word = sys.stdin.readline()
entite = word.split("\t")[0]
c = [0]*5

while word :
    if word != "\n":
        next_entite = word.split("\t")[0]
        et = word.split("\t")[1][:-1]
        if (entite == next_entite):
            if(et == "geoloc"): c[0] +=1
            elif(et == "org"): c[1] +=1
            elif(et == "person"): c[2] +=1
            elif(et == "product"): c[3] +=1
            elif(et == "O"): c[4] +=1
            word = sys.stdin.readline()
        else :
            print(entite, "\tgeoloc\t", c[0], "\torg\t", c[1],  "\tperson\t", c[2], "\tproduct\t", c[3], "\tO\t", c[4])
            entite = next_entite
            c = [0]*5
    else : 
        word = sys.stdin.readline()