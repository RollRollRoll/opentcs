// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;

/**
 */
class AuthenticatorTest {

  private ServiceWebApiConfiguration configuration;

  private Authenticator authenticator;

  @BeforeEach
  void setUp() {
    configuration = mock(ServiceWebApiConfiguration.class);
    authenticator = new Authenticator(configuration);
  }

  @Test
  void allowRequestsIfNoAccessKeyConfigured() {
    when(configuration.accessKey()).thenReturn("");

    assertTrue(authenticator.isAuthenticated(aRequestWithAccessKey(null)));
    assertTrue(authenticator.isAuthenticated(aRequestWithAccessKey("")));
    assertTrue(authenticator.isAuthenticated(aRequestWithAccessKey("some random value")));
  }

  @Test
  void allowRequestIfCorrectAccessKeyGiven() {
    when(configuration.accessKey()).thenReturn("my access key");

    assertTrue(authenticator.isAuthenticated(aRequestWithAccessKey("my access key")));
  }

  @Test
  void disallowRequestIfWrongAccessKeyGiven() {
    when(configuration.accessKey()).thenReturn("my access key");

    assertFalse(authenticator.isAuthenticated(aRequestWithAccessKey(null)));
    assertFalse(authenticator.isAuthenticated(aRequestWithAccessKey("")));
    assertFalse(authenticator.isAuthenticated(aRequestWithAccessKey("some random value")));
  }

  private Request aRequestWithAccessKey(String accessKey) {
    Request request = mock(Request.class);

    when(request.headers(HttpConstants.HEADER_NAME_ACCESS_KEY)).thenReturn(accessKey);

    return request;
  }

}
