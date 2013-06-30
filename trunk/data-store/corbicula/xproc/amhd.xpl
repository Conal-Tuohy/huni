<?xml version="1.0"?>
<p:declare-step 
	version="1.0" 
	xmlns:p="http://www.w3.org/ns/xproc" 
	xmlns:fn="http://www.w3.org/2005/xpath-functions" 
	xmlns:c="http://www.w3.org/ns/xproc-step" 
	xmlns:drone="http://corbicula.huni.net.au/about/drone" 
	xmlns:oai="http://www.openarchives.org/OAI/2.0/"
>
	<p:import href="corbicula.xpl"/>

	<!--
	<drone:list-all-records
		name="harvest"
		cache-location="/var/corbicula/amhd/"/>
-->
		

	<drone:list-new-records 
		name="harvest" 
		base-uri="http://amhd.info/oai/provider" 
		cache-location="/var/corbicula/amhd/" 
		metadata-prefix="oai_dc"/>

	
	<p:for-each name="transform-record-to-rdf">
		<p:iteration-source>
			<p:pipe step="harvest" port="updates"/>
		</p:iteration-source>
		<p:variable name="xml-base" select="/*/@xml:base"/>
		<drone:xml-to-rdf name="rdf" xslt="map-oai_dc-to-rdf.xsl" resource-base-uri="http://corbicula.huni.net.au/data/amhd/"/>


		<drone:store-graph name="put-graph-in-sparql-graph-store">
			<p:with-option name="graph-uri" select="$xml-base"/>
		</drone:store-graph>
	</p:for-each>	
	
</p:declare-step>
