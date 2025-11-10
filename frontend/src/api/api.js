import axios from "axios";
import keycloak from "../composables/useKeycloak.js"; // must be the actual instance

const api = axios.create({
    baseURL: "/api", // proxied to backend (see vite.config.js)
});

// Add Authorization header automatically
api.interceptors.request.use(async (config) => {
    if (keycloak?.authenticated) {
        try {
            // Refresh if needed
            await keycloak.updateToken(30);
            config.headers.Authorization = `Bearer ${keycloak.token}`;
        } catch (err) {
            console.warn("Token refresh failed → redirecting to login");
            keycloak.login();
        }
    }
    return config;
});

export default api;
