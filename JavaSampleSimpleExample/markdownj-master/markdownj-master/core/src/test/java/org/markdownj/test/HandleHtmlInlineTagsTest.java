package org.markdownj.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.markdownj.MarkdownProcessor;

public class HandleHtmlInlineTagsTest {
    private MarkdownProcessor m;

    @Before
    public void createProcessor() {
        m = new MarkdownProcessor();
    }

    @Test
    public void testInlineTagsWithMarkdown() {
        assertEquals("<p><span><strong>Something really enjoyable!</strong></span></p>",
                m.markdown("<span>**Something really enjoyable!**</span>").trim());
    }

    @Test
    public void testUpperCaseInlineTagsWithMarkdown() {
        assertEquals("<p><SPAN><strong>oh no</strong></SPAN></p>",
                m.markdown("<SPAN>**oh no**</SPAN>").trim());
    }

}
