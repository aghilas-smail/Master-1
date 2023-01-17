import sys
import os
    
l = sys.stdin.readline()
while l :
    m = l.split(" ")
    if(m[1] == "<SAUT-LIGNE/>") : pos = 2
    else : pos = 1
    if(m[pos][0] >= 'A' and m[pos][0] <= 'Z') : 
        m_min = m[pos].lower()
        cmd = "grep '\t" + m_min + "\t' lex_lefff_pos_lemme_freq_90k.txt | wc -l"
        res = os.popen(cmd).read()
        if(res != "0\n") :
            m[pos] = m_min 
    for i in range(1, len(m)-2):
        print(m[i], end=" ")
    print()
    l = sys.stdin.readline()