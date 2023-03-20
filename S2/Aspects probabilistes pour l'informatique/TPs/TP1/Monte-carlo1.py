
'''
Le calcul de π par la méthode de Monte-Carlo consiste à tirer au hasard des nombres x et y dans l'intervalle [0 ; 1].

Si x2 + y2 < 1 le point M (x, y) appartient à un quart de disque de rayon 1. La probabilité pour qu'il en soit ainsi 
est le rapport des aires du quart de disque de rayon 1 et du carré de côté 1 et  soit π / 4.

Dans cette appliquette nbPoints est le nombre total de points générés par une suite pseudo-aléatoire, S est le nombre de points
à l'intérieur du quart de disque et 4*S / nbPoints donne par conséquent une valeur approchée de π

'''
from random import random as rd

nbPoints = 1000

s = 0
for i in range(nbPoints):
    x,y = rd(),rd()
    if x**2+y**2<1:
        s=s+1
estimPi = 4*s/nbPoints

print(estimPi)
