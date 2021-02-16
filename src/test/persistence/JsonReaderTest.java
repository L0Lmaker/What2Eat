package persistence;

import model.RecipeBook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//Tests the JsonReader class
//Modeled with the help of: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest {
    RecipeBook book;

    /*
    tests read() for a file path that does not exist
    should throw a IOException
    */
    @Test
    void testReaderNonExistentFile() {
        JsonReader in = new JsonReader("./data/noSuchFile.json");
        try {
            book = in.read();
            fail("File doesn't exist so the read method should fail.");
        } catch (IOException e) {
            // expected
        }
    }

    /*
    tests the reader when an empty RecipeBook is initialized
    */
    @Test
    void testReaderEmptyRecipeBook() {
        JsonReader in = new JsonReader("./data/testReaderEmptyRecipeBook.json");
        try {
            book = in.read();
        } catch (IOException e) {
            fail("read() method should not have failed.");
        }
        assertEquals(0, book.getRecipes().size());
    }

    /*
    tests the reader when a non empty RecipeBook is initialized
    */
    @Test
    void testReaderGeneralRecipeBook() {
        JsonReader in = new JsonReader("./data/testReaderGeneralRecipeBook.json");
        try {
            book = in.read();
        } catch (IOException e) {
            fail("read() method should not have failed.");
        }
        assertEquals(2, book.getRecipes().size());

        List<String> ings = new ArrayList<>();
        ings.add("a");
        ings.add("b");
        ings.add("c");
        assertEquals("abc", book.getRecipes().get(0).getName());
        assertArrayEquals(ings.toArray(), book.getRecipes().get(0).getIngredients().toArray());
        assertEquals("abc", book.getRecipes().get(0).getProcess());

        ings = new ArrayList<>();
        ings.add("b");
        ings.add("c");
        ings.add("d");
        assertEquals("bcd", book.getRecipes().get(1).getName());
        assertArrayEquals(ings.toArray(), book.getRecipes().get(1).getIngredients().toArray());
        assertEquals("bcd", book.getRecipes().get(1).getProcess());
    }
}
