package main;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@RestController
public class MongoDBController {

	private DBOperations _dbOperations = new DBOperations("localhost", 27017, "entries", "test");
	
    @RequestMapping("/GetDocument")
    public DBObject GetDocumentByID(@RequestParam(value="ID", defaultValue = "5672e654c633152ea6473b53") String ID) 
    {
    	return _dbOperations.GetDocumentByID(ID); 	
    }
    
    @RequestMapping("/UpdateObject")
    public void UpdateObject(@RequestParam(value="ID", defaultValue = "5672e654c633152ea6473b53") String ID
    		, @RequestParam(value="name", defaultValue = "Pranav") String name, 
    		@RequestParam(value="ID", defaultValue = "sample") String type) 
    {
    	DBObject oldObj = _dbOperations.GetDocumentByID(ID);
    	DBObject newObj = _dbOperations.CreateDocument(name, type);
    	_dbOperations.UpdateDocument((BasicDBObject)oldObj, (BasicDBObject)newObj);
    }
    
    @RequestMapping("/GetAllObjects")
    public List<DBObject> GetAllObjects() 
    {
    	return _dbOperations.GetAllDocuments();
    }
    
    @RequestMapping("/InsertObject")
    public void InsertObject(@RequestParam(value="name", defaultValue = "Pranav") String name, @RequestParam(value="type", defaultValue = "sample") String type) 
    {
    	BasicDBObject document = _dbOperations.CreateDocument(name, type);
    	_dbOperations.InsertDocument(document);
    }
    
    @RequestMapping("/DeleteObject")
    public void DeleteObject(@RequestParam(value="ID", defaultValue = "5672e654c633152ea6473b53") String ID) 
    {
    	_dbOperations.DeleteDocument(ID);
    }
    
    @RequestMapping("/DeleteAllObjects")
    public void DeleteAllObjects() 
    {
    	_dbOperations.DeleteAllDocuments();
    }
}

