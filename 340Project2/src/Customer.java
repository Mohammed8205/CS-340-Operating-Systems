import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customer extends Thread {

	public static ArrayList<Customer> consumer;
	public static int totalCustomer;
	public static int browsing = 0;
	public static Semaphore sem ;
	public static Semaphore mutex = new Semaphore(1,true);
	public static Semaphore sem2 = new Semaphore(0,true);
	public static int  fServe = 0;
	private int weight = 0;
	static final int LIMIT = 200;
	public static Semaphore sem3 = new Semaphore(0,true);
	public static Semaphore sem4 = new Semaphore(0,true);
	public static int bought = 0;
	public static int visit = 0;
	public static int nonVisit = 0;
	
	
	public Customer(){};
	
	
	public void run(){
		
		try{
			
			mamaCholDokaneJai();
			
			dokaneArsi();
			
			takade();
			
			weighterKahini();
			
			if(weight >= LIMIT){
				storeRoomejao();
			}
			
			sesmes();
			
			
		}
		catch(Exception excep){
			System.out.println(excep);
		}
		finally{
			
		}
		
		
	}
	
	public void mamaCholDokaneJai() throws InterruptedException{
		
		Thread.sleep(1000+this.getId());
		System.out.println("This is Customer Thread "+ this.getName()+" its deciding to go to store.. .. .. ");
		Thread.sleep(100+this.getId());
		System.out.println("This is Cusomter Thread "+ this.getName()+" is now leaving their house.. to goto BALA...");
		Thread.sleep(1000+this.getId());
		System.out.println("This is Customer Thread "+this.getName()+" is now going ");
	}
	
	public void dokaneArsi()throws InterruptedException{
		
		Thread.sleep(1000+this.getId());
		System.out.println("This is Customer Thread "+this.getName()+" is now arriving at store.....");
		Thread.sleep(1000+this.getId());
		System.out.println("This is Customer Thread "+ this.getName()+ " is now at store and looking to buy items.....");
		Thread.sleep(3000);
		System.out.println("This is Customer Thread "+this.getName()+" have now chosen an item will signal "+sem.availablePermits());
		sem.acquire();
		mutex.acquire();
		fServe++;
		FloorClerk.sem.release();
		mutex.release();
		sem2.acquire();
		Thread.sleep(1000);
		System.out.println("This is Customer Thread "+ this.getName()+" have now gotten their ticket will move to pay...");
		sem.release();
	}
	
	
	public void takade()throws InterruptedException{
		
		Thread.sleep(1000);
		System.out.println("This is customer thread will now pay for item");
		Thread.sleep(1500);
		System.out.println("This is customer Thread "+this.getName()+" have now paid for item ");
	}
	
	public void weighterKahini() throws InterruptedException{
		Random random = new Random();
		weight = random.nextInt(200)+100;
		Thread.sleep(500);
		System.out.println("Calculated weight for customer thread "+ this.getName()+" is : "+ weight);
	}
	
	
	public void storeRoomejao() throws InterruptedException{
		
		if(StoreRoomClerk.help.getQueueLength() < StoreRoomClerk.GROUP_SIZE){
			
			System.out.println("This is customer thread "+ this.getName()+ " no clerk available in store room will wait.......");
			sem3.acquire();
			
		}
		
		System.out.println("This is customer Thred "+ this.getName()+" clerk available at the time signaling them.....");
		for(int i = 0; i<StoreRoomClerk.GROUP_SIZE; i++){
			StoreRoomClerk.help.release();
		}
		while(StoreRoomClerk.help.getQueueLength() < StoreRoomClerk.GROUP_SIZE){};
		System.out.println("Customer Thread "+this.getName()+" is now waiting.....");
		sem4.acquire();
		
		while(StoreRoomClerk.current != 0){};
		
		
		Thread.sleep(1000);
		System.out.println("This is customer Thread " + this.getName()+ " is now helped! by store room clerks");
		if(sem3.getQueueLength()> 0)
		sem3.release();
		
	}
	
	public void sesmes() throws InterruptedException{
		
		mutex.acquire();
		bought++;
		nonVisit++;
		if(bought == totalCustomer){
			if(StoreRoomClerk.help.getQueueLength() > 0){
				
				
				while(StoreRoomClerk.help.getQueueLength()!= 0){
					
					StoreRoomClerk.help.release();
				}
				
				while(Main.dokanbondho.getQueueLength()!=0){
					
					Main.dokanbondho.release();
				}
				
				mutex.release();
			}
			
		}
		else{
			
			System.out.println("Cusotmer thread "+ this.getName()+" Will now brose still store close.....");
			mutex.release();
		}
		
		System.out.println("This is customer thread "+this.getName()+" store closed stopped browsing will now go home!");
		
	}
	
	
	public static void setKor(int x, int y){
		totalCustomer = x;
		sem = new Semaphore(y,true);
		consumer = new ArrayList<Customer>();
		int runner = 0;
		while(runner<totalCustomer){
			consumer.add(new Customer());
			runner++;
		}
		runner = 0;
		while(runner<totalCustomer){
			consumer.get(runner).start();
			runner++;
		}
	}
	
	
	
	
	
}
