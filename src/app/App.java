package app;

import client.User;
import exceptions.InputIntervalException;
import exceptions.SameIdException;
import server.Session;
import utils.DataBaseManager;
import utils.Validation;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Class application for checking validation of information
 * and sorting for receiving to client
 * and keeping the collection
 */
public class App {

    private ConcurrentHashMap<Long, Organization> collection;
    private CopyOnWriteArraySet<Long> idList;
    private ConcurrentHashMap<User, Session> activeSessions = new ConcurrentHashMap<>();
    private DataBaseManager dataBaseManager;


    public App() throws SQLException {

        dataBaseManager = new DataBaseManager();
        collection = dataBaseManager.getCollectionFromDatabase();

        if (collection != null) {
            Set<Long> set = collection.keySet();
            for (Long key : set) {
                try {
                    Organization org = collection.get(key);
                    //проверка валидности данных из файла
                    if (!Validation.checkInterval((long) org.getName().length(), 3, false, "Element has invalid name, id: " + org.getId())
                            || !Validation.checkIntervalDouble(org.getAnnualTurnover(), 0, true, "Element has invalid annual turnover, id: " + org.getId())
                            || !Validation.checkInterval(org.getEmployeesCount(), 0, false, "Element has invalid employees count, id: " + org.getId())
                            || !Validation.checkInterval((long) org.getPostalAddress().getZipCode().length(), 3, false, "Element has invalid postal address, id: " + org.getId())
                            || !Validation.checkInterval(org.getId(), 0, false, "Element has  invalid id, name of element: " + org.getName())
                            || !Validation.checkMax((long) org.getFullName().length(), 1273, true, "")
                            || org.getType() == null
                            || !Validation.checkInterval((long) org.getCoordinates().getX(), -164, false, "Element has invalid coordinate X, id: " + org.getId()))
                        throw new InputIntervalException();
                    if (!idList.add(org.getId()))
                        throw new SameIdException("There are several elements with same id in collection, only one will be downloaded!!!");
                    this.collection.put(key, org);
                } catch (InputIntervalException e) {
                    System.out.println("Error in database, collection will not be downloaded!!!");
                } catch (SameIdException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }


    public  ConcurrentHashMap<Long, Organization> sort( ConcurrentHashMap<Long, Organization> collection){
        Set<Long> set = collection.keySet();
        ArrayList<Float> sorted_x = new ArrayList<>();
        Hashtable<Float, Long> coord_key = new Hashtable<>();
        for (Long key : set) {

            sorted_x.add(collection.get(key).getCoordinates().getX());
            coord_key.put(collection.get(key).getCoordinates().getX(), key);
        }
            Collections.sort(sorted_x);
            Iterator<Float> iterator = sorted_x.iterator();

        ConcurrentHashMap<Long, Organization> collection_new = new ConcurrentHashMap<>();

            while (iterator.hasNext()) {
                Float element = iterator.next();
                collection_new.put(coord_key.get(element), collection.get(coord_key.get(element)));

            }

        return collection_new;
        }

    public CopyOnWriteArraySet<Long> getIdList() {
        return idList;
    }

    public void setIdList(CopyOnWriteArraySet<Long> idList) {
        this.idList = idList;
    }

    public void setCollection(ConcurrentHashMap<Long, Organization> collection){
        this.collection = collection;
    }

    public ConcurrentHashMap<Long, Organization> getCollection(){
        return this.collection;
    }

    public long getSumOfEmployeesCount() {
        return collection.values().stream().mapToLong(Organization::getEmployeesCount).sum();
    }

    public DataBaseManager getDataBaseManager(){
        return dataBaseManager;
    }

    /**
     * возвращает сессию из активных по пользователю
     * @param user - текущий пользователь
     * @return сессия
     */
    public Session getSession(User user) {
        if (user == null) return null;
        return activeSessions.get(user);
    }

    public boolean containsUserName(String name) {
        return activeSessions.keySet().stream().anyMatch(user -> user.getName().equals(name));
    }

    //добавляем новую сессию, с уникальным ключом!!!
    public boolean addSession(User user, Session session) {
        if (activeSessions.containsKey(user)) return false;
        activeSessions.put(user, session);
        return true;
    }

    //удаление сессии
    public void removeSession(User user, Session session) {
        activeSessions.remove(user, session);
    }
}




