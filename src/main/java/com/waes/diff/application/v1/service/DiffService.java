package com.waes.diff.application.v1.service;

import com.waes.diff.application.v1.model.domain.ComparisionModel;
import com.waes.diff.application.v1.model.domain.ComparisionResult;
import com.waes.diff.application.v1.model.domain.Left;
import com.waes.diff.application.v1.model.domain.Right;
import com.waes.diff.application.v1.model.event.LeftSideCreatedEvent;
import com.waes.diff.application.v1.model.event.RightSideCreatedEvent;
import com.waes.diff.application.v1.repository.DiffRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * Defines a Diff Service that contains a business logic to work with Diff Data and provides access to the Repository
 * layer
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DiffService implements ApplicationEventPublisherAware {

    @NonNull
    private final DiffRepository diffRepository;

    private ApplicationEventPublisher publisher;

    /**
     * Finds a Comparision Model by Id
     *
     * @param id - an id of the {@link ComparisionModel}
     * @return an instance of the {@link ComparisionModel}
     */
    public ComparisionModel findById(final Long id) {
        log.trace("findById.E id: {}", id);

        final ComparisionModel entity = diffRepository.getById(id);

        log.trace("findById.X id: {}", id);
        return entity;
    }

    /**
     * Finds the result of Comparision Model by Id
     *
     * @param id - an id of the {@link ComparisionModel}
     * @return an instance of the {@link ComparisionResult}
     */
    public ComparisionResult findResultById(final Long id) {
        log.trace("findResultById.E id: {}", id);

        final ComparisionModel entity = diffRepository.getById(id);
        final ComparisionResult result = entity.getResult();

        log.trace("findResultById.X id: {}, result: {}", id, result);
        return result;
    }

    /**
     * Saves a left part of the {@link ComparisionModel}
     *
     * @param id    - an id of the {@link ComparisionModel}
     * @param value - a value of the left part
     */
    public void saveLeft(final Long id, final String value) {
        log.trace("saveLeft.E id: {}, diff: {}", id, value);

        final ComparisionModel savedModel =
                diffRepository.saveOrUpdate(id, ComparisionModel.builder()
                                                                .id(id)
                                                                .left(Left.builder()
                                                                          .id(id)
                                                                          .value(value)
                                                                          .build())
                                                                .build());

        publisher.publishEvent(LeftSideCreatedEvent.builder()
                                                   .source(this)
                                                   .id(savedModel.getId())
                                                   .payload(savedModel.getLeft())
                                                   .build());

        log.trace("saveLeft.X id: {}, diff: {}", id, value);
    }

    /**
     * Saves a right part of the {@link ComparisionModel}
     *
     * @param id    - an id of the {@link ComparisionModel}
     * @param value - a value of the right part
     */
    public void saveRight(final Long id, final String value) {
        log.trace("saveRight.E id: {}, diff: {}", id, value);

        final ComparisionModel savedModel =
                diffRepository.saveOrUpdate(id, ComparisionModel.builder()
                                                                .id(id)
                                                                .right(Right.builder()
                                                                            .id(id)
                                                                            .value(value)
                                                                            .build())
                                                                .build());

        publisher.publishEvent(RightSideCreatedEvent.builder()
                                                    .source(this)
                                                    .id(id)
                                                    .payload(savedModel.getRight())
                                                    .build());
        log.trace("saveRight.X id: {}, diff: {}", id, value);
    }

    /**
     * Saves the result part of the {@link ComparisionModel}
     *
     * @param id                - an id of the {@link ComparisionModel}
     * @param comparisionResult - the result of comparing left and right sides of the model
     */
    public void saveResult(final Long id, final ComparisionResult comparisionResult) {
        log.trace("saveResult.E id: {}, comparisionResult: {}", id, comparisionResult);
        final ComparisionModel model = diffRepository.getById(id);

        final ComparisionModel savedModel = diffRepository.saveOrUpdate(id, model.toBuilder()
                                                                                 .result(comparisionResult)
                                                                                 .build());

        log.trace("saveResult.X id: {}, comparisionResult: {}", id, savedModel);
    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
