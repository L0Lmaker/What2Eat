package model;

import exceptions.NameIsEmptyException;
import exceptions.NoRecipesInBookException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents the CookBook that stores recipes
public class RecipeBook implements Writable {

    private List<Recipe> recipes; // stores Recipes

    /*
     * EFFECTS: initializes the recipes list
     */
    public RecipeBook() {
        recipes = new ArrayList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Adds a new recipe to the cookbook with th name ingredients and process
     *
     */
    public void addRecipe(String name, List<String> ingredients, String process) {
        try {
            recipes.add(new Recipe(name, ingredients, process));
        } catch (NameIsEmptyException e) {
            System.out.println("Name of Recipe cannot be an empty String");
        }
    }

    /*
     * EFFECTS: returns a random recipe from the cookbook
     *          if size of the cookbook is less than 1
     *          NoRecipesInBookException is thrown to caller
     */
    public Recipe getRandomRecipe() throws NoRecipesInBookException {
        if (recipes.size() < 1) {
            throw new NoRecipesInBookException("There are no Recipes in the Book yet!");
        }
        Random rand = new Random();
        return recipes.get(rand.nextInt(recipes.size()));
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    /*
     EFFECTS: returns the RecipeBook as a json object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("recipes", recipesToJson());
        return json;
    }

    /*
    EFFECTS: returns the recipes list
     */
    private JSONArray recipesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe recipe : recipes) {
            jsonArray.put(recipe.toJson());
        }

        return jsonArray;
    }

}
