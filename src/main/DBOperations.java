package main;

import com.mongodb.BasicDBObject; 
import com.mongodb.DB; 
import com.mongodb.DBCollection; 
import com.mongodb.DBCursor; 
import com.mongodb.DBObject; 
import com.mongodb.MongoClient; 
 
import java.util.Date;
import java.util.LinkedList;
import java.util.List; 

import org.bson.types.ObjectId;

/**
 * Perform CRUD operations on a MongoDB Server
 * @author Pranav Sharma
 * @version 1.0
 * @since 2015-12-17
*/
public class DBOperations {

	private String _host;
	private int _port;
	private MongoClient _mongoClient;
	private DBCollection _collection;
	private String _collectionName;
	private String _dbName;
	
	/**
	 * Instantiating the Client and Getting the Name of the Collection
	*/
	public DBOperations(String host, int port, String collectionName, String dbName)
	{
		_host = host;
		_port = port;
		_collectionName = collectionName;
		_dbName = dbName;
		
		try {
			_mongoClient = new MongoClient(_host, _port);
			_collection = GetCollection(_collectionName, _dbName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets Name of Collection
	 * If Collection does not exist, then it is created
	*/
	private DBCollection GetCollection(String collectionName, String dbName)
	{
		DB db = _mongoClient.getDB(dbName);
		DBCollection collection = db.getCollection(collectionName);
		return collection;
	}
	
	/**
	 * Gets Name of Collection
	*/
	public DBCollection GetCollection()
	{
		return _collection;
	}
	
	/**
	 * Inserts a Document in the Collection
	*/
	public void InsertDocument(BasicDBObject document)
	{
		_collection.insert(document);
	}
	
	/**
	 * Creates a Document 
	*/
	public BasicDBObject CreateDocument()
	{
		BasicDBObject document = new BasicDBObject();
		document.append("Name", "Pranav Sharma");
		document.append("DateAdded", new Date());
		return document;
	}
	
	/**
	 * Creates a Document given the name and type
	*/
	public BasicDBObject CreateDocument(String name, String type)
	{
		BasicDBObject document = new BasicDBObject();
		document.append("Name", name);
		document.append("Type", type);
		return document;
	}
	
	/**
	 * Creates a Sample Document
	*/
	public BasicDBObject CreateSampleDocument()
	{
		BasicDBObject document = new BasicDBObject();
		document.append("Name", "Pranav Sharma 2.0");
		document.append("DateAdded", new Date());
		return document;
	}
	
	/**
	 * Gets the Number of Items in the Collection
	*/
	public long GetCollectionCount()
	{
		return _collection.count();
	}
	
	/**
	 * Returns the Document given and ID
	*/
	public DBObject GetDocumentByID(String ID)
	{
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(ID));
		DBObject dbObj = _collection.findOne(query);
		return dbObj;
	}
	
	/**
	 * Returns all Documents from the Collection
	*/
	public List<DBObject> GetAllDocuments()
	{
		DBCursor cursor = _collection.find();
		List<DBObject> list = new LinkedList<DBObject>();
		try{
			while(cursor.hasNext()) {
				list.add(cursor.next());
			}
		} finally {
			cursor.close();
		}
		return list;
	}
	
	/**
	 * Replaces the Old Document with the New Document in the Collection
	*/
	public void UpdateDocument(BasicDBObject oldObj, BasicDBObject newObj)
	{
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newObj);
		_collection.update(oldObj, updateObj, false, true);
	}
	
	/**
	 * Deletes the Document with the Specific ID
	*/
	public void DeleteDocument(String ID)
	{
		DBObject object = GetDocumentByID(ID);
		if (!object.equals(null))
		{
			_collection.remove(object);
		}
	}
	
	/**
	 * Deleting all Documents from the Collection
	*/
	public void DeleteAllDocuments()
	{
		if (GetCollectionCount() != 0)
		{
			DBCursor cursor = _collection.find();
			while (cursor.hasNext())
			{
				_collection.remove(cursor.next());
			}
		}
	}
}

