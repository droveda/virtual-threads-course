package com.mudra;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This handler is an example of code which is a mix of sequential coding and
 * Completable Futures. 
 * 
 * @author vshetty
 *
 */
@SuppressWarnings("preview")
public class UserRequestHandler implements Callable<String> {

	@Override
	public String call() throws Exception {
		
		// Sequential coding .. 
		String result1 = dbCall1();
		String result2 = dbCall2(); 
		
		// complicated parallel threads code in limited to the block below
		try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
			
			String result = CompletableFuture
								.supplyAsync(this::restCall1, service)
								.thenCombine(
										 CompletableFuture.supplyAsync(this::restCall2, service)
										,this::mergeResults)
								.join(); // join blocks in a virtual thread. so its okay. 
			
			String output = mergeResults(result1, result2, result);
			
			System.out.println(output);
			return output;
			
		}
		
		// Once block ends all tasks have terminated. We are sure of that. 
		
	}
	
	private String mergeResults(String... s) {
		return "[" + String.join(",", s) + "]";
	}


	/**
	 * Simulates a database call which returns in 2 secs
	 * @return
	 */
	private String dbCall1() {
		try {
			NetworkCaller caller = new NetworkCaller("data1");
			return caller.makeCall(2);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Simulates a database call which returns in 3 secs
	 * @return
	 */
	private String dbCall2() {
		try {
			NetworkCaller caller = new NetworkCaller("data2");
			return caller.makeCall(3);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Simulates a REST call which returns in 5 secs
	 * @return
	 */
	private String restCall1() {
		try {
			NetworkCaller caller = new NetworkCaller("rest1");
			return caller.makeCall(5);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * Simulates a REST call which returns in 6 secs
	 * @return
	 */
	private String restCall2() {
		try {
			NetworkCaller caller = new NetworkCaller("rest2");
			return caller.makeCall(6);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}


}

