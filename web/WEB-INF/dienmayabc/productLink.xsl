<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="UTF-8" method="text"/>
    <xsl:variable name="host">https://dienmayabc.com</xsl:variable>
    <xsl:template match="/">
            <xsl:for-each select="//div[@class='product']">
                <xsl:value-of select="h3/p[@class='name']/a/@href"/>
            </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>