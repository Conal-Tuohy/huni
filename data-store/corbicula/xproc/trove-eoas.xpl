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
		cache-location="/data/xml/trove-eoas/"/>	
		<!--
	<drone:list-new-records 
		name="harvest" 
		base-uri="http://www.nla.gov.au/apps/peopleaustralia-oai/OAIHandler" 
		set="AU-VU:EOAS"
		cache-location="/data/xml/trove-eoas/" 
		metadata-prefix="eac-cpf"/>	
		-->
	
	<p:for-each name="transform-record-to-rdf">
		<p:iteration-source>
			<p:pipe step="harvest" port="updates"/>
		</p:iteration-source>
		<p:variable name="xml-base" select="/*/@xml:base"/>
		<drone:xml-to-rdf name="rdf" xslt="trove-eac-cpf-to-rdf.xsl"/>

		<drone:store-graph name="put-graph-in-sparql-graph-store">
			<p:with-option name="graph-uri" select="$xml-base"/>
		</drone:store-graph>
	</p:for-each>
	
</p:declare-step>
