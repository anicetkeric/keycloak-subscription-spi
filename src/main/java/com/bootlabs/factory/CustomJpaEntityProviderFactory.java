package com.bootlabs.factory;


import com.bootlabs.provider.CustomJpaEntityProvider;
import org.keycloak.Config;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class CustomJpaEntityProviderFactory implements JpaEntityProviderFactory {

    public static final String ID = "custom-subscription-provider";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public JpaEntityProvider create(KeycloakSession keycloakSession) {
        return new CustomJpaEntityProvider();
    }

    @Override
    public void init(Config.Scope scope) {
    // No special configuration required
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // No post-initialization actions required
    }

    @Override
    public void close() {
        // Nothing to close
    }
}
