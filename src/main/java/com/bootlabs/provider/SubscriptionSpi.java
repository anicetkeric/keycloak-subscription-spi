package com.bootlabs.provider;

import com.bootlabs.factory.SubscriptionProviderFactory;
import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

/**
 * SPI definition for Subscription management.
 * This tells Keycloak that a new SPI exists and how to load it.
 */
public class SubscriptionSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        // SPI name used by Keycloak to register providers
        return "subscription";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return SubscriptionProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return SubscriptionProviderFactory.class;
    }
}
