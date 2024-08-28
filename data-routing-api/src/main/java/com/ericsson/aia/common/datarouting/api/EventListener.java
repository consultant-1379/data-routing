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
package com.ericsson.aia.common.datarouting.api;

import java.util.Collection;

/**
 * The <code>EventListener</code> is a generic interface for the data collection. This interface's onEven method will be called by the internal
 * processor manager.
 *
 * @param <V>
 *            value type for the EventListener.
 */
public interface EventListener<V> {

    /**
     * This method receives Collection of Events from the consumer. Any class implement must provide appropriate implementation for efficient data
     * consumption. If the data-consumption is slow, it will impact the performance of the consumer and broker.
     *
     * @param microBatch
     *            subscriber will be notify for the batch received
     */
    void onEvent(Collection<V> microBatch);
}
