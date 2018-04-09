import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class StoreRoomClerk extends Thread{
	
	
	public static final int GROUP_SIZE = 3;
	public static Semaphore help = new Semaphore(0,true);
	public static Semaphore mutex = new Semaphore(1,true);
	public static int current = 0;
	public static boolean proper = false;
	public static ArrayList<StoreRoomClerk> kormochari = new ArrayList<StoreRoomClerk>();
	public static int number = 0;
	
	public void run(){
		
		try{
		kajeJai();
		
		sahajjo();
		
		}
		catch(Exception e){
			
			System.out.println(e);
		}
		
	}

	
	public void kajeJai() throws InterruptedException{
		
		Thread.sleep(1000);
		System.out.println("This is Store Room Clerk Thread "+ this.getName()+" is now coming to work...");
		Thread.sleep(1000);
		System.out.println("This is store room Clerk Thread "+this.getName()+ " is now at work waiting to help customers.....");
	}
	
	public void sahajjo()throws InterruptedException{
		
		while(true){
			
			help.acquire();
			
			if(Customer.totalCustomer == Customer.bought){
				
				System.out.println("This is StoreRoomClerk Thread "+this.getName()+" is now going home");
				break;
			}
			
			mutex.acquire();
			current++;
			if(current%GROUP_SIZE == 0){
				Customer.visit++;
				proper = true;
				mutex.release();
			}
			else{
				mutex.release();
				while(!proper){};
			}
			Thread.sleep(1000);
			System.out.println("This is store room Clerk Thread "+this.getName()+ " Will now help customer");
			
			mutex.acquire();
			current--;
			if(current%GROUP_SIZE == 0){
				Customer.sem4.release();
				proper = false;
			}
			mutex.release();
			
		}
	}
	
	public static void setKor(int x){
		
		number = x;
		
		for(int i = 0; i<x; i++){
			
			StoreRoomClerk.kormochari.add(new StoreRoomClerk());
		}
		for(int i = 0 ; i<x; i++){
			
			StoreRoomClerk.kormochari.get(i).start();
		}
		
	}
}
