package com.bootlabs.provider;


import com.bootlabs.dto.SubscriptionModel;
import com.bootlabs.entity.CustomSubscriptionEntity;
import org.keycloak.provider.Provider;

import java.util.List;


/**
 * SubscriptionProvider defines the contract for managing
 * subscription entities in Keycloak.
 *
 * This provider is responsible for handling CRUD operations
 * on {@link CustomSubscriptionEntity}, which extends the Keycloak
 * persistence layer with subscription management features.
 *
 * Typical usage includes:
 * - Creating a subscription when a user registers
 * - Retrieving subscriptions by user or ID
 * - Updating subscription plans
 * - Deleting subscriptions when accounts are removed
 *
 * The provider is managed by Keycloak and should not
 * handle transactions directly; Keycloak manages transactions
 * around provider calls.
 */
public interface SubscriptionProvider extends Provider {

    /**
     * Creates a new subscription based on the provided model.
     *
     * @param model the subscription input model
     * @return the created {@link CustomSubscriptionEntity}
     */
    CustomSubscriptionEntity create(SubscriptionModel model);

    /**
     * Retrieves a subscription by its primary key.
     */
    CustomSubscriptionEntity getById(String id);

    /**
     * Retrieves a subscription linked to a specific user.
     */
    CustomSubscriptionEntity getByUser(String userId);

    /**
     * Retrieves all subscriptions across the realm.
     */
    List<CustomSubscriptionEntity> getAll();

    /**
     * Updates a subscription with new values from the model.
     *
     * @param id the subscription ID
     * @param model the updated subscription input model
     * @return the updated {@link CustomSubscriptionEntity}, or null if not found
     */
    CustomSubscriptionEntity update(String id, SubscriptionModel model);

    /**
     * Deletes a subscription by its ID.
     */
    boolean delete(String id);
}