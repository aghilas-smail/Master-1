import sys

line = sys.stdin.readline()
entite = line.split("\t")[0]
count = 0

while line :
    if line != "\n":
        next_entite = line.split("\t")[0]
        if (entite == next_entite):
            count += 1
        else :
            print(entite, "\t", count)
            entite = next_entite
            count = 1 
    line = sys.stdin.readline()