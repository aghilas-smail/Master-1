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