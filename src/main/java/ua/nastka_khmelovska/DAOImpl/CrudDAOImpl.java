package ua.nastka_khmelovska.DAOImpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ua.nastka_khmelovska.Main;
import ua.nastka_khmelovska.DAO.CrudDAO;

public class CrudDAOImpl implements CrudDAO {

	private SessionFactory sessionFactory = Main.databaseUtil.getSessionFactory();
	
	public void addObj(Class<?> objClazz, Object obj) throws ClassCastException {
		Session session = sessionFactory.getCurrentSession();
		session.save(objClazz.cast(obj));
	}

	
	public List<?> getAllObjs(String table) throws ClassCastException {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from ".concat(table)).list();
	}

	
	public Object getObjById(Class<?> objClazz, Class<?> idClazz, Object id) throws ClassCastException {
		Session session = sessionFactory.getCurrentSession();
		return session.get(objClazz, (Serializable) idClazz.cast(id));
	}

	
	public void updateObj(Class<?> objClazz, Object obj) throws ClassCastException {
		Session session = sessionFactory.getCurrentSession();
		session.update(objClazz.cast(obj));
	}

	
	public void removeObj(Class<?> objClazz, Object obj) throws ClassCastException {
		Session session = sessionFactory.getCurrentSession();
		session.delete(objClazz.cast(obj));
	}

	
	public void removeObjById(Class<?> objClazz, Class<?> idClazz, Object id) throws ClassCastException {
		Session session = sessionFactory.getCurrentSession();
		Object obj = session.get(objClazz, (Serializable) idClazz.cast(id));
		if (obj != null) {
			session.delete(objClazz.cast(obj));
		}
	}
	
}
