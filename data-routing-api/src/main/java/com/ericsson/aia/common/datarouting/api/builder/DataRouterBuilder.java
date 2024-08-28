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

package com.ericsson.aia.common.datarouting.api.builder;

/**
 *
 * This class is responsible for building either input or output data routers.
 *
 */
public class DataRouterBuilder {

    private DataRouterBuilder() {

    }

    /**
     * Builder for inputs
     *
     * @param <K>
     *            The type of the record key.
     *
     * @param <V>
     *            The type of the record being consumed.
     *
     * @return InputDataRouterBuilder
     */
    public static <K, V> InputDataRouterBuilder<K, V> inputRouter() {
        return new InputDataRouterBuilder<>();
    }

    /**
     * Builder for outputs.
     *
     * @param <V>
     *            The type of the record being sent.
     *
     * @return OutputDataRouterBuilder
     */
    public static <V> OutputDataRouterBuilder<V> outputRouter() {
        return new OutputDataRouterBuilder<>();
    }

}
