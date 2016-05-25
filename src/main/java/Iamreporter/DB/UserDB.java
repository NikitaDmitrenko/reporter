package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by Nikita on 21.05.2016.
 */
public class UserDB {

    public void saveUser(User user){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public User getUserByUUID(String userUUID){
        Transaction transaction = null;
        User user = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = (User)session.createQuery("from User where userUUID = :userUUID").setParameter("userUUID",userUUID).uniqueResult();
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    public void updateUser(User user){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public User getUserByEmail(String email){
        Transaction transaction = null;
        User user = null;

        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = (User)session.createQuery("from User where email = :email").setParameter("email",email).uniqueResult();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return user;
    }
}
