package com.bootlabs.rest;


import com.bootlabs.dto.SubscriptionModel;
import com.bootlabs.dto.SubscriptionRepresentation;
import com.bootlabs.entity.CustomSubscriptionEntity;
import com.bootlabs.provider.SubscriptionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.services.resource.RealmResourceProvider;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * REST API for managing subscriptions.
 */
public class SubscriptionResource implements RealmResourceProvider {

    private final KeycloakSession session;

    public SubscriptionResource(KeycloakSession session) {
        this.session = session;
    }

    private SubscriptionProvider provider() {
        SubscriptionProvider provider = session.getProvider(SubscriptionProvider.class, "subscription-provider");
        if (provider == null) {
            throw new IllegalStateException("SubscriptionProvider is not found. Did you register the SPI correctly?");
        }
        return provider;
    }

    /**
     * Create a new subscription.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSubscription(SubscriptionModel model) {

        CustomSubscriptionEntity entity = provider().create(model);
        UserModel user = session.users().getUserById(session.getContext().getRealm(), model.getUserId());
        SubscriptionRepresentation rep = toRepresentation(entity, user);

        return Response.ok(rep).build();
    }

    /**
     * Get subscription by ID.
     */
    @GET
    @Path("/{id}")
    public Response getSubscription(@PathParam("id") String id) {
        CustomSubscriptionEntity entity = provider().getById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        UserModel user = session.users().getUserById(session.getContext().getRealm(), entity.getUser().getId());
        SubscriptionRepresentation rep = toRepresentation(entity, user);

        return Response.ok(rep).build();
    }

    /**
     * Get all subscriptions.
     */
    @GET
    public Response getAllSubscriptions() {
        List<CustomSubscriptionEntity> entities = provider().getAll();

        List<SubscriptionRepresentation> reps = entities.stream()
                .map(entity -> {
                    UserModel user = session.users().getUserById(session.getContext().getRealm(), entity.getUser().getId());
                    return toRepresentation(entity, user);
                })
                .toList();

        return Response.ok(reps).build();
    }

    /**
     * Update subscription.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSubscription(@PathParam("id") String id, SubscriptionModel model) {
        CustomSubscriptionEntity updated = provider().update(id, model);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        UserModel user = session.users().getUserById(session.getContext().getRealm(), updated.getUser().getId());
        SubscriptionRepresentation rep = toRepresentation(updated, user);

        return Response.ok(rep).build();
    }

    /**
     * Delete subscription.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteSubscription(@PathParam("id") String id) {
        boolean deleted = provider().delete(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }


    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {
        // Nothing to clean up
    }


    private SubscriptionRepresentation toRepresentation(CustomSubscriptionEntity entity, UserModel userModel) {
        if (entity == null) return null;

        SubscriptionRepresentation rep = new SubscriptionRepresentation();
        rep.setId(entity.getId());

        UserRepresentation userRep = ModelToRepresentation.toBriefRepresentation(userModel);
        rep.setUser(userRep);

        rep.setPlan(entity.getPlanType());
        rep.setStartDate(entity.getStartDate());
        rep.setEndDate(entity.getEndDate());

        return rep;
    }
}
