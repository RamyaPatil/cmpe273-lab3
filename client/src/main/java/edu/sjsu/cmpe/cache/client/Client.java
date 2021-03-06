package edu.sjsu.cmpe.cache.client;

import java.util.List;
import java.util.ArrayList;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        //CacheServiceInterface cache = new DistributedCacheService(
        //        "http://localhost:3000");

       	//System.out.println("First time routed to: " + serversList.get(bucket));

        List<CacheServiceInterface> serversList = new ArrayList<CacheServiceInterface>();
        serversList.add(new DistributedCacheService("http://localhost:3000"));
        serversList.add(new DistributedCacheService("http://localhost:3001"));
        serversList.add(new DistributedCacheService("http://localhost:3002"));

	String[] value = {" ","a","b","c","d","e","f","g","h","i","j"};
 
	for(int key=1;key<=value.length-1;key++)
	{
		int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), serversList.size());
        	CacheServiceInterface cache = serversList.get(bucket);
        	cache.put(key,value[key]);
		System.out.print("PUT---> ");
		System.out.println("key="+key+" value="+value[key]+" routed to server at http://localhost:300"+bucket);
	}
	
	System.out.println(" ");

	for(int key=1;key<=value.length-1;key++)
	{
		int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), serversList.size());
        	CacheServiceInterface cache = serversList.get(bucket);
        	String val = cache.get(key);
		System.out.print("GET---> ");
		System.out.println("key="+key+" value="+val+" routed to server at http://localhost:300"+bucket);
	}

	//cache.put(1, "foo");
        //System.out.println("put(1 => foo)");

        //String value = cache.get(1);
        //System.out.println("get(1) => " + value);

        System.out.println("Existing Cache Client...");
    }

}
