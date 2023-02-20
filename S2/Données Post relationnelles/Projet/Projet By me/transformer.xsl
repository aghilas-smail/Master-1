<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet  version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"
        doctype-system="recette.dtd" />
    <xsl:template match="/">
        <Recettes>
            <xsl:call-template name="liste-des-recettes"></xsl:call-template>
        </Recettes>
    </xsl:template>

    <xsl:template name="liste-des-recettes">
        <xsl:for-each select = "//object[@type = 'recette']">
            <recette>
                <xsl:attribute name = "id">
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
</xsl:stylesheet>
