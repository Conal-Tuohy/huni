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
	
	<xsl:key name="graph-uris" match="s:binding[@name='g']" use="."/>
	<xsl:key name="results-by-graph" match="s:result" use="s:binding[@name='g']"/>
	<xsl:key name="results-by-graph-and-subject" match="s:result" use="concat(s:binding[@name='g'], ' ', s:binding[@name='s'])"/>
	
	<xsl:variable name="resource" select="/s:sparql/s:results/s:result[1]/s:binding[@name='resource']/s:uri"/>

	<xsl:template match="/s:sparql/s:results">
		<html>
			<head>
				<link rel="stylesheet" type="text/css" href="/css/linked-data.css"/>
				<style type="text/css">
				</style>
				<title><xsl:value-of select="$resource"/></title>
			</head>
			<body>
				<div class="banner">
					<a href="http://huni.net.au/" title="Humanities Networked Infrastructure" alt="Humanities Networked Infrastructure"><img style="class" src="http://huni.net.au/wp-content/uploads/2012/10/huni-logo-21.png"/></a>
					<a href="/"><span>linked data</span></a>
				</div>
				<!-- find the uris of the source graphs -->
				<xsl:variable name="graphs" select="s:result/s:binding[@name='g'][generate-id()=generate-id(key('graph-uris', .))]"/>
				<!-- create a div for each source graph -->
				<xsl:for-each select="$graphs">
					<div class="source-graph">
					<xsl:variable name="graph-uri" select="."/>
					<!-- find the uris of the unique subjects in this graph -->
					<xsl:for-each select="key('results-by-graph', $graph-uri)[generate-id()=generate-id(key('results-by-graph-and-subject', concat($graph-uri, ' ', s:binding[@name='s']))[1])]">
						<xsl:sort select="s:binding[@name='s']"/>
						<xsl:variable name="subject" select="s:binding[@name='s']"/>
						<div class="subject">
							<h2 class="subject"><xsl:apply-templates select="$subject"/></h2>
							<table>
								<xsl:for-each select="key('results-by-graph-and-subject', concat($graph-uri, ' ', $subject))">
									<xsl:sort select="concat(s:binding[@name='p'], s:binding[@name='o'])"/>
									<tr>
										<td class="predicate"><xsl:apply-templates select="s:binding[@name='p']"/></td>
										<td class="object"><xsl:apply-templates select="s:binding[@name='o']"/></td>
									</tr>
								</xsl:for-each>
							</table>
						</div>
					</xsl:for-each>
					<cite><xsl:apply-templates select="$graph-uri"/></cite>
				</div>
			</xsl:for-each>
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
				<span class="prefix"><xsl:value-of select="$namespace/@prefix"/><xsl:text>:</xsl:text></span>
				<span class="local-name"><xsl:value-of select="substring-after($uri, $namespace/@uri)"/></span>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$uri"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
