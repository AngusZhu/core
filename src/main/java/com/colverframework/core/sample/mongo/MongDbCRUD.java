package com.colverframework.core.sample.mongo;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
  
  
public class MongDbCRUD {  
    
	@Autowired
	private MongoConifg mongoConfig;
    private static Mongo mongo = null;  
    private static DB db;  
    private static DBCollection table;  
      
    
    private static MongDbCRUD instace;  
      
    
    
 

	public void setMongoConfig(MongoConifg mongoConfig) {
		this.mongoConfig = mongoConfig;
	}

	public static synchronized MongDbCRUD getInstance(){  
        if(instace==null||mongo==null||db==null||table==null){  
            instace = new MongDbCRUD();  
        }  
        return instace;  
    }  
      
    public MongDbCRUD(){  
        String host = ConstantUtils.getValue("mongo.host").toString();  
        int port = Integer.parseInt(ConstantUtils.getValue("mongo.port").toString());  
        String datablease = ConstantUtils.getValue("mongo.datablease").toString();  
        String tablelename = ConstantUtils.getValue("mongo.table.bound").toString();  
          
        try {  
            mongo = new Mongo(host, port);  
            // 连接池  
            MongoOptions opt = mongo.getMongoOptions();  
            opt.connectionsPerHost =  10  ; //poolsize  
            opt.threadsAllowedToBlockForConnectionMultiplier =  10 ;  
              
            //获取temp DB；如果默认没有创建，mongodb会自动创建  
            db = mongo.getDB(datablease);  
            //获取users DBCollection；如果默认没有创建，mongodb会自动创建  
            table = db.getCollection(tablelename);  
            //DO SOMETHING  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (MongoException e) {  
            e.printStackTrace();  
        }  
    }  
      
    public MongDbCRUD(String host, int port, String datablease, String tablelename){  
        try {  
            mongo = new Mongo(host, port);  
            // 连接池  
            MongoOptions opt = mongo.getMongoOptions();  
            opt.connectionsPerHost =  10  ; //poolsize  
            opt.threadsAllowedToBlockForConnectionMultiplier =  10 ;  
              
            //获取temp DB；如果默认没有创建，mongodb会自动创建  
            db = mongo.getDB(datablease);  
            //获取users DBCollection；如果默认没有创建，mongodb会自动创建  
            table = db.getCollection(tablelename);  
            //DO SOMETHING        
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (MongoException e) {  
            e.printStackTrace();  
        }  
    }  
      
    public static void free(){  
        if(mongo!=null){  
            mongo.close();  
        }  
        mongo = null;  
        db = null;  
        table = null;  
        System.gc();  
    }  
      
    public static DBCollection getDBCollection(){  
        return table;  
    }  
      
    public void add(DBObject row){  
        table.save(row);  
    }  
      
    public void query(){  
        DBCursor cur = table.find();  
        while (cur.hasNext()) {   
            DBObject dbobject = cur.next();  
            System.out.println(dbobject.get("key"));//get value  
        }   
    }  
      
}  