package edu.neu.angelhack.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import edu.neu.angelhack.entity.User;


public class UserDAO {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("AngelHack");
	EntityManager em = null;
	
	public User userLogin(String userName , String password){

		em = factory.createEntityManager();

		User u = null;	

		try{
			u = (User) em.createQuery(
					"SELECT p FROM User p WHERE p.userName = :name")
					.setParameter("name", userName)
					.getSingleResult();

			if(u.getPassWord().equals(password)){
				return u;
			}
			else{
				return null;
			}

		}

		catch(NoResultException e){
			return null;
		}
		finally{
			em.close();

		}
	}
	
	//inserts an address to the database
		public void insertUser(User user)
		{
			em = factory.createEntityManager();
			em.getTransaction().begin();
			
			em.persist(user);
			
			em.getTransaction().commit();
			em.close();
		}
}
