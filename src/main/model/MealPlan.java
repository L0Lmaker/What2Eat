package model;

import exceptions.NoRecipesInBookException;

// Represents the MealPlan using a 2 dimensional Recipe Array
public class MealPlan {


    public Recipe[][] plan; // stores the Meal plan
    private int numDays = 5;
    private int mealsPerDay = 3;


    // Modifies: this
    // Effects: Initializes an empty schedule
    public MealPlan() {
        this.plan = new Recipe[numDays][mealsPerDay];
    }

    //    Modifies: this
    //    Effects: Creates a randomized schedule and displays it on the console
    public Recipe[][] createPlan(RecipeBook book) {
        try {
            for (int i = 0; i < plan.length; i++) {
                for (int j = 0; j < plan[0].length; j++) {
                    plan[i][j] = book.getRandomRecipe();
                }
            }
            return plan;
        } catch (NoRecipesInBookException e) {
            System.out.println("There are currently no Recipes. "
                    + "Please add a recipe into the cookbook before generating a new Plan.");
            return plan;
        }
    }
}

