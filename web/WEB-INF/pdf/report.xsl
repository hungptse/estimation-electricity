<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:param name="pathFile" select="'test'"/>
    <xsl:template match="/">

        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Tahoma">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="x" page-height="8.5in"
                                       page-width="11in" margin-top="0.5in" margin-bottom="0.5in"
                                       margin-left="1in" margin-right="1in">
                    <fo:region-body margin-top="0.5in" />
                    <fo:region-before extent="1in" />
                    <fo:region-after extent=".75in" />
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="x">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size="16pt" font-family="Helvetica" color="blue" font-weight="bold" space-after="5mm">
                        Report Estimate Electricity By Month
                    </fo:block>
<!--                    <fo:block font-size="14pt"-->
<!--                              line-height="24pt" background-color="cyan"-->
<!--                              space-after.optimum="15pt" text-align="center"-->
<!--                              padding-top="3pt">-->
<!--                        Report Estimate Electricity By Month-->
<!--                    </fo:block>-->
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-size="18pt"
                              line-height="24pt" space-after.optimum="15pt"
                              text-align="center" padding-top="3pt">
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <fo:table border-collapse="separate" table-layout="fixed">
                            <fo:table-column column-width="1cm"/>
                            <fo:table-column column-width="3cm"/>
                            <fo:table-column column-width="10cm"/>
                            <fo:table-column column-width="3cm"/>
                            <fo:table-column column-width="3cm"/>
                            <fo:table-column column-width="3cm"/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell border-color="blue"
                                                   border-width="0.5pt"
                                                   border-style="solid">
                                        <fo:block text-align="left">No.</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue"
                                                   border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="left">Code</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue"
                                                   border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="left">Name</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue"
                                                   border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="left">Wattage (W)</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue"
                                                   border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="left">Time (hour)</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-color="blue"
                                                   border-width="0.5pt" border-style="solid">
                                        <fo:block text-align="center">Total </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:for-each select="products/product">
                                    <fo:table-row>
                                        <fo:table-cell border-color="blue"
                                                       border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="left" font-family="Menlo">
                                                <xsl:number level="single" count="product"/>.
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue"
                                                       border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="left" font-family="Menlo">
                                                <xsl:value-of select="code"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue"
                                                       border-width="0.5pt" border-style="solid" >
                                            <fo:block text-align="left" font-family="Menlo">
                                                <xsl:value-of select="name"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue"
                                                       border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="left" font-family="Menlo">
                                                <xsl:value-of select="wattage"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue"
                                                       border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="left" font-family="Menlo">
                                                <xsl:value-of select="time"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="blue"
                                                       border-width="0.5pt" border-style="solid">
                                            <fo:block text-align="left" font-family="Menlo">
                                                <xsl:value-of select="total"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
