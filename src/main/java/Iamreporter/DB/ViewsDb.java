package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.Views;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ViewsDb {

    public void save(Views views){
            Transaction transaction = null;
            try{
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                transaction = session.beginTransaction();
                session.save(views);
                transaction.commit();
            }catch (RuntimeException e){
                if(transaction!=null){
                    transaction.rollback();
                }
                e.printStackTrace();
            }
    }

    public int getUserNewsCountViews(String uuid){
        Transaction transaction = null;
        int count = 0;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            count = ((Long)session.createQuery("select count (*) from Views where newsUUID =:uuid").setParameter("uuid",uuid).uniqueResult()).intValue();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
               transaction.rollback();
            }
            e.printStackTrace();
        }
        return count;
    }

    public int commentsCount(String uuid){
        Transaction transaction = null;
        int count = 0;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            count = ((Long)session.createQuery("select count (*) from Views where commentUUID =:uuid").setParameter("uuid",uuid).uniqueResult()).intValue();
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
