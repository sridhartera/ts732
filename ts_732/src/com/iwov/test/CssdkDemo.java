package com.iwov.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.interwoven.cssdk.access.CSAuthorizationException;
import com.interwoven.cssdk.access.CSExpiredSessionException;
import com.interwoven.cssdk.access.CSGroup;
import com.interwoven.cssdk.access.CSRole;
import com.interwoven.cssdk.access.CSUser;
import com.interwoven.cssdk.common.CSClient;
import com.interwoven.cssdk.common.CSContext;
import com.interwoven.cssdk.common.CSException;
import com.interwoven.cssdk.common.CSInstallationInfo;
import com.interwoven.cssdk.common.CSIterator;
import com.interwoven.cssdk.common.CSNameValuePair;
import com.interwoven.cssdk.common.CSObjectNotFoundException;
import com.interwoven.cssdk.common.CSProduct;
import com.interwoven.cssdk.common.CSRemoteException;
import com.interwoven.cssdk.sci.filesys.CSBranch;
import com.interwoven.cssdk.search.CSSearchEngine;


public class CssdkDemo {
	CSClient myclient;
	/*	public CssdkDemo(){
		//super();		
	}
*/	
	public void getUserProperties(CSClient myclient) throws CSAuthorizationException, CSRemoteException, CSExpiredSessionException, CSException{
		this.myclient = myclient;
		CSUser currentUser = myclient.getCurrentUser();
		System.out.println("current user:"+ currentUser);
		String tst = "abcd";
		if(tst.matches("bc")){
			System.out.println("match worked");
			
		}
		
		CSRole[] myroles = currentUser.getRoles(null);		
		for (CSRole myrole : myroles){
			String rname = myrole.getName();
			System.out.println("my roles are:"+ rname);
		}
		
/*		CSNameValuePair[] myprops = currentUser.getProperties(null);
		for (CSNameValuePair myprop : myprops){
			System.out.println("my prop name:value::"+ myprop.getName() +":"+ myprop.getValue());		
		}		*/
	}
	public void getServerProps(CSClient myclient) throws CSException, UnknownHostException {
		this.myclient = myclient;
		CSInstallationInfo installInfo = myclient.getInstallationInfo();
		System.out.println("install info obj"+ installInfo.toString());		
/*		CSProduct[] products = installInfo.getAllProducts();
		try {
			for (CSProduct product : products){
				System.out.println("csproduct obj info:" + product.toString());
				System.out.println("TS products installed:"+ product.getName());		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				*/
		
	}
	public void tsFunction(CSClient myclient) throws CSAuthorizationException, CSExpiredSessionException, CSRemoteException, com.interwoven.cssdk.common.CSException {
		this.myclient = myclient;

	  	  //CSUser currentUser = myclient.getCurrentUser();
	  	  CSUser someUser = myclient.getUser("dchan", true);
	  	  CSGroup someGroup = myclient.getGroup("iwEveryone",true);
	  	  CSIterator someGroupMembersIterator = someGroup.getUsers(true);
	  	  CSIterator someGroupManagersIterator = someGroup.getAdministrators();
	  if (someUser!=null){
	      System.out.println(someUser.getName() + ":" + someUser.getFullName());
	  }

	  if (someGroup!=null){
	      System.out.println(someGroup.getName() + ":" + someGroup.getDescription());
	  }

	  //Iterate group to get member details

	  while (someGroupMembersIterator.hasNext()){
	      CSUser groupMember=(CSUser)someGroupMembersIterator.next();
	      System.out.println(groupMember.getName() + " belongs to group " + someGroup.getName());
	  }
	  while (someGroupManagersIterator.hasNext()){

	      CSUser groupManager=(CSUser)someGroupMembersIterator.next();
	      System.out.println(groupManager.getName() + " manages group " + someGroup.getName());
	  }	     
	}
	public void sessionContext(CSClient myclient) {
		this.myclient = myclient;
		CSContext csc = new CSContext();
		csc = myclient.getContext();
		/*String sessionStr = csc.getSessionString();
		System.out.println("session str: "+ sessionStr);
		System.out.println("app context: "+ csc.getAppContext());
		System.out.println("locale str: "+ csc.getLocaleStr());
		System.out.println("server name: "+ csc.getServerName());
		System.out.println("getClass: "+ csc.getClass());
		System.out.println("exp date: "+ csc.getExpirationDate());
		System.out.println("getLocale: "+ csc.getLocale());*/
		System.out.println("to string: "+ csc.toString());
		
	}
	public void tsSearch() throws CSRemoteException, CSExpiredSessionException, CSAuthorizationException, CSException {
		CSSearchEngine searchEngine = myclient.getSearchEngine();
		System.out.println("search obj info:"+ searchEngine.toString());
		String[] indexedBranches = searchEngine.getIndexedBranches();		
		for(String indexedBranch : indexedBranches ){
			System.out.println("indexed Branch:"+ indexedBranch);
			
			
		}	
		 
		
	}
	
	//main
	public static void main (String[] args) throws CSAuthorizationException, CSExpiredSessionException, CSRemoteException, com.interwoven.cssdk.common.CSException, UnknownHostException {
		GetClient gc = new GetClient();
		CssdkDemo cd = new CssdkDemo();
		cd.myclient = gc.getClient();
		cd.getUserProperties(cd.myclient);
				
		//cd.tsFunction(cd.myclient);
		cd.getServerProps(cd.myclient);
		//cd.sessionContext(cd.myclient);
		cd.tsSearch();
		
		gc.endClientSession(cd.myclient);
		
		
	}	
}
