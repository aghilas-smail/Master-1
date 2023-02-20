<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output name="my-output" method="html" encoding="ISO-8859-1" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>
    <xsl:template match="/">
        <xsl:result-document href="recettes.html" format="my-output">
            <html>
                <head>
                    <title>Recettes</title>
                </head>
                <body>
                    <h1>Les recettes</h1>
                    <xsl:call-template name="liste-des-recettes" />
                    <h1>Les fiches des recettes</h1>
                    <xsl:call-template name="fiches-des-recettes" />
                </body>
            </html>
        </xsl:result-document>

        <xsl:result-document href="auteurs.html" format="my-output">
            <html>
                <head>
                    <title>Auteurs</title>
                </head>
                <body>
                    <h1>Les auteurs</h1>
                    <xsl:call-template name="liste-des-auteurs" />
                    <h1>Les fiches des auteurs</h1>
                    <xsl:call-template name="fiches-des-auteurs" />
                </body>
            </html>
        </xsl:result-document>

        <xsl:result-document href="ingredients.html" format="my-output">
            <html>
                <head>
                    <title>Ingrédints</title>
                </head>
                <body>
                    <h1>Les Ingrédents</h1>
                    <xsl:call-template name="liste-des-ingredients" />
                    <h1>Les fiches des ingrédients</h1>
                    <xsl:call-template name="fiches-des-ingredients" />
                </body>
            </html>
        </xsl:result-document>

        <xsl:result-document href="produits.html" format="my-output">
            <html>
                <head>
                    <title>Produits</title>
                </head>
                <body>
                    <h1>Les produits</h1>
                    <xsl:call-template name="liste-des-produits" />
                    <h1>Les fiches des produits</h1>
                    <xsl:call-template name="fiches-des-produits" />
                </body>
            </html>
        </xsl:result-document>

        <xsl:result-document href="categories.html" format="my-output">
            <html>
                <head>
                    <title>Categories</title>
                </head>
                <body>
                    <h1>Les categories</h1>
                    <xsl:call-template name="liste-des-categories" />
                    <h1>Les fiches des categories</h1>
                    <xsl:call-template name="fiches-des-categories" />
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="fiches-des-recettes">
        <xsl:for-each select="//recette">
            <xsl:sort select="date-publication" order="descending"/>
            <xsl:variable name="id" select="@id"/>
            <div id="{$id}">
                <h2><xsl:value-of select="titre/text()"/></h2>
                <img>
                    <xsl:attribute name="src">
                        <xsl:value-of select="photo/@href"/>
                    </xsl:attribute>
                </img>
                <p>resumé : <xsl:copy-of select="resume/*"/></p>
                <p>date de publication : <xsl:value-of select="date-publication/text()"/></p>
                <p>difficulté : <xsl:value-of select="difficulte/text()"/></p>
                <p>Les ingrédients :
                    <ul>
                        <xsl:for-each select="ref-ingredient">
                            <xsl:variable name="idi" select="@ref"/>
                            <li><xsl:value-of select="@quantite"/> - <a href="ingredients.html#{$idi}"><xsl:value-of select="ancestor::node()//ingredient[@id=$idi]/nom/text()"/></a></li>
                        </xsl:for-each>
                    </ul>
                </p>
                <p>temps de préparation : <xsl:value-of select="temps-preparation/text()"/></p>
                <p>temps de cuisson : <xsl:value-of select="temps-cuisson/text()"/></p>
                <p>temps de repos : <xsl:value-of select="temps-repos/text()"/></p>
                <p>pour <xsl:value-of select="nb-personnes/text()"/> personnes</p>
                <p>description : <xsl:copy-of select="description/*"/></p>
                <p>note : <xsl:value-of select="note/text()"/></p>
                <p>Par :
                    <ul>
                        <xsl:for-each select="parent::node()/auteur[ref-recette[@ref=$id]]">
                            <xsl:variable name="ida" select="@id"/>
                            <li><a href="auteurs.html#{$ida}" ><xsl:value-of select="nom/text()"/></a></li>
                        </xsl:for-each>
                    </ul>
                </p>
                <p>Catégorie :
                    <xsl:variable name="idc" select="@sous-categorie"/>
                    <a href="categories.html#{$idc}"><xsl:value-of select="ancestor::node()//sous-categorie[@id=$idc]/nom/text()"/></a>
                </p>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-recettes">
        <ul>
            <xsl:for-each select="//recette">
                <xsl:sort select="date-publication" order="descending"/>
                <xsl:variable name="id" select="@id"/>
                <li><a href="#{$id}" ><xsl:value-of select="titre/text()"/></a></li>
            </xsl:for-each>
        </ul>
    </xsl:template>

    <xsl:template name="fiches-des-auteurs">
        <xsl:for-each select="//auteur">
            <xsl:sort select="nom"/>
            <xsl:variable name="id" select="@id"/>
            <div id="{$id}">
                <h2><xsl:value-of select="nom/text()"/></h2>
                <p>pays : <xsl:value-of select="pays/text()"/></p>
                <p>sexe : <xsl:value-of select="sexe/text()"/></p>
                <p>biographie : <xsl:value-of select="biographie/text()"/></p>
                <p>Liste des recettes :
                    <ul>
                        <xsl:for-each select="ref-recette">
                            <xsl:variable name="idr" select="@ref"/>
                            <li><a href="recette.html#{$idr}"><xsl:value-of select="ancestor::node()//recette[@id=$idr]/titre/text()"/></a></li>
                        </xsl:for-each>
                    </ul>
                </p>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-auteurs">
        <xsl:for-each select="//auteur">
            <xsl:sort select="nom"/>
            <xsl:variable name="id" select="@id"/>
            <li><a href="#{$id}" ><xsl:value-of select="nom/text()"/></a></li>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="fiches-des-ingredients">
        <xsl:for-each select="//ingredient">
            <xsl:variable name="id" select="@id"/>
            <div id="{$id}">
                <h2><xsl:value-of select="nom/text()"/></h2>
                <p>apport nutritif : <xsl:value-of select="apport-nutritif/text()"/></p>
                <p>apport énergetique : <xsl:value-of select="apport-energetique/text()"/></p>
                <p>saison : <xsl:value-of select="saison/text()"/></p>
                <p>description : <xsl:value-of select="description/text()"/></p>
                <p>
                    <ul>
                        <xsl:for-each select="ref-recette">
                            <xsl:variable name="idr" select="@ref"/>
                            <li><a href="recette.html#{$idr}"><xsl:value-of select="ancestor::node()//recette[@id=$idr]/titre/text()"/></a></li>
                        </xsl:for-each>
                    </ul>
                </p>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-ingredients">
        <xsl:for-each select="//ingredient">
            <xsl:variable name="id" select="@id"/>
            <li><a href="#{$id}" ><xsl:value-of select="nom/text()"/></a></li>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="fiches-des-produits">
        <xsl:for-each select="//produit">
            <xsl:variable name="id" select="@id"/>
            <div id="{$id}">
                <h2><xsl:value-of select="nom/text()"/></h2>
                <p>
                    <ul>
                        <xsl:for-each select="ref-ingredient">
                            <xsl:variable name="idi" select="@ref"/>
                            <li><a href="ingredients.html#{$idi}"><xsl:value-of select="ancestor::node()//ingredient[@id=$idi]/nom/text()"/></a></li>
                        </xsl:for-each>
                    </ul>
                </p>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-produits">
        <xsl:for-each select="//produit">
            <xsl:variable name="id" select="@id"/>
            <li><a href="#{$id}" ><xsl:value-of select="nom/text()"/></a></li>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="fiches-des-categories">
        <xsl:for-each select="//categorie">
            <xsl:variable name="id" select="@id"/>
            <div id="{$id}">
                <h2><xsl:value-of select="nom/text()"/></h2>
                <p>Description : <xsl:copy-of select="description/*"/></p>
                <xsl:for-each select="parent::node()//sous-categorie[@categorie=$id]">
                    <xsl:variable name="ids" select="@id"/>
                    <div style="margin-left:40px;" id="{$ids}">
                        <h3><xsl:value-of select="nom/text()"/></h3>
                        <p>Description : <xsl:copy-of select="description/*"/></p>
                        <p>Liste des recettes :
                            <ul>
                                <xsl:for-each select="ancestor::node()//recette[@sous-categorie=$ids]">
                                    <xsl:variable name="idr" select="@id"/>
                                    <li><a href="recettes.html#{@idr}"><xsl:value-of select="titre/text()"/></a></li>
                                </xsl:for-each>
                            </ul>
                        </p>
                    </div>
                </xsl:for-each>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-categories">
        <xsl:for-each select="//categorie">
            <xsl:variable name="id" select="@id"/>
            <li><a href="#{$id}" ><xsl:value-of select="nom/text()"/></a></li>
            <xsl:for-each select="parent::node()//sous-categorie[@categorie=$id]">
                <xsl:variable name="ids" select="@id"/>
                <li style="margin-left:20px;"><a href="#{$ids}" ><xsl:value-of select="nom/text()"/></a></li>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>