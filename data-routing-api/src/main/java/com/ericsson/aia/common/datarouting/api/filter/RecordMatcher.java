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

package com.ericsson.aia.common.datarouting.api.filter;

/**
 * Interface for a matcher used to check a records attributes.
 *
 * @param <V>
 *            The type of the record being matched against.
 */
public interface RecordMatcher<V> {

    /**
     * This method will take a check if a record meets the specification in the flow xml.
     *
     * @param record
     *            The Record being checked.
     *
     * @return True if record is accepted by this matcher.
     */
    boolean matches(V record);

}
