#!/bin/bash
set -e

#psql -v ON_ERROR_STOP=1 -U postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'devdata'" | grep -q 1 || psql -U postgres -c "CREATE DATABASE my_db"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER devdata;
    CREATE DATABASE devdata;
    GRANT ALL PRIVILEGES ON DATABASE devdata TO devdata;
    \c devdata devdata;
    CREATE TABLE received(id SERIAL PRIMARY KEY, time TIMESTAMP with time zone, deveui VARCHAR(16), payload_hex VARCHAR(128), message_text VARCHAR(2048));
EOSQL
