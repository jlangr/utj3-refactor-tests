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
   // ...
   // END:test
   static final String A_TITLE = "1";

   // START:test
    // START:twoparts
   @Test
   void testSearch() throws IOException {
       // END:twoparts
      // START_HIGHLIGHT
       // START:twoparts
      var stream = streamOn("There are certain queer times and occasions "
          // END:twoparts
          // END_HIGHLIGHT
          + "in this strange mixed affair we call life when a man "
          + "takes this whole universe for a vast practical joke, "
          + "though the wit thereof he but dimly discerns, and more "
          + "than suspects that the joke is at nobody's expense but his own.");
      // END:test
       // START:twoparts
      // ...
      // START:test
      var search = new Search(stream, "practical joke", A_TITLE);
      // END:twoparts
      // ...
      // START:twoparts
      // END:test
      Search.LOGGER.setLevel(Level.OFF);
      search.setSurroundingCharacterCount(10);
      search.execute();
      assertFalse(search.errored());
      var matches = search.getMatches();
      assertEquals(List.of(
         new Match(A_TITLE,
            "practical joke",
            "or a vast practical joke, though t")),
         matches);
      stream.close();

      var connection =
         new URL("http://bit.ly/15sYPA7").openConnection();
      var inputStream = connection.getInputStream();
      search = new Search(
         inputStream, "smelt", A_TITLE);
      search.execute();
      assertTrue(search.getMatches().isEmpty());
      stream.close();
      // START:test
   }
   // END:twoparts

   // START_HIGHLIGHT
   private ByteArrayInputStream streamOn(String text) {
       return new ByteArrayInputStream(text.getBytes());
   }
   // END_HIGHLIGHT
}
// END:test
