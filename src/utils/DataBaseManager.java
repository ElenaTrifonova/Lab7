package utils;

import app.Address;
import app.Coordinates;
import app.Organization;
import app.OrganizationType;
import client.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.ConcurrentHashMap;


public class DataBaseManager {
    //For Database
    //private static final String DB_URL = "jdbc:postgresql://pg:5432/studs";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Collection";
    private static String USER;
    private static String PASS;
    private static final String FILE_WITH_ACCOUNT = "account";
    private static final String TABLE_NAME = "Organizations";
    private static final String USERS_TABLE = "users";
    private static final String pepper = "1@#$&^%$)3";

    //читаем данные аккаунта для входа подключения к бд, ищем драйвер
    static {
        try (FileReader fileReader = new FileReader(FILE_WITH_ACCOUNT);
             BufferedReader reader = new BufferedReader(fileReader)) {
            USER = reader.readLine();
            PASS = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }



        System.out.println("Connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver successfully connected");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path");
            e.printStackTrace();
        }
    }

    private Connection connection;
    private PassEncoder passEncoder;

    public DataBaseManager(String dbUrl, String user, String pass) {
        try {
            connection = DriverManager.getConnection(dbUrl, user, pass);
            passEncoder = new PassEncoder(pepper);
        } catch (SQLException e) {
            System.out.println("Connection to database failed");
            e.printStackTrace();
        }
    }


    public DataBaseManager() {
        this(DB_URL, USER, PASS);
    }



    //загрузка коллекции в память
    public ConcurrentHashMap<Long, Organization> getCollectionFromDatabase() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from " + TABLE_NAME);
        ResultSet resultSet = preparedStatement.executeQuery();
        ConcurrentHashMap<Long, Organization> collection = new ConcurrentHashMap<>();
        while (resultSet.next()) {
            Long key = resultSet.getLong("key");
            Coordinates coordinates = new Coordinates(resultSet.getFloat("coordinate_x"), resultSet.getInt("coordinate_y"));
            Double annualTurnover = resultSet.getDouble("annual_turnover") == 0 ? null : resultSet.getDouble("annual_turnover");
            long employeesCount = resultSet.getLong("employees_count");
            Date date = resultSet.getDate("creation_date");
            LocalDate creationDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Address postalAddress = new Address(resultSet.getString("postal_address"));

            Organization organization = new Organization(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    coordinates,
                    creationDate,
                    annualTurnover,
                    resultSet.getString("full_name"),
                    employeesCount,
                    OrganizationType.valueOf(resultSet.getString("organization_type")),
                    postalAddress,
                    resultSet.getString("owner")
            );
            collection.put(key, organization);
        }
        return collection;
    }

    //добаление нового элемента
    public boolean addGroup(Organization organization) {
        try {
            long id = generate_id();
            Coordinates coordinates = organization.getCoordinates();
            PreparedStatement statement = connection.prepareStatement("insert into " + TABLE_NAME + " values (?, ?, ?, ?, ?, ?,?,? cast (? as organization_type), ?)");
            statement.setLong(1, id);
            statement.setString(2, organization.getName());
            statement.setFloat(3, coordinates.getX());
            statement.setLong(4, coordinates.getY());
            statement.setObject(5, organization.getCreationDate());
            statement.setDouble(6, organization.getAnnualTurnover());
            statement.setString(7, organization.getFullName());
            statement.setLong(8, organization.getEmployeesCount());
            statement.setObject(9, organization.getType());
            statement.setObject(10, organization.getPostalAddress());
            organization.setId(id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //удаление элемента по id
    public int remove(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from " + TABLE_NAME + " where id=?");
            statement.setLong(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //добавление нового пользователя
    public void addUser(User user) {
        String salt = new SimplePasswordGenerator(true, true, true, true).generate(10, 10);
        String hash = passEncoder.getHashMD(user.getPass() + salt);
        /*try {
            PreparedStatement statement = connection.prepareStatement("insert into " + USERS_TABLE + " values (?, ?, ?)");
            statement.setString(1, user.getName());
            statement.setString(2, hash);
            statement.setString(3, salt);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

         */
        try {
            String query = "CREATE ROLE " + user.getName() + " WITH\n" +
                    "  LOGIN\n" +
                    "  SUPERUSER\n" +
                    "  INHERIT\n" +
                    "  NOCREATEDB\n" +
                    "  NOCREATEROLE\n" +
                    "  NOREPLICATION\n" +
                    "  ENCRYPTED PASSWORD '" + hash + "';\n" +
                    "GRANT users TO " + user.getName() + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //ищем пользователя
    public boolean containsUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from " + USERS_TABLE + " where name = ?");
            statement.setString(1, user.getName());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return false;
            String salt = resultSet.getString("salt");
            String hash = passEncoder.getHashMD(user.getPass() + salt);
            statement = connection.prepareStatement("select * from " + USERS_TABLE + " where name = ? and password = ? and salt=?");
            statement.setString(1, user.getName());
            statement.setString(2, hash);
            statement.setString(3, salt);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //ищем пользователя только по имени
    public boolean containsUserName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from " + TABLE_NAME + " where owner = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //генерируем id с помощью sequence
    public long generate_id() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select nextval('randomid')");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getLong("id");
    }

    //удаляем все элементы, принадлежащие пользователю
    public boolean removeAll(String userName) {
        try {
            PreparedStatement statement = connection.prepareStatement("select from " + TABLE_NAME + " where owner=?");
            statement.setString(1, userName);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //обновляем поля элемента
    public int update(long id, Organization org) {
        Coordinates coordinates = org.getCoordinates();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "update " + TABLE_NAME + " set name=?, coordinate_x=? , coordinate_y=?, creation_date=?, annual_turnover=?, full_name=?, employees_count=?, organization_type=cast (? as organization_type), postal_address=? where id = ?");
            statement.setLong(15, id);
            statement.setString(1, org.getName());
            statement.setFloat(2, coordinates.getX());
            statement.setLong(3, coordinates.getY());
            statement.setObject(4, org.getCreationDate());
            statement.setDouble(5, org.getAnnualTurnover());
            statement.setString(7, org.getFullName());
            statement.setLong(8, org.getEmployeesCount());
            statement.setObject(9, org.getType());
            statement.setObject(10, org.getPostalAddress());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
