//package memcache;

import com.danga.MemCached.*;

public class MemCach {
// create a static client as most installs only need
// a single instance
   protected static MemCachedClient mcc = new MemCachedClient();

// set up connection pool once at class load
    public void connectMemCach()

    {


// server list and weights
        String[] servers =
                {
//"10.139.201.51:11211",
                        "192.168.0.98:11211",
//"server2.mydomain.com:1624",
//"server3.mydomain.com:1624"
                };

        Integer[] weights = {new Integer(3), new Integer(3), new Integer(2)};

// grab an instance of our connection pool
        SockIOPool pool = SockIOPool.getInstance();

// set the servers and the weights
        pool.setServers(servers);
        pool.setWeights(weights);

// set some basic pool settings
// 5 initial, 5 min, and 250 max conns
// and set the max idle time for a conn
// to 6 hours
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaxIdle(1000 * 60 * 60 * 6);

// set the sleep for the maint thread
// it will wake up every x seconds and
// maintain the pool size
        pool.setMaintSleep(30);

// set some TCP settings
// disable nagle
// set the read timeout to 3 secs
// and don't set a connect timeout
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);

// initialize the connection pool
        pool.initialize();

// lets set some compression on for the client
// compress anything larger than 64k
        mcc.setCompressEnable(true);
        mcc.setCompressThreshold(64 * 1024);
    }

    /**
     * http://jehiah.cz/projects/memcached-win32/
     * 1. Unzip the binaries in your desired directory (eg. c:\memcached)
     * 2. Install the service using the command: 'c:\memcached\memcached.exe -d install' from either the command line
     * 3. Start the server from the Microsoft Management Console or by running the following command: 'c:\memcached\memcached.exe -d start'
     * 4. Use the server, by default listening to port 11211
     * <p/>
     * memcached.exe -v -d start -m 128 -l 10.139.201.51 -p 11211
     *
     * @param arg
     */
    

}
