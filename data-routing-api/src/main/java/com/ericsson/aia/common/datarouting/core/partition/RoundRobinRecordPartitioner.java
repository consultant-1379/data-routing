/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.aia.common.datarouting.core.partition;

import java.util.concurrent.atomic.AtomicInteger;

import com.ericsson.aia.common.datarouting.api.partitioner.RecordPartitioner;
import com.ericsson.aia.common.datarouting.technology.Constants;

/**
 * Round robin partitioner using an atomicInteger
 *
 * @param <V>
 *            The type of the record.
 */
public class RoundRobinRecordPartitioner<V> implements RecordPartitioner<V> {

    private final AtomicInteger counter = new AtomicInteger(0);
    private final int partitions;

    /**
     * Instantiates a RecordPartitioner that switches between the defined number of partitions in a round robin fashion.
     *
     * @param partitions
     *            THe number of topic partitions.
     */
    public RoundRobinRecordPartitioner(final Integer partitions) {
        this.partitions = partitions;
    }

    @Override
    public Integer getPartitionKey(final V record) {

        final Integer partitionId = counter.incrementAndGet() % partitions;
        if (counter.get() > Constants.MAXIMUM_VALID_PORT_VALUE) {
            counter.set(0);
        }

        return partitionId;
    }
}
