import sys

wavant = "XX"
mot2 = sys.stdin.readline()
mot3 = sys.stdin.readline()
if not(mot3) : mot3 = "XX"

while mot3 :
    if mot2[0]<'0' or mot2[0]>'9': 
        wavant = "XX"
        mot2 = mot3
        mot3 = sys.stdin.readline()
    target = mot2.split("\t")[1]
    if mot3[0]<'0' or mot3[0]>'9' : wapres = "XX"
    else : wapres = mot3.split("\t")[1]
    i = mot2.split("\t")[0]
    cate = mot2.split("\t")[2][:-1]
    if(target == ',') : target = "!VIRGULE"
    if(target == '.') : target = "!POINT"
    if(wapres == ',') : wapres = "!VIRGULE"
    if(wapres == '.') : wapres = "!POINT"
    target = target.replace(",", ".")
    wapres = wapres.replace(",", ".")
    print(i, " , ", wavant, " , ", target, " , ", wapres, " , ", cate, " .")
    wavant = target
    mot2 = mot3
    mot3 = sys.stdin.readline()
