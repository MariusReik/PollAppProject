import { inject } from 'vue';

export function useKeycloak() {
    const keycloak = inject('keycloak');
    if (!keycloak) {
        throw new Error('Keycloak instance not provided');
    }
    return keycloak;
}
