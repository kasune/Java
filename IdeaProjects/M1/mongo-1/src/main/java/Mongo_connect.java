import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.util.Arrays;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: emkasun
 * Date: 10/31/13
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Mongo_connect {

    public static void main(String args[]) {

        try {
            //MongoClient mongoClient = new MongoClient();
// or
            //mongoClient = new MongoClient( "localhost" );
// or
            //mongoClient = new MongoClient( "localhost" , 27017 );
// or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
            MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),new ServerAddress("localhost", 37017)/*, new ServerAddress("localhost", 47017)*/));

            DB db = mongoClient.getDB("kasun");
            //boolean auth = db.authenticate(myUserName, myPassword);
            Set<String> colls = db.getCollectionNames();

            for (String s : colls) {
                System.out.println(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
