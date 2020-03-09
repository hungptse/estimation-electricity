<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="https://dienmayabc.com/">
    <xsl:output encoding="UTF-8" method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:variable name="host">https://dienmayabc.com</xsl:variable>

    <xsl:template match="/">
        <xsl:variable name="doc" select="//div[@class='product-detail']"/>
        <product>
            <code>
                <xsl:value-of select="$doc//span[@class='value txt_blue']"/>
            </code>
            <name>
                <xsl:value-of select="$doc//div[@class='big']/ul/li[1]/a/@title"/>
            </name>
            <image>
                <xsl:value-of select="$host"/><xsl:value-of select="$doc//div[@class='big']/ul/li[1]/a/img/@src"/>
            </image>
            <wattage>
                <xsl:choose>
                    <xsl:when
                            test="$doc//*[@id='tab2']/table/tbody/tr[.//strong[contains(text(),'suất') and not(contains(text(),'loa') and not(contains(text(),'nóng')))]]">
                        <xsl:value-of
                                select="$doc//*[@id='tab2']/table/tbody/tr[.//*[contains(text(),'suất') and not(contains(text(),'loa'))]]"/>
                    </xsl:when>
                    <xsl:when
                            test="$doc//*[@id='tab2']/table/tbody/tr[.//strong[contains(text(),'năng') and not(contains(text(),'minh'))]]">
                        <xsl:value-of
                                select="$doc//*[@id='tab2']/table/tbody/tr[.//*[contains(text(),'năng') and not(contains(text(),'minh'))]]"/>
                    </xsl:when>
                    <xsl:when
                            test="$doc//*[@id='tab2']/table/tbody/tr[.//strong[contains(text(),'lượng') and not(contains(text(),'Khối'))]]">
                        <xsl:value-of
                                select="$doc//*[@id='tab2']/table/tbody/tr[.//*[contains(text(),'lượng') and not(contains(text(),'Khối'))]]"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'0'"/>
                    </xsl:otherwise>
                </xsl:choose>
            </wattage>
        </product>
    </xsl:template>
</xsl:stylesheet>