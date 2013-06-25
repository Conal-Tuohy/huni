
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:s="http://www.w3.org/2005/sparql-results#" xmlns:lookup="local-namespace" exclude-result-prefixes="s">
	
	<!--
		generic stylesheet as a fallback for resources whose classes are not specifically handled by a page template
	-->
	<xsl:import href="sparql-results-to-html.xsl"/>
	
	<xsl:key name="graph-uris" match="s:binding[@name='g']" use="."/>
	<xsl:key name="results-by-graph" match="s:result" use="s:binding[@name='g']"/>
	<xsl:key name="results-by-graph-and-subject" match="s:result" use="concat(s:binding[@name='g'], ' ', s:binding[@name='s'])"/>

	<xsl:variable name="results" select="/s:sparql/s:results/s:result"/>
	
	<!-- template matches the sparql query results document -->
	<xsl:template match="/">
		<!-- See if there is a page template available to process a resource of this class; if so, use it -->
		<!-- If not, fall back to the generic stylesheet -->
	
		<!-- The "root resource" is the central node of this dataset. This is where the template will start -->	
		<xsl:variable name="root-resource" select="/s:sparql/s:results/s:result[1]/s:binding[@name='resource']/s:uri"/>
		
		<!-- the class of the root resource determines which template is used to render it -->
		<xsl:variable name="root-resource-class" select="string(/s:sparql/s:results/s:result[s:binding[@name='s']/s:uri = $root-resource][s:binding[@name='p']/s:uri='http://www.w3.org/1999/02/22-rdf-syntax-ns#type']/s:binding[@name='o']/s:uri)"/>
		
		<!-- the map of RDF classes to template pages is used to select the template page appropriate to the resource's class -->
		<xsl:variable name="class-to-template-map" select="document('/linked-data/templates/class-to-template-map.xml')"/>
		
		<!-- the name of the template for this class is drawn from the map -->
		<xsl:variable name="class-template-name" select="$class-to-template-map/map/class[@enabled='true'][@uri=$root-resource-class]/@template"/>

		<xsl:choose>
			<xsl:when test="$class-template-name">
				<!-- a template existed for the class -->
				<xsl:variable name="template" select="document($class-template-name)"/>
				<xsl:apply-templates select="$template/*" mode="graph">
					<xsl:with-param name="current-node" select="$root-resource"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:otherwise>
				<!-- No template defined and enabled for this class - use a generic styling instead -->
				<xsl:apply-imports/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- 
	two modes: "graph" and "triple"
	In graph mode, handle any graph-repetition attribute attached to the template node,
	and apply templates to the same template node in "triple" mode.
	In triple mode, handle any triple repetition attributes, and apply templates to child nodes of the template node, in graph mode.
	-->
	
	<xsl:template match="text()|*[not(@graph)]" mode="graph">
		<xsl:param name="current-node"/>
		<xsl:param name="current-graph" select="/.."/>
		<xsl:apply-templates select="." mode="triple">
			<xsl:with-param name="current-graph" select="$current-graph"/>
			<xsl:with-param name="current-node" select="$current-node"/>
		</xsl:apply-templates>
	</xsl:template>
	
	<!-- handle any graph-switching code attached to template node -->
	<xsl:template match="*" mode="graph">
		<xsl:param name="current-node"/>
		<xsl:param name="current-graph" select="/.."/>
		<xsl:choose>
			<xsl:when test="@graph='any'">
				<xsl:apply-templates select="." mode="triple">
					<xsl:with-param name="current-graph" select="''"/>
					<xsl:with-param name="current-node" select="$current-node"/>
				</xsl:apply-templates>
			</xsl:when>
			<xsl:when test="@graph='each'">
				<xsl:variable name="current-template" select="."/>
				<!-- repeat for each graph
					=> apply templates to current template
				-->
				<xsl:for-each select="$results">
					<!-- loop over each unique graph uri -->
					<xsl:if test="not(
						preceding-sibling::s:result[
							s:binding[@name='g']/s:uri = current()/s:binding[@name='g']/s:uri
						]
					)">
						<xsl:variable name="graph" select="s:binding[@name='g']/s:uri/text()"/>
						<!-- apply templates to triples whose subject is the current node, within the current graph -->
						<!-- if the current graph doesn't contain any triples with that subject, ignore the graph -->
						<xsl:if test="$results[s:binding[@name='g']/s:uri = $graph][s:binding[@name='s']/s:uri = $current-node]">
							<xsl:apply-templates mode="triple" select="$current-template">
								<xsl:with-param name="current-node" select="$current-node"/>
								<xsl:with-param name="current-graph" select="$graph"/>
							</xsl:apply-templates>
						</xsl:if>
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template match="@for-each" mode="triple"/>
	
	<xsl:template match="*[@for-each]" mode="triple">
		<xsl:param name="current-node"/>
		<xsl:param name="current-graph"/><!--
		<xsl:comment>for-each="<xsl:value-of select="@for-each"/>" current template="<xsl:value-of select="local-name()"/>" current-node="<xsl:value-of select="$current-node"/>" current-graph="<xsl:value-of select="$current-graph"/>"</xsl:comment>-->
		<xsl:call-template name="evaluate-expression">
			<xsl:with-param name="expression" select="@for-each"/>
			<xsl:with-param name="current-node" select="$current-node"/>
			<xsl:with-param name="context-node" select="$current-node"/>
			<xsl:with-param name="current-graph" select="$current-graph"/>
			<xsl:with-param name="continuation" select=" 'for-each' "/>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template match="@*" mode="triple">
		<xsl:copy/>
	</xsl:template>
	
	<xsl:template match="@if" mode="triple"/>
	
	<xsl:template match="*[@if]" mode="triple">
		<xsl:param name="current-node"/>
		<xsl:param name="current-graph"/>
		<!--<xsl:comment>for-each="<xsl:value-of select="@for-each"/>" current template="<xsl:value-of select="local-name()"/>" current-node="<xsl:value-of select="$current-node"/>" current-graph="<xsl:value-of select="$current-graph"/>"</xsl:comment>-->
		<xsl:call-template name="evaluate-expression">
			<xsl:with-param name="expression" select="@if"/>
			<xsl:with-param name="current-node" select="$current-node"/>
			<xsl:with-param name="context-node" select="$current-node"/>
			<xsl:with-param name="current-graph" select="$current-graph"/>
			<xsl:with-param name="continuation" select=" 'if' "/>
		</xsl:call-template>
	</xsl:template>	
	
	<xsl:template match="text()[contains(., '{')]" mode="triple">
		<xsl:param name="current-node"/>
		<xsl:param name="current-graph"/>
		<xsl:call-template name="evaluate-expressions">
			<xsl:with-param name="current-node" select="$current-node"/>
			<xsl:with-param name="context-node" select="$current-node"/>
			<xsl:with-param name="current-graph" select="$current-graph"/>
			<xsl:with-param name="continuation" select=" 'value-of' "/>
		</xsl:call-template>
	</xsl:template>
	
	<!-- expand value templates e.g. {http://xmlns.com/foaf/0.1/isPrimaryTopicOf} in attribute values -->
	<xsl:template match="@*[contains(., '{')]" mode="triple">
		<xsl:param name="current-node"/>
		<xsl:param name="current-graph"/>
		<xsl:attribute name="{local-name(.)}">
			<xsl:call-template name="evaluate-expressions">
				<xsl:with-param name="current-node" select="$current-node"/>
				<xsl:with-param name="context-node" select="$current-node"/>
				<xsl:with-param name="current-graph" select="$current-graph"/>
				<xsl:with-param name="continuation" select="'value-of'"/>
			</xsl:call-template>
		</xsl:attribute>
	</xsl:template>

	
	<xsl:template match="*" mode="triple">
		<xsl:param name="current-node"/>
		<xsl:param name="current-graph"/>
		<xsl:copy>
			<xsl:apply-templates select="@*" mode="triple">
				<xsl:with-param name="current-node" select="$current-node"/>
				<xsl:with-param name="current-graph" select="$current-graph"/>
			</xsl:apply-templates>
			<xsl:apply-templates mode="graph">
				<xsl:with-param name="current-node" select="$current-node"/>
				<xsl:with-param name="current-graph" select="$current-graph"/>
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>
	
	<xsl:template name="evaluate-expressions">
		<xsl:param name="text" select="."/>
		<xsl:param name="current-node"/>
		<xsl:param name="current-graph"/>
		<xsl:param name="continuation"/>
		<xsl:value-of select="substring-before($text, '{')"/>
		<xsl:call-template name="evaluate-expression">
			<xsl:with-param name="expression" select="substring-before(substring-after($text, '{'), '}')"/>
			<xsl:with-param name="current-node" select="$current-node"/>
			<xsl:with-param name="context-node" select="$current-node"/>
			<xsl:with-param name="current-graph" select="$current-graph"/>
			<xsl:with-param name="continuation" select="$continuation"/>
		</xsl:call-template>
		<xsl:variable name="remainder" select="substring-after($text, '}')"/>
		<xsl:if test="$remainder">
			<xsl:call-template name="evaluate-expressions">
				<xsl:with-param name="text" select="$remainder"/>
				<xsl:with-param name="current-node" select="$current-node"/>
				<xsl:with-param name="context-node" select="$current-node"/>
				<xsl:with-param name="current-graph" select="$current-graph"/>
				<xsl:with-param name="continuation" select="$continuation"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	
		<!-- qaz evaluate the expression as a path of one or more predicates -->
		<!-- qaz evaluate within the context of the current graph -->
	<xsl:template name="evaluate-expression">
		<xsl:param name="expression"/>
		<xsl:param name="current-node"/>
		<xsl:param name="context-node"/>
		<xsl:param name="current-graph"/>
		<xsl:param name="continuation"/>
		<!--<xsl:comment>Expression: <xsl:value-of select="$expression"/></xsl:comment>-->
		<xsl:variable name="normalized-expression" select="normalize-space($expression)"/>
		<xsl:variable name="first-step" select="substring-before(concat($normalized-expression, ' '), ' ')"/>
		<xsl:choose>
			<xsl:when test="contains($normalized-expression, ' ')">
				<!-- the expression is a path containing at least two steps, so process it recursively -->
				<xsl:variable name="remaining-steps" select="substring-after($normalized-expression, ' ')"/>
				<!-- iterate over objects of the current triples, select triples with the matching subjects, and
				and evaluate remainder of the expression using those triples as context -->
				<xsl:variable name="triples" select="
					$results
						[s:binding[@name='s']/s:uri/text() = $context-node]
						[s:binding[@name='p']/s:uri/text() = $first-step]
						[(s:binding[@name='g']/s:uri/text() = $current-graph) or (not($current-graph))]
				"/>
				<!--<xsl:comment>triples in graph "<xsl:value-of select="$current-graph"/>" matching subject "<xsl:value-of select="$context-node"/>" and predicate "<xsl:value-of select="$first-step"/>": <xsl:for-each select="$triples">
				{s=<xsl:value-of select="normalize-space(s:binding[@name='s'])"/> p=<xsl:value-of select="normalize-space(s:binding[@name='p'])"/> o=<xsl:value-of select="normalize-space(s:binding[@name='o'])"/> g=<xsl:value-of select="normalize-space(s:binding[@name='g'])"/>}</xsl:for-each></xsl:comment>-->
				<xsl:variable name="current-template" select="."/>
				<xsl:for-each select="$triples">
					<xsl:variable name="object" select="string(s:binding[@name='o']/s:uri/text())"/>
					<!--<xsl:comment>New context node = <xsl:value-of select="$object"/></xsl:comment>-->
					<xsl:for-each select="$current-template">
						<xsl:call-template name="evaluate-expression">
							<xsl:with-param name="expression" select="$remaining-steps"/>
							<xsl:with-param name="current-node" select="$current-node"/>
							<xsl:with-param name="context-node" select="$object"/>
							<xsl:with-param name="current-graph" select="$current-graph"/>
							<xsl:with-param name="continuation" select="$continuation"/>
						</xsl:call-template>
					</xsl:for-each>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<!-- the expression consists of a single step (predicate). Evaluate it and continue the continuation -->
				<!--<xsl:comment>Expression <xsl:value-of select="$expression"/> is a single predicate</xsl:comment>-->
				<xsl:call-template name="execute-operation-on-triples">
					<xsl:with-param name="expression" select="$expression"/>
					<xsl:with-param name="current-node" select="$current-node"/>
					<xsl:with-param name="context-node" select="$context-node"/>
					<xsl:with-param name="current-graph" select="$current-graph"/>
					<xsl:with-param name="continuation" select="$continuation"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="execute-operation-on-triples">
		<xsl:param name="expression"/>
		<xsl:param name="current-node"/>
		<xsl:param name="context-node"/>
		<xsl:param name="current-graph"/>
		<xsl:param name="continuation"/>

		<!--<xsl:comment>Current node = <xsl:value-of select="$current-node"/>. Context node="<xsl:value-of select="$context-node"/>". Expression="<xsl:value-of select="$expression"/></xsl:comment>-->
		<xsl:variable name="current-template" select="."/>
		<xsl:choose>
			<xsl:when test="$expression='graph'">
				<!-- qaz if continuation is value-of -->
				<xsl:value-of select="$current-graph"/>
			</xsl:when>
			<xsl:when test="$expression='.'">
				<!-- "current node" -->
				<xsl:value-of select="$current-node"/>
			</xsl:when>
			<xsl:otherwise><!-- expression is a predicate - return all matching property values of $current-node -->
				<xsl:variable name="triples" select="
					$results
						[s:binding[@name='s']/s:uri/text() = $context-node]
						[s:binding[@name='p']/s:uri/text() = $expression]
						[(s:binding[@name='g']/s:uri/text() = $current-graph) or (not($current-graph))]
				"/>
				<!--<xsl:comment>triples in graph "<xsl:value-of select="$current-graph"/>" matching subject "<xsl:value-of select="$context-node"/>" and predicate "<xsl:value-of select="$expression"/>": <xsl:for-each select="$triples">
				{s=<xsl:value-of select="normalize-space(s:binding[@name='s'])"/> p=<xsl:value-of select="normalize-space(s:binding[@name='p'])"/> o=<xsl:value-of select="normalize-space(s:binding[@name='o'])"/> g=<xsl:value-of select="normalize-space(s:binding[@name='g'])"/>}</xsl:for-each></xsl:comment>-->
				<xsl:choose>
					<xsl:when test="$continuation='value-of'">
						<xsl:apply-templates mode="value-of" select="($triples/s:binding[@name='o']/*)[1]"/>
					</xsl:when>
					<xsl:when test="$continuation='if'">
						<!--<xsl:comment>evaluating "if"; triples are: <xsl:value-of select="$triples"/></xsl:comment>-->
						<xsl:if test="$triples"><!--/s:binding[@name='o']/s:uri">-->
							<xsl:copy>
								<xsl:apply-templates select="@*" mode="triple"/>
								<xsl:apply-templates mode="graph">
									<xsl:with-param name="current-node" select="$current-node"/>
									<xsl:with-param name="current-graph" select="$current-graph"/>
								</xsl:apply-templates>
							</xsl:copy>
						</xsl:if>
					</xsl:when>
					<xsl:when test="$continuation='for-each'">
						<xsl:for-each select="$triples/s:binding[@name='o']/s:uri">
							<xsl:variable name="resource" select="string(.)"/>
							<xsl:for-each select="$current-template">
								<xsl:copy>
									<xsl:apply-templates select="@*" mode="triple"/>
									<xsl:apply-templates mode="graph">
										<xsl:with-param name="current-node" select="$resource"/>
										<xsl:with-param name="current-graph" select="$current-graph"/>
									</xsl:apply-templates>
								</xsl:copy>
							</xsl:for-each>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise></xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="s:literal[@datatype='http://www.w3.org/2001/XMLSchema#date']" mode="value-of">
		<xsl:variable name="year" select="substring(., 1, 4)"/>
		<xsl:variable name="month" select="substring(., 6, 2)"/>
		<xsl:variable name="day" select="substring(., 9)"/>
		<xsl:value-of select="$day"/>
		<xsl:text> </xsl:text>
		<xsl:choose>
			<xsl:when test="$month='01'">January</xsl:when>	
			<xsl:when test="$month='02'">February</xsl:when>	
			<xsl:when test="$month='03'">March</xsl:when>	
			<xsl:when test="$month='04'">April</xsl:when>	
			<xsl:when test="$month='05'">May</xsl:when>	
			<xsl:when test="$month='06'">June</xsl:when>	
			<xsl:when test="$month='07'">July</xsl:when>	
			<xsl:when test="$month='08'">August</xsl:when>	
			<xsl:when test="$month='09'">September</xsl:when>	
			<xsl:when test="$month='10'">October</xsl:when>	
			<xsl:when test="$month='11'">November</xsl:when>	
			<xsl:when test="$month='12'">December</xsl:when>	
			<xsl:otherwise/>
		</xsl:choose>
		<xsl:text> </xsl:text>
		<xsl:value-of select="$year"/>
	</xsl:template>
	
</xsl:stylesheet>
