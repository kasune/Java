/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Jul 26, 2011
 * Time: 2:35:34 PM
 * To change this template use File | Settings | File Templates.
 */

import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import java.util.Set;

public class Test1 {
    Mongo m;
    DB db;

    public static void main(String args[]) {

        Test1 t1 = new Test1();
        DB d = t1.connectMongo();
        //t1.insert(d);
        t1.view(d,"things");
        t1.viewQuery(d,"things");

    }

    private DB connectMongo() {
        try {
            m = new Mongo("localhost", 27017);
            db = m.getDB("test");
            Set<String> colls = db.getCollectionNames();
            // DBCollection coll = db.getCollection("things");
           /* for (String s : colls) {
                System.out.println(s);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return db;
    }

    private void insert(DB db) {
        DBCollection coll = db.getCollection("things");
        BasicDBObject doc = new BasicDBObject();

        doc.put("name", "MongoDB");
        doc.put("type", "database");
        doc.put("count", 1);

        BasicDBObject info = new BasicDBObject();

        info.put("x", 203);
        info.put("y", 102);
        doc.put("info", info);

        coll.insert(doc);
    }

    private void view(DB database, String collectionName){

       DBCollection collection = database.getCollection(collectionName);
       DBCursor dbc = collection.find();
       System.out.println("-------------------ViewGeneral--------------------------");
        while(dbc.hasNext()){
       System.out.println(dbc.next());
        }
       System.out.println("-------------------EndViewGeneral--------------------------");
    }

    private void viewQuery(DB database, String collectionName){
        DBCollection collection = database.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject();
        query.put("name", "kasun");
        DBCursor cur = collection.find(query);

        System.out.println("-------------------ViewByQuery--------------------------");
        System.out.println("");
        while(cur.hasNext()) {

                    System.out.println(cur.next());
                }
        System.out.println("-------------------EndViewByQuery--------------------------");
    }
}
