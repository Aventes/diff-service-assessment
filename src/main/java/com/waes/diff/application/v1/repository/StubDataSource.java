package com.waes.diff.application.v1.repository;

import com.waes.diff.application.v1.model.domain.ComparisionModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Database Repository to work on
 * <p>
 * TODO: [Maksym.Yurin] A Temporary solution,
 * come up with idea which Database you willing to use as for storing Comparision Models
 *
 * @author maksym.yurin (Maksym Yurin)
 * @since 0.1
 */
@Component
public class StubDataSource {
    private Map<Long, ComparisionModel> map = new HashMap<>();

    /**
     * Gets Comparision Model by ID
     *
     * @param id - ID of the model
     * @return {@link ComparisionModel} or null when model doesn't exist
     */
    public ComparisionModel getModelById(final Long id) {
        return map.get(id);
    }

    /**
     * Stores of updates Comparision Models
     *
     * @param id               - an ID of the Model to store
     * @param comparisionModel - an instance of the {@link ComparisionModel} to store
     * @return an instance of the {@link ComparisionModel}
     */
    public ComparisionModel saveOrUpdate(final Long id, final ComparisionModel comparisionModel) {
        if (!map.containsKey(id)) {
            map.put(id, comparisionModel);
        }
        else {
            map.merge(id, comparisionModel, (v1, v2) -> ComparisionModel.builder()
                                                                        .id(id)
                                                                        .left(v1.getLeft() != null
                                                                              ? v1.getLeft()
                                                                              : v2.getLeft())
                                                                        .right(v1.getRight() != null
                                                                               ? v1.getRight()
                                                                               : v2.getRight())
                                                                        .result(v2.getResult())
                                                                        .build());
        }

        return map.get(id);
    }
}
