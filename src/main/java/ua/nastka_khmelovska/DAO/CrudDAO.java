package ua.nastka_khmelovska.DAO;

import java.util.List;

/**
 * CRUD DAO interface for interaction between application and database
 * 
 * @author valentine_linux
 *
 */
public interface CrudDAO {

	/**
	 * Function to add objects to the database
	 * 
	 * @param objClazz
	 *            - class of the object which you want to get
	 * @param obj
	 *            - object, that need to add
	 * @throws ClassCastException
	 *             if incoming object cannot be converted to the type of
	 *             database table
	 */
	public void addObj(Class<?> objClazz, Object obj) throws ClassCastException;

	/**
	 * Function for getting all objects from the database table
	 * 
	 * @param table
	 *            - name of the table that you want to receive from the database
	 * 
	 * @return list of the database objects
	 * @throws ClassCastException
	 *             if the type of the returned list is not equal with type of
	 *             database entity and cannot be given
	 */
	public List<?> getAllObjs(String table) throws ClassCastException;

	/**
	 * Function for getting object by id from the database
	 * 
	 * @param objClazz
	 *            - class of the object which you want to get
	 * @param idClazz
	 *            - class of the id(object) that you want to get
	 * @param id
	 *            - id of the object, which need to get
	 * @return casted object to the requirement type
	 * @throws ClassCastException
	 *             if the ID of the object can not be converted to the necessary
	 *             type or returned from the database object can not be
	 *             converted to the required type
	 */
	public Object getObjById(Class<?> objClazz, Class<?> idClazz, Object id) throws ClassCastException;

	/**
	 * Function for updating object in the database
	 * 
	 * @param objClazz
	 *            - class of the object which you want to get
	 * @param obj
	 *            - object, that need to upgrade
	 * @throws ClassCastException
	 *             if incoming object cannot be converted to the type of
	 *             database table
	 */
	public void updateObj(Class<?> objClazz, Object obj) throws ClassCastException;

	/**
	 * Function for deleting object from the database
	 * 
	 * @param objClazz
	 *            - class of the object which you want to get
	 * @param obj
	 *            - object, that need to delete from the database
	 * @throws ClassCastException
	 *             if incoming object cannot be converted to the type of
	 *             database table
	 */
	public void removeObj(Class<?> objClazz, Object obj) throws ClassCastException;

	/**
	 * Function for deleting object by id from the database
	 * 
	 * @param objClazz
	 *            - class of the object which you want to get
	 * @param idClazz
	 *            - class of the id(object) that you want to get
	 * @param id
	 *            - id of the object, which need to delete
	 * @throws ClassCastException
	 *             if the ID of the object can not be converted to the necessary
	 *             type
	 */
	public void removeObjById(Class<?> objClazz, Class<?> idClazz, Object id) throws ClassCastException;

}
