package app;

import java.io.Serializable;

/**
 * Class that represents Coordinates objects
 */
public class Coordinates implements Serializable {
    private float x; //Значение поля должно быть больше -164
    private Integer y; //Поле не может быть null

    /**
     * Constructor that creates Coordinates objects
     * @param x
     * @param y
     */
    public Coordinates(float x, Integer y){
        this.x = x;
        this.y = y;
    }

    /**
     * Method that sets coordinate x
     * @param x
     */
    public void setX(float x){
        this.x = x;
    }

    /**
     * Method that sets coordinate y
     * @param y
     */
    public void setY(Integer y){
        this.y = y;
    }

    /**
     * Method that gets coordinate x
     * @return float x
     */
    public float getX(){
        return x;
    }

    /**
     * Method that gets coordinate y
     * @return Integer y
     */
    public Integer getY(){
        return y;
    }

    @Override
    public String toString(){
        return "collection.Coordinates{" + "x=" + x + " y=" + y + "}";
    }
}
