package org.zkoss.bind.examples.spring.order;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("orderDao")
public class OrderDao{
	@PersistenceContext
	private EntityManager em;
//	private JpaTemplate jpaTemplate;
	
	
	@Transactional
	public void newOrder(Order order) {
		em.getTransaction().begin();
		
		em.persist(order);
		em.getTransaction().commit();
	}
	
	public List findAll(){
		return em.createQuery("select O from Order O").getResultList();
	}

	/*
	@Transactional
	public Booking createBooking(Long hotelId, String username) {
		EntityManager em = jpaTemplate.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Hotel hotel = em.find(Hotel.class, 20L);
		User user = findUser("erwin");
		Booking booking = new Booking(hotel, user);
		em.persist(booking);
		tx.commit();
		return booking;
	}

	@Transactional
	public Booking createBooking() {
		EntityManager em = jpaTemplate.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Hotel hotel = em.find(Hotel.class, 20L);
		User user = findUser("erwin");
		Booking booking = new Booking(hotel, user);
		em.persist(booking);
		tx.commit();
		return booking;
	}

	private User findUser(String username) {
		EntityManager em = jpaTemplate.getEntityManagerFactory().createEntityManager();
		return (User) em.createQuery("select u from User u where u.username = :username").setParameter("username",
				username).getSingleResult();
	}

	@Transactional
	public void commitTx() {
		EntityManager em = jpaTemplate.getEntityManagerFactory().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Hotel hotel = em.find(Hotel.class, 20L);
		User user = findUser("erwin");
		System.out.println("--------------commit tx----------------");
//		Booking booking = new Booking(hotel, user);
//		em.persist(booking);
		tx.commit();
	}	
	*/
	
}
