wordMap = dict()
with open("corpus_en_200k.train.txt") as f:
    content = f.readlines()

for line in content:
    words = line.split("\t")
    key = words[len(words) - 1]
    if key[0] == 'I':
        continue
    if len(key) > 2:
        key = key[2:]
    key = key[:-1]
    if key != "\n" and len(key) > 0:
        # print("key: ", key, "len: ", len(key))
        if key in wordMap:
            wordMap.update({key: wordMap[key] + 1})
        else:
            wordMap[key] = 1

print(wordMap)