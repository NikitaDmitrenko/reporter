package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.User;
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

    public List<User> getMySubscribers(String userWhomAddUUID){
        Transaction transaction = null;
        List<User> users = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            users = (List<User>)session.createQuery("from User" +
                    " where privateUUID in " +
                    "(select userWhoAddUUID from FriendRelation" +
                    " where userWhomAddUUID =:userWhomAddUUID" +
                    " and " +
                    "status =:status)").setParameter("userWhomAddUUID",userWhomAddUUID).setParameter("status","subscribe").list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getAllReporters(String userUUID){
        Transaction transaction = null;
        List<User> users = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            users = (List<User>)session.createQuery("from User where privateUUID !=:privateUUID ").setParameter("privateUUID",userUUID).list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getMyReaders(String userWhomAddUUID){
        Transaction transaction = null;
        List<User> users = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            users = (List<User>)session.createQuery("from User" +
                    " where privateUUID in " +
                    "(select userWhoAddUUID from FriendRelation" +
                    " where userWhomAddUUID =:userWhomAddUUID" +
                    " and " +
                    "status =:status)").setParameter("userWhomAddUUID",userWhomAddUUID).setParameter("status","subscribe").list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    public User getUserByPrivateUUID(String privateUUID){
        Transaction transaction = null;
        User user = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = (User)session.createQuery("from User where privateUUID = :privateUUID").setParameter("privateUUID", privateUUID).uniqueResult();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByPublicUUID(String publicUUID){
        Transaction transaction = null;
        User user = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = (User)session.createQuery("from User where publicUUID=:publicUUID").setParameter("publicUUID", publicUUID).uniqueResult();
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
            user = (User)session.createQuery("from User where email = :email and password = :password").setParameter("password", password).setParameter("email", email).uniqueResult();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return user;
    }


    public User getUserByFacebookId(String facebookid){
        Transaction transaction = null;
        User user = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = (User)session.createQuery("from User where facebookId =:facebookid ").setParameter("facebookid",facebookid).uniqueResult();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return user;
    }

    public User getUserByVkId(String vkId){
        Transaction transaction = null;
        User user = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = (User)session.createQuery("from User where vkId =:vkid ").setParameter("vkid",vkId).uniqueResult();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return user;
    }

    public User getUserByTwitterId(String twitterId){
        Transaction transaction = null;
        User user = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            user = (User)session.createQuery("from User where twitterId =:twitterId ").setParameter("twitterId",twitterId).uniqueResult();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return user;
    }
}
