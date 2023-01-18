<?xml version="1.0" encoding="iso-8859-1" ?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >

  <xsl:output method="text"/>


	<xsl:template match="@*">
		<xsl:apply-templates select="." mode="indent" />
		<xsl:value-of select="namespace-uri(.)" />
		<xsl:value-of select="name(.)" />
		<xsl:text>="</xsl:text>
		<xsl:value-of select="translate(.,'&#10;','R')" />
		<xsl:text>"</xsl:text>
		<xsl:text>&#10;</xsl:text>
	</xsl:template>


	<xsl:template match="/">
		<xsl:text> /&#10;</xsl:text>
		<xsl:apply-templates select="node()" />
	</xsl:template>


	<xsl:template match="node()">
		<xsl:apply-templates select="." mode="indent" />
		<xsl:choose>
			<xsl:when test="not(parent::node())">
				<xsl:text> /</xsl:text>
			</xsl:when>
			<xsl:when test="self::text()">
				<xsl:text>"</xsl:text>
				<xsl:value-of select="translate(.,'&#10;','R')" />
				<xsl:text>"</xsl:text>
			</xsl:when>
			<xsl:when test="self::processing-instruction()">
				<xsl:text>&lt;?</xsl:text>
				<xsl:value-of select="name(.)" />
				<xsl:text> </xsl:text>
				<xsl:value-of select="translate(.,'&#10;','R')" />
				<xsl:text>?&gt;</xsl:text>
			</xsl:when>
			<xsl:when test="self::comment()">
				<xsl:text>&lt;!--</xsl:text>
				<xsl:value-of select="translate(.,'&#10;','R')" />
				<xsl:text>--&gt;</xsl:text>
			</xsl:when>
			<xsl:when test="self::*">
				<xsl:value-of select="name(.)" />
			</xsl:when>
		</xsl:choose>
		<xsl:text>&#10;</xsl:text>
		<xsl:for-each select="namespace::*" >
			<xsl:apply-templates select=".." mode="indent" />
			<xsl:text> | xmlns:</xsl:text>
			<xsl:value-of select="name(.)" />
			<xsl:text>=</xsl:text>
			<xsl:value-of select="(.)" />
			<xsl:text>&#10;</xsl:text>
		</xsl:for-each>
		<xsl:apply-templates select="attribute::*" />
		<xsl:apply-templates select="node()" />
	</xsl:template>


	<xsl:template match="/" mode="indent">
	</xsl:template>


	<xsl:template match="@* | node()" mode="indent">
		<xsl:text> | </xsl:text>
		<xsl:apply-templates select="parent::node()" mode="indent" />
	</xsl:template>


</xsl:stylesheet>
