/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.jersey.tests.performance.mbw.parametrized;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.glassfish.jersey.tests.performance.mbw.parametrized.container.JerseyAppTestContainerFactory;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Miroslav Fuksa (miroslav.fuksa at oracle.com)
 */
public class JsonJacksonTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig();
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new JerseyAppTestContainerFactory(new String[]{"-t", "application/json", "-j", "-u",
                getBaseUri().toString(), "-p", "org.glassfish.jersey.jackson.JacksonFeature"});
    }

    @Test
    public void testGet() {
        final Response response = target().path("person").request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(JerseyApp.PERSON, response.readEntity(Person.class));
    }

    @Test
    public void testPost() throws InterruptedException {
        final Response response = target().path("person").request(MediaType.APPLICATION_JSON).post(Entity.entity(JerseyApp.PERSON, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(JerseyApp.PERSON, response.readEntity(Person.class));
    }

    @Test
    public void testPut() {
        final Response response = target().path("person").request(MediaType.APPLICATION_JSON).put(Entity.entity(JerseyApp.PERSON, MediaType.APPLICATION_JSON));
        Assert.assertEquals(204, response.getStatus());
    }

}
