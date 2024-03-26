package util;

// text courtesy of Herman Melville (Moby Dick) from
// http://www.gutenberg.org/cache/epub/2701/pg2701.txt 

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

// START:test
public class SearchTest {
   @Test
   public void testSearch() {
      try {
         String pageContent = "There are certain queer times and occasions "
            + "in this strange mixed affair we call life when a man "
            + "takes this whole universe for a vast practical joke, "
            + "though the wit thereof he but dimly discerns, and more "
            + "than suspects that the joke is at nobody's expense but "
            + "his own.";
         byte[] bytes = pageContent.getBytes();
         ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
         // search
         Search search = new Search(stream, "practical joke", "1");
         Search.LOGGER.setLevel(Level.OFF);
         search.setSurroundingCharacterCount(10);
         search.execute();
         assertFalse(search.errored());
         List<Match> matches = search.getMatches();
         assertNotNull(matches);
         assertTrue(matches.size() >= 1);
         Match match = matches.get(0);
         assertEquals("practical joke", match.searchString());
         assertEquals("or a vast practical joke, though t",
            match.surroundingContext());
         stream.close();

         // negative
         URLConnection connection =
            new URL("http://bit.ly/15sYPA7").openConnection();
         InputStream inputStream = connection.getInputStream();
         search = new Search(
            inputStream, "smelt", "http://bit.ly/15sYPA7");
         search.execute();
         assertEquals(0, search.getMatches().size());
         stream.close();
      } catch (Exception e) {
         e.printStackTrace();
         fail("exception thrown in test" + e.getMessage());
      }
   }
}
// END:test