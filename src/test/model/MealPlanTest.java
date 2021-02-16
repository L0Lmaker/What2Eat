package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MealPlanTest {

    private MealPlan mp;
    private RecipeBook book;

    @BeforeEach
    void runBefore() {
        mp = new MealPlan();
        book = new RecipeBook();
    }

    @Test
    void testCreatePlan() {
        book.addRecipe("a", Arrays.asList("1", "2", "3"), "abc");
        Recipe[][] schedule = mp.createPlan(book);
        for (Recipe[] day : schedule) {
            for (Recipe meal : day) {
                assertTrue(book.getRecipes().contains(meal));
            }
        }
    }

    @Test
    void testCreatePlanNoRecipes() {
        Recipe[][] schedule = mp.createPlan(book);
        assertNull(schedule[0][0]);
    }


}
