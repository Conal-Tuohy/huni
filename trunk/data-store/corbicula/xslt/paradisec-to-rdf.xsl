<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:xlink="http://www.w3.org/1999/xlink" 
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:foaf="http://xmlns.com/foaf/0.1/"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	xmlns:crm="http://erlangen-crm.org/current/"
	xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
	xmlns:lookup="local-namespace"
	xmlns:olac="http://www.language-archives.org/OLAC/1.1/"
	xmlns:rif="http://ands.org.au/standards/rif-cs/registryObjects"
	xmlns:dc="http://purl.org/dc/elements/1.1/">
	
	<xsl:param name="resource-base-uri"/>
	
	<xsl:variable name="item-identifier" select="/olac:olac/dc:identifier"/>

	<!-- this is roughly how birth and death are modelled in CLAROS, though, lacking gregorian dates, they use rdfs:label to attach the values -->
	<xsl:template name="render-date-value">
		<xsl:param name="date-value"/>
		<xsl:choose>
			<xsl:when test="string-length($date-value)=4">
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYear"><xsl:value-of select="$date-value"/></rdf:value>
			</xsl:when>
			<xsl:when test="string-length($date-value)=6">
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYearMonth"><xsl:value-of select="
					concat(substring($date-value, 1, 4), '-', substring($date-value, 5, 2))
				"/></rdf:value>
			</xsl:when>
			<xsl:otherwise>
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#date"><xsl:value-of select="
					concat(substring($date-value, 1, 4), '-', substring($date-value, 5, 2), '-', substring($date-value, 7, 2))
				"/></rdf:value>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="/olac:olac">
		<rdf:RDF xml:base="{@xml:base}">
			<!-- The record is about a linguistic object -->
			<crm:E33_Linguistic_Object rdf:about="{concat($resource-base-uri, $item-identifier)}">
			
				<!-- dc:title → crm P102_has_title E35_Title -->
				<xsl:for-each select="dc:title">
					<crm:P102_has_title>
						<crm:E35_Title rdf:about="{concat($resource-base-uri, 'title/', $item-identifier)}">
							<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#string"><xsl:value-of select="."/></rdf:value>
						</crm:E35_Title>
					</crm:P102_has_title>
				</xsl:for-each>
				
				<crm:P94i_was_created_by>
					<frbr:F28_Expression_Creation rdf:about="{concat($resource-base-uri, 'creation/', $item-identifier)}">
						<xsl:variable name="date" select="dcterms:created[xsi:type='dcterms:W3CDTF']"/>
						<crm:P4_has_time-span>
							<crm:E52_Time-span rdf:about="{$resource-base-uri}timespan/{$date}">
								<xsl:call-template name="render-date-value">
									<xsl:with-param name="date-value" select="$date"/>
								</xsl:call-template>
							</crm:E52_Time-span>
						</crm:P4_has_time-span>
					</frbr:F28_Expression_Creation>
				</crm:P94i_was_created_by>
				
				
				
				<!-- The "dc:subjects" of the record are an ontological mixture; -->
				<!-- some of them are subjects (aboutness), and others are item types (genre / form) -->
				
				<!-- The collection is the physical carrier of a (composite) intellectual work --> 
				<crm:P128_carries>
					<crm:E89_Propositional_Object rdf:about="{concat($resource-base-uri, 'content/', $identifier)}">
						<!-- The intellectual work is about particular subjects -->
						<xsl:for-each select="dc:subject">
							<xsl:call-template name="parse-terms">
								<xsl:with-param name="class" select="$E90_Symbolic_Object"/>
							</xsl:call-template>
						</xsl:for-each>
					</crm:E89_Propositional_Object>
				</crm:P128_carries>
				
				<!-- The collection includes items of particular types -->
				<xsl:for-each select="dc:subject">
					<xsl:call-template name="parse-terms">
						<xsl:with-param name="class" select="$E55_Type"/>
					</xsl:call-template>
				</xsl:for-each>
			</crm:E78_Collection>
		</rdf:RDF>
	</xsl:template>

	<xsl:template match="crm:E55_Type">
		<!-- Handle the term as an E55_Type of physical item -->
		<!-- If the term isn't in the predefined list of types, then ignore it; we'll treat it as a subject -->
		<xsl:param name="term"/>
		<xsl:param name="uri"/>
		<xsl:if test="$types[. = $term]">
			<!-- the URI identifies one of a set of things we consider a type (not a subject) -->
			<crm:P2_has_type rdf:resource="{$resource-base-uri}collection-type/{$uri}"/>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="crm:E90_Symbolic_Object">
		<!-- Handle the term as an E90_Symbolic_Object which the collection is about. -->
		<!-- If the term is in the predefined list of types, then ignore it; we'll treat it as a type -->
		<xsl:param name="term"/>
		<xsl:param name="uri"/>
		<xsl:if test="not($types[. = $term])">
			<crm:P129_is_about rdf:resource="{$resource-base-uri}subject/{$uri}"/>
		</xsl:if>
	</xsl:template>

	<xsl:template name="parse-terms">
		<xsl:param name="class"/>
		<xsl:param name="term-list" select="."/>
		<xsl:choose>
			<xsl:when test="starts-with($term-list, ', ')">
				<xsl:call-template name="parse-terms">
					<xsl:with-param name="class" select="$class"/>
					<xsl:with-param name="term-list" select="substring-after($term-list, ', ')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains($term-list, ', ')">
				<xsl:call-template name="parse-terms">
					<xsl:with-param name="class" select="$class"/>
					<xsl:with-param name="term-list" select="substring-before($term-list, ', ')"/>
				</xsl:call-template>
				<xsl:call-template name="parse-terms">
					<xsl:with-param name="class" select="$class"/>
					<xsl:with-param name="term-list" select="substring-after($term-list, ', ')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<!-- only one term remains in the list -->
				<xsl:variable name="term" select="normalize-space($term-list)"/>
				<xsl:variable name="unwanted-characters">'"</xsl:variable>
				<xsl:variable name="uri" select="translate($term, concat(' ', $unwanted-characters), '_')"/>
				<!-- handle the term appropriately as a potential instance of the current class -->
				<xsl:apply-templates select="$class">
					<xsl:with-param name="term" select="$term"/>
					<xsl:with-param name="uri" select="$uri"/>
				</xsl:apply-templates>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="render-date-value">
		<xsl:param name="date-value"/>
		<xsl:choose>
			<xsl:when test="string-length($date-value)=4">
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYear"><xsl:value-of select="$date-value"/></rdf:value>
			</xsl:when>
			<xsl:when test="string-length($date-value)=6">
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYearMonth"><xsl:value-of select="
					concat(substring($date-value, 1, 4), '-', substring($date-value, 5, 2))
				"/></rdf:value>
			</xsl:when>
			<xsl:otherwise>
				<rdf:value rdf:datatype="http://www.w3.org/2001/XMLSchema#date"><xsl:value-of select="
					concat(substring($date-value, 1, 4), '-', substring($date-value, 5, 2), '-', substring($date-value, 7, 2))
				"/></rdf:value>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
