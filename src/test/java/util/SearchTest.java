package util;

// text courtesy of Herman Melville (Moby Dick) from
// http://www.gutenberg.org/cache/epub/2701/pg2701.txt 

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
   // START:test
   @Test
   void testSearch() throws IOException {
      var pageContent = "There are certain queer times and occasions "
         + "in this strange mixed affair we call life when a man "
         + "takes this whole universe for a vast practical joke, "
         + "though the wit thereof he but dimly discerns, and more "
         + "than suspects that the joke is at nobody's expense but "
         + "his own.";
      var bytes = pageContent.getBytes();
      var stream = new ByteArrayInputStream(bytes);
      // ...
      // END:test
      var search = new Search(stream, "practical joke", "1");
      Search.LOGGER.setLevel(Level.OFF);
      search.setSurroundingCharacterCount(10);
      search.execute();
      assertFalse(search.errored());
      // START:notNull
      var matches = search.getMatches();
      assertTrue(matches.size() >= 1);
      // END:notNull
      var match = matches.get(0);
      assertEquals("practical joke", match.searchString);
      assertEquals("or a vast practical joke, though t",
         match.surroundingContext);
      stream.close();

      // negative
      var connection =
         new URL("http://bit.ly/15sYPA7").openConnection();
      var inputStream = connection.getInputStream();
      search = new Search(
         inputStream, "smelt", "http://bit.ly/15sYPA7");
      search.execute();
      assertEquals(0, search.getMatches().size());
      // START:test
      stream.close();
   }
   // END:test
}
