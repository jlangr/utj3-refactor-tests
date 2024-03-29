package util;

// text courtesy of Herman Melville (Moby Dick) from
// http://www.gutenberg.org/cache/epub/2701/pg2701.txt 

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

// START:test
class SearchTest {
   // START_HIGHLIGHT
   static final String A_TITLE = "1";
   // END_HIGHLIGHT

   @Test
   void testSearch() throws IOException {
      // START:createStream
      var pageContent = "There are certain queer times and occasions "
         + "in this strange mixed affair we call life when a man "
         + "takes this whole universe for a vast practical joke, "
         + "though the wit thereof he but dimly discerns, and more "
         + "than suspects that the joke is at nobody's expense but "
         + "his own.";
      var bytes = pageContent.getBytes();
      var stream = new ByteArrayInputStream(bytes);
      // END:createStream
      // START_HIGHLIGHT
      var search = new Search(stream, "practical joke", A_TITLE);
      // END_HIGHLIGHT
      Search.LOGGER.setLevel(Level.OFF);
      search.setSurroundingCharacterCount(10);
      search.execute();
      assertFalse(search.errored());
      var matches = search.getMatches();
      assertEquals(List.of(
         // START_HIGHLIGHT
         new Match(A_TITLE,
         // END_HIGHLIGHT
            "practical joke",
            "or a vast practical joke, though t")),
         matches);
      stream.close();

      var connection =
         new URL("http://bit.ly/15sYPA7").openConnection();
      var inputStream = connection.getInputStream();
      search = new Search(
          // START_HIGHLIGHT
         inputStream, "smelt", A_TITLE);
      // END_HIGHLIGHT
      search.execute();
      assertTrue(search.getMatches().isEmpty());
      stream.close();
   }
}
// STOP:test
