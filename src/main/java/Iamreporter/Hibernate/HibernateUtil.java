package Iamreporter.Hibernate;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final Logger logger = Logger.getLogger(HibernateUtil.class);

    static{
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex){
            logger.fatal("Some problem with inializing sessionFactory Hibernate");
            System.err.println("Intial SessionFactory creation failed "+ex);
            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
