Projet DPR S1 M1 Informatique 2023
Projet réalisé par SMAIL AGHILAS (DRP1).

Le fichier transform.xsl permet de transformer un fichier XML (donnees-recettes.xml) en un fichier XML (RecepiesTransforme.xml) selon un fichier DTD (Recepies.dtd).

Le fichier XSL converhtml.xml permet de transformer un fichier XML (RecepiesTransforme.xml) respectant un fichier DTD (Recettes.dtd) en pages HTML :

auteurs.html : Fichier permettant le recensement de tous les auteurs avec leurs recettes.
categories.html :  Fichier permettant le recensement de toutes les catégories.
ingredients.html :  Fichier permettant le recensement de tous les ingrédients.
produits.html :  Fichier permettant le recensement de tous les produits.
recettes.html :  Fichier permettant le recensement de toutes les recettes triées par ordre décroissant de publication.
index.html :  page d'accueil.
Le fichier XqueryConvert.xq permet de produire un fichier XML (XqueryTransforme.xml) qui liste les auteurs avec, pour chacun, les recettes qu’il a proposées et les sous-catégories dans lesquelles les recettes apparaissent, triés par ordre alphabétique et ordre de date de publication.
