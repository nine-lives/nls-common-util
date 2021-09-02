package com.nls.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VatUtilTest {
    @Test
    public void valid() throws Exception {
        assertTrue(VatUtil.valid("945628393"));
        assertTrue(VatUtil.valid("GB945628393"));
        assertTrue(VatUtil.valid("G B 9 4 5 6 2 8 3 9 3"));
        assertFalse(VatUtil.valid(""));
        assertFalse(VatUtil.valid("GA945628393"));
        assertFalse(VatUtil.valid("94562839"));
        assertFalse(VatUtil.valid("9456283935"));

        assertTrue(VatUtil.valid("945628393111"));
        assertTrue(VatUtil.valid("GB945628393111"));
        assertTrue(VatUtil.valid("G B 9 4 5 6 2 8 3 9 3 1 1 1"));
        assertFalse(VatUtil.valid("GA945628393111"));
        assertFalse(VatUtil.valid("94562839"));
        assertFalse(VatUtil.valid("9456283935"));

        assertTrue(VatUtil.valid("GD111"));
        assertTrue(VatUtil.valid("GBGD111"));
        assertTrue(VatUtil.valid("G B G D 1 1 1"));
        assertFalse(VatUtil.valid("GXGD111"));
        assertFalse(VatUtil.valid("GBGX111"));
        assertFalse(VatUtil.valid("GBGD1111"));
        assertFalse(VatUtil.valid("GBGD11"));

        assertTrue(VatUtil.valid("HA111"));
        assertTrue(VatUtil.valid("GBHA111"));
        assertTrue(VatUtil.valid("G B H A 1 1 1"));
        assertFalse(VatUtil.valid("GXHA111"));
        assertFalse(VatUtil.valid("GBHX111"));
        assertFalse(VatUtil.valid("GBHA1111"));
        assertFalse(VatUtil.valid("GBHA11"));
    }

    @Test
    public void prettify() throws Exception {
        assertEquals("GB945 6283 93", VatUtil.prettify("945628393"));
        assertEquals("GB945 6283 93", VatUtil.prettify("G B 9 4 5 6 2 8 3 9 3"));
        assertEquals("GB945 6283 93 111", VatUtil.prettify("945628393111"));
        assertEquals("GB945 6283 93 111", VatUtil.prettify("G B 9 4 5 6 2 8 3 9 3 1 1 1"));
        assertEquals("GBGD111", VatUtil.prettify("GD111"));
        assertEquals("GBGD111", VatUtil.prettify("G B G D 1 1 1"));
        assertEquals("GBHA111", VatUtil.prettify("HA111"));
        assertEquals("GBHA111", VatUtil.prettify("G B H A 1 1 1"));
    }
}