package model;

import exceptions.NameIsEmptyException;
import exceptions.NoRecipesInBookException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeBookTest {
    private RecipeBook book;

    @BeforeEach
    void setup(){
        book = new RecipeBook();
    }

    @Test
    void testAddRecipe(){
        //test for 1 recipe added in the cookbook
        book.addRecipe("a", Arrays.asList("1", "2", "3"), "abcd" );
        assertEquals(1, book.getRecipes().size());
        assertEquals("a", book.getRecipes().get(0).getName());

        //adding another recipe to the cookbook
        book.addRecipe("b", Arrays.asList("4", "5", "6"), "efgh" );
        assertEquals(2, book.getRecipes().size());
        assertEquals("b", book.getRecipes().get(1).getName());
    }

    @Test
    void testGetRandomRecipe(){
        //adding two recipes into cookbook
        book.addRecipe("a", Arrays.asList("1", "2", "3"), "abcd" );
        book.addRecipe("b", Arrays.asList("4", "5", "6"), "efgh" );

        //testing if getRandomRecipe returns a recipe that exists in the textbook
        try {
            Recipe temp = book.getRandomRecipe();
            assertTrue(book.getRecipes().contains(temp));
        } catch (NoRecipesInBookException e){
            fail("Exception should not have been called.");
        }
    }

    @Test
    void testGetRandomRecipeWhenNoRecipesInBook(){
        try {
            Recipe temp = book.getRandomRecipe();
            fail("NoRecipesInBookException should have been called.");
        } catch (NoRecipesInBookException e){
            //pass
        }
    }

    @Test
    void testAddRecipeWithNoName(){
        book.addRecipe("", null, "abcde");
        assertEquals(0, book.getRecipes().size());
    }
}
