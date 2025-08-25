package com.bootlabs.factory;

import com.bootlabs.provider.SubscriptionProvider;
import com.bootlabs.repository.SubscriptionRepositoryProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * Factory for creating instances of {@link SubscriptionProvider}.
 * Registers the provider into Keycloak’s SPI system.
 */
public class SubscriptionProviderFactoryImpl implements SubscriptionProviderFactory {

    public static final String PROVIDER_ID = "subscription-provider";

    @Override
    public SubscriptionProvider create(KeycloakSession session) {
        return new SubscriptionRepositoryProvider(session);
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // Initialize from config if needed
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // Perform actions after all factories initialized
    }

    @Override
    public void close() {
        // Cleanup resources if necessary
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
