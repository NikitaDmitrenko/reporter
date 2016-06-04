package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.FriendRelation;
import Iamreporter.Model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FriendRelationDB {


    public FriendRelation getFriendRelation(String userWhoAddUUID,String userWhomAddUUID){
        Transaction transaction = null;
        FriendRelation friendRelation = null;
        System.out.println();
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            friendRelation = (FriendRelation) session.createQuery("from FriendRelation " +
                    "where userWhoAddUUID =:userWhoAddUUID" +
                    " and" +
                    " userWhomAddUUID =:userWhomAddUUID").
                    setParameter("userWhoAddUUID",userWhoAddUUID).setParameter("userWhomAddUUID",userWhomAddUUID).uniqueResult();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return friendRelation;
    }

    public void saveFriendRelation(FriendRelation friendRelation){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(friendRelation);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(FriendRelation friendRelation){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.update(friendRelation);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}
