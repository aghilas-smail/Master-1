with open("corpus_en_200K.train.txt") as f:
    content = f.readlines()

size = len(content)
taille_train = round(size*0.7)
taille_dev = round(size*0.2)
taille_test = round(size*0.1)

f1 = open("corpus.data", "w")
f2 = open("corpus.dev", "w")
# f3 = open("corpus.test", "w")

for i in range(size):
    line = content[i]
    if i < taille_train:
        f1.write(line)
    else:
        f2.write(line)
    # else:
    #     f3.write(line)