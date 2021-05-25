package app;

import java.io.Serializable;

/**
 * Class that represents Address objects
 */
public class Address implements Serializable {
    private String zipCode; //Длина строки должна быть не меньше 3, Поле может быть null

    /**
     * Constructor that creates objects of Address
     * @param zipCode
     */
    public Address(String zipCode){
        this.zipCode = zipCode;
    }

    @Override
    public String toString(){
        return zipCode;
    }

    /**
     * Method that set zip code
     * @param zipCode
     */
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }

    /**
     * Method that get zip code
     * @return String zipCode
     */
    public String getZipCode(){
        return zipCode;
    }



}
