package model;

import exceptions.NameIsEmptyException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.Objects;

// Represents a Recipe with a name, ingredients and process
public class Recipe implements Writable {

    private String name;                //Recipe name
    private List<String> ingredients;   //Ingredients needed for the recipe
    private String process;             //The process for making the Dish

    /*
     *  EFFECTS: creates a new recipe with name, ingredients and process
     *           if name is a zero length String, NameIsEmptyException is thrown to caller
     */
    public Recipe(String name, List<String> ingredients, String process) throws NameIsEmptyException {
        if (name.length() == 0) {
            throw new NameIsEmptyException("Name cannot be empty.");
        }
        this.name = name;
        this.ingredients = ingredients;
        this.process = process;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getProcess() {
        return process;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("ingredients", ingredientsToJsonArray());
        json.put("process", process);
        return json;
    }

    /*
    EFFECTS: returns a JSONArray of ingredients
     */
    private JSONArray ingredientsToJsonArray() {
        JSONArray jsonArray = new JSONArray();

        for (String ingredient : ingredients) {
            jsonArray.put(ingredient);
        }

        return jsonArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        return name.equals(recipe.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
