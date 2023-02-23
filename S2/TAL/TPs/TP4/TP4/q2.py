with open("corpus_en_2k.train.txt") as f:
    content = f.readlines()

ambiguityMap = dict()
for line in content:
    words = line.split("\t")
    if len(words) > 2:
        word = words[1]
        word_type = words[3]
        if len(word_type) > 2:
            word_type = word_type[2:]
        word_type = word_type[:-1]
        if word in ambiguityMap:
            listA = ambiguityMap[word]
            if listA is not None:
                if word_type not in listA:
                    typeList = ambiguityMap[word]
                    typeList.append(word_type)
                    ambiguityMap.update({word: typeList})
        else:
            ambiguityMap[word] = [word_type]


orderedMap = dict()
for k in sorted(ambiguityMap, key=lambda k: len(ambiguityMap[k]), reverse=True):
    orderedMap[k] = ambiguityMap[k]
    
for k in in orderedMap:
    print(k, ":", orderedMap[k])