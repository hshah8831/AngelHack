package edu.neu.angelhack.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.neu.angelhack.entity.Tran;

public class TranDAO {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("AngelHack");
	EntityManager em = null;

	// inserts an address to the database
	public void insertTran(Tran tran) {
		em = factory.createEntityManager();
		em.getTransaction().begin();

		em.persist(tran);

		em.getTransaction().commit();
		em.close();
	}
}
