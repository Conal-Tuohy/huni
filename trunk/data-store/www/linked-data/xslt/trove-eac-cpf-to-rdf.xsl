<!--This is an XSLT program which converts an XML document from one format into another - a "crosswalk", to use the library jargon.

The input format for this crosswalk is "Encoded Archival Context, Corporate Bodies, People, and Families" (EAC-CPF), as produced
by the National Library of Australia's "People Australia" service.

The output format is Resource Description Framework XML (RDF/XML), using the OWL ontology.

Note that the input document (the EAC-CPF record) contains a large amount of data, almost all of which is ignored.
This crosswalk captures and translates only a single aspect of the input record; namely, the link between several identifiers which
the National Library assert are all about the same individual or group.

The input document is an EAC-CPF record which aggregates a number of other EAC-CPF records sourced from the various institutional
contributors to the People Australia service. The National Library's record has a "party identifier", and the various records contained
within it have their own local identifiers. This program extracts only these identifiers, and creates RDF to say that the identifiers
are equivalents.

The output of this program is an RDF document like this:

<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:owl="http://www.w3.org/2002/07/owl#" xml:base="oai:nla.gov.au:nla.party-1465637">
	<rdf:Description rdf:about="http://nla.gov.au/nla.party-1465637">
		<owl:sameAs rdf:resource="http://corbicula.huni.net.au/data/adb/A040463"/>
	</rdf:Description>
	<rdf:Description rdf:about="http://nla.gov.au/nla.party-1465637">
		<owl:sameAs rdf:resource="http://corbicula.huni.net.au/data/eoas/P000058"/>
	</rdf:Description>
</rdf:RDF>
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:eac="urn:isbn:1-931666-33-4" 
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:owl="http://www.w3.org/2002/07/owl#"
	exclude-result-prefixes="eac">

	<!--This variable represents all the aggregated EAC-CPF records -->
	<xsl:variable name="control-records" select="
		/eac:eac-cpf/eac:cpfDescription/eac:alternativeSet/eac:setComponent/eac:objectXMLWrap/eac:eac-cpf/eac:control"/>
			
	<!--Template matches the root element of the NLA's record,  and creates an RDF document-->
	<xsl:template match="/eac:eac-cpf">
		<rdf:RDF xml:base="{@xml:base}">
			<!-- for each of the aggregate records ...-->
			<xsl:for-each select="$control-records">
				<!-- apply templates (see below) to the record to try to generate a URI from it-->
				<xsl:variable name="sameAsResource">
					<xsl:apply-templates select="."/>
				</xsl:variable>
				<!-- if the contributor was one of the known contributors, we will now have a URI...-->
				<xsl:if test="normalize-space($sameAsResource)">
					<!--Create a description of the entity identified by the NLA's party identifier...-->
					<rdf:Description rdf:about="{/eac:eac-cpf/eac:control/eac:otherRecordId[starts-with(., 'http://nla.gov.au/nla.party-')]}">
						<!-- ... and assert that it's the same as the entity identified by the contributor's identifier-->
						<owl:sameAs rdf:resource="{$sameAsResource}"/>
					</rdf:Description>
				</xsl:if>
			</xsl:for-each>
		</rdf:RDF>
	</xsl:template>
	
	<!--Templates to match records contributed by various NLA partner institutions, and create URIs to identity them-->
	
	<!--The Australian Dictionary of Biography -->
	<xsl:template match="eac:control[eac:maintenanceAgency/eac:agencyCode='AU-ANU:ADBO']">http://corbicula.huni.net.au/data/adb/<xsl:value-of select="eac:recordId"/></xsl:template>
	<!-- The Encyclopedia of Australian Science-->
	<xsl:template match="eac:control[eac:maintenanceAgency/eac:agencyCode='AU-VU:EOAS']">http://corbicula.huni.net.au/data/eoas/<xsl:value-of select="eac:recordId"/></xsl:template>
	<!-- the Australian Women's Register-->
	<xsl:template match="eac:control[eac:maintenanceAgency/eac:agencyCode='AU-VU:AWR']">http://corbicula.huni.net.au/data/awr/<xsl:value-of select="eac:recordId"/></xsl:template>
	
	<!-- any other contributor (e.g. Libraries Australia) is ignored-->
	<xsl:template match="eac:control" priority="0"/>

</xsl:stylesheet>
