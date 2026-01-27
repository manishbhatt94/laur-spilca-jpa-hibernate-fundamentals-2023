package org.example;

import java.util.HashMap;

import org.example.entities.Product;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {

	public static void main(String[] args) {

		// ------ USING persistence.xml -------------
		// value of name attribute of <persistence-unit/> element in persistence.xml
		// file
		// EntityManagerFactory emf =
		// Persistence.createEntityManagerFactory("my-persistence-unit");

		// ------ USING Programmatic Approach -------------
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap<>());

		EntityManager em = emf.createEntityManager(); // represents the context

		try {
			em.getTransaction().begin();

			Product product = new Product();
			product.setId(2L);
			product.setName("Butter Croissant");

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
