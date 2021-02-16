package persistence;

import model.RecipeBook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//tests the JsonWriter Class
//Modeled with the help of: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest {

    /*
    tests JsonWriter in the case invalid file path is provided
     */
    @Test
    void testWriterInvalidFile() {
        try {
            RecipeBook book = new RecipeBook();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    /*
    tests JsonWriter in the case an empty RecipeBook is saved
     */
    @Test
    void testWriterEmptyRecipeBook() {
        try {
            RecipeBook book = new RecipeBook();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecipeBook.json");
            writer.open();
            writer.write(book);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRecipeBook.json");
            book = reader.read();
            assertEquals(0, book.getRecipes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    /*
    tests JsonWriter in the case that a non-empty RecipeBook is saved
     */
    @Test
    void testWriterGeneralRecipeBook() {
        try {
            RecipeBook book = new RecipeBook();
            List<String> ings1 = new ArrayList<>();
            ings1.add("a");
            ings1.add("b");
            ings1.add("c");
            book.addRecipe("abc", ings1, "abc");

            List<String> ings2 = new ArrayList<>();
            ings2.add("b");
            ings2.add("c");
            ings2.add("d");
            book.addRecipe("bcd", ings2, "bcd");

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecipeBook.json");
            writer.open();
            writer.write(book);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRecipeBook.json");
            book = reader.read();

            assertEquals("abc", book.getRecipes().get(0).getName());
            assertArrayEquals(ings1.toArray(), book.getRecipes().get(0).getIngredients().toArray());
            assertEquals("abc", book.getRecipes().get(0).getProcess());

            assertEquals("bcd", book.getRecipes().get(1).getName());
            assertArrayEquals(ings2.toArray(), book.getRecipes().get(1).getIngredients().toArray());
            assertEquals("bcd", book.getRecipes().get(1).getProcess());


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
