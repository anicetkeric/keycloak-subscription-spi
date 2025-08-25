package com.bootlabs.repository;

import com.bootlabs.dto.SubscriptionModel;
import com.bootlabs.entity.CustomSubscriptionEntity;
import com.bootlabs.provider.SubscriptionProvider;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.keycloak.models.jpa.entities.UserEntity;

import java.util.List;

/**
 * JPA implementation of the SubscriptionProvider with model-based input.
 */
public class SubscriptionRepositoryProvider implements SubscriptionProvider {

    private final KeycloakSession session;
    private final EntityManager em;

    public SubscriptionRepositoryProvider(KeycloakSession session) {
        this.session = session;
        this.em = session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    @Override
    public CustomSubscriptionEntity create(SubscriptionModel model) {
        CustomSubscriptionEntity sub = new CustomSubscriptionEntity();
        sub.setId(java.util.UUID.randomUUID().toString());
        sub.setPlanType(model.getPlan().name());
        sub.setStartDate(model.getStartDate());
        sub.setEndDate(model.getEndDate());

        // Fetch the Keycloak user entity
        UserEntity userEntity = em.find(UserEntity.class, model.getUserId());
        if (userEntity == null) {
            throw new IllegalArgumentException("User not found with id: " + model.getUserId());
        }
        sub.setUser(userEntity);


        em.persist(sub);
        return sub;
    }

    @Override
    public CustomSubscriptionEntity getById(String id) {
        return em.find(CustomSubscriptionEntity.class, id);
    }

    @Override
    public CustomSubscriptionEntity getByUser(String userId) {
        try {
            return em.createQuery(
                            "SELECT s FROM CustomSubscriptionEntity s WHERE s.user.id = :userId",
                            CustomSubscriptionEntity.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<CustomSubscriptionEntity> getAll() {
        return em.createQuery("SELECT s FROM CustomSubscriptionEntity s", CustomSubscriptionEntity.class)
                .getResultList();
    }

    @Override
    public CustomSubscriptionEntity update(String id, SubscriptionModel model) {
        CustomSubscriptionEntity sub = getById(id);
        if (sub != null) {
            if (model.getPlan() != null) sub.setPlanType(model.getPlan().name());
            if (model.getStartDate() != null) sub.setStartDate(model.getStartDate());
            if (model.getEndDate() != null) sub.setEndDate(model.getEndDate());

            if (model.getUserId() != null) {
                UserEntity userEntity = em.find(UserEntity.class, model.getUserId());
                if (userEntity == null) {
                    throw new IllegalArgumentException("User not found with id: " + model.getUserId());
                }
                sub.setUser(userEntity);
            }

            em.merge(sub);
        }
        return sub;
    }

    @Override
    public boolean delete(String id) {
        CustomSubscriptionEntity sub = getById(id);
        if (sub != null) {
            em.remove(sub);
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        // No cleanup needed
    }
}
