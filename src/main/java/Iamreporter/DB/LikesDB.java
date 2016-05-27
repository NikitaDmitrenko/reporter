package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
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
}
