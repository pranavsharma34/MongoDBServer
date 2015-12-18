package main;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Implements CRUD Operations on the Server as either GET or POST
 * @author Pranav Sharma
 * @version 1.0
 * @since 2015-12-17 
*/
@RestController
public class MongoDBController {

	private DBOperations _dbOperations = new DBOperations("localhost", 27017, "entries", "test");
	
	/**
	 * Gets Document specified by ID
	 * If ID is not specified then returns an exisiting Document
	*/
    @RequestMapping("/GetDocument")
    public DBObject GetDocumentByID(@RequestParam(value="ID", defaultValue = "5672e654c633152ea6473b53") String ID) 
    {
    	return _dbOperations.GetDocumentByID(ID); 	
    }
    
    /**
	 * Gets the Old Document specified by ID
	 * Replaces it with a new Document that is created by providing name and type
	*/
    @RequestMapping("/UpdateObject")
    public void UpdateObject(@RequestParam(value="ID", defaultValue = "5672e654c633152ea6473b53") String ID
    		, @RequestParam(value="name", defaultValue = "Pranav") String name, 
    		@RequestParam(value="ID", defaultValue = "sample") String type) 
    {
    	DBObject oldObj = _dbOperations.GetDocumentByID(ID);
    	DBObject newObj = _dbOperations.CreateDocument(name, type);
    	_dbOperations.UpdateDocument((BasicDBObject)oldObj, (BasicDBObject)newObj);
    }
    
    /**
	 * Gets All Documents that are present in the Collection
	*/
    @RequestMapping("/GetAllObjects")
    public List<DBObject> GetAllObjects() 
    {
    	return _dbOperations.GetAllDocuments();
    }
    
    /**
	 * Creates a Document based on the name and type
	 * Document is then inserted into the Collection
	*/
    @RequestMapping("/InsertObject")
    public void InsertObject(@RequestParam(value="name", defaultValue = "Pranav") String name, 
    		@RequestParam(value="type", defaultValue = "sample") String type) 
    {
    	BasicDBObject document = _dbOperations.CreateDocument(name, type);
    	_dbOperations.InsertDocument(document);
    }
    
    /**
	 * Deletes Document specified by the ID
	*/
    @RequestMapping("/DeleteObject")
    public void DeleteObject(@RequestParam(value="ID", defaultValue = "5672e654c633152ea6473b53") String ID) 
    {
    	_dbOperations.DeleteDocument(ID);
    }
    
    /**
	 * Gets all Documents present in the Collection
	*/
    @RequestMapping("/DeleteAllObjects")
    public void DeleteAllObjects() 
    {
    	_dbOperations.DeleteAllDocuments();
    }
    
    /**
	 * Returns Count of Documents present in the Collection
	*/
    @RequestMapping("/CollectionCount")
    public long CollectionCount() 
    {
    	return _dbOperations.GetCollectionCount();
    }
}

