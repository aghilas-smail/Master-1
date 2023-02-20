<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:template match="/">
        <xsl:call-template name="index" />
        <xsl:call-template name="recettes" />
        <xsl:call-template name="categories" />
        <xsl:call-template name="auteurs" />
        <xsl:call-template name="ingredients" />
        <xsl:call-template name="produits" />
        <xsl:call-template name="recettes-categories" />
    </xsl:template>

    <xsl:template name="index">
        <xsl:result-document method="html" href="index.html">
            <html>
                <xsl:call-template name="header" />
                <body>
                    <xsl:call-template name="menu" />
                    <div class="container" style="margin-top: 60px;">
                        Bienvenue sur le site développé par Benjamin BEN ESTI et César Cardinale, grâce au XML et au XSL pour le projet de Données Post Relationnelles du semestre 2 du Master 1 d'Informatique de Luminy.
                    </div>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="recettes" match="/Recettes">
        <xsl:result-document method="html" href="Recettes.html">
            <html>
                <xsl:call-template name="header" />
                <body>
                    <xsl:call-template name="menu" />
                    <div class="container">
                        <div class="row recipe" id="recettes">
                            <h1 class="text-center">Toutes nos recettes</h1>
                            <xsl:for-each select="/Recettes/Recette">
                                <xsl:sort select="DatePublication" data-type="text" order="descending"/>
                                <xsl:call-template name="recette" />
                            </xsl:for-each>
                        </div>
                    </div>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="recette" match="/Recettes/Recette">
        <xsl:variable name="id-cateogry" select="@sous-categorie"/>
        <div class="row justify-content-center recipe">
            <div class="col-10 mb-3">
                <xsl:attribute name="id">rece_<xsl:value-of select="@id-recette"/></xsl:attribute>
                <div class="img">
                    <xsl:attribute name="style">background-image: url('<xsl:value-of select="Photo"/>')</xsl:attribute>
                    <h2><xsl:value-of select="Titre"/></h2>
                </div>
                <div class="row justify-content-center">
                    <div>
                        <xsl:for-each select="/Recettes/Sous-categories/Sous-categorie">
                            <xsl:if test="@id = $id-cateogry">
                                <xsl:variable name="id-cateogry-parent" select="@cat"/>
                                <xsl:for-each select="/Recettes/Categories/Categorie">
                                    <xsl:if test="@id-categorie = $id-cateogry-parent">
                                        <a>
                                            <xsl:attribute name="href">Categories.html#cat_<xsl:value-of select="$id-cateogry-parent"/></xsl:attribute>
                                            <xsl:value-of select="@nom"/>
                                        </a> >
                                    </xsl:if>
                                </xsl:for-each>
                                <a>
                                    <xsl:attribute name="href">Categories.html#cat_<xsl:value-of select="$id-cateogry"/></xsl:attribute>
                                    <xsl:value-of select="@nom"/>
                                </a>
                            </xsl:if>
                        </xsl:for-each>
                    </div>
                </div>
                <div class="row data">
                    <div class="col-6">Pour <xsl:value-of select="NbPersonnes"/> personne<xsl:if test="NbPersonnes &gt; 1">s</xsl:if></div>
                    <xsl:if test="number(Note) > 0"><div class="col-6"><xsl:value-of select="Note"/> ★</div></xsl:if>
                </div>
                <xsl:if test="string-length(Resume)>0"><div class="resume"><xsl:value-of select="Resume"/></div></xsl:if>
                <div class="ingredients">
                    <span>Ingrédients :</span>
                    <ul>
                        <xsl:for-each select="ListeIngredients/Ingredient">
                            <xsl:variable name="id-ingredient" select="@id"/>
                            <li class="ingredient">
                                <xsl:value-of select="@quantite"/>
                                <a>
                                    <xsl:attribute name="href">./Ingredients.html#ingr_<xsl:value-of select="$id-ingredient"/></xsl:attribute>
                                    <xsl:value-of select="concat(' ',/Recettes/Ingredients/Ingredient[@id-ingredient = $id-ingredient]/Nom)"/>
                                </a>
                            </li>
                        </xsl:for-each>
                    </ul>
                </div>
                <div class="time">
                    <xsl:if test="number(translate(TempsPreparation, 'min', '')) > 0"><div>Temps de préparation : <xsl:value-of select="TempsPreparation" /></div></xsl:if>
                    <xsl:if test="number(translate(TempsCuisson, 'min', '')) > 0"><div>Temps de cuisson : <xsl:value-of select="TempsCuisson" /></div></xsl:if>
                    <xsl:if test="number(translate(TempsRepos, 'min', '')) > 0"><div>Temps de repos : <xsl:value-of select="TempsRepos" /></div></xsl:if>
                </div>
                <div class="steps">
                    <xsl:call-template name="tokenize">
                        <xsl:with-param name="text" select="Description"/>
                    </xsl:call-template>
                    <xsl:value-of select="Description" />
                </div>
                <div class="date">
                    Publié le <xsl:value-of select="DatePublication"/>
                    <xsl:if test="ListeAuteurs/Auteur">
                        par
                        <xsl:for-each select="ListeAuteurs/Auteur">
                            <xsl:variable name="id-auteur" select="@idref"/>
                            <a>
                                <xsl:attribute name="href">./Auteurs.html#aute_<xsl:value-of select="$id-auteur"/></xsl:attribute>
                                <xsl:value-of select="/Recettes/Auteurs/Auteur[@id = $id-auteur]/Nom"></xsl:value-of>
                            </a>
                        </xsl:for-each>
                    </xsl:if>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="categories" match="/Recettes/Categories">
        <xsl:result-document method="html" href="Categories.html">
            <html>
                <xsl:call-template name="header" />
                <body>
                    <xsl:call-template name="menu" />
                    <div class="row justify-content-center categories" id="categories">
                        <h1 class="text-center">Toutes les catégories</h1>
                        <xsl:for-each select="/Recettes/Categories/Categorie">
                            <xsl:variable name="id-category" select="@id-categorie"/>
                            <div class="col-sm-10 mb-3">
                                <xsl:attribute name="id">cat_<xsl:value-of select="$id-category"/></xsl:attribute>
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">
                                            <a>
                                                <xsl:attribute name="href">Recettes-cat.html#cat_<xsl:value-of select="$id-category"/></xsl:attribute>
                                                <xsl:value-of select="@nom" />
                                            </a>
                                        </h4>
                                        <div><xsl:value-of select="Descriptif" /></div>
                                        <ul class="list-group">
                                            <xsl:for-each select="/Recettes/Sous-categories/Sous-categorie">
                                                <xsl:if test="$id-category = @cat" >
                                                    <li class="list-group-item">
                                                        <xsl:attribute name="id">cat_<xsl:value-of select="@id"/></xsl:attribute>
                                                        <a>
                                                            <xsl:attribute name="href">Recettes-cat.html#cat_<xsl:value-of select="@id"/></xsl:attribute>
                                                            <h5><xsl:value-of select="@nom" /></h5>
                                                        </a>
                                                        <div><xsl:value-of select="Descriptif" /></div>
                                                    </li>
                                                </xsl:if>
                                            </xsl:for-each>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </xsl:for-each>
                    </div>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="ingredients" match="/Recettes/Ingredients">
        <xsl:result-document method="html" href="Ingredients.html">
            <html>
                <xsl:call-template name="header" />
                <body>
                    <xsl:call-template name="menu" />
                    <div class="row justify-content-center ingredients" id="ingredients">
                        <h1 class="text-center">Tous les ingrédients</h1>
                        <div class="col-10 mb-3">
                            <ul>
                                <xsl:for-each select="/Recettes/Ingredients/Ingredient">
                                    <li class="main">
                                        <xsl:variable name="id-ingredient" select="@id-ingredient"/>
                                        <xsl:attribute name="id">ingr_<xsl:value-of select="$id-ingredient"/></xsl:attribute>
                                        <h4><xsl:value-of select="Nom" /></h4>
                                        <div><xsl:value-of select="ApportNutritif" /></div>
                                        <div><xsl:value-of select="ApportÉnergétique" /></div>
                                        <xsl:if test="string-length(Saison) > 0">
                                            <div>Saison : <xsl:value-of select="Saison" /></div>
                                        </xsl:if>
                                        <div>Recettes associées :</div>
                                        <xsl:for-each select="/Recettes/Recette">
                                            <xsl:variable name="recette-titre" select="Titre"/>
                                            <xsl:variable name="id-recette" select="@id-recette"/>
                                            <xsl:for-each select="ListeIngredients/Ingredient">
                                                <xsl:if test="@id = $id-ingredient">
                                                    <li class="second">
                                                        <a>
                                                            <xsl:attribute name="href">./Recettes.html#rece_<xsl:value-of select="$id-recette"/></xsl:attribute>
                                                            <xsl:value-of select="$recette-titre"/>
                                                        </a>
                                                    </li>
                                                </xsl:if>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </li>
                                </xsl:for-each>
                            </ul>
                        </div>
                    </div>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>
    <xsl:template name="produits" match="/Recettes/Produits">
        <xsl:result-document method="html" href="Produits.html">
            <html>
                <xsl:call-template name="header" />
                <body>
                    <xsl:call-template name="menu" />
                    <div class="row justify-content-center products" id="products">
                        <h1 class="text-center">Tous les produits</h1>
                        <xsl:for-each select="/Recettes/Produits/Produit">
                            <xsl:variable name="id-produit" select="@id-produit"/>
                            <div class="col-10 mb-3">
                                <xsl:attribute name="id">prod_<xsl:value-of select="$id-produit"/></xsl:attribute>
                                <h4><xsl:value-of select="@nom" /></h4>
                                <xsl:for-each select="/Recettes/Ingredients/Ingredient">
                                    <xsl:if test="$id-produit = @id-produit" >
                                        <a>
                                            <xsl:attribute name="href">./Ingredients.html#ingr_<xsl:value-of select="@id-ingredient"/></xsl:attribute>
                                            <h4><xsl:value-of select="Nom" /></h4>
                                        </a>
                                    </xsl:if>
                                </xsl:for-each>
                            </div>
                        </xsl:for-each>
                    </div>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>
    <xsl:template name="auteurs" match="/Recettes/Auteurs">
        <xsl:result-document method="html" href="Auteurs.html">
            <html>
                <xsl:call-template name="header" />
                <body>
                    <xsl:call-template name="menu" />
                    <div class="row justify-content-center authors" id="authors">
                        <h1 class="text-center">Tous les auteurs</h1>
                        <xsl:for-each select="/Recettes/Auteurs/Auteur">
                            <xsl:sort select="Nom" data-type="text" order="ascending"/>
                            <div class="col-sm-10 mb-3">
                                <div class="card">
                                    <xsl:attribute name="id">aute_<xsl:value-of select="@id"/></xsl:attribute>
                                    <div class="card-body">
                                        <h4 class="card-title"><xsl:value-of select="Nom" /></h4>
                                        <div><xsl:value-of select="Age" /></div>
                                        <div><xsl:value-of select="Pays" /></div>
                                        <div><xsl:value-of select="Biographie" /></div>
                                        <ul class="list-group">
                                            <xsl:for-each select="ListeRecettes/Recette">
                                                <xsl:variable name="id-recette" select="@id"/>
                                                <xsl:for-each select="/Recettes/Recette">
                                                    <xsl:if test="$id-recette = @id-recette" >
                                                        <li class="list-group-item">
                                                            <a>
                                                                <xsl:attribute name="href">./Recettes.html#rece_<xsl:value-of select="@id-recette"/></xsl:attribute>
                                                                <h6><xsl:value-of select="Titre" /></h6>
                                                            </a>
                                                        </li>
                                                    </xsl:if>
                                                </xsl:for-each>
                                            </xsl:for-each>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </xsl:for-each>
                    </div>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>


    <xsl:template name="recettes-categories" match="/Recettes/Categories">
        <xsl:result-document method="html" href="Recettes-cat.html">
            <html>
                <xsl:call-template name="header" />
                <body>
                    <xsl:call-template name="menu" />
                    <div class="row justify-content-center receipes-categories" id="categories">
                        <h1 class="text-center">Toutes les recettes par catégories</h1>
                        <xsl:for-each select="/Recettes/Categories/Categorie">
                            <xsl:variable name="id-category" select="@id-categorie"/>
                            <div class="col-sm-10 mb-3">
                                <xsl:attribute name="id">cat_<xsl:value-of select="$id-category"/></xsl:attribute>
                                <h4><xsl:value-of select="@nom" /></h4>
                            </div>
                            <xsl:for-each select="/Recettes/Sous-categories/Sous-categorie">
                                <xsl:variable name="id-subcategory" select="@id" />
                                <xsl:if test="$id-category = @cat" >
                                    <div class="col-sm-10 mb-3">
                                        <xsl:attribute name="id">cat_<xsl:value-of select="@id"/></xsl:attribute>
                                        <h5><xsl:value-of select="@nom" /></h5>
                                    </div>
                                    <xsl:for-each select="/Recettes/Recette">
                                        <xsl:if test="$id-subcategory = @sous-categorie" >
                                            <xsl:call-template name="recette" />
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:if>
                            </xsl:for-each>

                        </xsl:for-each>
                    </div>
                </body>
            </html>
        </xsl:result-document>
    </xsl:template>

    <xsl:template name="tokenize">
        <xsl:param name="text"/>
        <xsl:param name="delimiter" select="'\r'"/>
        <xsl:choose>
            <xsl:when test="contains($text, $delimiter)">
                <xsl:value-of select="substring-before($text, $delimiter)"/>
                <br/>
                <!-- recursive call -->
                <xsl:call-template name="tokenize">
                    <xsl:with-param name="text" select="substring-after($text, $delimiter)"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$text"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="header">
        <head>
            <link href="style.css" rel="stylesheet" type="text/css" />
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous" />
            <meta name="viewport" content="width=device-width, initial-scale=1" />
        </head>
    </xsl:template>
    <xsl:template name="menu">
        <nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <div class="collapse navbar-collapse">
                    <div class="navbar-nav me-auto mb-2 mb-lg-0">
                        <a class="navbar-brand" href="./index.html">DPR Recettes</a>
                        <a class="nav-link" href="./Recettes.html">Toutes les recettes</a>
                        <a class="nav-link" href="./Categories.html">Les catégories</a>
                        <a class="nav-link" href="./Produits.html">Les produits</a>
                        <a class="nav-link" href="./Ingredients.html">Les ingrédients</a>
                        <a class="nav-link" href="./Auteurs.html">Les auteurs</a>
                    </div>
                </div>
            </div>
        </nav>
    </xsl:template>
</xsl:stylesheet>