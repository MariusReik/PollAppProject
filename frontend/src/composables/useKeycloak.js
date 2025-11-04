import Keycloak from 'keycloak-js'

let keycloakInstance = null

export function initKeycloak() {
    if (!keycloakInstance) {
        keycloakInstance = new Keycloak({
            url: import.meta.env.VITE_KEYCLOAK_URL,
            realm: import.meta.env.VITE_KEYCLOAK_REALM,
            clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID,
        })
    }
    return keycloakInstance
}

export function getKeycloak() {
    if (!keycloakInstance) {
        throw new Error('Keycloak not initialized. Call initKeycloak() first.')
    }
    return keycloakInstance
}
