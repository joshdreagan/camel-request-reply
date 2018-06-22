/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.examples;

import java.util.Objects;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class RequestReplyAggregationStrategy implements AggregationStrategy, CamelContextAware, InitializingBean {

  private static final Logger log = LoggerFactory.getLogger(RequestReplyAggregationStrategy.class);

  private CamelContext camelContext;

  @Override
  public void setCamelContext(CamelContext camelContext) {
    this.camelContext = camelContext;
  }

  @Override
  public CamelContext getCamelContext() {
    return camelContext;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Objects.requireNonNull(camelContext, "The camelContext property must not be null.");
  }

  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    if (oldExchange == null) {
      return newExchange;
    } else {
      String callbackUrl = oldExchange.getIn().getHeader("CallbackURL", String.class);
      String message = oldExchange.getIn().getBody(String.class);
      String confirmationNumber = newExchange.getIn().getBody(String.class);
      oldExchange.setProperty("CallbackURL", callbackUrl);
      oldExchange.getIn().setBody(String.format("Transaction Details:\n\tConfirmation Number: %s\n\tMessage: %s\n", confirmationNumber, message));
      return oldExchange;
    }
  }
}