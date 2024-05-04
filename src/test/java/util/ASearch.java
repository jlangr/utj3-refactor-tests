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

class ASearch {
   static final String A_TITLE = "1";
   // START:test
   @Test
   void returnsMatchesWithSurroundingContext() throws IOException {
      var stream = streamOn("There are certain queer times and occasions "
          // ...
          // END:test
          + "in this strange mixed affair we call life when a man "
          + "takes this whole universe for a vast practical joke, "
          + "though the wit thereof he but dimly discerns, and more "
          + "than suspects that the joke is at nobody's expense but his own.");
      // START:test
      var search = new Search(stream, "practical joke", A_TITLE);
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
      // START_HIGHLIGHT
      stream.close(); // delete me
      // END_HIGHLIGHT
   }

   @Test
   void returnsNoMatchesWhenSearchTextNotFound() throws IOException {
      var connection =
         new URL("http://bit.ly/15sYPA7").openConnection();
      var inputStream = connection.getInputStream();
      // START_HIGHLIGHT
      var search = new Search(inputStream, "smelt", A_TITLE);
      // END_HIGHLIGHT

      search.execute();
      assertTrue(search.getMatches().isEmpty());
      // START_HIGHLIGHT
      inputStream.close();
      // END_HIGHLIGHT
   }
   // END:test
   private static ByteArrayInputStream streamOn(String text) {
       return new ByteArrayInputStream(text.getBytes());
   }
}
