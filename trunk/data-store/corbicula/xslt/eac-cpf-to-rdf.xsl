<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:xlink="http://www.w3.org/1999/xlink" 
	xmlns:eac="urn:isbn:1-931666-33-4" 
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:foaf="http://xmlns.com/foaf/0.1/"
	xmlns:crm="http://erlangen-crm.org/current/"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	exclude-result-prefixes="eac xlink">
	
	<xsl:param name="resource-base-uri"/>
	
	<!-- the unique identifier of the EAC record about the party (person, family, or corporate body) is used to mint -->
	<!-- unique identifiers for the party, for their birth and death, their appellation, etc. --> 
	<!-- The convention is to mint an identifier by concatenating: -->
	<!-- 1) the resource-base-uri which is unique to each provider -->
	<!-- 2) For entities which are subordinate to the party, an indicator of the resource class (e.g. "death", "name", etc.) -->
	<!-- 3) The record id -->
	
	<xsl:variable name="record-id" select="/eac:eac-cpf/eac:control/eac:recordId"/>
	
	<xsl:template match="/eac:eac-cpf">
		<rdf:RDF xml:base="{@xml:base}">
			<rdf:Description rdf:about="{concat($resource-base-uri, $record-id)}">
				<xsl:apply-templates select="eac:cpfDescription/eac:identity/eac:nameEntry[1]"/>
				<xsl:apply-templates select="eac:cpfDescription/eac:identity/eac:entityType"/>
				<xsl:apply-templates select="eac:cpfDescription/eac:identity/eac:entityId"/>
				<xsl:apply-templates select="eac:cpfDescription/eac:description/eac:occupations/eac:occupation/eac:term"/>
				<xsl:apply-templates select="eac:cpfDescription/eac:description/eac:existDates"/>
				<xsl:apply-templates select="eac:cpfDescription/eac:relations/eac:cpfRelation"/>
			</rdf:Description>
		</rdf:RDF>
	</xsl:template>
	
	<!-- eac:entityId can point to a page about the entity -->
	<xsl:template match="eac:entityId">
		<xsl:if test="starts-with(., 'http:')">
			<foaf:isPrimaryTopicOf rdf:resource="{.}"/>
		</xsl:if>
	</xsl:template>
	
	<!-- EAC-CPF entities are either persons or groups -->
	<xsl:template match="eac:entityType[.='person']">
		<rdf:type rdf:resource="http://erlangen-crm.org/current/E21_Person"/>
	</xsl:template>
	<xsl:template match="eac:entityType[.='corporateBody']">
		<rdf:type rdf:resource="http://erlangen-crm.org/current/E74_Group"/>
	</xsl:template>
	
	
	<!-- birth and death -->
	<!-- this is roughly how birth and death are modelled in CLAROS -->
	<xsl:template match="eac:cpfDescription[eac:identity/eac:entityType='person']/eac:description/eac:existDates/eac:dateRange/eac:fromDate">
		<crm:P98i_was_born>
			<crm:E67_Birth rdf:about="{$resource-base-uri}birth/{$record-id}">
				<crm:P4_has_time-span>
					<crm:E52_Time-span rdf:about="{$resource-base-uri}timespan/{@standardDate}">
						<xsl:call-template name="render-date-value">
							<xsl:with-param name="date-value" select="@standardDate"/>
						</xsl:call-template>
					</crm:E52_Time-span>
				</crm:P4_has_time-span>
			</crm:E67_Birth>
		</crm:P98i_was_born>
	</xsl:template>

	<xsl:template match="eac:cpfDescription[eac:identity/eac:entityType='person']/eac:description/eac:existDates/eac:dateRange/eac:toDate">
		<crm:P100i_died_in>
			<crm:E69_Death rdf:about="{$resource-base-uri}death/{$record-id}">
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
	
	<!-- formation and dissolution -->

	<xsl:template match="eac:cpfDescription[eac:identity/eac:entityType='corporateBody']/eac:description/eac:existDates/eac:dateRange/eac:fromDate">
		<crm:P95i_was_formed_by>
			<crm:E66_Formation rdf:about="{$resource-base-uri}formation/{$record-id}">
				<crm:P4_has_time-span>
					<crm:E52_Time-span rdf:about="{$resource-base-uri}timespan/{@standardDate}">
						<xsl:call-template name="render-date-value">
							<xsl:with-param name="date-value" select="@standardDate"/>
						</xsl:call-template>
					</crm:E52_Time-span>
				</crm:P4_has_time-span>
			</crm:E66_Formation>
		</crm:P95i_was_formed_by>
	</xsl:template>

	<xsl:template match="eac:cpfDescription[eac:identity/eac:entityType='corporateBody']/eac:description/eac:existDates/eac:dateRange/eac:toDate">
		<crm:P99i_was_dissolved_by>
			<crm:E68_Dissolution rdf:about="{$resource-base-uri}dissolution/{$record-id}">
				<crm:P4_has_time-span>
					<crm:E52_Time-span rdf:about="{$resource-base-uri}timespan/{@standardDate}">
						<xsl:call-template name="render-date-value">
							<xsl:with-param name="date-value" select="@standardDate"/>
						</xsl:call-template>
					</crm:E52_Time-span>
				</crm:P4_has_time-span>
			</crm:E68_Dissolution>
		</crm:P99i_was_dissolved_by>
	</xsl:template>
	
	<!-- this is roughly how birth and death are modelled in CLAROS, though, lacking XSD-compliant date data, they use rdfs:label to attach the values -->
	<xsl:template name="render-date-value">
		<xsl:param name="date-value"/>
		<xsl:choose>
			<xsl:when test="string-length($date-value)=4">
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYear"><xsl:value-of select="$date-value"/></rdf:value>
			</xsl:when>
			<xsl:when test="string-length($date-value)=7">
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYearMonth"><xsl:value-of select="$date-value"/></rdf:value>
			</xsl:when>
			<xsl:otherwise>
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#date"><xsl:value-of select="substring($date-value, 1, 10)"/></rdf:value>
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

	<!-- names. EAC-CPF names are broken into parts, but we simply produce a full name from those parts -->
	<xsl:template match="eac:nameEntry">
		<!-- concatenate "eac:part" values to create an actor appelation -->
		<xsl:variable name="full-name">
			<xsl:for-each select="eac:part">
				<xsl:value-of select="."/>
				<xsl:text> </xsl:text>
			</xsl:for-each>
		</xsl:variable>
		<crm:P131_is_identified_by>
			<crm:E82_Actor_Appellation rdf:about="{$resource-base-uri}name/{$record-id}">
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#string"><xsl:value-of select="normalize-space($full-name)"/></rdf:value>
			</crm:E82_Actor_Appellation>
		</crm:P131_is_identified_by>
	</xsl:template>	
	
	<xsl:template match="eac:cpfRelation">
		<xsl:if test="eac:natureOfRelation = 'associative' and eac:descriptiveNote/eac:p = 'Corporate Body'">
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
