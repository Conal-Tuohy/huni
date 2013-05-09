<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:eac="urn:isbn:1-931666-33-4" 
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:owl="http://www.w3.org/2002/07/owl#"
	exclude-result-prefixes="eac">
	
	<xsl:variable name="control-records" select="
		/eac:eac-cpf/eac:cpfDescription/eac:alternativeSet/eac:setComponent/eac:objectXMLWrap/eac:eac-cpf/eac:control"/>
			
	<xsl:template match="/eac:eac-cpf">
		<rdf:RDF xml:base="{@xml:base}">
			<xsl:for-each select="$control-records">
				<xsl:variable name="sameAsResource">
					<xsl:apply-templates select="."/>
				</xsl:variable>
				<xsl:if test="normalize-space($sameAsResource)">
					<rdf:Description rdf:about="{/eac:eac-cpf/eac:control/eac:otherRecordId[starts-with(., 'http://nla.gov.au/nla.party-')]}">
						<owl:sameAs rdf:resource="{$sameAsResource}"/>
					</rdf:Description>
				</xsl:if>
			</xsl:for-each>
		</rdf:RDF>
	</xsl:template>
	
	<xsl:template match="eac:control[eac:maintenanceAgency/eac:agencyCode='AU-ANU:ADBO']">http://corbicula.huni.net.au/data/adb/<xsl:value-of select="eac:recordId"/></xsl:template>
	<xsl:template match="eac:control[eac:maintenanceAgency/eac:agencyCode='AU-VU:EOAS']">http://corbicula.huni.net.au/data/eoas/<xsl:value-of select="eac:recordId"/></xsl:template>
	<xsl:template match="eac:control[eac:maintenanceAgency/eac:agencyCode='AU-VU:AWR']">http://corbicula.huni.net.au/data/awr/<xsl:value-of select="eac:recordId"/></xsl:template>
	
	<xsl:template match="eac:control" priority="0"/>

</xsl:stylesheet>
