package main;

import org.junit.Test;
import org.junit.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MyTests 
{
	private static final String _dbName = "test";
	private static final String _collectionName = "entries";
	private static final String _host = "localhost";
	private static final int _port = 27017;
	private DBOperations _dbOperations = new DBOperations(_host, _port, _collectionName, _dbName);
	
	@Test
	public void CreateDocument()
	{
		BasicDBObject obj1 = _dbOperations.CreateDocument();
		BasicDBObject obj2 = _dbOperations.CreateDocument();
		Assert.assertEquals(obj1, obj2);
	}
	
	@Test
	public void InsertDocument()
	{
		_dbOperations.DeleteAllDocuments();
		BasicDBObject obj = new BasicDBObject("key", "value");
		_dbOperations.InsertDocument(obj);
		Assert.assertEquals("value", _dbOperations.GetCollection().findOne().get("key"));
	}
	
	@Test
	public void CollectionCount()
	{
		_dbOperations.DeleteAllDocuments();
		_dbOperations.InsertDocument(_dbOperations.CreateDocument());
		Assert.assertEquals(1, _dbOperations.GetCollectionCount());
	}
	
	@Test
	public void GetDocumentByID()
	{
		//Inserted 2 Objects in Main and then Printed them to get ObjectID's
		DBObject obj1 = _dbOperations.GetDocumentByID("5672e654c633152ea6473b53");
		DBObject obj2 = _dbOperations.GetDocumentByID("5672e907c63323b4fba244b1");
		Assert.assertEquals(obj1.get("Name"), obj2.get("Name"));	
	}
	
	@Test
	public void GetAllDocuments()
	{
		_dbOperations.DeleteAllDocuments();
		BasicDBObject obj1 = new BasicDBObject("key1", "value1");
		_dbOperations.InsertDocument(obj1);
		BasicDBObject obj2 = new BasicDBObject("key2", "value2");	
		_dbOperations.InsertDocument(obj2);;
		BasicDBObject obj3 = new BasicDBObject("key3", "value3");	
		_dbOperations.InsertDocument(obj3);;	
		Assert.assertEquals(3, _dbOperations.GetAllDocuments().size());
	}
	
	@Test
	public void UpdateDocument()
	{
		_dbOperations.DeleteAllDocuments();
		BasicDBObject oldObj = new BasicDBObject("oldKey", "oldValue");
		_dbOperations.InsertDocument(oldObj);
		BasicDBObject newObj = new BasicDBObject("newKey", "newValue");	
		_dbOperations.UpdateDocument(oldObj, newObj);
		Assert.assertEquals("newValue", _dbOperations.GetCollection().findOne().get("newKey"));
	}
	
	@Test
	public void DeleteDocumentByID()
	{
		_dbOperations.DeleteDocument("5672e907c63323b4fba244b1");
		Assert.assertEquals(null, _dbOperations.GetDocumentByID("5672e907c63323b4fba244b1"));
	}
	
	@Test
	public void DeleteAllDocuments()
	{
		_dbOperations.DeleteAllDocuments();
		Assert.assertEquals(0, _dbOperations.GetCollectionCount());
	}
	
}

