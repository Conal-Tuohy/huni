<!--This is an XSLT program which converts an XML document from one format into another - a "crosswalk", to use the library jargon.
The input format for this crosswalk is a beta version of "Encoded Archival Context" (EAC).
The output format is Resource Description Framework XML (RDF/XML), using a mixture of RDF vocabularies or ontologies.
This stylesheet is functionally similar to the stylesheet called eac-cpf-to-rdf.xsl, which handles the current version of EAC, and which
is better commented. This stylesheet handles the legacy EAC content and should be considered a stop-gap measure until such
time as the eSRC datasets are available in the current version of EAC.
-->

<!-- Some misconfiguration at the eSRC end currently causes records to be published in the OAI-PMH 2.0 namespace -->
<!-- instead of the real EAC namespace -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:xlink="http://www.w3.org/1999/xlink" 
	xmlns:eac="http://www.openarchives.org/OAI/2.0/" 
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:foaf="http://xmlns.com/foaf/0.1/"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:crm="http://erlangen-crm.org/current/">
	
	<xsl:param name="resource-base-uri"/>
	
	<xsl:template match="/eac:eac">
		<rdf:RDF xml:base="{@xml:base}">
			<rdf:Description rdf:about="{concat($resource-base-uri, eac:control/eac:id)}">
				<xsl:apply-templates select="eac:identity/eac:nameEntry[1]/eac:part"/>
				<xsl:apply-templates select="eac:identity/eac:entityType"/>
				<xsl:apply-templates select="eac:description/eac:occupation/eac:term"/>
				<xsl:apply-templates select="eac:description/eac:existDates"/>
				<xsl:apply-templates select="eac:relations/eac:cpfRelation"/>
			</rdf:Description>
		</rdf:RDF>
	</xsl:template>

	
	<!-- EAC-CPF entities are either persons or groups -->
	<xsl:template match="eac:entityType[.='person']">
		<crm:P2_has_type rdf:resource="http://erlangen-crm.org/current/E21_Person"/>
		<!--<rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>-->
	</xsl:template>
	<xsl:template match="eac:entityType[.='corporateBody']">
		<crm:P2_has_type rdf:resource="http://erlangen-crm.org/current/E74_Group"/>
		<rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Group"/>
	</xsl:template>
	
	<!-- birth and death -->
	<!-- this is roughly how birth and death are modelled in CLAROS -->
	<xsl:template match="eac:eac[eac:identity/eac:entityType='person']/eac:description/eac:existDates/eac:fromDate">
		<crm:P98i_was_born>
			<crm:E67_Birth rdf:about="{$resource-base-uri}birth/{/eac:eac/eac:control/eac:id}">
				<crm:P4_has_time-span>
					<crm:E52_Time-span rdf:about="{$resource-base-uri}timespan/{@standardForm}">
						<xsl:call-template name="render-date-value">
							<xsl:with-param name="date-value" select="@standardForm"/>
						</xsl:call-template>
					</crm:E52_Time-span>
				</crm:P4_has_time-span>
			</crm:E67_Birth>
		</crm:P98i_was_born>
	</xsl:template>

	<xsl:template match="eac:cpfDescription[eac:identity/eac:entityType='person']/eac:description/eac:existDates/eac:dateRange/eac:toDate">
		<crm:P100i_died_in>
			<crm:E69_Death rdf:about="{$resource-base-uri}birth/{/eac:eac/eac:control/eac:id}">
				<crm:P4_has_time-span>
					<crm:E52_Time-span rdf:about="{$resource-base-uri}timespan/{@standardDate}">
						<xsl:call-template name="render-date-value">
							<xsl:with-param name="date-value" select="@standardDate"/>
						</xsl:call-template>
					</crm:E52_Time-span>
				</crm:P4_has_time-span>
			</crm:E69_Death>
		</crm:P100i_died_in>
	</xsl:template>
	
	<!-- this is roughly how birth and death are modelled in CLAROS, though, lacking gregorian dates, they use rdfs:label to attach the values -->
	<xsl:template name="render-date-value">
		<xsl:param name="date-value"/>
		<xsl:choose>
			<xsl:when test="string-length($date-value)=4">
				<rdfs:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYear"><xsl:value-of select="$date-value"/></rdfs:value>
			</xsl:when>
			<xsl:when test="string-length($date-value)=6">
				<rdfs:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYearMonth"><xsl:value-of select="
					concat(substring($date-value, 1, 4), '-', substring($date-value, 5, 2))
				"/></rdfs:value>
			</xsl:when>
			<xsl:otherwise>
				<rdfs:value rdf:datatype="http://www.w3.org/2001/XMLSchema#date"><xsl:value-of select="
					concat(substring($date-value, 1, 4), '-', substring($date-value, 5, 2), '-', substring($date-value, 7, 2))
				"/></rdfs:value>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- occupations e.g. "Artist and Naturalist" "Nurse, Author and Historian" -->
	<xsl:template match="eac:occupation/eac:term">
		<xsl:call-template name="parse-terms"/>
	</xsl:template>
	
	<xsl:template name="parse-terms">
		<xsl:param name="term-list" select="."/>
		<xsl:choose>
			<xsl:when test="contains($term-list, ' and ')">
				<xsl:call-template name="parse-terms">
					<xsl:with-param name="term-list" select="substring-before($term-list, ' and ')"/>
				</xsl:call-template>
				<xsl:call-template name="parse-terms">
					<xsl:with-param name="term-list" select="substring-after($term-list, ' and ')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="starts-with($term-list, ', ')">
				<xsl:call-template name="parse-terms">
					<xsl:with-param name="term-list" select="substring-after($term-list, ', ')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains($term-list, ', ')">
				<xsl:call-template name="parse-terms">
					<xsl:with-param name="term-list" select="substring-before($term-list, ', ')"/>
				</xsl:call-template>
				<xsl:call-template name="parse-terms">
					<xsl:with-param name="term-list" select="substring-after($term-list, ', ')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="unwanted">'"</xsl:variable>
				<crm:P2_has_type rdf:resource="{$resource-base-uri}occupation/{translate(normalize-space($term-list), concat(' ', $unwanted), '_')}"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- names (using FOAF to model name parts) -->
	<xsl:template match="eac:nameEntry/eac:part">
		<xsl:choose>
			<xsl:when test="@type='givenname'">
				<foaf:firstName rdf:datatype="http://www.w3.org/2001/XMLSchema#string"><xsl:value-of select="."/></foaf:firstName>
			</xsl:when>
			<xsl:when test="@type='familyname'">
				<foaf:lastName rdf:datatype="http://www.w3.org/2001/XMLSchema#string"><xsl:value-of select="."/></foaf:lastName>
			</xsl:when>
			<xsl:otherwise>
				<foaf:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string"><xsl:value-of select="."/></foaf:name>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	
	
	<xsl:template match="eac:cpfRelation">
		<xsl:if test="eac:natureOfRelation = 'associative' and eac:descNote/eac:p = 'Corporate Body'">
			<xsl:variable name="related-resource-id" select="
				concat(
					$resource-base-uri, 
					substring-before(
						substring-after(eac:relationLink/@xlink:href, '/biogs/'), 
						'.htm'
					)
				)
			"/>
			<crm:P107i_is_current_or_former_member_of rdf:resource="{$related-resource-id}"/>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>
