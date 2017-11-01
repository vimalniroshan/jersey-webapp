package com.vml.jersey.bak;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Singleton
public class
TransactionalContext implements Context<Transaction> {

    private static Map<ActiveDescriptor, Object> cache = new HashMap<>();

    @Inject
    private ServiceLocator locator;

    @Override
    public Class <? extends Annotation> getScope() {
        return Transaction.class;
    }

    @Override
    public <U> U findOrCreate(final ActiveDescriptor<U> activeDescriptor, final ServiceHandle<?> root) {
        if(!cache.containsKey(activeDescriptor)) {
            synchronized(cache) {
                if(!cache.containsKey(activeDescriptor)) {
                    U u = createTransactionEnablingProxy(activeDescriptor, activeDescriptor.create(root), locator.getService(EntityManager.class));
                    cache.put(activeDescriptor, u);
                }
            }
        }

        return (U)cache.get(activeDescriptor);
    }

    @Override
    public boolean containsKey(final ActiveDescriptor <?> descriptor) {
        return cache.containsKey(descriptor);
    }

    @Override
    public void destroyOne(final ActiveDescriptor <?> descriptor) {
        cache.remove(descriptor);
    }

    @Override
    public boolean supportsNullCreation() {
        return false;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void shutdown() {
        cache.clear();
    }

    private static <U> U createTransactionEnablingProxy(final ActiveDescriptor <U> activeDescriptor, final U u, final EntityManager entityManager) {
        Set<Type> types = activeDescriptor.getContractTypes();
        return (U) Proxy.newProxyInstance(TransactionalContext.class.getClassLoader(),
                types.toArray(new Class <?>[types.size()]), (p, m, a) -> {
                    EntityTransaction t = entityManager.getTransaction();
                    try {
                        t.begin();
                        Object o = m.invoke(u, a);

                        t.commit();
                        return o;
                    } catch (Exception e) {
                        t.rollback();
                        throw e;
                    }
                });
    }
}
