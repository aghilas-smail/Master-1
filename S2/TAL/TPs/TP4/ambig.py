import sys

line = sys.stdin.readline()
word = line.split("\t")[0]

c = 0
et = []
somme = 0
nb_line = 0
dictionnaire_de_somme = {}
dic_nombre = {}
while line :
    if line != "\n":
        # On donne le prmier valeur a mot_apres et en le compare avec next
        mot_apres = line.split("\t")[0]
        # On parcours tout le line de la deuxieme valeur a la dernier
        next = line.split("\t")[1][:-1]
        if len(next) > 2 : 
            # On supprime les B et I
            next = next.replace("B-", "")
            next = next.replace("I-", "")
        if word == mot_apres :
            if next not in et : 
                et.append(next)
                c += 1
        else :
            # Somme qui sert a calculer la moyenne 
            somme += c
            nb_line += 1
            nombre_mots = len(word.split(" "))
            # On increment notre dictionnaire avec le nombre de mots
            if(nombre_mots in dictionnaire_de_somme.keys()) : 
                dictionnaire_de_somme[nombre_mots] += c
            else : dictionnaire_de_somme[nombre_mots] = c

            
            if(nombre_mots in dic_nombre.keys()) : dic_nombre[nombre_mots] += 1
            else : dic_nombre[nombre_mots] = 1


            if(len(sys.argv) < 2) : print(c, "\t", word, end="\t")

            for e in et :
                if(len(sys.argv) < 2) : print(e, end=" ")
            if(len(sys.argv) < 2) : print()
            word = mot_apres
            c = 1
            et = [next]
    else : word = line.split("\t")[0]
    line = sys.stdin.readline()

if(len(sys.argv) > 1):
    if(sys.argv[1] == "-m") : print("Ambiguite moyenne : ", somme/nb_line)
    if(sys.argv[1] == "-mw") : 
        for key in dictionnaire_de_somme.keys():
            print(key, "\t", dictionnaire_de_somme[key]/dic_nombre[key])
        