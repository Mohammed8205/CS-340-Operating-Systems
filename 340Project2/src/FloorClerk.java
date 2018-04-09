import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class FloorClerk extends Thread {
	
	
	public static ArrayList<FloorClerk>fclerks;
	public static Semaphore sem = new Semaphore(0,true);
	public static Semaphore mutex = new Semaphore(1,true);
	public static int nFClerk = 0;
	
	public FloorClerk(){};
	
	
	public void run(){
		
		try{
			
			kajeAstesi();
			
			sahajjo();
			
			dara();
			
		}
		catch(Exception ie){
			System.out.println(ie);
		}
		finally{
			
		}
		
		
	}
	
	public void kajeAstesi() throws InterruptedException{
		
		System.out.println("This is FloorClerk Thread "+this.getName()+" is leaving house....");
		Thread.sleep(1000);
		System.out.println("This is FloorClerk Thread "+ this.getName()+" is now at work.....");
		Thread.sleep(1000);
		System.out.println("This is FloorClerk Thread "+ this.getName()+" is on standby waiting for Customer.....");
	}
	
	
	public void sahajjo()throws InterruptedException{
		
		while(true){
			
			if(Customer.fServe == Customer.totalCustomer)break;
			
			sem.acquire();
			
			
			System.out.println("This is Floor Clerk Thread "+ this.getName()+" will now assist and get ticket");
			
			Thread.sleep(1000);
			
			System.out.println("This is Floor Clerk Thread "+ this.getName()+" is now presenting ticket ");
			
			
			mutex.acquire();
			if(Customer.sem2.getQueueLength() > 0){
				
				Customer.sem2.release();
			}
			mutex.release();
		}
		
		Thread.sleep(1000);
		System.out.println("This is Floor Clerk Thread "+ this.getName()+" No more customer left to help will wait on Main.dokanbondo");
		
	}
	
	public void dara()throws InterruptedException{

		
		Thread.sleep(1000);
		System.out.println("This is customer Thread "+ this.getName()+" will for store to close");
		
		Main.dokanbondho.acquire();
		
		System.out.println("This is cusomter Thread "+ this.getName()+" store close for today going home!");
		
	}
	
	
	public static void setKor(int x){
		
		nFClerk = x;
		
		fclerks = new ArrayList<FloorClerk>();
		
		for(int i = 0; i<x; i++){
			fclerks.add(new FloorClerk());
		}
		
		for(int i = 0; i<x; i++){
			fclerks.get(i).start();
		}
	}

}
