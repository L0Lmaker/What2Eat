package persistence;

import model.RecipeBook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//represents a reader that reads RecipeBook from JSON data stored in a file
//Modeled with the help of: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from specified file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads RecipeBook from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RecipeBook read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecipeBook(jsonObject);
    }

    // EFFECTS: parses RecipeBook from JSON object and returns it
    private RecipeBook parseRecipeBook(JSONObject jsonObject) {
        RecipeBook book = new RecipeBook();
        addRecipes(book, jsonObject);
        return book;
    }


    // MODIFIES: book
    // EFFECTS: parses Recipes from JSON object and adds them to RecipeBook
    private void addRecipes(RecipeBook book, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("recipes");
        for (Object json : jsonArray) {
            JSONObject recipe = (JSONObject) json;
            addRecipe(book, recipe);
        }
    }

    // MODIFIES: book
    // EFFECTS: parses a Recipe object from JSON object and adds it to RecipeBook
    private void addRecipe(RecipeBook book, JSONObject recipe) {
        String name = recipe.getString("name");
        List<String> ingredients = new ArrayList<>();
        JSONArray jsonArray = recipe.getJSONArray("ingredients");
        for (Object json : jsonArray) {
            ingredients.add((String)json);
        }
        String process = recipe.getString("process");

        book.addRecipe(name, ingredients, process);
    }

    // EFFECTS: reads source file as a string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }
}
