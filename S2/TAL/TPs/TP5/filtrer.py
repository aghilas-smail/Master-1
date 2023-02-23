import sys 

lines = sys.stdin.read().splitlines()


for line in lines:
    lines_splite = line.split(" ")
    if "np" in lines_splite:
        print(line)
    if len(lines_splite) >= 4 and "nc" in lines_splite:
        print(line)