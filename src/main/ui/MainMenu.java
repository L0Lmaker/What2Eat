package ui;

import model.Recipe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

// Represents the GUI class that the User interacts with
public class MainMenu extends JFrame implements ActionListener {
    protected MealPlanApp app;
    private ArrayList<Recipe> recipeButtons;

    /**
     * EFFECTS: constructs the Main Menu Object and sets the attributes of the Frame
     * Initializes the MealPlanApp
     */
    public MainMenu() {
        super("MEal Planner");
        app = new MealPlanApp();

        // Setting frame attributes
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setClosingEventToSaveQuestion();
        setPreferredSize(new Dimension(1000, 700));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(100, 50, 50, 50));
        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon("./data/Pictures/UiBackground.jpg")));
        setLayout(new FlowLayout());

        drawMainMenu();
    }

    /**
     * EFFECTS: calls the appropriate method when a button is clicked in the Frame
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Recipes")) {
            drawRecipeMenu();
        } else if (e.getActionCommand().equals("Load File")) {
            app.loadRecipeBook();
        } else if (e.getActionCommand().equals("Create Week's Mealplan")) {
            drawMealPlan();
        } else if (e.getActionCommand().equals("Open Recipe")) {
            drawSpecificRecipe((JButton) e.getSource());
        } else if (e.getActionCommand().equals("Main menu")) {
            drawMainMenu();
        } else if (e.getActionCommand().equals("Add recipe")) {
            drawAddRecipeMenu();
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: draws the MainMenu on the Frame
     */
    protected void drawMainMenu() {
        clearFrame();

        setLayout(new FlowLayout());
        addEmptyTransparentBarForUi();

        // Creating Menu Buttons

        // Load File Button
        addMainMenuUiButton("./data/Pictures/LoadRecipesPicture.jpg", "Load File");
        // Recipes Menu Button
        addMainMenuUiButton("./data/Pictures/RecipesPicture.jpg", "Recipes");
        // Generate Mealplan button
        addMainMenuUiButton("./data/Pictures/GenerateMealPlanPicture.jpg", "Create Week's Mealplan");
        // Add New Recipe Button
        addMainMenuUiButton("./data/Pictures/AddNewRecipePicture.jpg", "Add recipe");

        packingMethod();
    }

    /**
     * MODIFIES: this
     * EFFECTS: adds a transparent bar in the frame, mainly for better UI look.
     */
    private void addEmptyTransparentBarForUi() {
        JPanel emptyPanel = new JPanel();
        JTextPane titlePane = new JTextPane();
        titlePane.setPreferredSize(new Dimension(750, 17));
        titlePane.setOpaque(false);
        emptyPanel.setOpaque(false);
        titlePane.setEditable(false);
        emptyPanel.add(titlePane);
        add(emptyPanel);
    }

    /**
     * MODIFIES: this
     * EFFECTS: gets the recipe names from the MealPlan
     * draws a main menu button that redirects User to Main Menu
     * draws the Recipe Menu UI as a grid of Recipes
     */
    private void drawRecipeMenu() {
        clearFrame();
        setLayout(new GridBagLayout());
        int totalRecipes = app.book.getRecipes().size();
        int rows = (int) Math.ceil((double) totalRecipes / 5);
        JPanel containerPanel = new JPanel();
        addMainMenuButton(containerPanel);
        if (totalRecipes > 0) {
            addRecipesToGrid(rows, containerPanel);
        } else {
            drawNoRecipesLoadedMenu(containerPanel);
        }
        processFrame(containerPanel);
    }

    /**
     * MODIFIES: this
     * EFFECTS: draws the generated Meal Plan for the week on the frame
     */
    private void drawMealPlan() {
        clearFrame();

        setLayout(new GridBagLayout());
        int columns = 6;
        int rows = 4;
        JPanel mainPanel = new JPanel();
        addMainMenuButton(mainPanel);

        JPanel mealPlanGrid = new JPanel();
        mealPlanGrid.setPreferredSize(new Dimension(800, 500));
        mealPlanGrid.setLayout(new GridLayout(rows, columns));
        mainPanel.add(mealPlanGrid);

        JPanel[][] panelHolder = new JPanel[rows][columns];
        addPanelsToPanelHolder(columns, rows, mealPlanGrid, panelHolder);
        setDayLabels(panelHolder[0]);
        setBldLabels(panelHolder);

        addMealsToGrid(panelHolder, app.getPlan(app.book), rows, columns);

        processFrame(mainPanel);
    }

    /**
     * MODIFIES: this
     * EFFECTS: draws the UI and information for a specific recipe that is selected.
     */
    private void drawSpecificRecipe(JButton button) {
        clearFrame();
        setLayout(new GridBagLayout());

        JPanel centerPanel = drawSpecificRecipePanel(button);

        JPanel totalPanel = new JPanel(new FlowLayout());
        addMainMenuButton(totalPanel);
        totalPanel.add(centerPanel);

        processFrame(totalPanel);
    }

    /**
     * MODIFIES: this
     * EFFECTS: displays popups, asking the user to enter the information to
     * add a new Recipe to the Recipe Book, and adds a new Recipe to the Recipe Book
     */
    private void drawAddRecipeMenu() {
        String recipeName = showRecipeNameInputDialog("Enter the name of the Recipe", "Recipe Name");
        ArrayList<String> ingredients = showIngredientsInputDialog();
        String recipeProcess = showRecipeNameInputDialog(
                "Enter the process of the Recipe as one line.",
                "Recipe Details");
        app.book.addRecipe(recipeName, ingredients, recipeProcess);
        drawMainMenu();
    }

    /**
     * MODIFIES: this
     * EFFECTS: creates a new MainMenu() object
     */
    public static void main(String[] args) {
        new MainMenu();
    }


    /**
     * Helper Methods
     */

    /**
     * REQUIRES: s is a valid file path to a photo
     * MODIFIES: this
     * EFFECTS: draws a button with an image with filepath s on the Frame
     */
    private void addMainMenuUiButton(String s, String s2) {
        ImageIcon i;
        i = new ImageIcon(s);
        JButton loadButton = new JButton(i);
        loadButton.setPreferredSize(new Dimension(400, 300));
        loadButton.setActionCommand(s2);
        loadButton.addActionListener(this);
        add(loadButton);
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the closing event on the frame to ask the save question
     */
    protected void setClosingEventToSaveQuestion() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String[] objButtons;
                objButtons = new String[]{"Yes", "No"};
                int promptResult;
                promptResult = JOptionPane.showOptionDialog(
                        null,
                        "Do you want to save changes before closing?",
                        "Save changes?",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        objButtons,
                        objButtons[1]);
                if (promptResult == JOptionPane.YES_OPTION) {
                    app.saveRecipeBook();
                    System.exit(0);
                } else if (promptResult == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    /**
     * MODIFIES: this
     * EFFECTS: packs the frames attributes
     */
    private void packingMethod() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    /**
     * MODIFIES: this
     * EFFECTS: adds the recipes in the Recipe Book to a grid on the frame
     */
    private void addRecipesToGrid(int rows, JPanel containerPanel) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(rows, 5));
        recipeButtons = new ArrayList<>();
        for (Recipe elem : app.book.getRecipes()) {
            JButton temp = new JButton(elem.getName());
            buttonPanel.add(temp);
            recipeButtons.add(elem);
            temp.setActionCommand("Open Recipe");
            temp.addActionListener(this);
        }
        buttonPanel.setPreferredSize(new Dimension(800, 500));
        containerPanel.add(buttonPanel);
    }

    /**
     * MODIFIES: this
     * EFFECTS: draws the No Recipes Loaded Frame when an action that Requires app.book.getRecipes().size()>0 and
     * this requirement is not met
     */
    private void drawNoRecipesLoadedMenu(JPanel containerPanel) {
        JTextArea noRecipesText = new JTextArea("No recipes loaded!");
        containerPanel.add(noRecipesText);
    }

    /**
     * MODIFIES: this
     * EFFECTS: keeps displaying the ingredient input dialog and accepts input
     * until the exit case reached. The Arraylist of ingredients entered is returned to the caller
     */
    private ArrayList<String> showIngredientsInputDialog() {
        ArrayList<String> ingredients = new ArrayList<>();
        String entered = "";
        while (!entered.equals(".")) {
            entered = (String) JOptionPane.showInputDialog(
                    this,
                    "Enter the ingredient. (put . if all ingredients have been entered)",
                    "Ingredients",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""
            );
            ingredients.add(entered);
        }
        ingredients.remove(ingredients.size() - 1);
        return ingredients;
    }

    /**
     * MODIFIES: this
     * EFFECTS: displays a Recipe Name input dialog
     * take input of Recipe Name from the user and returns it to caller
     */
    private String showRecipeNameInputDialog(String s, String s2) {
        return (String) JOptionPane.showInputDialog(
                this,
                s,
                s2,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
        );
    }

    /**
     * MODIFIES: this
     * EFFECTS: adds Panels to panelHolder 2d array
     * and adds a reference to the panel into the mealPlanGrid
     * so it can be accessed individually
     * as required
     */
    private void addPanelsToPanelHolder(int columns, int rows, JPanel mealPlanGrid, JPanel[][] panelHolder) {
        for (int m = 0; m < rows; m++) {
            for (int n = 0; n < columns; n++) {
                panelHolder[m][n] = new JPanel();
                mealPlanGrid.add(panelHolder[m][n]);
            }
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: draws a button that redirects User to the Main Menu.
     */
    private void addMainMenuButton(JPanel mainPanel) {
        JButton mainMenuButton = new JButton("Main Menu");

        mainMenuButton.setActionCommand("Main menu");
        mainMenuButton.addActionListener(this);
        mainPanel.add(mainMenuButton);
    }

    /**
     * MODIFIES: this
     * EFFECTS: adds Recipe Buttons to the grid
     * Buttons redirect User to the Specific details of the Recipe
     */
    private void addMealsToGrid(JPanel[][] panelHolder, Recipe[][] plan, int rows, int columns) {
        if (plan[0][0] != null) {
            for (int i = 1; i < rows; i++) {
                for (int j = 1; j < columns; j++) {
                    JButton temp = new JButton(plan[j - 1][i - 1].getName());
                    panelHolder[i][j].add(temp);
                    temp.setActionCommand("Open Recipe");
                    temp.addActionListener(this);
                }
            }
        } else {
            JTextArea noRecipesText = new JTextArea();
            noRecipesText.setText("No recipes Loaded.");
            panelHolder[0][0].add(noRecipesText);
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the Breakfast Lunch and Dinner Labels onto the Panel Holder
     */
    private void setBldLabels(JPanel[][] panelHolder) {
        JTextArea temp;
        temp = new JTextArea("Breakfast");
        temp.setFont(new Font("Serif", Font.ITALIC, 20));
        panelHolder[1][0].add(temp);
        temp = new JTextArea("Lunch");
        temp.setFont(new Font("Serif", Font.ITALIC, 20));
        panelHolder[2][0].add(temp);
        temp = new JTextArea("Dinner");
        temp.setFont(new Font("Serif", Font.ITALIC, 20));
        panelHolder[3][0].add(temp);
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the Day Labels (Monday, Tuesday, ... ) to the PanelHolder
     */
    private void setDayLabels(JPanel[] jpanels) {
        JTextArea temp = new JTextArea("Monday");
        temp.setFont(new Font("Serif", Font.ITALIC, 20));
        jpanels[1].add(temp);
        temp = new JTextArea("Tuesday");
        temp.setFont(new Font("Serif", Font.ITALIC, 20));
        jpanels[2].add(temp);
        temp = new JTextArea("Wednesday");
        temp.setFont(new Font("Serif", Font.ITALIC, 20));
        jpanels[3].add(temp);
        temp = new JTextArea("Thursday");
        temp.setFont(new Font("Serif", Font.ITALIC, 20));
        jpanels[4].add(temp);
        temp = new JTextArea("Friday");
        temp.setFont(new Font("Serif", Font.ITALIC, 20));
        jpanels[5].add(temp);
    }

    /**
     * MODIFIES: this
     * EFFECTS: draws the UI and information of a specific recipe onto a panel
     * and returns the panel to the caller
     */
    private JPanel drawSpecificRecipePanel(JButton button) {


        Recipe recipe = null;
        recipe = findRecipeFromTitle(button, recipe);

        // Main Panel
        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.setPreferredSize(new Dimension(800, 620));

        //Recipe name panel
        JPanel titlePanel = new JPanel(new FlowLayout());
        setRecipeTitlePanel(recipe, titlePanel);

        //information panel
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.setPreferredSize(new Dimension(900, 550));

        //Ingredients Panel
        JPanel ingPanel = new JPanel(new FlowLayout());
        setIngredientsPanelAttributes(ingPanel);

        //Ingredients Panel Title
        setIngredientsPanelTitleAttributes(ingPanel);

        // adding the ingredients to the ingPane
        setIngredientsTextPaneAttributes(recipe, infoPanel, ingPanel);

        //adding process string to process panel
        JPanel processPanel = new JPanel();
        JTextArea processPane = new JTextArea();
        setProcessPaneAttributes(processPane);

        setProcessPanelAttributes(infoPanel, processPanel);

        //Process Pane title
        JPanel processTitlePanel = new JPanel(new FlowLayout());
        addProcessDetailsToPanel(recipe, infoPanel, processPanel, processPane, processTitlePanel);

        mainPanel.add(titlePanel);
        mainPanel.add(infoPanel);


        return mainPanel;
    }

    /**
     * MODIFIES: this
     * EFFECTS: clears the UI elements on the Frame
     */
    private void clearFrame() {
        getContentPane().removeAll();
        repaint();
    }

    /**
     * MODIFIES: this
     * EFFECTS: draws the passed Panel onto the Frame
     */
    private void processFrame(JPanel mainPanel) {
        getContentPane().add(mainPanel);
        pack();
        setVisible(true);
    }

    /**
     * MODIFIES: this
     * EFFECTS: finds the recipe in the Recipe Book from a given title
     * returns the Recipe Object
     */
    private Recipe findRecipeFromTitle(JButton button, Recipe recipe) {
        for (Recipe elem : app.book.getRecipes()) {
            recipe = elem;
            if (elem.getName().equals(button.getText())) {
                break;
            }
        }
        return recipe;
    }

    /**
     * MODIFIES: this
     * EFFECTS: displays the Recipe's process details onto the Frame
     */
    private void addProcessDetailsToPanel(Recipe recipe, JPanel infoPanel, JPanel processPanel,
                                          JTextArea processPane, JPanel processTitlePanel) {
        JTextPane processTitlePane = new JTextPane();
        processTitlePanel.setPreferredSize(new Dimension(435, 40));
        processTitlePanel.add(processTitlePane, BorderLayout.CENTER);
        processTitlePane.setText("Process");
        processPanel.add(processTitlePanel);
        processTitlePane.setFont(new Font("Serif", Font.ITALIC, 30));
        processTitlePane.setBackground(new Color(238, 238, 238));
        processPanel.add(processTitlePanel);

        // adding the process to the processPane
        processPanel.add(processPane);
        processPane.setText(recipe.getProcess());
        processPane.setFont(new Font("Serif", Font.ITALIC, 20));
        infoPanel.add(processPanel);
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the attributes of the Process Panel in the Specific recipe View
     */
    private void setProcessPanelAttributes(JPanel infoPanel, JPanel processPanel) {
        processPanel.setPreferredSize(new Dimension(465, 550));
        processPanel.setBackground(new Color(255, 255, 255));
        infoPanel.add(processPanel);
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the text Pane attributes for the Process Panel
     */
    private void setProcessPaneAttributes(JTextArea processPane) {
        processPane.setBackground(new Color(255, 255, 255));
        processPane.setPreferredSize(new Dimension(435, 500));
        processPane.setLineWrap(true);
        processPane.setWrapStyleWord(true);
        processPane.setEditable(false);
    }

    /**
     * MODIFIES: this
     * EFFECTS: Sets the ingredients text pane attributes
     */
    private void setIngredientsTextPaneAttributes(Recipe recipe, JPanel infoPanel, JPanel ingPanel) {
        JTextPane ingPane = new JTextPane();
        ingPane.setEditable(false);
        ingPanel.add(ingPane);
        infoPanel.add(ingPanel);
        ingPane.setText("\n" + app.getIngredientsAsList(recipe));
        ingPane.setFont(new Font("Serif", Font.ITALIC, 20));
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the Ingredients title panel attributes
     */
    private void setIngredientsPanelTitleAttributes(JPanel ingPanel) {
        JPanel ingTitlePanel = new JPanel(new FlowLayout());
        ingTitlePanel.setPreferredSize(new Dimension(300, 40));
        JTextPane ingPaneTitle = new JTextPane();
        ingTitlePanel.add(ingPaneTitle, BorderLayout.CENTER);
        ingPaneTitle.setText("Ingredients");
        ingPaneTitle.setAlignmentY(FlowLayout.CENTER);
        ingPaneTitle.setFont(new Font("Serif", Font.ITALIC, 30));
        ingPanel.add(ingTitlePanel, BorderLayout.CENTER);
        ingPaneTitle.setBackground(new Color(238, 238, 238));
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the Ingredients panel attributes
     */
    private void setIngredientsPanelAttributes(JPanel ingPanel) {
        ingPanel.setPreferredSize(new Dimension(315, 550));
        ingPanel.setBackground(new Color(255, 255, 255));
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the Recipe Title panel attributes
     */
    private void setRecipeTitlePanel(Recipe recipe, JPanel titlePanel) {
        JTextPane titlePane = new JTextPane();
        titlePane.setText(recipe.getName());
        titlePanel.setPreferredSize(new Dimension(785, 45));
        titlePanel.add(titlePane, BorderLayout.CENTER);
        titlePane.setBackground(new Color(255, 255, 255));
        titlePanel.setBackground(new Color(255, 255, 255));
        titlePane.setFont(new Font("Serif", Font.ITALIC, 40));
    }

}