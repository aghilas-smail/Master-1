from collections import defaultdict
with open("corpus_en_2k.train.txt") as f:
    content = f.readlines()
 
    d = defaultdict(int)
    for e in range(len(content)) :
        d[e] += 1
    
    print (d)