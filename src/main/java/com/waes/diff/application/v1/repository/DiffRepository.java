package com.waes.diff.application.v1.repository;

import com.waes.diff.application.v1.DiffSystemNotFoundException;
import com.waes.diff.application.v1.model.domain.ComparisionModel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * The Comparision Repository that provides access to the data stored to the Database
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class DiffRepository {

    @NonNull
    private final StubDataSource stubDataSource;

    /**
     * Stores of updates Comparision Models
     *
     * @param id   - an ID of the Model to store
     * @param diff - an instance of the {@link ComparisionModel} to store
     * @return an instance of the {@link ComparisionModel}
     */
    public ComparisionModel saveOrUpdate(final Long id, final ComparisionModel diff) {
        log.info("Saving the diff value by id: {}, diff: {}", id, diff);

        final ComparisionModel model = stubDataSource.saveOrUpdate(id, diff);

        log.info("Saved the diff value by id: {}, diff: {}", id, model);
        return model;
    }

    /**
     * Finds a {@link ComparisionModel} by a given ID
     *
     * @param id - ID of the model to search
     * @return an instance of the {@link ComparisionModel}
     */
    public ComparisionModel getById(final Long id) {
        final ComparisionModel model = stubDataSource.getModelById(id);

        if (Objects.isNull(model)) {
            throw new DiffSystemNotFoundException(
                    String.format("A Diff with such ID doesn't exist: id=%s", id));
        }

        return model;
    }
}
