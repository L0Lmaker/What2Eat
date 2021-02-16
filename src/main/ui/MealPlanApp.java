package ui;

import model.MealPlan;
import model.Recipe;
import model.RecipeBook;
import org.json.JSONArray;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//Meal Plan Application
public class MealPlanApp {
    private static final String JSON_STORE = "./data/RecipeBook.json";
    protected MealPlan mealPlan;
    protected RecipeBook book;
    protected Scanner input;
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;

    // EFFECTS: runs the meal plan application
    public MealPlanApp() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initializeFields();
        //runMealPlan();
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/TellerApp (has some changes)
    // MODIFIES: this
    // EFFECTS: processes user input
    public void runMealPlan() {
        boolean keepGoing = true;
        String command;

        initializeFields();
        //addDefaultRecipes();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                saveBeforeClosing();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nHope that was Fun!");
    }

    // Source:
    // MODIFIES: this
    // EFFECTS: processes user input
    private void processCommand(String command) {
        switch (command.toLowerCase()) {
            case "add":
                addRecipe();
                break;
            case "all":
                showAllRecipeNames();
                break;
            case "gen":
                displayPlan(mealPlan.createPlan(book));
                break;
            case "show":
                showAllRecipeNames();
                showSpecificRecipe();
                break;
            case "load":
                loadRecipeBook();
                break;
            case "save":
                saveRecipeBook();
                break;
            default:
                System.out.println("Please enter a correct input value.");
        }
    }

    // EFFECTS: displays information about a specific recipe
    private void showSpecificRecipe() {
        if (book.getRecipes().size() != 0) {
            return;
        }
        System.out.println("Enter a number from 1-" + book.getRecipes().size());
        int index = input.nextInt() - 1;
        while (index >= book.getRecipes().size() || index < 1) {
            System.out.println("Enter a valid value in the range 1-" + book.getRecipes().size());
            index = input.nextInt() - 1;
        }
        printRecipe(book.getRecipes().get(index));
    }

    // MODIFIES: this
    // EFFECTS: initializes the Cookbook, Meal-plan and the Input scanner
    private void initializeFields() {
        book = new RecipeBook();
        mealPlan = new MealPlan();
        input = new Scanner(System.in);
    }


    //Helpers

    // MODIFIES: this
    // EFFECTS: loads RecipeBook from file
    protected void loadRecipeBook() {
        try {
            book = jsonReader.read();
            System.out.println("Loaded RecipeBook" + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the RecipeBook to file
    protected void saveRecipeBook() {
        try {
            jsonWriter.open();
            jsonWriter.write(book);
            jsonWriter.close();
            System.out.println("Saved RecipeBook" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: asks user whether save RecipeBook before closing
    //          if user enters y, then RecipeBook is saved and then program quits
    //          else the program just quits
    private void saveBeforeClosing() {
        System.out.println("Would you like to save? (y/n)");
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            saveRecipeBook();
        }
    }

    // EFFECTS: prompts user to enter ingredients one by one and
    //          returns an Arraylist of ingredients (helper).
    private ArrayList<String> getIngredientsFromUser() {
        ArrayList<String> ing = new ArrayList<>();

        while (true) {
            String ingredient = input.nextLine();
            if (ingredient.equals(".")) {
                return ing;
            }
            ing.add(ingredient);
        }
    }

    // SOURCE: https://github.students.cs.ubc.ca/CPSC210/TellerApp (has some changes)
    // EFFECTS: displays the main menu to user
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?:");
        System.out.println("\tload -> load recipes from save file");
        System.out.println("\tsave -> save recipes to save file");
        System.out.println("\tadd -> add a recipe to your cookbook");
        System.out.println("\tall -> show all of your stored recipes");
        System.out.println("\tshow -> show a specific recipe's details");
        System.out.println("\tgen -> generate a meal-plan from your cookbook");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: adds omelette Recipe to the cookbook
    private void addOmeletteMeal() {
        String[] ings;
        ings = new String[]{"Eggs", "Grated Cheese", "Spices"};
        book.addRecipe(
                "Omelette",
                Arrays.asList(ings),
                "Beat 3 eggs in a mug. Add to a pan on low heat. As the eggs cook, add cheese. "
                        + "Add some spices as the cheese melts. Serve."
        );
    }

    // EFFECTS: adds Veggie Pasta Recipe to the cookbook
    private void addVeggiePastaMeal() {
        String[] ings;
        ings = new String[]{"Pasta", "Pasta sauce", "Frozen Veggies", "Spices", "Cheese"};
        book.addRecipe(
                "Veggie Pasta",
                Arrays.asList(ings),
                "Cook Pasta. "
                        + "Boil veggies for 5 minutes and chop into pieces. "
                        + "Cook Pasta sauce with spices and add in the veggies. "
                        + "Mix in the cooked pasta and mix in some Cheese. Serve."
        );
    }

    // EFFECTS: adds Chicken Pasta Recipe to the cookbook
    private void addChickenPastaMeal() {
        String[] ings;
        ings = new String[]{"Pasta", "Pasta sauce", "Chicken Sausage", "Spices", "Cheese"};
        book.addRecipe(
                "Chicken Pasta",
                Arrays.asList(ings),
                "Cook Pasta. "
                        + "Boil chicken sausages for 5 minutes and chop into pieces. "
                        + "Cook Pasta sauce with spices and add in the chicken pieces. "
                        + "Mix in the cooked pasta and mix in some Cheese. Serve."
        );
    }

    // EFFECTS: adds Cereal Recipe to the cookbook
    private void addCerealMeal() {
        String[] ings = new String[]{"Milk", "Your choice of cereal"};
        book.addRecipe(
                "Cereal",
                Arrays.asList(ings),
                "First add cereal to bowl. Next add in your "
                        + "preferred amount of milk. Do not do it the wrong way around."
        );
    }

    // MODIFIES: this
    // EFFECTS: adds 4 default recipes to the Cookbook
    private void addDefaultRecipes() {
        addCerealMeal();
        addChickenPastaMeal();
        addVeggiePastaMeal();
        addOmeletteMeal();
    }

    // MODIFIES: this
    // EFFECTS: Adds a new User defined recipe to the cookbook
    private void addRecipe() {
        System.out.println("Enter Name of the Dish: ");
        input.nextLine();
        String name = input.nextLine();

        System.out.println("Enter ingredients ( enter . to stop adding to list): ");
        ArrayList<String> ing = getIngredientsFromUser();


        System.out.println("Enter Recipe Process (only use new line when done with writing): ");
        String process = input.nextLine();

        book.addRecipe(name, ing, process);
    }

    // REQUIRES: pre existing schedule must exist
    // EFFECTS: displays the current stored schedule on the console
    public void displayPlan(Recipe[][] plan) {
        if (book == null || book.getRecipes().size() == 0) {
            System.out.println("The RecipeBook empty. Add some Recipes!");
            return;
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%15s  %15s  %15s  %15s  %15s", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------");
        for (int i = 0; i < plan[0].length; i++) {
            for (Recipe[] recipes : plan) {
                System.out.format(" %15s ", recipes[i].getName());
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    /*
     *  EFFECTS: prints out the Recipe details on the console
     */
    public void printRecipe(Recipe recipe) {
        System.out.println(recipe.getName().toUpperCase());
        System.out.println("Ingredients:");
        for (String elem : recipe.getIngredients()) {
            System.out.println("  - " + elem);
        }
        System.out.println("process: " + recipe.getProcess());
    }

    /*
     * REQUIRES: at least 1 recipe stored in the cookbook
     * EFFECTS: prints out all the recipe names in the cookbook onto the console
     */
    public void showAllRecipeNames() {
        if (book == null || book.getRecipes().size() == 0) {
            System.out.println("No Recipes Loaded!");
            return;
        }

        System.out.println("Recipes:");
        System.out.println("=================");
        int counter = 1;
        for (Recipe elem : book.getRecipes()) {
            System.out.println(counter + ") " + elem.getName());
            counter++;
        }
    }

    /*
     * REQUIRES: recipe is not a null object
     * EFFECTS: returns the ingredients list seperated by new lines
     *          as a string
     */
    public String getIngredientsAsList(Recipe recipe) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String elem : recipe.getIngredients()) {
            stringBuilder.append(elem + "\n");
        }
        return stringBuilder.toString();
    }

    /*
     * REQUIRES: book is a non null Recipe Book object
     * EFFECTS: if mealplan is not initialized
     *          creates a new plan and returns the plan
     *          otherwise returns the stored plan
     */
    public Recipe[][] getPlan(RecipeBook book) {
        if (mealPlan.plan[0][0] == null) {
            mealPlan.createPlan(book);
        }
        return mealPlan.plan;
    }

}
