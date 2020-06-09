package com.autumn.events.impl;

import com.autumn.domain.entities.Entity;
import com.autumn.events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 事件总线实现
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-29 22:15
 **/
public class EventBusImpl implements EventBus {

    private final Map<Class<?>, Set<EntityInsertEventListener>> insertListenerMap = new ConcurrentHashMap<>(100);
    private final Map<Class<?>, Set<EntityUpdateEventListener>> updateListenerMap = new ConcurrentHashMap<>(100);
    private final Map<Class<?>, Set<EntityDeleteEventListener>> deleteListenerMap = new ConcurrentHashMap<>(100);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private Class<?> getEntityClass(Class<?> listenerClass) {
        Type[] types = listenerClass.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType t = (ParameterizedType) type;
                if (t.getRawType().equals(EntityEventListener.class)
                        || EntityEventListener.class.isAssignableFrom((Class<?>) t.getRawType())) {
                    return (Class<?>) t.getActualTypeArguments()[0];
                }
            }
        }
        if (!listenerClass.getSuperclass().equals(Object.class)) {
            listenerClass = listenerClass.getSuperclass();
            return this.getEntityClass(listenerClass);
        }
        return null;
    }

    @Override
    public <TEntity extends Entity<TKey>, TKey extends Serializable> void registerEntityEventListener(EntityEventListener<TEntity, TKey> listener) {
        Class<?> entityClass = this.getEntityClass(listener.getClass());
        if (entityClass != null) {
            if (listener instanceof EntityInsertEventListener) {
                this.insertListenerMap
                        .computeIfAbsent(entityClass, key -> new HashSet<>(10))
                        .add((EntityInsertEventListener) listener);
            }
            if (listener instanceof EntityUpdateEventListener) {
                this.updateListenerMap
                        .computeIfAbsent(entityClass, key -> new HashSet<>(10))
                        .add((EntityUpdateEventListener) listener);
            }
            if (listener instanceof EntityDeleteEventListener) {
                this.deleteListenerMap
                        .computeIfAbsent(entityClass, key -> new HashSet<>(10))
                        .add((EntityDeleteEventListener) listener);
            }
        }
    }

    @Override
    public boolean existEntityInsertEventListener(Class<?> entityClass) {
        return this.insertListenerMap.containsKey(entityClass);
    }

    @Override
    public boolean existEntityUpdateEventListener(Class<?> entityClass) {
        return this.updateListenerMap.containsKey(entityClass);
    }

    @Override
    public boolean existDeleteUpdateEventListener(Class<?> entityClass) {
        return this.deleteListenerMap.containsKey(entityClass);
    }

    @Override
    public void callEntityInsertEventListener(Class<?> entityClass, Consumer<EntityInsertEventListener> consumer) {
        if (consumer != null) {
            Set<EntityInsertEventListener> listenerSet = this.insertListenerMap.get(entityClass);
            if (listenerSet != null) {
                for (EntityInsertEventListener eventListener : listenerSet) {
                    consumer.accept(eventListener);
                }
            }
        }
    }

    @Override
    public void callEntityUpdateEventListener(Class<?> entityClass, Consumer<EntityUpdateEventListener> consumer) {
        if (consumer != null) {
            Set<EntityUpdateEventListener> listenerSet = this.updateListenerMap.get(entityClass);
            if (listenerSet != null) {
                for (EntityUpdateEventListener eventListener : listenerSet) {
                    consumer.accept(eventListener);
                }
            }
        }
    }

    @Override
    public void callEntityDeleteEventListener(Class<?> entityClass, Consumer<EntityDeleteEventListener> consumer) {
        if (consumer != null) {
            Set<EntityDeleteEventListener> listenerSet = this.deleteListenerMap.get(entityClass);
            if (listenerSet != null) {
                for (EntityDeleteEventListener eventListener : listenerSet) {
                    consumer.accept(eventListener);
                }
            }
        }
    }

    @Override
    public void publishTransactionEvent(TransactionEvent event) {
        eventPublisher.publishEvent(event);
    }
}
