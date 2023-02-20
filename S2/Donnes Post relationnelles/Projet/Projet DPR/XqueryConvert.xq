declare namespace output = "http://www.w3.org/2010/xslt-xquery-serialization";
declare option output:method 'xhtml';
declare option output:doctype-public '-//W3C//DTD XHTML 1.0 Strict//EN';
declare option output:doctype-system 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd';
<html>
    <head></head>
    <body>{
        for $auteur in doc("RecepiesTransforme.xml")//auteur
        let $bio := $auteur/biographie/text()
        return
            <div id="{$auteur/@id}">
                <h2>{$auteur/nom/text()}</h2>
                <p>Pays : {$auteur/pays/text()}</p>
                <p>Sexe : {$auteur/sexe/text()}</p>
                {if (fn:string-length($bio) > 0) then
                    <p>Biographie : {$bio}</p>
                 else ()
                }
                <p>Listes des recettes :
                <ul>
                {
                    for $idr in $auteur/ref-recette/@ref
                    return
                        for $recette in doc("RecepiesTransforme.xml")//recette
                        where $recette/@id = $idr
                        return
                            <li><a href="recettes.html#{$idr}">{$recette/titre/text()}</a></li>
                }
                </ul></p>
                <p>Listes des catégories :
                <ul>
                {
                    for $idr in $auteur/ref-recette/@ref
                    return
                        for $cate in distinct-values(doc("RecepiesTransforme.xml")//recette[@id=$idr]/@sous-categorie)
                        return
                            <li><a href="categories.html#{$cate}">{//sous-categorie[@id=$cate]/nom/text()}</a></li>
                }
                </ul></p>
            </div>
    }
    </body>
</html>