package persistence;

import model.RecipeBook;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that saves JSON representation of the RecipeBook to file
//Modeled with the help of: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs the writer by setting the destination path
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination
    //          cannot be opened to write to
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /*
    MODIFIES: this
    EFFECTS: writes the Json representation of the RecipeBook to file
     */
    public void write(RecipeBook book) {
        JSONObject json = book.toJson();
        saveToFile(json.toString());
    }

    /*
    MODIFIES: this
    EFFECTS: writes string to file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }
}
