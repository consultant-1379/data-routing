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

package com.ericsson.aia.common.datarouting.core;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import com.ericsson.aia.common.datarouting.api.DataSink;

/**
 * This sink is used as a proxy for a sink if more than one sink is added to an output.
 *
 * @param <V>
 *            The type of the record being matched against.
 */
public class SinkProxy<V> implements Callable<Boolean> {

    private final BlockingQueue<Collection<V>> queue;
    private final DataSink<V> dataSink;

    /**
     * Instantiates an object to make sinks callable.
     *
     * @param queue
     *            used to receive messages.
     *
     * @param dataSink
     *            The sync that is to be proxied.
     */
    public SinkProxy(final BlockingQueue<Collection<V>> queue, final DataSink<V> dataSink) {
        this.dataSink = dataSink;
        this.queue = queue;
    }

    @Override
    public Boolean call() throws InterruptedException {
        dataSink.sendMessage(queue.take());
        return Boolean.TRUE;
    }

}
