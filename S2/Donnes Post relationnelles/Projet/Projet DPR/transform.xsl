<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"
                doctype-system="Recepies.dtd" />
    <xsl:template match="/">
        <Recettes>
            <xsl:call-template name="liste-des-recettes"></xsl:call-template>
            <xsl:call-template name="liste-des-categories"></xsl:call-template>
            <xsl:call-template name="liste-des-sous-categories"></xsl:call-template>
            <xsl:call-template name="liste-des-ingredients"></xsl:call-template>
            <xsl:call-template name="liste-des-produits"></xsl:call-template>
            <xsl:call-template name="liste-des-auteurs"></xsl:call-template>
        </Recettes>
    </xsl:template>

    <xsl:template name="liste-des-recettes" >
        <xsl:for-each select="//objet[@type = 'recette']">
            <recette>
                <xsl:attribute name="id">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <titre><xsl:value-of select="info[@nom='nom']/@value"/></titre>
                <photo>
                    <xsl:attribute name="href">
                        <xsl:value-of select="info[@nom='photo']/@value"/>
                    </xsl:attribute>
                </photo>
                <resume><xsl:copy-of select="info[@nom='résumé']/*"/></resume>
                <date-publication><xsl:value-of select="info[@nom='date_publication']/@value"/></date-publication>
                <difficulte><xsl:value-of select="info[@nom='difficulté']/@value"/></difficulte>
                <xsl:for-each select="info[@nom='ingrédient']">
                    <ref-ingredient>
                        <xsl:attribute name="ref">
                            <xsl:value-of select="@value"/>
                        </xsl:attribute>
                        <xsl:attribute name="quantite">
                            <xsl:value-of select="@quantite"/>
                        </xsl:attribute>
                    </ref-ingredient>
                </xsl:for-each>
                <temps-preparation><xsl:value-of select="info[@nom='Préparation']/@value"/></temps-preparation>
                <temps-cuisson><xsl:value-of select="info[@nom='Cuisson']/@value"/></temps-cuisson>
                <temps-repos><xsl:value-of select="info[@nom='Repos']/@value"/></temps-repos>
                <nb-personnes><xsl:value-of
                        select="info[@nom='nbre_personnes']/@value"/></nb-personnes>
                <description>
                    <xsl:copy-of select="info[@nom='Préparation']/*"/></description>
                <note></note>
            </recette>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-ingredients">
        <xsl:for-each select="//objet[@type = 'ingrédient']">
            <ingredient>
                <xsl:attribute name="id">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <xsl:attribute name="refprod">
                    <xsl:value-of select="info[@nom='produit']/@value"/>
                </xsl:attribute>
                <nom><xsl:value-of select="info[@nom='nom']/@value"/></nom>
                <apport-nutritif><xsl:value-of select="info[@nom='Apport nutritif']/@value"/></apport-nutritif>
                <apport-energetique><xsl:value-of select="info[@nom='Apport énergétique']/@value"/></apport-energetique>
                <saison><xsl:value-of select="info[@nom='saison']/@value"/></saison>
                <description><xsl:value-of select="info[@nom='descriptif']/@value"/></description>
                <xsl:for-each select="info[@nom='recette']">
                    <ref-recette>
                        <xsl:attribute name="ref">
                            <xsl:value-of select="@value"/>
                        </xsl:attribute>
                    </ref-recette>
                </xsl:for-each>
            </ingredient>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-produits">
        <xsl:for-each select="//objet[@type = 'produit']">
            <produit>
                <xsl:attribute name="id">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <nom><xsl:value-of select="info[@nom='nom']/@value"/></nom>
                <nom-court><xsl:value-of select="info[@nom='nom-court']/@value"/></nom-court>
                <xsl:for-each select="info[@nom='ingrédient']">
                    <ref-ingredient>
                        <xsl:attribute name="ref">
                            <xsl:value-of select="@value"/>
                        </xsl:attribute>
                    </ref-ingredient>
                </xsl:for-each>
            </produit>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-sous-categories">
        <xsl:for-each select="//objet[@type = 'sous-catégorie']">
            <sous-categorie>
                <xsl:attribute name="id">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <xsl:attribute name="categorie">
                    <xsl:value-of select="info[@nom='catégorie']/@value"/>
                </xsl:attribute>
                <nom><xsl:value-of select="info[@nom='nom']/@value"/></nom>
                <nom-court><xsl:value-of select="info[@nom='nom-court']/@value"/></nom-court>
                <description><xsl:copy-of select="info[@nom='descriptif']/*"/></description>
            </sous-categorie>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-auteurs">
        <xsl:for-each select="//objet[@type = 'auteur']">
            <auteur>
                <xsl:attribute name="id">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <nom >
                    <xsl:value-of select="info[@nom='prenom']/@value"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="info[@nom='nom']/@value"/>
                </nom>
                <pays><xsl:value-of select="info[@nom='pays']/@value"/></pays>
                <sexe><xsl:value-of select="info[@nom='sexe']/@value"/></sexe>
                <xsl:for-each select="info[@nom='recette']">
                    <ref-recette>
                        <xsl:attribute name="ref">
                            <xsl:value-of select="@value"/>
                        </xsl:attribute>
                    </ref-recette>
                </xsl:for-each>
            </auteur>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="liste-des-categories">
        <xsl:for-each select="//objet[@type = 'catégorie']">
            <categorie>
                <xsl:attribute name="id">
                    <xsl:value-of select="@id"/>
                </xsl:attribute>
                <nom><xsl:value-of select="info[@nom='nom']/@value"/></nom>
                <nom-court><xsl:value-of select="info[@nom='nom-court']/@value"/></nom-court>
                <description><xsl:copy-of select="info[@nom='descriptif']/*"/></description>
            </categorie>
        </xsl:for-each>
    </xsl:template>



</xsl:stylesheet>