<?xml version="1.0"?>
<p:declare-step 
	version="1.0" 
	xmlns:p="http://www.w3.org/ns/xproc" 
	xmlns:fn="http://www.w3.org/2005/xpath-functions" 
	xmlns:c="http://www.w3.org/ns/xproc-step" 
	xmlns:drone="http://corbicula.huni.net.au/about/drone" 
	xmlns:oai="http://www.openarchives.org/OAI/2.0/"
	xmlns:test="temporary"
	name="etl"
>
<!--	<p:output port="result" sequence="true"/>-->
	
	<p:import href="corbicula.xpl"/>
	

	<drone:list-all-records 
		name="harvest" 
		cache-location="/data/xml/awr/"/>
			
<!--
	-->
	<!--
	
	<drone:list-new-records 
		name="harvest" 
		base-uri="http://oai.esrc.unimelb.edu.au/AWAP/provider" 
		cache-location="/data/xml/awr/" 
		metadata-prefix="eac"/>
	-->
	<!-- keep a record of deletions and updates -->
	<!-- TODO replace these steps with steps to crosswalk updates to RDF, and to store 
	and delete records from triple-store -->
	
	<!--
	<p:for-each name="deletion">
		<p:iteration-source>
			<p:pipe step="harvest" port="deletions"/>
		</p:iteration-source>
		<p:store>
			<p:with-option name="href" select="concat('/data/xml/awr/deletions/', /*/@xml:base)"/>
		</p:store>
	</p:for-each>
	
	<p:for-each name="error">
		<p:iteration-source>
			<p:pipe step="harvest" port="errors"/>
		</p:iteration-source>
		<p:store>
			<p:with-option name="href" select="concat('/data/xml/awr/errors/error-', position(), '.xml')"/>
		</p:store>
	</p:for-each>		
	-->
	
	<p:for-each name="transform-record-to-rdf">
		<p:iteration-source>
			<p:pipe step="harvest" port="updates"/>
		</p:iteration-source>
		<p:variable name="xml-base" select="/*/@xml:base"/>
		<!-- convert the record to RDF, using the resource-base-uri parameter as the base for minting resource URIs -->
		<drone:xml-to-rdf name="rdf" xslt="esrc-eac-to-rdf.xsl" resource-base-uri="http://corbicula.huni.net.au/data/awr/"/>

		<!--

		<p:store>
			<p:with-option name="href" select="concat('/data/xml/awr/graphs/', fn:encode-for-uri(fn:encode-for-uri($xml-base)), '.xml')"/>
		</p:store>		-->
		<drone:store-graph name="put-graph-in-sparql-graph-store">
			<p:with-option name="graph-uri" select="$xml-base"/>
		</drone:store-graph>
	</p:for-each>
	
</p:declare-step>
