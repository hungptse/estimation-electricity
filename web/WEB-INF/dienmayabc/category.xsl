<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="https://dienmayabc.com/">
    <xsl:output encoding="UTF-8" method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:variable name="host">https://dienmayabc.com</xsl:variable>
    <xsl:template match="/">
        <xsl:variable name="doc" select="//div[@class='navigation hide']"/>
        <categories>
            <xsl:for-each select="$doc//a[@data-view='.menu-top']">
                <category>
                    <name>
                        <xsl:value-of select="h2"/>
                    </name>
                    <url>
                        <xsl:value-of select="$host"/><xsl:value-of select="@href"/>
                    </url>
                </category>
            </xsl:for-each>
        </categories>
    </xsl:template>

</xsl:stylesheet>