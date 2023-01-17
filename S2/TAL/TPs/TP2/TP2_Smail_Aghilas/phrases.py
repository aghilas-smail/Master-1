import sys
    
l1 = sys.stdin.readline()
l2 = sys.stdin.readline()


print("<s>", end=" ")
while l2 :
    print(l1[:-1], end=" ")
    if (l1 == '.\n') :
        if (l2 == "<SAUT-LIGNE/>\n" or (l2[0] >= 'A' and l2[0] <= 'Z')) :
            print("</s>")
            print("<s>", end=" ")
    l1 = l2
    l2 = sys.stdin.readline()
print("</s>")