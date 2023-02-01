import sys
    
l1 = sys.stdin.readline()
l2 = sys.stdin.readline()



while l2 :
    data = l1.split('\t')
    if (data == "_,_") :
        print("VEGULE")
    if (data == "_._") :
        print("POINT")     
    l1 = l2
    l2 = sys.stdin.readline()
print(l2)
