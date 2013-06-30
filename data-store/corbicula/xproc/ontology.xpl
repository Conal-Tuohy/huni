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
	
	<p:import href="corbicula.xpl"/>
	
	<p:load name="load-cidoc" href="http://erlangen-crm.org/current/"/>
	<drone:store-graph name="put-graph-in-sparql-graph-store" graph-uri="http://erlangen-crm.org/current/"/>
	
</p:declare-step>
