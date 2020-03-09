<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="UTF-8" method="xml" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="/">
        <xsl:variable name="doc" select="//div[@id='tabs-1']"/>
        <xsl:variable name="lowercase" select="'abcdefghijklmnopqrstuvwxyz'"/>
        <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>

        <xsl:choose>
            <xsl:when test="$doc//*[contains(translate(text(), $uppercase, $lowercase),'Công suất định danh')]">
                1 <xsl:value-of select="$doc//*[contains(translate(text(), $uppercase, $lowercase),'Công suất định danh')]"/>
            </xsl:when>
            <xsl:when test="$doc//*[contains(translate(text(), $uppercase, $lowercase),'công suất') and not(contains(text(),'mL/h')) and not(contains(text(),'gas')) or contains(translate(text(), $uppercase, $lowercase),'Công suất tiêu thụ trung bình')]">
                2<xsl:if test="$doc//*[contains(translate(text(), $uppercase, $lowercase),'công suất') and not(contains(text(),'mL/h')) and not(contains(text(),'gas'))]/../*[2]">
                    <xsl:value-of select="$doc//*[contains(translate(text(), $uppercase, $lowercase),'công suất') and not(contains(text(),'mL/h')) and not(contains(text(),'gas'))]/../*[2]"/>
                </xsl:if>
                <xsl:if test="$doc//*[contains(translate(text(), $uppercase, $lowercase),'Công suất tiêu thụ trung bình')]">
                    <xsl:value-of select="$doc//*[contains(translate(text(), $uppercase, $lowercase),'Công suất tiêu thụ trung bình')]/../*[2]"/>
                </xsl:if>

<!--                <xsl:copy-of select="$doc//*[contains(translate(text(), $uppercase, $lowercase),'công suất') and not(contains(text(),'mL/h')) and not(contains(text(),'gas'))]/../*[2]"/>-->
            </xsl:when>
            <xsl:when test="$doc//*[contains(text(),'Điện năng')]">
               3 <xsl:value-of select="$doc//*[contains(text(),'Điện năng')]"/>
            </xsl:when>
            <xsl:when test="$doc//*[contains(translate(text(), $uppercase, $lowercase),'hiệu suất sử dụng điện')]">
               4 <xsl:value-of
                        select="$doc//*[contains(translate(text(), $uppercase, $lowercase),'hiệu suất sử dụng điện')]"/>
            </xsl:when>
            <xsl:when test="$doc//*[contains(translate(text(), $uppercase, $lowercase),'Điện năng tiêu thụ')]">
                5 <xsl:value-of
                        select="$doc//*[contains(translate(text(), $uppercase, $lowercase),'Điện năng tiêu thụ')]"/>
            </xsl:when>
            <xsl:when test="$doc//*[contains(translate(text(), $uppercase, $lowercase),'điện tiêu thụ')]">
                6 <xsl:value-of
                        select="$doc//*[contains(translate(text(), $uppercase, $lowercase),'điện tiêu thụ')]"/>
            </xsl:when>
            <xsl:when test="$doc//*[contains(translate(text(), $uppercase, $lowercase),'Tổng công suất loa')]">
                7 <xsl:value-of
                        select="$doc//*[contains(translate(text(), $uppercase, $lowercase),'Tổng công suất loa')]"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>