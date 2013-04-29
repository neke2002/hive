/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.exec.vector.expressions;

import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;

/**
 * Base class for expressions.
 */
public abstract class VectorExpression {

  /**
   * Child expressions are evaluated post order.
   */
  protected VectorExpression [] childExpressions = null;

  /**
   * This is the primary method to implement expression logic.
   * @param vrg
   */
  public abstract void evaluate(VectorizedRowBatch vrg);

  /**
   * Returns the index of the output column in the array
   * of column vectors. If not applicable, -1 is returned.
   * @return Index of the output column
   */
  public abstract int getOutputColumn();

  /**
   * Returns type of the output column.
   */
  public abstract String getOutputType();

  /**
   * Initialize the child expressions.
   */
  public void setChildExpressions(VectorExpression [] ve) {
    childExpressions = ve;
  }

  public VectorExpression[] getChildExpressions() {
    return childExpressions;
  }

  /**
   * Evaluate the child expressions on the given input batch.
   * @param vrg {@link VectorizedRowBatch}
   */
  final protected void evaluateChildren(VectorizedRowBatch vrg) {
    if (childExpressions != null) {
      for (VectorExpression ve : childExpressions) {
        ve.evaluate(vrg);
      }
    }
  }
}