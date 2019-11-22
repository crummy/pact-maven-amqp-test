package com.github.uglyog.mavenamqp;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.junit.MessagePactProviderRule;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.core.model.messaging.MessagePact;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConsumerTest
{
    @Rule
    public MessagePactProviderRule mockProvider = new MessagePactProviderRule(this);

    @Pact(provider = "maven_message_provider", consumer = "maven_message_consumer")
    public MessagePact createPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody()
            .integerType("value", 100)
            .stringValue("type", "COST");

        return builder
          .expectsToReceive("a test message")
          .withContent(body)
          .toPact();
    }

    @Test
    @PactVerification
    public void test() throws Exception {
        assertThat(new Consumer().processMessage(mockProvider.getMessage()), is(true));
    }
}
