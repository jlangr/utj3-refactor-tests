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
// START_HIGHLIGHT
class ASearch {
   // END_HIGHLIGHT
   // ...
   // END:test
   static final String A_TITLE = "1";

   @Test
      // STAR_:HIGHLIGHT
   void returnsMatchesWithSurroundingContext() throws IOException {
      // END_HIGHLIGHT
      var stream = streamOn("There are certain queer times and occasions "
          + "in this strange mixed affair we call life when a man "
          + "takes this whole universe for a vast practical joke, "
          + "though the wit thereof he but dimly discerns, and more "
          + "than suspects that the joke is at nobody's expense but his own.");
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
      stream.close();
   }

   @Test
   // START_HIGHLIGHT
   void returnsNoMatchesWhenSearchTextNotFound() throws IOException {
      // END_HIGHLIGHT
      var connection =
         new URL("http://bit.ly/15sYPA7").openConnection();
      var inputStream = connection.getInputStream();
      // START_HIGHLIGHT add var keyword
      var search = new Search(inputStream, "smelt", A_TITLE);
      // END_HIGHLIGHT

      search.execute();
      assertTrue(search.getMatches().isEmpty());
      // START_HIGHLIGHT change stream to inputStream
      inputStream.close();
      // END_HIGHLIGHT
   }
   // ...
   // END:test

   private static ByteArrayInputStream streamOn(String text) {
       return new ByteArrayInputStream(text.getBytes());
   }
   // START:test
}
// END:test
