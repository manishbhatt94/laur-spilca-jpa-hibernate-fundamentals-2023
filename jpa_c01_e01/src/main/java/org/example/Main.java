package org.example;

import org.example.entities.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

	public static void main(String[] args) {

		// value of name attribute of <persistence-unit/> element in persistence.xml
		// file
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");

		EntityManager em = emf.createEntityManager(); // represents the context

		try {
			em.getTransaction().begin();

			Product product = new Product();
			product.setId(1L);
			product.setName("Reynolds Trimax Blue Rollerball Pen");

			// em.persist(product) --> add this to the context
			// NOT AN INSERT QUERY
			em.persist(product);

			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

	}

}
