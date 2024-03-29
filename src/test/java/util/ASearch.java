package util;

// text courtesy of Herman Melville (Moby Dick) from
// http://www.gutenberg.org/cache/epub/2701/pg2701.txt 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class ASearch {
   static final String A_TITLE = "1";

   @BeforeEach
   void suppressLogging() {
      Search.LOGGER.setLevel(Level.OFF);
   }

   // START:test1
   @Test
   void returnsMatchesWithSurroundingContext() {
      // START_HIGHLIGHT
      var stream = streamOn("rest of text here"
              + "1234567890search term1234567890"
               + "more rest of text");
      var search = new Search(stream, "search term", A_TITLE);
      // END_HIGHLIGHT
      search.setSurroundingCharacterCount(10);

      search.execute();

      var matches = search.getMatches();
      assertEquals(List.of(
              new Match(A_TITLE,
                  // START_HIGHLIGHT
                  "search term",
                  "1234567890search term1234567890")),
          // END_HIGHLIGHT
          matches);
   }
   // END:test1

   // START:test2
   @Test
   void returnsNoMatchesWhenSearchTextNotFound() throws IOException {
      var connection =
         new URL("http://bit.ly/15sYPA7").openConnection();
      try (var inputStream = connection.getInputStream()) {
         var search = new Search(inputStream, "smelt", A_TITLE);

         search.execute();

         assertTrue(search.getMatches().isEmpty());
      }
   }
   // END:test2

   private static ByteArrayInputStream streamOn(String text) {
       return new ByteArrayInputStream(text.getBytes());
   }
}
