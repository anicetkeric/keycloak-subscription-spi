package com.bootlabs.provider;

import com.bootlabs.entity.CustomSubscriptionEntity;
import com.bootlabs.factory.CustomJpaEntityProviderFactory;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

import java.util.Collections;
import java.util.List;

public class CustomJpaEntityProvider implements JpaEntityProvider {

    @Override
    public List<Class<?>> getEntities() {
        // Register our entity here
        return Collections.singletonList(CustomSubscriptionEntity.class);
    }

    @Override
    public String getChangelogLocation() {
        // Make sure this file exists in src/main/resources/META-INF
        return "META-INF/custom-subscription-changelog.xml";
    }

    @Override
    public String getFactoryId() {
        // This must match the ID in your factory
        return CustomJpaEntityProviderFactory.ID;
    }

    @Override
    public void close() {
        // Nothing to clean up
    }

}
