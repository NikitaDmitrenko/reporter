package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.FacebookFriend;
import Iamreporter.Model.TwitterFriend;
import Iamreporter.Model.User;
import Iamreporter.Model.VkontakteFriend;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

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

    public List<User> getUsers(){
        Transaction transaction = null;
        List<User> users = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            users = (List<User>)session.createQuery("from User").setMaxResults(50).list();
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;

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

    public User getUserByNameSurName(String name,String surName){
        Transaction transaction = null;
        User user = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = (User)session.createQuery("from User where name = :name and surName = :surName").setParameter("name", name).setParameter("surName",surName).uniqueResult();
            session.getTransaction().commit();
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

    public User getUserByEmailPassword(String email,String password){
        Transaction transaction = null;
        User user = null;

        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = (User)session.createQuery("from User where email = :email and password = :password").setParameter("password",password).setParameter("email", email).uniqueResult();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return user;
    }

    public void saveFacebookFriend(FacebookFriend friend){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(friend);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void saveVkontakteFriend(VkontakteFriend friend){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(friend);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void saveTwitterFriend(TwitterFriend friend){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(friend);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
