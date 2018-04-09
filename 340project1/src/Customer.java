import java.util.Random;
import java.util.Vector;

public class Customer extends Thread {
	
	public static volatile int group = 0;
	public static volatile int currentStudents;
	int id;
	public  volatile boolean waitingForFClerk;
	public  volatile boolean waitingforTicket;
	public static volatile int waiting =0 ;
	public double weight = 0.0;
	public static final double max = 60.00;
	public static volatile Vector<Customer>storageRoom = new Vector<Customer>();
	public volatile boolean storeWait = true;
	public static volatile boolean ex = false;
	int count = 0;
	public static volatile int left = Main.numberOfCustomers;
	public static volatile int taken = Main.numberofSclerk;
	
	
	public Customer(int i){
		id = i;
		waitingForFClerk = true;
	}
	
	
	public void run(){
		try{
		Thread.sleep(20*this.getId());
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+ " is now leaving his/her home and on their way to BALA");
		Thread.sleep(30*this.getId());
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+ " have now arrived at BALA...");
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+" is now browsing for items to purchase.....");
		Browse();
		
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+ " have now chosen their items they need Floor Clerk for ticket ");
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+ " will now wait.....");
		Thread.sleep(80*this.getId());
		
		needClerk();
		
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+" is now rushing to pay !");
		Thread.sleep(20*this.getId());
		
		payforItem();
		
		Thread.sleep(200+this.getId());
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+" got my item! but need weight");
		
		calculateWeight();
		
		Thread.sleep(20+this.getId());
		if(weight >= max){
			Thread.sleep(200+this.getId());
			Main.willNeedHelp++;
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+ " has a package that is too heavy need to visit storage room!");
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+ " is now moving towards the store room!");
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+" But Customer "+ this.getName()+ " is now getting hungry will take a break");
			getHungry();
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+ " is back from eating and break now going to storage Room for item");
			Thread.sleep(200+this.getId());
			getItem();
			}
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+" done going home!");
		Thread.sleep(200+this.getId());
		Customer.left--;
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Amount of customers left : "+ left);
			if(Customer.left == 0){
				for(int i = 0; i<Main.nFClerk.size(); i++){
					Main.nFClerk.get(i).interrupt();
				}
				for(int i = 0; i<Main.nSClerk.size(); i++){
					Main.nSClerk.get(i).interrupt();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void Browse(){
		try{
			Thread.sleep(800+this.getId());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void needClerk(){
		try{
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Cutomer "+ this.getName()+" is now waiting to get his/her ticket ");
			waiting++;
			while(waitingForFClerk){};
			Thread.sleep(20*this.getId());
			this.waitingforTicket = true;
			while(this.waitingforTicket){};
			Thread.sleep(30*this.getId());
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+ " have now received his/her ticket! will move onto pay ");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void payforItem() throws InterruptedException{
		Thread.sleep(20+this.getId());
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+" is now getting ready to pay");
		this.setPriority(MAX_PRIORITY);
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+" is now paying for his/her item!");
		Thread.sleep(50);
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+ " paid for my item! ");
		Thread.sleep(100+this.getId());
		this.setPriority(NORM_PRIORITY);
		}

	public void calculateWeight() throws InterruptedException{
		Thread.sleep(20+this.getId());
		Random rando = new Random();
		weight = (rando.nextDouble()%50.0)*100.0;
		Thread.sleep(20+this.getId());
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+this.getName()+" has package with weight "+ weight+ "!");
	}
	
	public void getHungry() throws InterruptedException{
		Random rando = new Random();
		int sleepTime = rando.nextInt(100)+500;
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+" will get something to eat");
		yield();
		yield();
		Thread.sleep(sleepTime);
	}
	
	public synchronized void getItem() throws InterruptedException{
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+"Customer "+ this.getName()+" now waiting in store room queue ");
		synchronized(this){
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+" Customer "+ this.getName()+ " now will wait to be helped by store clerk");
			int it = 0;
			if(this.weight>StorageClerk.max){
				while(Main.nSClerk.size()<2){};
				for(int i = 0; i<Main.numberofSclerk; i++){
					if(Main.nSClerk.get(i).need == true){
						Main.nSClerk.get(i).need = false;
						Main.nSClerk.get(i).serving = this;
						it++;
					}
					if(it == 2 )break;
				}
				while(this.storeWait){};
			}
			else{
				while(Main.nSClerk.size()<1){};
				for(int i = 0; i<Main.numberofSclerk; i++){
					if(Main.nSClerk.get(i).need == true){
						Main.nSClerk.get(i).need = false;
						Main.nSClerk.get(i).serving = this;
						it++;
					}
					if(it == 1 )break;
				}
				while(this.storeWait){};
				System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+" : "+" Customer "+ this.getName()+" is served and can go home now");
			}
		}//en synchro
		Customer.storageRoom.remove(this);
	}
}