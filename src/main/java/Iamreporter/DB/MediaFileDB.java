package Iamreporter.DB;

import Iamreporter.Hibernate.HibernateUtil;
import Iamreporter.Model.MediaFile;
import Iamreporter.Model.UserNews;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MediaFileDB {

    public void saveMediaFile(MediaFile mediaFile){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(mediaFile);
            transaction.commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteMediaFiles(UserNews userNews){
        Transaction transaction = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.createQuery("delete from MediaFile where newsUUID = :newsUUID").setParameter("newsUUID",userNews.getUuid()).executeUpdate();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<MediaFile> getNewsPhotos(String userNewsUUID){
        Transaction transaction = null;
        List<MediaFile> mediaFiles  = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            mediaFiles = (List<MediaFile>) session.createQuery("from MediaFile " +
                    "where newsUUID =:userNewsUUID ").setParameter("userNewsUUID",userNewsUUID).list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return mediaFiles;
    }

    public List<MediaFile> getNewsMediaFiles(String userNewsUUID){
        Transaction transaction = null;
        List<MediaFile> mediaFile = null;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            mediaFile = (List<MediaFile>) session.createQuery("from MediaFile " +
                    "where newsUUID =:userNewsUUID").setParameter("userNewsUUID",userNewsUUID).list();
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if(transaction!=null){
                transaction.rollback();
            }
        }
        return mediaFile;
    }

}
