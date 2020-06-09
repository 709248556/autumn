package com.autumn.events;

import com.autumn.domain.entities.Entity;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 事务事件监听
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-30 00:11
 **/
public class TransactionEventListener {

    private final EventBus eventBus;

    /**
     * 实例化
     *
     * @param eventBus 事件总线
     */
    public TransactionEventListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * 事务成功并完成
     *
     * @param event 事件
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void transactionComplete(TransactionEvent event) {
        if (event.getEventType().equals(EntityEventType.INSERT)) {
            this.eventBus.callEntityInsertEventListener(event.getEntityClass(), c -> {
                c.insertComplete(event.getSource(), (Entity) event.getEntityOrkey());
            });
        } else if (event.getEventType().equals(EntityEventType.UPDATE)) {
            this.eventBus.callEntityUpdateEventListener(event.getEntityClass(), c -> {
                c.updateComplete(event.getSource(), (Entity) event.getEntityOrkey());
            });
        } else if (event.getEventType().equals(EntityEventType.DELETE)) {
            this.eventBus.callEntityDeleteEventListener(event.getEntityClass(), c -> {
                c.deleteComplete(event.getSource(), event.getEntityOrkey());
            });
        }
    }

    /**
     * 事务回滚
     *
     * @param event 事件
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void transactionRollback(TransactionEvent event) {
        if (event.getEventType().equals(EntityEventType.INSERT)) {
            this.eventBus.callEntityInsertEventListener(event.getEntityClass(), c -> {
                c.insertRollback(event.getSource(), (Entity) event.getEntityOrkey());
            });
        } else if (event.getEventType().equals(EntityEventType.UPDATE)) {
            this.eventBus.callEntityUpdateEventListener(event.getEntityClass(), c -> {
                c.updateRollback(event.getSource(), (Entity) event.getEntityOrkey());
            });
        } else if (event.getEventType().equals(EntityEventType.DELETE)) {
            this.eventBus.callEntityDeleteEventListener(event.getEntityClass(), c -> {
                c.deleteRollback(event.getSource(), event.getEntityOrkey());
            });
        }
    }
}
