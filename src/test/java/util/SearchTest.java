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

class SearchTest {
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
      // START:constant1a
      var search = new Search(stream, "practical joke", "1");
      // END:constant1a
      Search.LOGGER.setLevel(Level.OFF);
      search.setSurroundingCharacterCount(10);
      search.execute();
      assertFalse(search.errored());
      var matches = search.getMatches();
      // START:constant1b
      assertEquals(List.of(
         new Match("1",
            "practical joke",
            "or a vast practical joke, though t")),
         matches);
      // END:constant1b
      stream.close();

      // START:constant1c
      var connection =
         new URL("http://bit.ly/15sYPA7").openConnection();
      var inputStream = connection.getInputStream();
      search = new Search(
          // START_HIGHLIGHT
         inputStream, "smelt", "http://bit.ly/15sYPA7");
      // END_HIGHLIGHT
      // END:constant1c
      // START:assertEmpty
      search.execute();
      // START_HIGHLIGHT
      assertTrue(search.getMatches().isEmpty());
      // END_HIGHLIGHT
      stream.close();
      // END:assertEmpty
   }
}
