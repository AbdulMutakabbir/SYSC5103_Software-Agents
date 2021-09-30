//	File:			ReactiveMapper.java
//	Author:		    Mutakabbir
//	Date:			30/09/2021

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/* 
*   class contains mapping infrmation for the Agents
*/
public class ReactiveMapper {

    // maping variables
    private static String FILE_NAME = "ReactiveAgent_Maping.csv";
    HashMap<String, String> mappings;

    // constructor initializes the HashMap
    public ReactiveMapper() {
        this.mappings = new HashMap<String, String>();
    }

    // returns the action that will be performed based on environment parameter
    public Action getActionForEnvironment(String environment) {
        String action = mappings.get(environment);
        return getActionFromString(action);
    }

    // returns the Environment 
    private Environment getEnvironmentFromString(String evironment_string) {
        Environment env = new Environment(evironment_string);
        return env;
    }

    // returns the action
    private Action getActionFromString(String action_string) {
        Action action;
        switch (action_string) {
            case "Turn":
                action = new ActionTurn();
                break;
            case "KickBall":
                action = new ActionKick();
                break;
            case "MoveTowardsBall":
                action = new ActionMoveTowardsBall();
                break;
            default:
                action = new ActionUnknown();
                break;
        }

        return action;
    }

    // reads the external file and sets the maping
    public void setMappings() throws IOException {

        // initialize mappping by clearing it
        mappings.clear();

        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        // read first line
        String mapping_string = br.readLine();

        while (mapping_string != null) {

            // parsing the csv file
            String[] mapping_string_array = mapping_string.split(",");

            // adding the mapping
            String action = mapping_string_array[1].trim();
            String env = mapping_string_array[0].trim();
            mappings.put(env, action);

            // read next line
            mapping_string = br.readLine();
        }

    }

    // prints maping
    public void printMappings() {
        for (Map.Entry<String, String> mapping : mappings.entrySet()) {
            System.out.println(mapping.getKey() + " --> " + mapping.getValue());
        }
    }
}