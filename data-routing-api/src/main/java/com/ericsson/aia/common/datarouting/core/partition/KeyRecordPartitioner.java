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

import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;
import com.ericsson.aia.common.datarouting.api.partitioner.RecordPartitioner;

/**
 * A RecordPartitioner that uses a value of a key in the record.
 *
 * @param <V>
 *            The type of the record.
 */
public class KeyRecordPartitioner<V> implements RecordPartitioner<V> {

    private final RecordInterpreter<V> recordInterpreter;
    private final String attribute;

    /**
     * Instantiates a RecordPartitioner that retrieves the value of a specified key from a record for use as a partition key.
     *
     * @param attribute
     *            The name of the attribute in the record to use as a partition key.
     *
     * @param recordInterpreter
     *            Object used to interpret a record.
     */
    public KeyRecordPartitioner(final String attribute, final RecordInterpreter<V> recordInterpreter) {
        this.attribute = attribute;
        this.recordInterpreter = recordInterpreter;
    }

    @Override
    public Integer getPartitionKey(final V record) {
        return recordInterpreter.getAttributeValueFromRecord(record, attribute).hashCode();
    }

}
