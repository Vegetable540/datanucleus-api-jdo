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
package org.datanucleus.api.jdo.query.inmemory;

import javax.jdo.JDOHelper;

import org.datanucleus.exceptions.NucleusException;
import org.datanucleus.store.query.QueryUtils;
import org.datanucleus.store.query.expression.Expression;
import org.datanucleus.store.query.expression.InvokeExpression;
import org.datanucleus.store.query.expression.ParameterExpression;
import org.datanucleus.store.query.expression.PrimaryExpression;
import org.datanucleus.store.query.inmemory.InMemoryExpressionEvaluator;
import org.datanucleus.store.query.inmemory.InvocationEvaluator;

/**
 * Evaluator for the function JDOHelper.getVersion(obj).
 */
public class JDOHelperGetVersionFunction implements InvocationEvaluator
{
    /* (non-Javadoc)
     * @see org.datanucleus.query.evaluator.memory.InvocationEvaluator#evaluate(org.datanucleus.query.expression.InvokeExpression, org.datanucleus.query.evaluator.memory.InMemoryExpressionEvaluator)
     */
    public Object evaluate(InvokeExpression expr, Object invokedValue, InMemoryExpressionEvaluator eval)
    {
        Expression argExpr = expr.getArguments().get(0);
        if (argExpr instanceof PrimaryExpression)
        {
            PrimaryExpression primExpr = (PrimaryExpression)argExpr;
            Object value = eval.getValueForPrimaryExpression(primExpr);
            return JDOHelper.getVersion(value);
        }
        else if (argExpr instanceof ParameterExpression)
        {
            ParameterExpression paramExpr = (ParameterExpression)argExpr;
            Object value = QueryUtils.getValueForParameterExpression(eval.getParameterValues(), paramExpr);
            return JDOHelper.getVersion(value);
        }
        else
        {
            throw new NucleusException("Dont currently support JDOHelper.getVersion with arg of type " + argExpr.getClass().getName());
        }
    }
}