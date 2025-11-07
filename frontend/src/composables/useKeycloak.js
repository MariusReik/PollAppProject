import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
    url: "http://localhost:8082",
    realm: "pollapp-realm",
    clientId: "pollapp-frontend",
});

export const initKeycloak = async () => {
    try {
        const authenticated = await keycloak.init({
            onLoad: "login-required",
            checkLoginIframe: false,
            pkceMethod: "S256",
        });

        if (!authenticated) {
            console.warn("User not authenticated, redirecting to login...");
            await keycloak.login();
        }

        return keycloak;
    } catch (error) {
        console.error("Failed to initialize Keycloak", error);
        throw error;
    }
};

export default keycloak;
