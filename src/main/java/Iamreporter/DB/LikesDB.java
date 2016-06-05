package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.LikeNews;
import Iamreporter.Model.User;
import Iamreporter.Model.UserNews;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class LikesDB {

    public int getNewsLikesCount(String newsUUID){
        Transaction transaction = null;
        int count = 0;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            count = ((Long)session.createQuery("select count (*) from LikeNews where newsUUID =:newsUUID").setParameter("newsUUID",newsUUID).uniqueResult()).intValue();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return count;
    }

    public void saveLike(LikeNews likeNews){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(likeNews);
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(LikeNews likeNews){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.update(likeNews);
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public LikeNews getUserLikeFromUserNews(User user , UserNews userNews){
        Transaction transaction = null;
        LikeNews likeNews = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            likeNews = (LikeNews) session.createQuery("from LikeNews where" +
                    " userUUID =:userUUID " +
                    "and" +
                    " newsUUID =:newsUUID").
                    setParameter("userUUID",user.getPrivateUUID()).
                    setParameter("newsUUID",userNews.getUuid()).
                    uniqueResult();
            session.beginTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return likeNews;
    }
}
