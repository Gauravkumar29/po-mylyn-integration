package com.project_open.mylyn.core.util;

import java.util.Date;

import junit.framework.TestCase;

import com.project_open.mylyn.core.ProjectOpenConstants;

public class ProjectOpenUtilTest extends TestCase {

	public void testMarshallDate() {
		Date date = ProjectOpenUtil.marshallDate("2010-01-01 00:00:00+01");
		assertEquals(date.getTime(), 1262300400000L);
	}
	
    public void testGetTicketUrl() {
        String expected = "foobar" + ProjectOpenConstants.TICKET_URL + "1";
        assertEquals(expected, ProjectOpenUtil.getTicketUrl("foobar", "1"));
        assertEquals(expected, ProjectOpenUtil.getTicketUrl("foobar/", "1"));
    }
}
