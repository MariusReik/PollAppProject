import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: window.location.hostname === 'localhost'
    ? 'http://localhost:8082'
    : 'http://pollapp-keycloak:8080',
  realm: 'myrealm',
  clientId: 'myclient',
});

export default keycloak;
