#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE patients_db;
    CREATE DATABASE doctors_db;
    CREATE DATABASE appointments_db;
    CREATE DATABASE auth_db;
    CREATE DATABASE pharmacist_db;
    CREATE DATABASE inventory_db;
    CREATE DATABASE prescription_db;
    CREATE DATABASE receptionist_db;
EOSQL
