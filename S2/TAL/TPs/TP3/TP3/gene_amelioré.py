import sys
import os

wavant = "XX"
mot2 = sys.stdin.readline()
mot3 = sys.stdin.readline()
if not(mot3) : mot3 = "XX"

def pre_suf(w):
    p = len(w)//2
    if p == len(w) : 
        pre = "XX"
        suf = "XX"
    else : 
        pre = w[:p]
        suf = w[p:]
    return (pre, suf)

while mot3 :
    if mot2[0]<'0' or mot2[0]>'9': 
        wavant = "XX"
        mot2 = mot3
        mot3 = sys.stdin.readline()
    if len(mot2.split("\t")) > 2 :
        target = mot2.split("\t")[1]
        if mot3[0]<'0' or mot3[0]>'9' : wapres = "XX"
        else : wapres = mot3.split("\t")[1]
        i = mot2.split("\t")[0]
        if(target == ',') : target = "!VIRGULE"
        if(target == '.') : target = "!POINT"
        if(wapres == ',') : wapres = "!VIRGULE"
        if(wapres == '.') : wapres = "!POINT"
        target = target.replace(",", ".")
        wapres = wapres.replace(",", ".") 
        cate = mot2.split("\t")[2][:-1]
        if wavant == "!VIRGULE" or wavant == "!POINT" : 
            pre_wavant = "XX"
            suf_wavant = "XX"
        else : pre_wavant, suf_wavant  = pre_suf(wavant)
        if wapres == "!VIRGULE" or wapres == "!POINT" : 
            pre_wapres = "XX"
            suf_wapres = "XX"
        else : pre_wapres, suf_wapres = pre_suf(wapres)
        print(i, " , ", pre_wavant, " , ", wavant, " , ", suf_wavant, " , ", target, " , ", pre_wapres, " , ", wapres, " , ", suf_wapres, " , ", cate, " .")
    else : 
        target = mot2.split("\t")[1][:-1]
        if mot3[0]<'0' or mot3[0]>'9' : wapres = "XX"
        else : wapres = mot3.split("\t")[1][:-1]
        i = mot2.split("\t")[0]
        if(target == ',') : target = "!VIRGULE"
        if(target == '.') : target = "!POINT"
        if(wapres == ',') : wapres = "!VIRGULE"
        if(wapres == '.') : wapres = "!POINT"
        target = target.replace(",", ".")
        wapres = wapres.replace(",", ".") 
        if wavant == "!VIRGULE" or wavant == "!POINT" : 
            pre_wavant = "XX"
            suf_wavant = "XX"
        else : pre_wavant, suf_wavant  = pre_suf(wavant)
        if wapres == "!VIRGULE" or wapres == "!POINT" : 
            pre_wapres = "XX"
            suf_wapres = "XX"
        else : pre_wapres, suf_wapres = pre_suf(wapres)
        print(i, " , ", pre_wavant, " , ", wavant, " , ", suf_wavant, " , ", target, " , ", pre_wapres, " , ", wapres, " , ", suf_wapres, " .")
    wavant = target
    mot2 = mot3
    mot3 = sys.stdin.readline()