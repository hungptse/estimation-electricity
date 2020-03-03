<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="UTF-8" method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="/">
        <xsl:variable name="unitPrice">đồng/kWh</xsl:variable>
        <prices>
            <xsl:for-each select="//table[last()]/tbody/tr">
                <xsl:choose>
                    <xsl:when test="position() = last()">
                    </xsl:when>
                    <xsl:when test="position() > 1">
                        <price>
                            <level>
                                <xsl:value-of
                                        select="normalize-space(substring-before(substring-after(td[2]/p,' '),':'))"/>
                            </level>
                            <from>
                                <xsl:value-of
                                        select="normalize-space(substring-after(substring-before(td[2]/p,'-'),'từ'))"/>
                            </from>
                            <to>
                                <xsl:value-of select="normalize-space(substring-after(td[2]/p,'-'))"/>
                            </to>
                            <rate>
                                <xsl:value-of select="td[3]/p"/>
                            </rate>
                            <unit>
                                <xsl:value-of select="$unitPrice"/>
                            </unit>
                        </price>
                    </xsl:when>
                </xsl:choose>
            </xsl:for-each>
        </prices>

    </xsl:template>

</xsl:stylesheet>