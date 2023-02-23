import sys

line = sys.stdin.readline()

while line :
    print(line.split("\t")[0][:-1])
    line = sys.stdin.readline()