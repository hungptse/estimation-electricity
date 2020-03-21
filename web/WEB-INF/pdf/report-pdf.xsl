<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-width="210mm" page-height="297mm" margin="10mm">
                    <fo:region-body margin-top="25mm"/>
                    <fo:region-before region-name="header"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="header">
                    <fo:block>
                        <fo:block-container position="absolute" top="2mm" left="2mm" width="40mm" height="20mm">
                            <fo:block>
                                <fo:external-graphic
                                        src="url(https://dnuni.fpt.edu.vn/wp-content/uploads/2020/02/logo-1.png)"/>
                            </fo:block>
                        </fo:block-container>
                        <fo:block-container position="absolute" top="1mm" left="70mm" width="120mm" height="20mm">
                            <fo:block text-align="right">
                                <fo:basic-link external-destination="url('https://hcmuni.fpt.edu.vn/')" color="blue"
                                               text-decoration="underline">https://hcmuni.fpt.edu.vn/
                                </fo:basic-link>
                            </fo:block>
                            <fo:block text-align="right">
                                Report document only for services PRX301 - Spring 2020
                            </fo:block>
                        </fo:block-container>
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>
                        <fo:block-container width="190mm" font-size="2em" border-color="#FFA500" color="#FFA500"
                                            border-after-style="solid" border-width="0.5mm" top="50mm" height="40mm">
                            <fo:block>REPORT INFORMATION</fo:block>
                        </fo:block-container>
                        <fo:block>
                            <fo:block-container position="absolute" width="95mm" left="0mm" top="12mm">
                                <fo:block>[FPT University]</fo:block>
                                <fo:block>[Lo E2a-7, Duong D1 Khu Cong nghe cao, P.Long Thanh My, Q.9, TP.Ho Chi Minh]
                                </fo:block>
                                <fo:block>[https://hcmuni.fpt.edu.vn/]</fo:block>
                            </fo:block-container>
                            <fo:block-container position="absolute" width="95mm" left="95mm" top="12mm"
                                                text-align="right">
                                <fo:block>Created Date:
                                    <xsl:value-of select="products/createdAt"/>
                                </fo:block>
                                <!--                                <fo:block>Invoice#: ${wpbAppModel["invoice_no"]?string.computer}</fo:block>-->
                            </fo:block-container>
                        </fo:block>
                    </fo:block>
                    <fo:block>
                        <fo:block font-size="10pt">
                            <fo:table table-layout="fixed" width="100%" border-color="#888888" border-width="medium"
                                      border-style="solid">
                                <fo:table-column column-width="20mm"/>
                                <fo:table-column column-width="80mm"/>
                                <fo:table-column column-width="30mm"/>
                                <fo:table-column column-width="30mm"/>
                                <fo:table-column column-width="30mm"/>
                                <fo:table-header background-color="#DFDFDF">
                                    <fo:table-cell padding="2mm">
                                        <fo:block># No</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="2mm">
                                        <fo:block>Name</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="2mm">
                                        <fo:block>Code</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="2mm">
                                        <fo:block>Wattage (W)</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell padding="2mm">
                                        <fo:block>Time (Hour)</fo:block>
                                    </fo:table-cell>
                                </fo:table-header>
                                <fo:table-body>
                                    <xsl:for-each select="products/product">
                                        <fo:table-row>
                                            <fo:table-cell padding="2mm">
                                                <fo:block>
                                                    <xsl:number level="single" count="product"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="2mm">
                                                <fo:block>
                                                    <xsl:value-of select="name"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="2mm">
                                                <fo:block>
                                                    <xsl:value-of select="code"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="2mm">
                                                <fo:block>
                                                    <xsl:value-of select="wattage"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell padding="2mm">
                                                <fo:block>
                                                    <xsl:value-of select="time"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </fo:block>
                    </fo:block>
                    <fo:block>
                        <fo:leader leader-pattern="rule" leader-length="100%" rule-style="solid" rule-thickness="1pt"
                                   border-color="#FFA500"/>
                        <fo:block-container height="10mm" width="190mm" left="150mm">
                            <fo:block text-align="right" font-size="10pt" font-weight="bold">Total:
                                <xsl:value-of select="format-number(products/total, '###,### VND')"/>
                            </fo:block>
                        </fo:block-container>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>