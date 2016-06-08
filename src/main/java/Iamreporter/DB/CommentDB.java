package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.Comment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CommentDB {

    public List<Comment> getNewsComments(String uuid){
        Transaction transaction = null;
        List<Comment> comments = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            comments = (List<Comment>)session.createQuery("from Comment where newsUUID =:userNewsUUID").setParameter("userNewsUUID",uuid).list();
            session.getTransaction();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return comments;
    }

    public int getNewsCommentsCount(String newsUUID){
        Transaction transaction = null;
        int count = 0;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            count = ((Long)session.createQuery("select count (*) from Comment where newsUUID =:newsUUID").setParameter("newsUUID",newsUUID).uniqueResult()).intValue();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction !=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return count;
    }


    public void saveComment(Comment comment){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(comment);
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
