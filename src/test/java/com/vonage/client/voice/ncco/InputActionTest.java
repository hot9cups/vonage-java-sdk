/*
 *   Copyright 2020 Vonage
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.vonage.client.voice.ncco;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class InputActionTest {
    @Test
    public void testBuilderMultipleInstances() {
        InputAction.Builder builder = InputAction.builder();
        assertNotSame(builder.build(), builder.build());
    }

    @Test
    public void testAllFields() {
        InputAction input = InputAction.builder()
                .timeOut(10)
                .maxDigits(4)
                .submitOnHash(true)
                .eventUrl("http://example.com")
                .eventMethod(EventMethod.POST)
                .build();

        String expectedJson = "[{\"timeOut\":10,\"maxDigits\":4,\"submitOnHash\":true,\"eventUrl\":[\"http://example.com\"],\"eventMethod\":\"POST\",\"action\":\"input\"}]";
        assertEquals(expectedJson, new Ncco(input).toJson());
    }

    @Test
    public void testGetAction() {
        InputAction input = InputAction.builder().build();
        assertEquals("input", input.getAction());
    }

    @Test
    public void testDefault() {
        InputAction input = InputAction.builder().build();

        String expectedJson = "[{\"action\":\"input\"}]";
        assertEquals(expectedJson, new Ncco(input).toJson());
    }

    @Test
    public void testTimeOutField() {
        InputAction input = InputAction.builder().timeOut(5).build();

        String expectedJson = "[{\"timeOut\":5,\"action\":\"input\"}]";
        assertEquals(expectedJson, new Ncco(input).toJson());
    }

    @Test
    public void testMaxDigitsField() {
        InputAction input = InputAction.builder().maxDigits(4).build();

        String expectedJson = "[{\"maxDigits\":4,\"action\":\"input\"}]";
        assertEquals(expectedJson, new Ncco(input).toJson());
    }

    @Test
    public void testSubmitOnHashField() {
        InputAction input = InputAction.builder().submitOnHash(true).build();

        String expectedJson = "[{\"submitOnHash\":true,\"action\":\"input\"}]";
        assertEquals(expectedJson, new Ncco(input).toJson());
    }

    @Test
    public void testEventUrlField() {
        InputAction input = InputAction.builder().eventUrl("http://example.com").build();

        String expectedJson = "[{\"eventUrl\":[\"http://example.com\"],\"action\":\"input\"}]";
        assertEquals(expectedJson, new Ncco(input).toJson());
    }

    @Test
    public void testEventMethodField() {
        InputAction input = InputAction.builder().eventMethod(EventMethod.POST).build();

        String expectedJson = "[{\"eventMethod\":\"POST\",\"action\":\"input\"}]";
        assertEquals(expectedJson, new Ncco(input).toJson());
    }
}
