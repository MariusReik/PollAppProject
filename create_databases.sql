-- Create role and database for Keycloak
CREATE ROLE keycloak WITH LOGIN PASSWORD 'keycloak';
CREATE DATABASE keycloak OWNER keycloak;

-- Create role and database for PollApp
CREATE ROLE pollapp WITH LOGIN PASSWORD 'pollapp';
CREATE DATABASE pollapp OWNER pollapp;
