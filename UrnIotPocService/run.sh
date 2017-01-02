#!/bin/sh


POSGRESQLPORT="${POSGRESQLPORT:-5432}"

if [ -n "${POSTGRESQL_URL}" ]; then
   sed -i -e "s|postgresql://localhost:5432/|postgresql://${POSTGRESQL_URL}:${POSGRESQLPORT}|g" 
fi

java -jar ${JAVA_OPTS} /opt/spring/PoC-UrnIoT-0.0.1.jar



