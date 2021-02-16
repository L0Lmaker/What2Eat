package model;

import exceptions.NameIsEmptyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    private Recipe rec;

    @BeforeEach
    void setup(){
        try {
            rec = new Recipe("a", Arrays.asList("1", "2", "3"), "abcd");
        } catch (NameIsEmptyException e){
            fail();
        }
    }

    @Test
    void testGetName(){
        assertEquals("a", rec.getName());
        assertEquals(3, rec.getIngredients().size());
        assertEquals("abcd", rec.getProcess());
    }

    @Test
    void testEquals() {
        try {
            Recipe r1 = new Recipe("abc", null, "12345");
            Recipe r2 = new Recipe("abc", null, "67890");
            Recipe r3 = null;
            Recipe r4 = new Recipe("123", null, "12345");
            RecipeBook rb = new RecipeBook();
            assertTrue(r1.equals(r2));
            assertTrue(r1.equals(r1));

            assertFalse(r1.equals(r3));
            assertFalse(r1.equals(rb));
            assertFalse(r1.equals(r4));
        } catch (NameIsEmptyException e){
            fail();
        }
    }

    @Test
    void testHashCode() {
        try {
            Recipe r1 = new Recipe("abc", null, "12345");
            Recipe r2 = new Recipe("abc", null, "67890");
            Recipe r3 = new Recipe("123", null, "12345");

            assertEquals(r2.hashCode(), r1.hashCode());
            assertNotEquals(r3.hashCode(), r1.hashCode());
        } catch (NameIsEmptyException e){
            fail();
        }
    }

    @Test
    void testNoNameGivenForRecipe(){
        try{
            Recipe recipe = new Recipe("", null, "");
            fail("Exception should have been thrown.");
        } catch (NameIsEmptyException e){
            //pass
        }
    }

    @Test
    void testNameGivenForRecipe(){
        try{
            Recipe recipe = new Recipe("name", null, "");
            //pass
        } catch (NameIsEmptyException e){
            fail("NameIsEmptyException should not have been thrown.");
        }
    }
}
