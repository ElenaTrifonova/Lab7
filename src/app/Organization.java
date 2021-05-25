package app;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class that represents Organization objects
 */
public class Organization implements Comparable<Organization>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private String fullName; //Значение этого поля должно быть уникальным, Длина строки не должна быть больше 1273, Поле может быть null
    private long employeesCount; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address postalAddress; //Поле не может быть null
    private String owner;

    /**
     * Constructor that createsOrganization objects
     * @param id - id of organization
     * @param name - name of organization
     * @param coordinates - coordinates of organization
     * @param creationDate
     * @param annualTurnover
     * @param fullName
     * @param employeesCount
     * @param type
     * @param postalAddress
     */
    public Organization(Long id, String name, Coordinates coordinates, LocalDate creationDate, Double annualTurnover, String fullName, long employeesCount, OrganizationType type, Address postalAddress, String owner){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.postalAddress = postalAddress;
        this.type = type;
        this.owner = owner;
    }
    public Organization(){

    }

    /**
     * Method that sets id of organization
     * @param id
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * Method  sets name of organization
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Method  sets coordinates of organization
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    /**
     * Method  sets date of creation this organization
     * @param creationDate
     */
    public void setCreationDate(LocalDate creationDate){
        this.creationDate = creationDate;
    }

    /**
     * Method sets annual turnover of organization
     * @param annualTurnover
     */
    public void setAnnualTurnover(Double annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    /**
     * Method sets employees count of organization
     * @param employeesCount
     */
    public void setEmployeesCount(long employeesCount) {
        this.employeesCount = employeesCount;
    }

    /**
     * Method sets full name of organization
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Method sets postal address of organization
     * @param postalAddress
     */
    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     * Method sets type of organization
     * @param type
     */
    public void setType(OrganizationType type) {
        this.type = type;
    }

    /**
     * Method gets id of organization
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * Method gets name of organization
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Method gets coordinates of organization
     * @return Coordinates coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Method gets date of creation this organization
     * @return LocalDateTime creationDate
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Method gets annual turnover of organization
     * @return Double annualTurnover
     */
    public Double getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * Method gets employees count of organization
     * @return long employees count
     */
    public long getEmployeesCount() {
        return employeesCount;
    }

    /**
     * Method gets full name of organization
     * @return String fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * method gets type of organization
     * @return OrganizationType type
     */
    public OrganizationType getType() {
        return type;
    }

    /**
     * method gets postal address of organization
     * @return Address postalAdress
     */
    public Address getPostalAddress() {
        return postalAddress;
    }

    public String getOwner(){return owner;}

    /**
     * method implements Comparable<Organization>
     * and compares two classes
     * @param org - organization
     * @return int
     */
    public int compareTo(Organization org){
        return name.substring(0, 1).compareTo(org.getName().substring(0, 1));
    }

    @Override
    public String toString(){
        return "Organization: " + "/n" +
                "id: " + id +
                ", name: " + name +
                ", coordinates: " + coordinates +
                ", creationDate: " + creationDate +
                ", annualTurnover: " + annualTurnover +
                ", employeesCount: " + employeesCount +
                ", fullName: " + fullName +
                ", type: " + type +
                ", postalAddress: " + postalAddress;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}