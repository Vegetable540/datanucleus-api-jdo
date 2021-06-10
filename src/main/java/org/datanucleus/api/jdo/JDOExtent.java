/**********************************************************************
Copyright (c) 2007 Andy Jefferson and others. All rights reserved. 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/
package org.datanucleus.api.jdo;

import java.util.Iterator;

import javax.jdo.Extent;
import javax.jdo.FetchPlan;
import javax.jdo.PersistenceManager;

/**
 * Wrapper implementation of a JDO Extent.
 * @param <E> type that this Extent is for.
 */
public class JDOExtent<E> implements Extent<E>
{
    private boolean closed = false;

    /** Underlying PersistenceManager. */
    PersistenceManager pm;

    /** Underlying Extent. */
    org.datanucleus.store.query.Extent<E> extent;

    /** JDO Fetch Plan. */
    JDOFetchPlan fetchPlan = null;

    /**
     * Constructor.
     * @param pm PersistenceManager
     * @param extent Underlying Extent
     */
    public JDOExtent(PersistenceManager pm, org.datanucleus.store.query.Extent extent)
    {
        this.pm = pm;
        this.extent = extent;
        fetchPlan = new JDOFetchPlan(extent.getFetchPlan());
    }

    public void close()
    {
        if (closed)
        {
            return;
        }

        this.closeAll();

        Boolean closeableQuery = extent.getExecutionContext().getBooleanProperty(JDOQuery.PROPERTY_CLOSEABLE_QUERY);
        if (closeableQuery == Boolean.TRUE)
        {
            // User has requested a closeable Query, so release connection to PM and underlying query etc
            this.fetchPlan.clearGroups();
            this.fetchPlan = null;
            this.extent = null;
            this.pm = null;

            this.closed = true;
        }
    }

    /**
     * Method to close the Extent iterator.
     * @param iterator Iterator for the extent.
     */
    public void close(Iterator<E> iterator)
    {
        extent.close(iterator);
    }

    /**
     * Method to close all Extent iterators.
     */
    public void closeAll()
    {
        extent.closeAll();
    }

    /**
     * Accessor for the candidate class of the Extent.
     * @return Candidate class
     */
    public Class<E> getCandidateClass()
    {
        return extent.getCandidateClass();
    }

    /**
     * Accessor for whether the Extent includes subclasses.
     * @return Whether it has subclasses
     */
    public boolean hasSubclasses()
    {
        return extent.hasSubclasses();
    }

    /**
     * Accessor for the FetchPlan for the Extent.
     * @return FetchPlan
     */
    public FetchPlan getFetchPlan()
    {
        return fetchPlan;
    }

    /**
     * Accessor for the PersistenceManager.
     * @return The PM
     */
    public PersistenceManager getPersistenceManager()
    {
        return pm;
    }

    /**
     * Accessor for the real extent.
     * @return The Underlying extent
     */
    public org.datanucleus.store.query.Extent<E> getExtent()
    {
        return extent;
    }

    /**
     * Accessor for an iterator for this Extent.
     * @return The iterator
     */
    public Iterator<E> iterator()
    {
        return extent.iterator();
    }
}