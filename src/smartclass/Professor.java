/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclass;

/**
 *
 * @author Pedro
 */
public class Professor {
    private String name;
    private Short temperature;
    
    public Professor(String name, Short temperature){
        this.name = name;
        this.temperature = temperature;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the temperature
     */
    public Short getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(Short temperature) {
        this.temperature = temperature;
    }
    
}
