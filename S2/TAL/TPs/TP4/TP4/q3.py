from util import get_entity

with open("corpus_en_200k.train.txt") as f:
    content = f.readlines()

def get_entity(i, content, wordList):
    patron_list = [content[i].split("\t")[2]]
    while i + 1 < len(content) - 1 and len(content[i + 1].split("\t")) > 2 and content[i + 1].split("\t")[3][0] == 'I':
        i += 1
        wordList.append(content[i].split("\t")[1])
        patron_list.append(content[i].split("\t")[2])
    key = wordList[0]
    patron = patron_list[0]
    for j in range(1, len(wordList)):
        key += " " + wordList[j]
        patron += " " + patron_list[j]

    return key, i, patron

entity_map = dict()
i = 0
while i < len(content):
    words = content[i].split("\t")
    if len(words) > 2 and words[3][0] == 'B':
        wordList = [words[1]]
        key, i, content = get_entity(i, content, wordList)
        if key in entity_map:
            entity_map.update({key: entity_map[key] + 1})
        else:
            entity_map[key] = 1
    i += 1


sorted_entity_map = dict()
for k in sorted(entity_map, key=lambda k: entity_map[k], reverse=True):
    sorted_entity_map[k] = entity_map[k]

for key in sorted_entity_map:
    print(key, ": ", sorted_entity_map[key])


patron_occ_map = dict()