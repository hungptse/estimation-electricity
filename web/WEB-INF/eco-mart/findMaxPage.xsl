<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="UTF-8" method="text"/>

    <xsl:template match="/">
        <xsl:for-each select="//a[contains(@href,'page')]">
            <xsl:choose>
                <xsl:when test="position() = last() - 1">
                    <xsl:value-of select="substring-after(@href, '=')"/>
                </xsl:when>
            </xsl:choose>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>