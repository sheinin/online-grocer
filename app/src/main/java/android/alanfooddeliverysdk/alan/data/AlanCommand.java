package com.alan.actsoft.alan.data;
/**
 * This object is used while processing the data of alan callback event's command.
 */
public class AlanCommand{
    private String command;
    private String value;
    private String screen;
    private String name;
    private String temp;
    private String error;


    public AlanCommand(String er){
        this.error = er;
    }

    public boolean hasError(){
        return this.error != null ? true : false;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
