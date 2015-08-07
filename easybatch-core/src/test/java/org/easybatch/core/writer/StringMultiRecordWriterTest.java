/*
 *  The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package org.easybatch.core.writer;

import org.easybatch.core.reader.IterableMultiRecordReader;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easybatch.core.impl.EngineBuilder.aNewEngine;
import static org.easybatch.core.util.Utils.LINE_SEPARATOR;

/**
 * Test class for {@link StringMultiRecordWriter}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class StringMultiRecordWriterTest {

    private List<String> items;

    private StringWriter stringWriter;

    private StringMultiRecordWriter stringMultiRecordWriter;

    @Before
    public void setUp() throws Exception {
        items = Arrays.asList("foo", "bar");
        stringWriter = new StringWriter();
        stringMultiRecordWriter = new StringMultiRecordWriter(stringWriter);
    }

    @Test
    public void testWriteCollection() throws Exception {
        stringMultiRecordWriter.writeRecord(items);

        assertThat(stringWriter.toString()).isEqualTo("foo" + LINE_SEPARATOR + "bar" + LINE_SEPARATOR);
    }

    @Test
    public void testWriteMultiRecord() throws Exception {

        aNewEngine()
                .reader(new IterableMultiRecordReader<String>(items, 2))
                .writer(stringMultiRecordWriter)
                .build().call();

        assertThat(stringWriter.toString()).isEqualTo("foo" + LINE_SEPARATOR + "bar" + LINE_SEPARATOR);
    }
}