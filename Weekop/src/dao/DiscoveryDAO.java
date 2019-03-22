package dao;

import java.util.List;

import model.Discovery;

public interface DiscoveryDAO extends GenericDAO<Discovery,Long> {

    List <Discovery> getAll();

    List <Discovery> getAllUserDiscoveries(Long userId);

    boolean deleteDiscoveryByUserId (long userId);
}
