<?xml version="1.0"?>
<p:library 
	version="1.0" 
	xmlns:p="http://www.w3.org/ns/xproc" 
	xmlns:fn="http://www.w3.org/2005/xpath-functions" 
	xmlns:c="http://www.w3.org/ns/xproc-step" 
	xmlns:drone="http://corbicula.huni.net.au/about/drone" 
	xmlns:corbicula="http://corbicula.huni.net.au/about/corbicula"
	xmlns:oai="http://www.openarchives.org/OAI/2.0/"
	xmlns:pxf="http://exproc.org/proposed/steps/file"
>
	<!-- import calabash extension library to enable use of delete-file step -->
	<p:import href="http://xmlcalabash.com/extension/steps/library-1.0.xpl"/>
	
	<!-- convert XML to RDF -->
	<p:declare-step type="drone:xml-to-rdf" name="xml-to-rdf">
		<!-- TODO allow parameters to be passed to the XSLT -->
	
		<p:input port="source" primary="true"/>
		<p:output port="result"/>
		
		<p:option name="xslt" required="true"/>
		<p:option name="resource-base-uri" required="false" select="''"/>
		
		<p:load name="load-stylesheet">
			<p:with-option name="href" select="concat('../xslt/', $xslt)"/>
		</p:load>

		<p:xslt name="execute-xslt">
			<p:input port="source">
				<p:pipe step="xml-to-rdf" port="source"/>
			</p:input>
			<p:input port="stylesheet">
				<p:pipe step="load-stylesheet" port="result"/>
			</p:input>
			<p:with-param name="resource-base-uri" select="$resource-base-uri"/>
		</p:xslt>
		
	</p:declare-step>
	
	<!-- create provenance graph -->
	<p:declare-step type="drone:create-provenance-graph" name="create-provenance-graph">
		<!-- convert a corbicula ingest script into a provenance graph -->
		<!-- can use xml-to-rdf to do the conversion? -->
		<!-- TODO -->
	</p:declare-step>

	<!-- store graph -->
	<p:declare-step type="drone:store-graph" name="store-graph">
		<p:input port="source"/>
		<p:option name="graph-uri" required="true"/>
		<!-- execute an HTTP PUT to store the graph in the graph store at the location specified -->
		<p:in-scope-names name="variables"/>
		<p:template name="generate-put-request">
			<p:input port="source">
				<p:pipe step="store-graph" port="source"/>
			  </p:input>
			<p:input port="template">
				<p:inline>
					<c:request method="PUT" href="http://localhost:3030/dataset/graph-store?graph={$graph-uri}" detailed="true">
						<c:body content-type="application/rdf+xml">{ /* }</c:body>
					</c:request>
				</p:inline>
			</p:input>
			<p:input port="parameters">
				<p:pipe step="variables" port="result"/>
			</p:input>
		</p:template>
		<!--
		<p:store>
			<p:with-option name="href" select="concat('/tmp/', $graph-uri)"/>
		</p:store>
		-->
		<p:http-request/>
		<p:sink/>
	</p:declare-step>

	
	<!-- list all the harvested records (used e.g. when ingest transformations are changed, to regenerate all derivatives) -->
	<p:declare-step type="drone:list-all-records" name="list-all-records">
		<!-- TODO: reconsider if this should be required? Can't it be generated automatically from the base-uri, set, and metadata prefix? -->
		<p:option name="cache-location" required="true"/>
		<p:output port="updates" sequence="true"/>
		<!-- list all *.xml files except those whose names begin with a "." -->
		<p:directory-list include-filter=".*\.xml" exclude-filter="\..*\.xml">
			<p:with-option name="path" select="$cache-location"/>
		</p:directory-list>
		<p:for-each name="xml-file-name">
			<p:iteration-source select="//c:file"/>
			<p:load name="cached-record">
				<p:with-option name="href" select="concat($cache-location, fn:encode-for-uri(//c:file/@name))"/>
			</p:load>
		</p:for-each>
	</p:declare-step>
	
	
	<!-- OAI-PMH harvesting -->
	
	<!-- harvest records from an OAI-PMH provider -->
	<p:declare-step type="drone:list-new-records" name="list-new-records">
		<p:output port="updates" sequence="true">
			<p:pipe step="records" port="updates"/>
		</p:output>
		<p:output port="deletions" sequence="true">
			<p:pipe step="records" port="deletions"/>
		</p:output>
		<p:output port="errors" sequence="true">
			<p:pipe step="begin-list-records-query" port="errors"/>
			<p:pipe step="records" port="errors"/>
		</p:output>
		
		<p:option name="base-uri" required="true"/>
		<p:option name="metadata-prefix" required="true"/>
		<p:option name="set" select="''"/>
		<!-- TODO: reconsider if this should be required? Can't it be generated automatically from the base-uri, set, and metadata prefix? -->
		<p:option name="cache-location" required="true"/>
		
	
		<!-- read previous harvest date from disk cache to begin query -->
		<p:try name="load-last-harvest-information">
			<p:group name="read-last-harvest-information">
				<p:load name="last-harvest-information">
					<p:with-option name="href" select="concat($cache-location, '.last-harvest.xml')"/>
				</p:load>
			</p:group>
			<p:catch>
				<p:identity>
					<p:input port="source">
						<p:inline>
							<nothing/>
						</p:inline>
					</p:input>
				</p:identity>
			</p:catch>
		</p:try>
		
		<!-- send off the OAI-PMH ListRecords request -->
		<drone:begin-list-records name="begin-list-records-query">
			<p:with-option name="base-uri" select="$base-uri"/>
			<p:with-option name="metadata-prefix" select="$metadata-prefix"/>
			<p:with-option name="set" select="$set"/>
			<p:with-option name="from" select="/*"/>
		</drone:begin-list-records>
		
		<!-- process all the harvested records -->
		<drone:handle-list-records-response name="records">
			<p:with-option name="base-uri" select="$base-uri"/>
			<p:with-option name="cache-location" select="$cache-location"/>
		</drone:handle-list-records-response>
		
		<!-- Compute and save latest datestamp from the sequence of headers-->
		<!-- but only if the sequence is not empty - otherwise leave the log file unchanged -->
		<p:wrap-sequence wrapper="headers" name="assemble-headers-log-file">
			<p:input port="source">
				<p:pipe step="records" port="headers"/>
			</p:input>
		</p:wrap-sequence>
		<p:choose>
			<p:when test="//oai:datestamp">
				<p:xslt name="compute-latest-datestamp">
					<p:input port="parameters">
						<p:empty/>
					</p:input>
					<p:input port="stylesheet">
						<p:inline exclude-inline-prefixes="c fn corbicula pxf">
							<xsl:stylesheet version="1.0"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
								<xsl:template match="/">
									<xsl:for-each select="//oai:datestamp">
										<xsl:sort order="descending"/>
										<xsl:if test="position() = 1">
											<xsl:copy-of select="."/>
										</xsl:if>
									</xsl:for-each>
								</xsl:template>
							</xsl:stylesheet>
						</p:inline>
					</p:input>
				</p:xslt>
				<p:store name="save-latest-datestamp">
					<p:with-option name="href" select="concat($cache-location, '.last-harvest.xml')"/>
				</p:store>
			</p:when>
			<p:otherwise>
				<p:sink/>
			</p:otherwise>
		</p:choose>
	
	</p:declare-step>
	
	<!-- resume a running OAI-PMH harvest -->
	<p:declare-step type="drone:resume-list-records" name="resume-list-records">
		<p:output port="updates" sequence="true">
			<p:pipe step="query-results" port="updates"/>
		</p:output>
		<p:output port="deletions" sequence="true">
			<p:pipe step="query-results" port="deletions"/>
		</p:output>
		<p:output port="headers" sequence="true">
			<p:pipe step="query-results" port="headers"/>
		</p:output>
		<p:output port="errors" sequence="true">
			<p:pipe step="errors" port="result"/>
			<p:pipe step="query-results" port="errors"/>
		</p:output>
		
		<p:option name="base-uri" required="true"/>
		<p:option name="resumption-token" required="true"/>
		
		<!-- TODO: reconsider if this should be required? Can't it be generated automatically from the base-uri, set, and metadata prefix? -->
		<p:option name="cache-location" required="true"/>
		
		<!-- send off the OAI-PMH ListRecords request -->
		<!--
		<p:load name="query-results">
			<p:with-option name="href" select="concat($base-uri, '?verb=ListRecords&amp;resumptionToken=', fn:encode-for-uri($resumption-token))"/>
		</p:load>
		-->
			
		<drone:http-request name="http-request">
			<p:with-option name="uri" select="concat($base-uri, '?verb=ListRecords&amp;resumptionToken=', fn:encode-for-uri($resumption-token))"/>
			<!-- no request body -->
			<p:input port="source"><p:empty/></p:input>
		</drone:http-request>
		
		<!-- a network error or a response with a status != 200 (OK) will be routed to the "errors" port -->
		<p:for-each name="errors">
			<p:iteration-source select="/c:response[@status != '200'] | /c:errors">
				<p:pipe step="http-request" port="result"/>
			</p:iteration-source>
			<p:output port="result" primary="true" sequence="true"/>
			<p:identity/>
		</p:for-each>

		<!-- handle response if the response status is 200 (OK) -->
		<p:for-each name="query-results">
			<p:iteration-source select="/c:response[@status = '200']/c:body/*">
				<p:pipe step="http-request" port="result"/>
			</p:iteration-source>
			<p:output port="updates" sequence="true">
				<p:pipe step="records" port="updates"/>
			</p:output>
			<p:output port="errors" sequence="true">
				<p:pipe step="records" port="errors"/>
			</p:output>
			<p:output port="deletions" sequence="true">
				<p:pipe step="records" port="deletions"/>
			</p:output>
			<p:output port="headers" sequence="true">
				<p:pipe step="records" port="headers"/>
			</p:output>
			<!-- process all the harvested records -->
			<drone:handle-list-records-response name="records">
				<p:with-option name="base-uri" select="$base-uri"/>
				<p:with-option name="cache-location" select="$cache-location"/>
				<p:input port="source">
					<p:pipe step="query-results" port="current"/>
				</p:input>
			</drone:handle-list-records-response>
			<p:sink name="wtf"/>
		</p:for-each>
		
	</p:declare-step>
	
	<p:declare-step type="drone:handle-resumption-token" name="handle-resumption-token">
		<p:input port="source"/>
		<p:output port="updates" sequence="true">
			<p:pipe step="resumption-token" port="updates"/>
		</p:output>
		<p:output port="deletions" sequence="true">
			<p:pipe step="resumption-token" port="deletions"/>
		</p:output>
		<p:output port="headers" sequence="true">
			<p:pipe step="resumption-token" port="headers"/>
		</p:output>
		<p:output port="errors" sequence="true">
			<p:pipe step="resumption-token" port="errors"/>
		</p:output>
		<p:option name="base-uri" required="true"/>
		<p:option name="cache-location" required="true"/>
		<p:for-each name="resumption-token">
			<p:iteration-source select="/oai:OAI-PMH/oai:ListRecords/oai:resumptionToken[normalize-space(.)]"/>
			<p:output port="updates" sequence="true">
				<p:pipe step="subsequent-pages-of-records" port="updates"/>
			</p:output>
			<p:output port="deletions" sequence="true">
				<p:pipe step="subsequent-pages-of-records" port="deletions"/>
			</p:output>
			<p:output port="headers" sequence="true">
				<p:pipe step="subsequent-pages-of-records" port="headers"/>
			</p:output>
			<p:output port="errors" sequence="true">
				<p:pipe step="subsequent-pages-of-records" port="errors"/>
			</p:output>
			<drone:resume-list-records name="subsequent-pages-of-records">
				<p:with-option name="base-uri" select="$base-uri"/>
				<p:with-option name="cache-location" select="$cache-location"/>
				<p:with-option name="resumption-token" select="."/>
			</drone:resume-list-records>
		</p:for-each>
	</p:declare-step>
	

	<p:declare-step type="drone:handle-list-records-response" name="handle-list-records-response">
		<p:input port="source"/>
		<!-- harvested deletions -->
		<p:output port="deletions" sequence="true">
			<p:pipe step="record" port="deletions"/>
			<p:pipe step="subsequent-pages-of-records" port="deletions"/>
		</p:output>
		<!-- harvested updates -->
		<p:output port="updates" sequence="true">
			<p:pipe step="record" port="updates"/>
			<p:pipe step="subsequent-pages-of-records" port="updates"/>
		</p:output>
		<!-- the headers of harvested records -->
		<p:output port="headers" sequence="true">
			<p:pipe step="record" port="headers"/>
			<p:pipe step="subsequent-pages-of-records" port="headers"/>
		</p:output>
		<!-- harvesting errors -->
		<p:output port="errors" sequence="true">
			<p:pipe step="oai-pmh-errors" port="result"/>
			<p:pipe step="subsequent-pages-of-records" port="errors"/>
		</p:output>
		<p:option name="base-uri" required="true"/>
		<p:option name="cache-location" required="true"/>
		
		<p:for-each name="oai-pmh-errors">
			<p:iteration-source select="/oai:OAI-PMH[oai:error]">
				<p:pipe port="source" step="handle-list-records-response"/>
			</p:iteration-source>
			<p:output port="result"/>
			<p:identity/>
		</p:for-each>
		
		<p:for-each name="record">
			<p:iteration-source select="oai:OAI-PMH/oai:ListRecords/oai:record">
				<p:pipe port="source" step="handle-list-records-response"/>
			</p:iteration-source>
			<p:output port="deletions" sequence="true">
				<p:pipe step="deleted-or-updated" port="deletions"/>
			</p:output>
			<p:output port="updates" sequence="true">
				<p:pipe step="deleted-or-updated" port="updates"/>
			</p:output>
			<p:output port="headers" sequence="true">
				<p:pipe step="header" port="result"/>
			</p:output>
			<!-- double-encode the record identifier so as to produce a URI-encoded file name -->
			<p:variable name="file-uri" select="concat($cache-location, fn:encode-for-uri(fn:encode-for-uri(/oai:record/oai:header/oai:identifier)), '.xml')"/>
			<!-- add the OAI identifier URI of the record as the base URI of the root element (the OAI record) and the root element of the enclosed metadata -->
			<p:add-attribute match="/oai:record | /oai:record/oai:metadata/*" attribute-name="xml:base">
				<p:with-option name="attribute-value" select="/oai:record/oai:header/oai:identifier"/>
			</p:add-attribute>
			
			<!-- Report the harvested record as either an update or a deletion -->
			<!-- Cache the new or updated record locally, or delete cached file if harvested record status is deleted -->
			<p:choose name="deleted-or-updated">
				<p:when test="/oai:record/oai:header/@status='deleted'">
					<p:output port="deletions">
						<p:pipe step="deletion" port="result"/>
					</p:output>
					<p:output port="updates" sequence="true">
						<p:empty/>
					</p:output>
					<p:identity name="deletion"/>
					<!-- delete the record from the cache -->
					<!-- ignore errors because the record may not be in the cache -->
					<!--
					<pxf:delete fail-on-error="false">
						<p:with-option name="href" select="$file-uri"/>
					</pxf:delete>
					-->
				</p:when>
				<p:otherwise>
					<!-- record has been created or updated, not deleted -->
					<p:output port="updates">
						<p:pipe step="update" port="result"/>
					</p:output>
					<p:output port="deletions" sequence="true">
						<p:empty/>
					</p:output>
					<p:identity name="update">
						<p:input port="source" select="/oai:record/oai:metadata/*"/>
					</p:identity>
					<!-- cache the harvested record -->
					<p:store name="save-record">
						<p:with-option name="href" select="$file-uri"/>
					</p:store>
				</p:otherwise>
			</p:choose>
					
			<p:identity name="header">
				<p:input port="source" select="oai:record/oai:header">
					<p:pipe step="record" port="current"/>
				</p:input>
			</p:identity>
		</p:for-each>
		
		<!-- query again (recursively) if a resumptionToken was returned in the query results -->
		<drone:handle-resumption-token name="subsequent-pages-of-records">
			<p:input port="source">
				<p:pipe step="handle-list-records-response" port="source"/>
			</p:input>
			<p:with-option name="base-uri" select="$base-uri"/>
			<p:with-option name="cache-location" select="$cache-location"/>
		</drone:handle-resumption-token>
	</p:declare-step>	
	
	<p:declare-step type="drone:begin-list-records" name="begin-list-records">
		<p:output port="result" primary="true" sequence="true">
			<p:pipe step="results" port="result"/>
		</p:output>
		<p:output port="errors" sequence="true">
			<p:pipe step="errors" port="result"/>
		</p:output>
		<p:option name="base-uri" required="true"/>
		<p:option name="metadata-prefix" required="true"/>
		<p:option name="set"/>
		<p:option name="from"/>
		<!-- if set is missing, produces nothing; if set is present, prefixes it with "&amp;set=" -->
		<p:variable name="set-parameter" select="
			fn:replace(
				fn:replace(
					string($set != ''),
					'false',
					''
				), 
				'true', 
				concat('&amp;set=', $set)
			)
		"/>
		<!-- if from is missing, produces nothing; if from is present, prefixes it with "&amp;from=" -->
		<p:variable name="from-parameter" select="
			fn:replace(
				fn:replace(
					string($from != ''),
					'false',
					''
				), 
				'true', 
				concat('&amp;from=', $from)
			)
		"/>
		<drone:http-request name="http-request">
			<p:with-option name="uri" select="
				concat(
					$base-uri,
					'?verb=ListRecords',
					'&amp;metadataPrefix=', $metadata-prefix, 
					$from-parameter,
					$set-parameter
				)
			"/>
			<!-- no request body -->
			<p:input port="source"><p:empty/></p:input>
		</drone:http-request>
		
		<!-- a network error or a response with a status != 200 (OK) will be routed to the "errors" port -->
		<p:for-each name="errors">
			<p:iteration-source select="/c:response[@status != '200'] | /c:errors">
				<p:pipe step="http-request" port="result"/>
			</p:iteration-source>
			<p:output port="result"/>
			<p:identity/>
		</p:for-each>
		<!-- the response entity will be routed to the "result" port if the response status is 200 (OK) -->
		<p:for-each name="results">
			<p:iteration-source select="/c:response[@status = '200']/c:body/*">
				<p:pipe step="http-request" port="result"/>
			</p:iteration-source>
			<p:output port="result"/>
			<p:identity/>
		</p:for-each>
	</p:declare-step>
	
	<p:declare-step type="drone:delete-file" name="delete-file">
		<p:option name="file-uri"/>
		<p:in-scope-names name="variables"/>
		<p:template name="construct-deletion-request">
			<p:input port="template">
				<p:inline>
					<c:request method="DELETE" href="{$file-uri}"/>
				</p:inline>
			</p:input>
			<p:input port="source">
				<p:empty/>
			</p:input>
			<p:input port="parameters">
				<p:pipe step="variables" port="result"/>
			</p:input>
			<p:log port="result" href="delete.xml"/>
		</p:template>
		<p:try name="attempt-to-delete-local-file">
			<p:group>
				<p:http-request name="perform-deletion"/>
				<p:store href="deletion-result.xml"/>
				<!--
				<p:sink name="ignore-deletion-result"/>
				-->
			</p:group>
			<!-- ignore any error caused by the local file not existing -->
			<p:catch name="could-not-delete-local-file">
				<p:store href="deletion-failure.xml"/>
				<!--
				<p:sink name="ignore-deletion-failure"/>
				-->
			</p:catch>
		</p:try>
	</p:declare-step>

	<p:declare-step type="drone:send-mail" name="send-mail">
		<p:input port="message" primary="true"/>
		<p:output port="result" primary="true"/>
		<p:variable name="from" select="/*/@from"/>
		<p:variable name="to" select="/*/@to"/>
		<p:variable name="subject" select="/*/@to"/>
		<!-- 
			mail -s subject -r from-address to-address
		-->
		<!-- execute "mail" program -->
		<p:exec name="mail" command="mail" result-is-xml="false" source-is-xml="false" arg-separator="|">
			<p:with-option name="args" select="concat('-s ', $subject, '|-r ', $from, '|', $to)"/>
		</p:exec>
	</p:declare-step>
	
	<p:declare-step type="drone:run-pipeline" name="run-pipeline">
		<!-- this pipeline is to interpret corbicula pipelines -->
		<p:input port="pipeline" primary="true"/>
		
		<!-- read pipeline configuration into variables -->
		<p:variable name="oai-pmh-provider-base-uri" select="/corbicula:pipeline/corbicula:oai-pmh/corbicula:base-uri"/>
		<p:variable name="oai-pmh-metadata-prefix" select="/corbicula:pipeline/corbicula:oai-pmh/corbicula:metadata-prefix"/>
		<p:variable name="oai-pmh-set" select="/corbicula:pipeline/corbicula:oai-pmh/corbicula:set"/>
		<p:variable name="xslt" select="/corbicula:pipeline/corbicula:crosswalk/corbicula:xslt"/>
		<p:variable name="parameters" select="/corbicula:pipeline/corbicula:crosswalk/corbicula:parameters/corbicula:parameter"/>
		<p:variable name="email" select="/corbicula:pipeline/corbicula:email"/>
		
		<!-- compute cache location -->
		<p:variable name="cache-location" select="
			concat(
				'/data/cache/', 
				fn:encode-for-uri($oai-pmh-provider-base-uri), '/', fn:encode-for-uri($oai-pmh-metadata-prefix), '/',
				fn:replace(
					fn:replace(
						string($set != ''),
						'false',
						''
					), 
					'true', 
					concat(fn:encode-for-uri($oai-pmh-set), '/')
				)
			)
		"/>
		
		<!-- execute harvest -->
		<drone:list-new-records>
			<p:with-option name="base-uri" select="$oai-pmh-provider-base-uri"/>
			<p:with-option name="metadata-prefix" select="$oai-pmh-metadata-prefix"/>
			<p:with-option name="set" select="$oai-pmh-set"/>
			<p:with-option name="cache-location" select="$cache-location"/>
		</drone:list-new-records>
		<!-- TODO -->
		<!-- if crosswalk specified then -->
			<!-- execute crosswalk -->
			<!-- post to graph store -->
		<!-- end if -->
		<!-- report errors / successes via email -->
		<p:sink/>
	</p:declare-step>

	<p:declare-step type="drone:install-pipeline" name="install-pipeline">
		<!-- this pipeline is to install or reinstall a corbicula pipeline, if necessary creating a schedule to launch it -->
		<!-- NB unscheduled pipelines (triggered by other events) may not need to do anything here --> 
		<p:input port="pipeline" primary="true"/>
		<!-- TODO -->
		<p:sink/>
	</p:declare-step>
	
	<p:declare-step type="drone:http-request" name="http-request">
		
		<p:input port="source"/>
		<p:output port="result" primary="true"/>
		
		<p:option name="method" select="'get'"/>
		<p:option name="username"/>
		<p:option name="password"/>
		<p:option name="uri" required="true"/>
		<p:option name="detailed" select="'true'"/>
		<p:option name="accept" select="'text/xml'"/>
		
		<p:in-scope-names name="variables"/>
		<p:choose name="choose-method">
			<p:when test="($method = 'get' or $method='head' or $method='delete')">
				<p:template name="construct-request-without-body">
					<p:input port="template">
						<p:inline exclude-inline-prefixes="c">
							<c:request detailed="{$detailed}" send-authorization="true"  method="{$method}" href="{$uri}" auth-method="Basic" username="{$username}" password="{$password}">
								<c:header name="Accept" value="{$accept}"/>
							</c:request>
						</p:inline>
					</p:input>
					<p:input port="source">
						<p:pipe step="http-request" port="source"/>
					</p:input>
					<p:input port="parameters">
						<p:pipe step="variables" port="result"/>
					</p:input>
				</p:template>
			</p:when>
			<p:otherwise><!-- put or post allow a message body -->
				<p:template name="construct-request-with-body">
					<p:input port="template">
						<p:inline exclude-inline-prefixes="c">
							<c:request detailed="{$detailed}" send-authorization="true" method="{$method}" href="{$uri}" auth-method="Basic" username="{$username}" password="{$password}">
								<c:header name="Accept" value="{$accept}"/>
								<c:body content-type="text/xml">{/*}</c:body>
							</c:request>
						</p:inline>
					</p:input>
					<p:input port="source">
						<p:pipe step="http-request" port="source"/>
					</p:input>
					<p:input port="parameters">
						<p:pipe step="variables" port="result"/>
					</p:input>
				</p:template>
			</p:otherwise>
		</p:choose>
		<p:identity name="http-request-specification"/>
		<p:try>
			<p:group>
				<p:output port="result"/>
				<!-- execute the request -->
				<p:http-request name="execute-request"/>
			</p:group>
			<p:catch name="network-error">
				<p:output port="result"/>
				<p:insert name="request-packaged-as-error" position="first-child">
					<p:input port="source">
						<p:inline exclude-inline-prefixes="fn drone pxf oai corbicula">
							<c:error name="http-request-network-error"/>
						</p:inline>
					</p:input>
					<p:input port="insertion">
						<p:pipe step="http-request-specification" port="result"/>
					</p:input>
				</p:insert>
				<p:insert position="first-child">
					<p:input port="source">
						<p:pipe step="network-error" port="error"/>
					</p:input>
					<p:input port="insertion">
						<p:pipe step="request-packaged-as-error" port="result"/>
					</p:input>
				</p:insert>
			</p:catch>
		</p:try>
	</p:declare-step>	
</p:library>
