package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.UserNews;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

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

    public void deleteNews(UserNews userNews){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.delete(userNews);
            session.getTransaction().commit();
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


    public List<UserNews> getUserNews(String userUUID){
        Transaction transaction = null;
        List<UserNews> userNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            userNews = (List<UserNews>)session.createQuery("from UserNews where authorUUID =:userUUID order by date desc ").setParameter("userUUID",userUUID).list();
            session.beginTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return userNews;
    }


    public List<UserNews> getTopNews(){
        Transaction transaction = null;
        List<UserNews> topNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            topNews = (List<UserNews>)session.createQuery("from UserNews order by countViews desc").list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                e.printStackTrace();
            }

        }
        return topNews;
    }

    public void update(UserNews userNews){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.update(userNews);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public int getUserNewsCount(String authorUUID){
        int count = 0;
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            count = ((Long)session.createQuery("select count(*) from UserNews where authorUUID =:authorUUID").setParameter("authorUUID",authorUUID).uniqueResult()).intValue();
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return count;
    }

    public int getUserNewsViewsCount(String authorUUID){
        int count = 0;
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            count = ((Long)session.createQuery("select sum(countViews) from UserNews where authorUUID =:authorUUID").setParameter("authorUUID",authorUUID).uniqueResult()).intValue();
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return count;
    }


    public List<UserNews> getSportNews(){
        Transaction transaction = null;
        List<UserNews> sportNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            sportNews = (List<UserNews>)session.createQuery("from UserNews where category =:category").setParameter("category",104).list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sportNews;
    }

    public List<UserNews> getPoliticNews(){
        Transaction transaction = null;
        List<UserNews> sportNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            sportNews = (List<UserNews>)session.createQuery("from UserNews where category =:category").setParameter("category",101).list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sportNews;
    }

    public List<UserNews> getLifeNews(){
        Transaction transaction = null;
        List<UserNews> sportNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            sportNews = (List<UserNews>)session.createQuery("from UserNews where category =:category").setParameter("category",102).list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sportNews;
    }

    public List<UserNews> getHumourNews(){
        Transaction transaction = null;
        List<UserNews> sportNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            sportNews = (List<UserNews>)session.createQuery("from UserNews where category =:category").setParameter("category",103).list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sportNews;
    }

    public List<UserNews> getAnotherNews(){
        Transaction transaction = null;
        List<UserNews> sportNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            sportNews = (List<UserNews>)session.createQuery("from UserNews where category =:category").setParameter("category",105).list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return sportNews;
    }

    public List<UserNews> getAllNews(){
        Transaction transaction = null;
        List<UserNews> allNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            allNews = (List<UserNews>)session.createQuery("from UserNews").setMaxResults(500).list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return allNews;
    }



}
