package persistence;

import org.json.JSONObject;

//represents the Writeable interface
////Modeled with the help of: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
