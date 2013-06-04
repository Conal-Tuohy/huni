<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:s="http://www.w3.org/2005/sparql-results#" 
	xmlns:lookup="local-namespace"
	exclude-result-prefixes="s">
	
	<!-- some known namespaces we can abbreviate in the UI -->
	<lookup:table>
		<lookup:namespace prefix="owl" uri="http://www.w3.org/2002/07/owl#"/>
		<lookup:namespace prefix="foaf" uri="http://xmlns.com/foaf/0.1/"/>
		<lookup:namespace prefix="cidoc" uri="http://erlangen-crm.org/current/"/>
		<lookup:namespace prefix="rdf" uri="http://www.w3.org/1999/02/22-rdf-syntax-ns#"/>
		<lookup:namespace prefix="rdfs" uri="http://www.w3.org/2000/01/rdf-schema#"/>
		<lookup:namespace prefix="HuNI" uri="http://corbicula.huni.net.au/data/"/>
	</lookup:table>
	
	<xsl:variable name="namespaces" select="document('')//lookup:namespace"/>

	<xsl:template match="/s:sparql/s:results">
		<html>
			<head>
				<link rel="stylesheet" type="text/css" href="/css/linked-data.css"/>
				<style type="text/css">
				</style>
				<title>List of types</title>
			</head>
			<body>
				<div class="banner">
					<a href="http://huni.net.au/" title="Humanities Networked Infrastructure" alt="Humanities Networked Infrastructure"><img style="class" src="http://huni.net.au/wp-content/uploads/2012/10/huni-logo-21.png"/></a>
					<a href="/"><span>linked data</span></a>
				</div>
				<div>
					<h2>List of types</h2>
					<ul>
						<xsl:for-each select="/s:sparql/s:results/s:result/s:binding[@name='type']/s:uri">
							<xsl:sort select="."/>
							<li><xsl:apply-templates select="."/></li>
						</xsl:for-each>
					</ul>
				</div>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="s:uri">
		<xsl:choose>
			<xsl:when test="starts-with(., 'http:')">
				<a href="{.}"><xsl:apply-templates mode="render-uri" select="."/></a>
			</xsl:when>
			<xsl:otherwise>
				<a href="/dataset/graph-store?graph={.}"><xsl:value-of select="."/></a>
<!--
				TODO render link to SPARQL query for all triples in this graph, using this stylesheet
				<a href="/dataset/query?output-xml&amp;stylesheet=/xslt/sparql-results-to-html.xsl&amp;query=select
-->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="s:uri" mode="render-uri">
		<xsl:variable name="uri" select="."/>
		<xsl:variable name="namespace" select="$namespaces[starts-with($uri, @uri)][1]"/>
		<xsl:choose>
			<xsl:when test="$namespace">
				<span class="prefix">	<xsl:value-of select="$namespace/@prefix"/><xsl:text>:</xsl:text></span>
				<span class="local-name"><xsl:value-of select="substring-after($uri, $namespace/@uri)"/></span>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$uri"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
