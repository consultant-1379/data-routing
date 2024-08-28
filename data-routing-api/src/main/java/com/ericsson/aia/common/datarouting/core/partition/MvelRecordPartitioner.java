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

import java.io.Serializable;

import org.mvel2.MVEL;

import com.ericsson.aia.common.datarouting.api.format.RecordInterpreter;
import com.ericsson.aia.common.datarouting.api.partitioner.RecordPartitioner;

/**
 * A RecordPartitioner that evaluates a MVEL function to determine the partition key of a record.
 *
 * @param <V>
 *            The type of the record.
 */
public class MvelRecordPartitioner<V> implements RecordPartitioner<V> {
    private final RecordInterpreter<V> recordInterpreter;
    private final Serializable stmt;

    /**
     * Instantiates a RecordPartitioner that evaluates a MVEL function to determine the partition key of a record.
     *
     * @param expression
     *            The MVEL expression to be evaluated.
     *
     * @param recordInterpreter
     *            Object used to interpret a record.
     */
    public MvelRecordPartitioner(final String expression, final RecordInterpreter<V> recordInterpreter) {
        stmt = MVEL.compileExpression(expression);
        this.recordInterpreter = recordInterpreter;
    }

    @Override
    public Integer getPartitionKey(final V record) {
        return MVEL.executeExpression(stmt, recordInterpreter.getMvelDataAccessor(record), Integer.class);
    }

}
