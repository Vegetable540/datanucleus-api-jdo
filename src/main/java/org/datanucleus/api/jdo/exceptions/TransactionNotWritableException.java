/**********************************************************************
Copyright (c) 2008 Andy Jefferson and others. All rights reserved. 
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
package org.datanucleus.api.jdo.exceptions;

import org.datanucleus.util.Localiser;

/**
 * A <i>TransactionNotReadableException</i> is thrown if an operation needs either of an active transaction or non-transactional read and neither is true.
 */
public class TransactionNotWritableException extends TransactionNotActiveException
{
    private static final long serialVersionUID = -8641406435403033411L;

    /**
     * Constructor.
     */
    public TransactionNotWritableException()
    {
        super(Localiser.msg("015041"), null);
    }

    /**
     * Constructor.
     * @param message the localized error message
     * @param failedObject the failed object
     */
    public TransactionNotWritableException(String message, Object failedObject)
    {
        super(message, failedObject);
    }
}