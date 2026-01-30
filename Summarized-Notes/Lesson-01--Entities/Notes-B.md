This video, "JPA/Hibernate Fundamentals 2023 - Lesson 1 - Entities," provides an introductory overview of JPA, ORM, and Hibernate, emphasizing the fundamental concepts crucial for Java developers. The instructor, Laur Spilca, aims to build a strong foundation for understanding how to implement the persistence layer of a Java application using these technologies.

Here are the key takeaways from the video, structured as study notes:

## 1. Understanding Core Concepts (2:17-8:45)

- JPA (Jakarta Persistence API): (2:56)
	- A specification, not an implementation.
	- Defines how an ORM framework should be implemented (3:14).
	- Previously known as Java Persistence API (3:05).
	- Includes standard contracts, classes (like EntityManager, EntityManagerFactory), and annotations (46:06).

- ORM (Object-Relational Mapping): (4:46)
	- A framework philosophy that bridges object-oriented programming (OOP) and relational databases.
	- Allows developers to work with database entities as objects in their Java application, abstracting database complexities (4:09, 47:15).
	- Applies only to relational databases (5:08).

- Hibernate: (6:59)
	- An implementation of the JPA specification (7:26).
	- A widely used framework in the Java ecosystem, often used with Spring (e.g., Spring Data JPA) (17:09-17:51).

---

## 2. Key Prerequisite: JDBC and SQL (1:18)

To effectively learn JPA/Hibernate, a solid understanding of SQL and Java fundamentals, especially JDBC (Java Database Connectivity), is essential (1:31-1:36).
JDBC is the "vanilla" way to connect to a database and send SQL queries (9:25-9:51).

---

## 3. The ORM Mental Model: Operations on Context (11:13-15:01)

- Crucial Shift from JDBC:
	- Unlike JDBC where queries are directly sent to the DBMS, in ORM (and JPA implementations like Hibernate), operations are not always executed immediately on the database (11:16-11:23).

- The "Context":
	- Operations (like persist) are executed within a context (12:24).
	- This context holds instances of objects (called entities) that the framework controls (12:47-13:07).

- Deferred Execution:
	- The ORM framework decides when and which operations are translated into actual database queries (13:08-13:34).
	- Example: Persisting an instance and then immediately removing it from the context might result in no database query being sent (13:37-14:00, 42:23-42:36).
	- Queries are sent to the database (mirrored) typically at the end of a transaction when changes are committed (14:07, 43:09).

- Performance Implications:
	- Misusing the framework (e.g., not understanding query generation) can lead to performance degradation rather than enhancement (14:43-15:01, 18:41-19:15).

- Not a Silver Bullet:
	- Hibernate (and JPA) is excellent for specific use cases but doesn't replace JDBC or other solutions for all scenarios (15:37-16:00).

---

## 4. Defining Entities (37:27-41:10)

- **Entities:** Represent database tables and their attributes in your Java application (37:39, 47:25).
- **Not all tables are entities:** Join tables (for many-to-many relationships) are typically not considered entities (37:57-38:22).
- **Annotation-driven:** Use the @Entity annotation (from jakarta.persistence) to mark a class as an entity (39:13-39:21).
- **Primary Key:** The @Id annotation is mandatory to mark the primary key field of an entity (40:31-40:37).

---

## 5. Configuring Hibernate and Creating EntityManagerFactory

The EntityManagerFactory is a factory pattern designed to create EntityManager instances, which in turn manage the "context" of entities (35:22-35:55). There are two primary ways to configure Hibernate and obtain an EntityManagerFactory:

### A. Using persistence.xml (Classic Approach) (29:29-37:20)

- **File Location:** META-INF/persistence.xml in your project's resources folder (29:55-30:01).
- **Key Elements:**
	- `persistence-unit`: Has a unique name (e.g., "persistence-unit-name") and specifies transaction-type (e.g., RESOURCE_LOCAL for independent applications or JTA for application servers) (31:33-32:17).
	- `provider`: Specifies the main class of the JPA implementation (e.g., org.hibernate.jpa.HibernatePersistenceProvider for Hibernate) (32:24-33:18).
	- `properties`: Contains database connection details like driver, url, username, and password (33:20-34:06).
- **Obtaining EntityManagerFactory:** Use Persistence.createEntityManagerFactory("your-unit-name") (35:50-36:09).

### B. Programmatic Approach (Without XML) (44:17-56:03)

- **Custom PersistenceUnitInfo:** Create a custom class (e.g., CustomPersistenceUnitInfo) that implements the PersistenceUnitInfo interface (48:50-49:46).
- **Method Implementations:** Override various methods to provide the same configuration details (name, provider, transaction type, data source, managed classes) that would otherwise be in the XML file (49:50-55:01).
- **Data Source:** You'll typically need a connection pool (like HikariCP – a widely used and performant choice) (51:30-52:28).
- **Obtaining EntityManagerFactory:** Use HibernatePersistenceProvider().createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), null) (55:34-55:58).
- **Security Note:** Database credentials should never be hard-coded; store them securely (e.g., in vaults or environment variables) (54:00-54:09).

---

## 6. Performing Operations (41:13-43:54)

- **EntityManager.persist():** Used to add a new entity instance to the context. This is not an immediate SQL INSERT (41:33-42:07).

### Example Workflow:

1. Create an EntityManagerFactory.
1. Create an EntityManager from the factory.
1. Begin a transaction (em.getTransaction().begin()).
1. Create an entity instance (e.g., Product p = new Product();).
1. Set properties of the entity (e.g., p.setId(1L); p.setName("Beer");).
1. Persist the entity to the context (em.persist(p)).
1. Commit the transaction (em.getTransaction().commit()). This is when changes are synchronized with the database.

