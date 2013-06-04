<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:s="http://www.w3.org/2005/sparql-results#" 
	xmlns:lookup="local-namespace"
	exclude-result-prefixes="s">
	
	<xsl:key name="results-by-person-uri" match="s:result" use="s:binding[@name='person']/s:uri"/>
	
	<xsl:template match="/s:sparql/s:results">
		<html>
			<head>
				<link rel="stylesheet" type="text/css" href="/css/linked-data.css"/>
				<style type="text/css">
				</style>
				<title>People by occupation</title>
			</head>
			<body>
				<div class="banner">
					<a href="http://huni.net.au/" title="Humanities Networked Infrastructure" alt="Humanities Networked Infrastructure"><img style="class" src="http://huni.net.au/wp-content/uploads/2012/10/huni-logo-21.png"/></a>
					<a href="/"><span>linked data</span></a>
				</div>
				<h1>People by occupation</h1>
				<xsl:choose>
					<xsl:when test="s:result">
						<table>
							<tr>
								<th>Person</th>
								<th>Birth</th>
								<th>Death</th>
								<th>Occupation</th>
								<th>Search</th>
							</tr>
						<xsl:for-each select="s:result">
							<xsl:sort select="s:binding[@name='lastName']"/>
							<xsl:variable name="firstName" select="s:binding[@name='firstName']/s:literal"/>
							<xsl:variable name="lastName" select="s:binding[@name='lastName']/s:literal"/>
							<tr>
								<td><a href="{s:binding[@name='person']/s:uri}"><xsl:value-of select="s:binding[@name='lastName']/s:literal"/>, <xsl:value-of select="$firstName"/></a></td>
								<td><xsl:value-of select="s:binding[@name='birthDate']"/></td>
								<td><xsl:value-of select="s:binding[@name='deathDate']"/></td>
								<xsl:choose>
									<xsl:when test="s:binding[@name='occupation']">
										<td><a href="{s:binding[@name='occupation']/s:uri}"><xsl:value-of select="substring-after(s:binding[@name='occupation']/s:uri, 'http://corbicula.huni.net.au/data/')"/></a></td>
									</xsl:when>
									<xsl:otherwise>
										<td><a href="{s:binding[@name='occupation1']/s:uri}"><xsl:value-of select="substring-after(s:binding[@name='occupation1']/s:uri, 'http://corbicula.huni.net.au/data/')"/></a> <a href="{s:binding[@name='occupation2']/s:uri}"><xsl:value-of select="substring-after(s:binding[@name='occupation2']/s:uri, 'http://corbicula.huni.net.au/data/')"/></a></td>
									</xsl:otherwise>
								</xsl:choose>
								<td><a href="http://trove.nla.gov.au/newspaper/result?anyWords={$firstName}+{$lastName}">Trove</a></td>
							</tr>
						</xsl:for-each>
						</table>
					</xsl:when>
					<xsl:otherwise>
						<p>No people matched your query.</p>
					</xsl:otherwise>
				</xsl:choose>
			</body>
		</html>
	</xsl:template>
	
</xsl:stylesheet>
