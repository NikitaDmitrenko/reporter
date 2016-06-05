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
                session.getTransaction().commit();
            }catch (RuntimeException e){
                if(transaction!=null){
                    transaction.rollback();
                }
                e.printStackTrace();
            }
    }
}
