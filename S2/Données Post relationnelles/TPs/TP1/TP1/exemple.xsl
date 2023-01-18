<?xml version="1.0" encoding="iso-8859-1" ?>


<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml"  >
<xsl:output method="xhtml" indent="yes" encoding=" iso-8859-1 "  doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>



  <xsl:template match="stock">
  
    <html>	<head>
			<title></title>
		</head><body>
      <h1>Stock de la société</h1>
      <xsl:apply-templates/>
    </body></html>
  </xsl:template>

  <xsl:template match="produit">
    <h2>Produit <xsl:value-of select="nom"/></h2>
    <xsl:apply-templates select="prix"/>
  </xsl:template>

  <xsl:template match="prix">
    <p>Prix : <xsl:value-of select="."/> en
    <xsl:value-of select="@monnaie"/></p>
  </xsl:template>

</xsl:stylesheet>
