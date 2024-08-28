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

package com.ericsson.aia.common.datarouting.api.format;

import java.util.Map;

/**
 * This is the standard interface used to interpret messages when applying filters. Format specific implementations are provided using service look
 * ups and the string returned by getInterpretableDataFormat.
 *
 * @param <V>
 *            The type of the record which is being interpreted.
 */
public interface RecordInterpreter<V> {

    /**
     * This method is used to provide an interpreter to allow MVEL to use the record.
     *
     * @param record
     *            The record being analysed.
     * @return A map of variables which MVEL function can access.
     */
    Map<String, Object> getMvelDataAccessor(final V record);

    /**
     * This method is used to provide an interpreter to allow a regular expression to be used against the to use the record.
     *
     * @param record
     *            The record being analysed.
     *
     * @param attribute
     *            The name of the attribute in the message.
     *
     * @return The value of the attribute in the message.
     */
    Object getAttributeValueFromRecord(final V record, final String attribute);

    /**
     * This method is used to provide an interpreter that can extract the name of the schema being used by the record.
     *
     * @param record
     *            The record being analysed.
     * @return The name of the message schema.
     */
    String getSchemaName(final V record);

    /**
     * This method is used to check the data format which can be interpreted
     *
     * @return which data format this interpreter is used for.
     */
    String getInterpretableDataFormat();

    /**
     * This method is used to provide an interpreter that can extract the name of the schema being used by the record.
     *
     * @param record
     *            The record being analysed.
     * @return name of the record
     */
    String getName(V record);
}
