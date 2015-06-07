package edu.neu.angelhack.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.neu.angelhack.entity.Item;

public class ItemDAO {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("AngelHack");
	EntityManager em = null;

	// inserts an address to the database
	public void insertItem(Item item) {
		em = factory.createEntityManager();
		em.getTransaction().begin();

		em.persist(item);

		em.getTransaction().commit();
		em.close();
	}
}
