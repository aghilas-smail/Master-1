import sys

line = sys.stdin.readline()
while line :
    mot = line[:-1].split("\t")
    label = ""
    if (mot[1] == "LOC") : label = "B-geoloc"
    elif (mot[1] == "ORG") : label = "B-org"
    elif (mot[1] == "PERS") : label = "B-personne"
    elif (mot[1] == "PROD") : label = "B-produit"
    print("0,XX,XX,XX,XX,{},XX,XX,XX,XX,XX,{}.".format(mot[0], label))
    line = sys.stdin.readline()