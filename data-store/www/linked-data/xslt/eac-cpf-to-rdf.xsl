<!--This is an XSLT program which converts an XML document from one format into another - a "crosswalk", to use the library jargon.
The input format for this crosswalk is "Encoded Archival Context, Corporate Bodies, People, and Families" (EAC-CPF).
The output format is Resource Description Framework XML (RDF/XML), using a mixture of RDF vocabularies or ontologies.

Note that the input document (the EAC-CPF record) may contain considerable amounts of data which are
ignored by this program; only certain pieces of information are captured, as detailed below.
-->
<!--The output of this crosswalk is a document like this. e.g.
<rdf:RDF 
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" 
	xmlns:crm="http://erlangen-crm.org/current/" 
	xmlns:foaf="http://xmlns.com/foaf/0.1/" 
	xml:base="oai:adb.anu.edu.au:4961">
	
	<rdf:Description rdf:about="http://corbicula.huni.net.au/data/adb/4961">
	
		<foaf:lastName rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Abbott</foaf:lastName>
		<foaf:firstName rdf:datatype="http://www.w3.org/2001/XMLSchema#string">John</foaf:firstName>
		<foaf:firstName rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Henry</foaf:firstName>
		<crm:P2_has_type rdf:resource="http://erlangen-crm.org/current/E21_Person"/>
		<foaf:isPrimaryTopicOf rdf:resource="http://adb.anu.edu.au/biography/abbott-john-henry-macartney-4961"/>
		<crm:P2_has_type rdf:resource="http://corbicula.huni.net.au/data/adb/occupation/author"/>
					
		<crm:P98i_was_born>
			<crm:E67_Birth rdf:about="http://corbicula.huni.net.au/data/adb/birth/4961">
				<crm:P4_has_time-span>
					<crm:E52_Time-span rdf:about="http://corbicula.huni.net.au/data/adb/timespan/1874-12-26">
						<rdfs:value rdf:datatype="http://www.w3.org/2001/XMLSchema#date">1874-12-26</rdfs:value>
					</crm:E52_Time-span>
				</crm:P4_has_time-span>
			</crm:E67_Birth>
		</crm:P98i_was_born>
		
		<crm:P100i_died_in>
			<crm:E69_Death rdf:about="http://corbicula.huni.net.au/data/adb/birth/4961">
				<crm:P4_has_time-span>
					<crm:E52_Time-span rdf:about="http://corbicula.huni.net.au/data/adb/timespan/1953-08-12">
						<rdfs:value rdf:datatype="http://www.w3.org/2001/XMLSchema#date">1953-08-12</rdfs:value>
					</crm:E52_Time-span>
				</crm:P4_has_time-span>
			</crm:E69_Death>
		</crm:P100i_died_in>
			
	</rdf:Description>
	
</rdf:RDF>
-->
<!--Vocabularies

The program (or "stylesheet") begins with a set of XML namespaces, which identify the vocabularies
used in either the input document. Each vocabulary has a unique identifier which is an HTTP URI; here
we associate each URI with a "prefix" which functions as an alias, mainly for brevity.

The namespaces "eac" and "xlink" are used in the input document, and are excluded from the output.

The remaining namespaces are used in the output:

	"rdf"	(Resource Description Framework) and "rdfs" are basic vocabularies of the Resource Description Framework (RDF) itself.
	"foaf"	(Friend Of A Friend) is a popular vocabulary used for describing people and social networks.
	"crm"	(Conceptual Reference Model) is an ontology used in museology and related fields.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:xlink="http://www.w3.org/1999/xlink" 
	xmlns:eac="urn:isbn:1-931666-33-4" 
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:foaf="http://xmlns.com/foaf/0.1/"
	xmlns:crm="http://erlangen-crm.org/current/"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
	exclude-result-prefixes="eac xlink">
	
<!--In RDF, things ("resources") are identified by URIs, and hence this crosswalk has to convert identifiers in the EAC-CPF into URIs,
which it does by prepending a "base" URI to the identifier.  Each dataset uses a different base URI so that their identifiers are
guaranteed to be distinct. These different base URIs are supplied to the crosswalk from outside, and typically
are something like this: "http://corbicula.huni.net.au/data/adb/". 

This "parameter" below allows for the URI to be supplied from outside, and the expression "$resource-base-uri" 
to serve as a placeholder for the actual URI supplied.
-->
	<xsl:param name="resource-base-uri"/>
	
<!--Templates

An XSLT program consists of a set of so-called "templates", whose job is to respond to elements in the input document,
and replace them with a translated version. The parts of the input document which each template match are specified by
an XPath expression. XPath is a language for specifying the location of elements within an XML document. 
-->
<!--The root element is converted into an RDF Description (a description of a person or group).

The first template matches the root element of the document; an element called eac:eac-cpf.
-->
	<xsl:template match="/eac:eac-cpf">
		<!--The rdf:RDF element is simply a container element; it has no meaning at all
		-->
		<rdf:RDF xml:base="{@xml:base}">
			<!--The rdf:Description element is a container for statements describing one particular thing.-->
			<!--The thing is identified by a URI produced by concatenating the base URI with the EAC record identifier.-->
			<rdf:Description rdf:about="{concat($resource-base-uri, eac:control/eac:recordId)}">
				<!--Within the Description element, a set of descriptive statements are generated, which are
				to be interpreted as being about the subject identified by the above URI.	-->
				<!--These statements are generated by applying further templates to various parts of the EAC document -->
				<!--Firstly, the various parts of the first EAC nameEntry -->
				<xsl:apply-templates select="eac:cpfDescription/eac:identity/eac:nameEntry[1]/eac:part"/>
				<!--The entity type (person or corporate body) -->
				<xsl:apply-templates select="eac:cpfDescription/eac:identity/eac:entityType"/>
				<!--The local identifier for the entity (might point to a web page) -->
				<xsl:apply-templates select="eac:cpfDescription/eac:identity/eac:entityId"/>
				<!--The occupations the person has-->
				<xsl:apply-templates select="eac:cpfDescription/eac:description/eac:occupations/eac:occupation/eac:term"/>
				<!--The dates of existence (birth and death, formation and dissolution in the case of groups)-->
				<xsl:apply-templates select="eac:cpfDescription/eac:description/eac:existDates"/>
				<!--Relationships to other entities (people's membership of groups)-->
				<xsl:apply-templates select="eac:cpfDescription/eac:relations/eac:cpfRelation"/>
			</rdf:Description>
		</rdf:RDF>
	</xsl:template>
	
	<!-- eac:entityId can point to a page about the entity -->
	<xsl:template match="eac:entityId">
		<!--If the identifier starts with 'http:' then we interpret it as a web page reference-->
		<xsl:if test="starts-with(., 'http:')">
			<!-- This asserts that the thing we are describing is the primary topic of that web page-->
			<foaf:isPrimaryTopicOf rdf:resource="{.}"/>
		</xsl:if>
	</xsl:template>
	
	<!-- EAC-CPF entities are either persons or groups -->
	<xsl:template match="eac:entityType[.='person']">
		<crm:P2_has_type rdf:resource="http://erlangen-crm.org/current/E21_Person"/>
	</xsl:template>
	<xsl:template match="eac:entityType[.='corporateBody']">
		<crm:P2_has_type rdf:resource="http://erlangen-crm.org/current/E74_Group"/>
	</xsl:template>
	
	
	<!--This template matches "fromDate" elements where the entity type is 'person' - this is a birth date -->
	<xsl:template match="eac:cpfDescription[eac:identity/eac:entityType='person']/eac:description/eac:existDates/eac:dateRange/eac:fromDate">
		<!-- this is a link between the person and their birth (the event)-->
		<crm:P98i_was_born>
			<!-- this is the birth itself (the event) -->
			<crm:E67_Birth rdf:about="{$resource-base-uri}birth/{/eac:eac-cpf/eac:control/eac:recordId}">
				<!-- this is the link between the birth and the time at which it took place-->
				<crm:P4_has_time-span>
					<!-- this is the date of the birth, drawn from the "standardDate" attribute in the EAC -->
					<crm:E52_Time-span rdf:about="{$resource-base-uri}timespan/{@standardDate}">
						<!-- here we call another template to render the actual calendar date (see below) -->
						<xsl:call-template name="render-date-value">
							<xsl:with-param name="date-value" select="@standardDate"/>
						</xsl:call-template>
					</crm:E52_Time-span>
				</crm:P4_has_time-span>
			</crm:E67_Birth>
		</crm:P98i_was_born>
	</xsl:template>
	
	<!--This template matches "toDate" elements where the entity type is 'person' - this is a death date -->
	<xsl:template match="eac:cpfDescription[eac:identity/eac:entityType='person']/eac:description/eac:existDates/eac:dateRange/eac:toDate">
		<crm:P100i_died_in>
			<crm:E69_Death rdf:about="{$resource-base-uri}birth/{/eac:eac-cpf/eac:control/eac:recordId}">
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
	
	<!--This template is used to render a calendar date. It's rendered slightly differently depending on how precise the date is-->
	<xsl:template name="render-date-value">
		<xsl:param name="date-value"/>
		<xsl:choose>
			<!--If we have only four digits in the date, then it's a year-->
			<xsl:when test="string-length($date-value)=4">
				<rdfs:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYear"><xsl:value-of select="$date-value"/></rdfs:value>
			</xsl:when>
			<!--If there are seven characters, then it is, or should be, a 4-digit year, followed by a dash, and a 2-digit month-->
			<xsl:when test="string-length($date-value)=7">
				<rdfs:value rdf:datatype="http://www.w3.org/2001/XMLSchema#gYearMonth"><xsl:value-of select="$date-value"/></rdfs:value>
			</xsl:when>
			<!-- otherwise it's a year-month-day, and may even include time of day, which we truncate-->
			<xsl:otherwise>
				<rdfs:value rdf:datatype="http://www.w3.org/2001/XMLSchema#date"><xsl:value-of select="substring($date-value, 1, 10)"/></rdfs:value>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- occupations e.g. "Artist and Naturalist" "Nurse, Author and Historian" -->
	<xsl:template match="eac:occupation/eac:term">
		<!--Here we call another template to parse what may be a comma-separated list of occupations into a set of distinct statements -->
		<xsl:call-template name="parse-terms"/>
	</xsl:template>
	
	<!--This utility template simply breaks a list of comma-separated occupations up into distinct statements-->
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
				<!--Remove any spaces and punctuation from the occupation name, to convert it to a URI-->
				<xsl:variable name="unwanted">'"</xsl:variable>
				<!--We say here that the person being described has the occupation as their "type" - the occupations are treated as a "type" of person-->
				<crm:P2_has_type rdf:resource="{$resource-base-uri}occupation/{translate(normalize-space($term-list), concat(' ', $unwanted), '_')}"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!--Template matching a part of a name; to be expressed using FOAF name vocabulary -->
	<xsl:template match="eac:nameEntry/eac:part">
		<xsl:choose>
			<!-- EAC "forename" = FOAF "firstName" (more or less) -->
			<xsl:when test="@localType='forename'">
				<foaf:firstName rdf:datatype="http://www.w3.org/2001/XMLSchema#string"><xsl:value-of select="."/></foaf:firstName>
			</xsl:when>
			<!-- EAC "surname" = FOAF "lastName" (more or less - FOAF is very eurocentric; not good for e.g. Chinese names) -->
			<xsl:when test="@localType='surname'">
				<foaf:lastName rdf:datatype="http://www.w3.org/2001/XMLSchema#string"><xsl:value-of select="."/></foaf:lastName>
			</xsl:when>
			<!-- EAC title = FOAF title -->
			<xsl:when test="@localType='title'">
				<foaf:title rdf:datatype="http://www.w3.org/2001/XMLSchema#string"><xsl:value-of select="."/></foaf:title>
			</xsl:when>
			<!-- otherwise it's some other kind of name, or an unspecified type - use the generic FOAF term "name" -->
			<xsl:otherwise>
				<foaf:name rdf:datatype="http://www.w3.org/2001/XMLSchema#string"><xsl:value-of select="."/></foaf:name>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	
	
	<!--Template matching a relation between one EAC entity and another -->
	<xsl:template match="eac:cpfRelation">
		<!-- if the relation is an association with a group, then we'll translate it, otherwise ignore it -->
		<xsl:if test="eac:natureOfRelation = 'associative' and eac:descNote/eac:p = 'Corporate Body'">
			<!--This is a hack which assumes that we are pointing at a web page produced by the OHRM database software-->
			<xsl:variable name="related-resource-id" select="
				concat(
					$resource-base-uri, 
					substring-before(
						substring-after(eac:relationLink/@xlink:href, '/biogs/'), 
						'.htm'
					)
				)
			"/>
			<!--The statement says that the entity being described was or is a member of the specified group-->
			<crm:P107i_is_current_or_former_member_of rdf:resource="{$related-resource-id}"/>
		</xsl:if>
	</xsl:template>

</xsl:stylesheet>
