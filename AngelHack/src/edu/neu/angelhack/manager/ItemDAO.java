package edu.neu.angelhack.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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

	   @SuppressWarnings("unchecked")
	public List<Item> getAllItemsByCategory()
	   {
	      em = factory.createEntityManager();
	      //em.getTransaction().begin();
	      List<Item> RlItem;
	    
	      try
	      {
	         RlItem = em.createQuery("SELECT category,sum(itemAmount) FROM Item " +
	                                    "group by Category").getResultList();
	            
	         return RlItem;
	      }

	      catch (NoResultException ex)
	      {
	         return null;
	      }


	   }

	    @SuppressWarnings("unchecked")
		public List<Item> fetchAllItemsfromID(String traID)
	    {
	        em = factory.createEntityManager();
	        //em.getTransaction().begin();
	        List<Item> lItem;

	        try
	        {
	            lItem =  em.createQuery("SELECT * FROM Item where tranId = :traID")
	                    .setParameter("traID", traID).getResultList();
	            ;
	            return lItem;
	        }

	        catch (NoResultException ex)
	        {
	            return null;
	        }


	    }
}
