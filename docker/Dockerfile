#FROM orientdb/orientdb:2.1.5
FROM orientdb/orientdb
#COPY ./default-distributed-db-config.json /orientdb/config/default-distributed-db-config.json
#COPY ./orientdb-server-config.xml /orientdb/config/orientdb-server-config.xml
#COPY ./orientdb-server-eu.xml /orientdb/config/orientdb-server-eu.xml
COPY ./databases /orientdb/databases
COPY ./server.sh /orientdb/bin/server.sh
