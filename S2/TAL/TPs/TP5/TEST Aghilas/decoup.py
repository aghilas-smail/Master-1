lines = open('corpus_en_200k.train.txt', "r")

#with open("corpus_en_200K.train.txt") as f:
#    content = f.readlines()
line = lines.readlines()
size = len(line)
taille_train = round(size*0.7)
taille_dev = round(size*0.2)
taille_test = round(size*0.1)

f1 = open("corpus.data", "w")
f2 = open("corpus.dev", "w")
f3 = open("corpus.test", "w")

for i in range(size):
    l = line[i]
    if i < taille_train:
        f1.write(l)
    if i < taille_dev:
        f2.write(l)
    if i < taille_test:
        f3.write(l)