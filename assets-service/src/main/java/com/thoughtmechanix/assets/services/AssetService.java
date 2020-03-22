package com.thoughtmechanix.assets.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.assets.clients.OrganizationRestTemplateClient;
import com.thoughtmechanix.assets.config.ServiceConfig;
import com.thoughtmechanix.assets.model.Asset;
import com.thoughtmechanix.assets.model.Organization;
import com.thoughtmechanix.assets.repository.AssetRepository;
import com.thoughtmechanix.assets.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Hystrix and Spring Cloud use the @HystrixCommand annotation to mark Java
  class methods as being managed by a Hystrix circuit breaker. When the Spring framework
  sees the @HystrixCommand, it will dynamically generate a proxy that will wrapper
  the method and manage all calls to that method through a thread pool  specifically 
  set aside to handle remote calls.
  
  Use one uncommented getAssetsByOrg() at a time.
 */
@Service
public class AssetService {
    private static final Logger logger = LoggerFactory.getLogger(AssetService.class);
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    ServiceConfig config;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;


    public Asset getAsset(String organizationId,String assetId) {
        Asset asset = assetRepository.findByOrganizationIdAndAssetId(organizationId, assetId);

        Organization org = getOrganization(organizationId);

        return asset
                .withOrganizationName( org.getName())
                .withContactName( org.getContactName())
                .withContactEmail( org.getContactEmail() )
                .withContactPhone( org.getContactPhone() )
                .withComment(config.getExampleProperty());
    }

    /**
     * 
     */
    @HystrixCommand
    private Organization getOrganization(String organizationId) {
        return organizationRestClient.getOrganization(organizationId);
    }

    
   /**
    * Listing 5.2. Wrappering a remote resource call with a circuit breaker with default timout of 1000 msec.
    * The circuit breaker will interrupt any call to the getAssets-
	* ByOrg() method any time the call takes longer than 1,000 milliseconds.
	* This verson of the getAssetsByOrg() is presented for illustration, since if the database is working properly,
	* then Hystrix will never be triggerred. This is a Happy Path.
    */  
   /* @HystrixCommand
    public List<Asset> getAssetsByOrg(String organizationId){
    	return assetRepository.findByOrganizationId(organizationId);
    }*/
    
    
    private void randomlyRunLong(){
      Random rand = new Random();

      int randomNum = rand.nextInt((3 - 1) + 1) + 1;

      if (randomNum==3) sleep();
    }

    private void sleep(){
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
   // Listing 5.3 Wrapping a remote resource call with a circuit breaker
   //Let’s simulate the method running into a slow database query by having
   // the call take more than a second on approximately every one in three calls.
    @HystrixCommand
   public List<Asset> getAssetsByOrg(String organizationId){
       logger.debug("AssetService.getAssetsByOrg  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
       randomlyRunLong();

       return assetRepository.findByOrganizationId(organizationId);
   }
   
    
    //Listing 5.4 Customizing the time out on a circuit breaker call.See below
    //Comment out for other Listings
 /*  @HystrixCommand(
    		  commandProperties=
    		     {@HystrixProperty(
    		   name="execution.isolation.thread.timeoutInMilliseconds",
    		   value="12000")}) 
   public List<Asset> getAssetsByOrg(String organizationId){
	   randomlyRunLong();
	   return AssetRepository.findByOrganizationId(organizationId);
   } */
    
    //Listing 5.5 Implementing a fallback in Hystrix. See below
    //Comment out for other Listings
  /* @HystrixCommand(fallbackMethod = "buildFallbackAssetList")
   public List<Asset> getAssetsByOrg(String organizationId){
       randomlyRunLong();
       return assetRepository.findByOrganizationId(organizationId);
     } */
    
    //Listing 5.6 Creating a bulkhead around the getAssetsByOrg() method
  //Comment out for  other Listings
  /*  @HystrixCommand(fallbackMethod = "buildFallbackAssetList",
            threadPoolKey = "assetByOrgThreadPool",
            threadPoolProperties =
                {@HystrixProperty(name = "coreSize",value="30"),
                @HystrixProperty(name="maxQueueSize", value="10")}
    )
    public List<Asset> getAssetsByOrg(String organizationId){ 
    		return assetRepository.findByOrganizationId(organizationId);
    } */
   
    
    //Listing 5.7 Configuring the behavior of a circuit breaker
    //Comment out for  other Listings
  /*  @HystrixCommand(
    		   fallbackMethod = "buildFallbackAssetList",
    		   threadPoolKey = "assetByOrgThreadPool",
    		   threadPoolProperties ={
    		             @HystrixProperty(name = "coreSize", value="30"),
    		        @HystrixProperty(name="maxQueueSize", value="10")},
    				   commandProperties={
    		                     @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
    		                     @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
    		                     @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
    		                     @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
    		                     @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")}
	  )
      public List<Asset> getAssetsByOrg(String organizationId){ 
    		logger.debug("getAssetsByOrg Correlation id: {}",
    				UserContextHolder
    		               .getContext()
    		               .getCorrelationId());
    		randomlyRunLong();
    		return assetRepository.findByOrganizationId(organizationId);
    	  } */
    // End of Listing 5.7			
    				
   
     //A simple fallback strategy for your assets database that returns
    // a assets object that says no assets information is currently available. 
    //The fallback method must have the exact same method signature as the originating 
    //function as all of the parameters passed into the original method protected 
    //by the @HystrixCommand will be passed to the fallback.
    private List<Asset> buildFallbackAssetList(String organizationId){
        List<Asset> fallbackList = new ArrayList<>();
        Asset asset = new Asset()
                .withId("0000000-00-00000")
                .withOrganizationId( organizationId )
                .withAssetName("Sorry no assets information currently available");

        fallbackList.add(asset);
        return fallbackList;
    } 

    public void saveAsset(Asset asset){
        asset.withId( UUID.randomUUID().toString());

        assetRepository.save(asset);
    }

    public void updateAsset(Asset asset){
      assetRepository.save(asset);
    }

    public void deleteAsset(Asset asset){
        assetRepository.delete( asset.getAssetId());
    }

}
