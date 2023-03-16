<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

<xsl:output method="html" version="5" encoding="UTF-8" indent="yes" />
<xsl:strip-space elements="*" />

<!--Règle 1-->
<xsl:template match="/">
   <html><body>
  <xsl:apply-templates/>	
   	

       </body></html>
   </xsl:template>

<!--Règle 2-->

<xsl:template match="auteurs">
	<xsl:for-each select="auteur">
		<h1> <xsl:value-of select="@nom"> </xsl:value-of> </h1>
		   <br/>
	</xsl:for-each>
</xsl:template>
<xsl:template match="chapitre">
       <h2>Ses paragraphes: <xsl:value-of select="paragraphe"/>
       </h2>
    </xsl:template>


</xsl:stylesheet>

