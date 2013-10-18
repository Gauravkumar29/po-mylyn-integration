package com.project_open.mylyn.core.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import junit.framework.TestCase;

import com.project_open.mylyn.core.model.Company;
import com.project_open.mylyn.core.model.Ticket;
import com.project_open.mylyn.core.model.User;

/**
 * @author Markus Knittig
 *
 */
public class RestfulProjectOpenReaderTest extends TestCase {

    private RestfulProjectOpenReader testReader;

    protected void setUp() throws Exception {
        super.setUp();
        testReader = new RestfulProjectOpenReader();
    }

    private String inputStreamToString(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        bufferedReader.close();

        return stringBuilder.toString();
    }

    public void testReadUsers() throws Exception {
        InputStream in = getClass().getResourceAsStream("/jsondata/users.json");

        List<User> users = testReader.readUsers(inputStreamToString(in));

        assertEquals(218, users.size());
        assertEquals("ana isabel_abadeas@project-open.org", users.get(0).getEmail());
    }

    public void testReadCompanies() throws Exception {
        InputStream in = getClass().getResourceAsStream("/jsondata/companies.json");

        List<Company> companies = testReader.readCompanies(inputStreamToString(in));

        assertEquals(44, companies.size());
        assertEquals("TecnoLodge", companies.get(0).getName());
    }

    public void testReadTickets() throws Exception {
        InputStream in = getClass().getResourceAsStream("/jsondata/tickets.json");

        List<Ticket> tickets = testReader.readTickets(inputStreamToString(in));

        assertEquals(18, tickets.size());
        assertEquals("Resolution Time Test - Simple2", tickets.get(0).getName());
        assertEquals("asdfasdfasdf", tickets.get(0).getDescription());
    }
    
}
