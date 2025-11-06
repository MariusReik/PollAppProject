import Keycloak from "keycloak-js";

export const initKeycloak = () => {
    return new Keycloak({
        url: import.meta.env.VITE_KEYCLOAK_URL,      // f.eks. http://localhost:8082
        realm: import.meta.env.VITE_KEYCLOAK_REALM,  // myrealm
        clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID // myclient
    });
};
