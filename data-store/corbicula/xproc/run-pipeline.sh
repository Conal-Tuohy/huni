#!/bin/bash
java -Djava.util.logging.config.file=/opt/calabash-1.0.9-94/calabash-logging.properties -Xmx1000m -jar /opt/calabash-1.0.9-94/calabash.jar -D "$@"
