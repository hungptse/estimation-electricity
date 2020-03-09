<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="https://eco-mart.com.vn">
    <xsl:output encoding="UTF-8" method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:variable name="domain">https://eco-mart.com.vn</xsl:variable>
    <xsl:template match="/">
        <products>
            <xsl:for-each select="//div[@class='product clearfix']">
                <product>
                    <title>
                        <xsl:value-of select="div/div/h4/a"/>
                    </title>
                    <code>
                        <xsl:value-of select="div/div/span"/>
                    </code>
                    <url>
                        <xsl:value-of select="$domain"/><xsl:value-of select="div/div/h4/a/@href"/>
                    </url>
                    <image>
                        <xsl:value-of select="substring-before($domain,'/')"/><xsl:value-of
                            select="div/a/img/@data-lazyload"/>
                    </image>
                    <wattage>1</wattage>
                    <unit>W</unit>
                </product>
            </xsl:for-each>
        </products>
    </xsl:template>

</xsl:stylesheet>