<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="UTF-8" method="xml" omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="/">
        <urls>
            <xsl:for-each select="//product">
                <url></url>
            </xsl:for-each>
        </urls>
    </xsl:template>

</xsl:stylesheet>