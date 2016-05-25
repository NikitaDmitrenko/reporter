package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.Comment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Nikita on 23.05.2016.
 */
public class CommentDB {

    public List<Comment> getCOmmentByNewsUUID(String uuid){
        Transaction transaction = null;
        List<Comment> comments = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            comments = (List<Comment>)session.createQuery("from Comment where userNewsUUID =:userNewsUUID order by id desc").setParameter("userNewsUUID",uuid).list();
            session.getTransaction();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return comments;
    }
}
