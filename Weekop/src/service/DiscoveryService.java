package service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dao.DAOFactory;
import dao.DiscoveryDAO;
import model.Discovery;
import model.User;

public class DiscoveryService {
    public void addDiscovery(String name,String desc,String url,User user) {
        Discovery discovery = createDiscoveryObject(name, desc,url,user);
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDao = factory.getDiscoveryDAO();
        discoveryDao.create(discovery);
    }

    private Discovery createDiscoveryObject (String name, String desc, String url, User user) {
        Discovery resultDiscovery = new Discovery();
        resultDiscovery.setName(name);
        resultDiscovery.setDescription(desc);
        resultDiscovery.setUrl(url);
        User userCopy = new User(user);
        resultDiscovery.setUser(userCopy);
        resultDiscovery.setTimestamp(new Timestamp(new Date().getTime()));
        resultDiscovery.setNumberOfComments(0);
        return resultDiscovery;
    }

    public Discovery getDiscoveryById(long discoveryId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDao = factory.getDiscoveryDAO();
        Discovery discovery = discoveryDao.read(discoveryId);
        return discovery;
    }

    public boolean updateDiscovery (Discovery discovery) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDao = factory.getDiscoveryDAO();
        boolean result = discoveryDao.update(discovery);
        return result;
    }

    public List<Discovery> getAllDiscoveries(){
        return getAllDiscoveries(null);
    }

    public List <Discovery> getAllDiscoveries(Comparator <Discovery> comparator){
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDao = factory.getDiscoveryDAO();
        List<Discovery> discoveries = discoveryDao.getAll();
        if (comparator!=null && discoveries!=null) {
            discoveries.sort(comparator);
        }

        return discoveries;
    }

    public List <Discovery> getAllUserDiscoveries(long userId, Comparator <Discovery> comparator){
        DAOFactory factory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDao = factory.getDiscoveryDAO();
        List<Discovery> discoveries = discoveryDao.getAllUserDiscoveries(userId);
        if (comparator!=null && discoveries!=null) {
            discoveries.sort(comparator);
        }

        return discoveries;
    }

    public boolean deleteDiscovery (long discoveryId){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDAO = daoFactory.getDiscoveryDAO();
        return discoveryDAO.delete(discoveryId);
    }

    public boolean deleteDiscoveryByUserId (long userId){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        DiscoveryDAO discoveryDAO = daoFactory.getDiscoveryDAO();
        return discoveryDAO.deleteDiscoveryByUserId(userId);
    }



}
