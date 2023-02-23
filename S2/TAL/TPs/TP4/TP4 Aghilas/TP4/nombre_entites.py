import sys

line = sys.stdin.readline()

geoloc = 0
org = 0
product = 0
person = 0
O = 0
while line :
    if line[0]>='0' and line[0]<='9': 
        et = line.split("\t")[3][:-1]
        if et == 'B-geoloc' : geoloc += 1
        if et == 'B-org' : org += 1
        if et == 'B-product' : product += 1
        if et == 'B-person' : person += 1
        if et == 'O' : O += 1
    line = sys.stdin.readline()
print("geoloc : ", geoloc)
print("org : ", org)
print("product : ", product)
print("person : ", person)
print("O : ", O)