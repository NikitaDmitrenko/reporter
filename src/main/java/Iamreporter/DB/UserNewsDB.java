package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.User;
import Iamreporter.Model.UserNews;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Nikita on 21.05.2016.
 */
public class UserNewsDB {

    public void saveUserNews(UserNews userNews){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(userNews);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public UserNews getNewsByUUID(String uuid){
        Transaction transaction = null;
        UserNews userNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            userNews = (UserNews)session.createQuery("from UserNews where uuid = :uuid").setParameter("uuid",uuid).uniqueResult();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return userNews;
    }

    public List<String> getUserNewsUUIDS(String authorUUID){
        Transaction transaction = null;
        List<String> userNewsUUIDS = null;

        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            userNewsUUIDS = session.createQuery("select uuid from UserNews where authorUUID =:authorUUID").list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return userNewsUUIDS;
    }

    public int getAuthorViewsCount(List<String> newsUUIDS){
        Transaction transaction = null;
        int count = 0;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            count = ((Long)session.createQuery("select count(*) from Views where newsUUID in :newsUUIDS").setParameter("newsUUIDS",newsUUIDS).uniqueResult()).intValue();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return count;
    }

}
