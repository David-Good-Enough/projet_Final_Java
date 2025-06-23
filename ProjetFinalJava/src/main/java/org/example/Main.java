package fr.ynov.collection;

import fr.ynov.collection.model.Support;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Support pc = new Support("PC");
        session.save(pc);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
        System.out.println("BDD ok !");
    }
}
