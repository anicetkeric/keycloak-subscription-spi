package com.bootlabs.factory;


import com.bootlabs.rest.SubscriptionResource;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

/**
 * Factory to register the Subscription REST resource with Keycloak.
 */
public class SubscriptionResourceProviderFactory implements RealmResourceProviderFactory {

    public static final String ID = "subscription-api";

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new SubscriptionResource(session);
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        // do nothing
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // do nothing
    }

    @Override
    public void close() {
        // do nothing
    }

    @Override
    public String getId() {
        return ID;
    }
}
