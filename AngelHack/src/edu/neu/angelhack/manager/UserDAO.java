package edu.neu.angelhack.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
//			em = factory.createEntityManager();
//			em.getTransaction().begin();
//			
//			em.persist(user);
//			
//			em.getTransaction().commit();
//			em.close();
			
			em = factory.createEntityManager();
			em.getTransaction().begin();

			Query query = em.createNativeQuery("INSERT INTO User (userName,passWord) " +
					" VALUES(?,?)");
			query.setParameter(1, user.getUserName());
			query.setParameter(2, user.getPassWord());
			query.executeUpdate(); 

			em.getTransaction().commit();
			em.close();



			System.out.println("User inserted");
		}
		
		
		//Get student details for student landing page
		public User getUser(String user){

			em = factory.createEntityManager();
			return em.find(User.class, user);
		}
		
		public Integer getLatestTransaction(String userName){
			em = factory.createEntityManager();

			Integer t = null;	

			try{
				t = (Integer) em.createQuery(
						"SELECT max(u.trans.tranId) FROM User u WHERE u.userName = :name")
						.setParameter("name", userName)
						.getSingleResult();

				return t;

			}

			catch(NoResultException e){
				return null;
			}
			finally{
				em.close();

			}
			
		}
}
