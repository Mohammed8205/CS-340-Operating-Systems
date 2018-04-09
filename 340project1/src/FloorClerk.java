
public class FloorClerk extends Thread {
	
	
	public volatile boolean waitingToHelp;
	public static volatile int available = Main.numberofFclerk;
	int id;
	public Customer current = null;
	public static volatile int removal = -1;
	
	public FloorClerk(int i){
		waitingToHelp = true;
		id = i;
	}
	
	
	public void run(){
		try{
			Thread.sleep(10+this.getId());
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+": "+"FloorClerk "+ this.getName()+" is now arriving at work ");
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+": "+"FloorClerk "+ this.getName()+ " is now at work.....");
			Thread.sleep(5+this.getId());
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+": "+"FloorClerk "+ this.getName() + " is now waiting to help any customer");
			help();
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+": "+"No more customer left to help therefore, Floor Clerk "+ this.getName()+" will now go on a long sleep ");
			Thread.sleep(999999999);
		}
		catch(Exception e){
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+": "+"Store close, Floor Clerk "+ this.getName()+" is going home");
		}
	}
	
	public void help(){
		try{
		while(!Main.floorClerkFinished){
		while(waitingToHelp){
			if(Customer.waiting == Main.numberOfCustomers && Customer.waiting > 0 && Main.numberOfCustomers > 0 )break;
		};
		Thread.sleep(20*this.getId());
		Customer.waiting--;
		Thread.sleep(20+this.getId());
		Main.numberOfCustomers--;
		Thread.sleep(20+this.getId());
		if(!Main.nCustomer.isEmpty()){
		current = Main.nCustomer.remove(0);
		current.waitingForFClerk = false;
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+": "+"Floor Clerk "+this.getName()+ " is now ready to help Customer "+ current.getName());
		Thread.sleep(200+this.getId());
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+": "+"Floor clerk "+ this.getName()+ " is now presenting the ticket to Customer " + current.getName());
		Thread.sleep(100+this.getId());
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+": "+"Floor Clerk "+this.getName()+" is now finished helping cusomter "+ current.getName());
		Thread.sleep(20+this.getId());
		System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+": "+getName()+": "+"Floor Clerk "+ this.getName()+ " I have now given Cutomer "+ current.getName()+" his/her ticket ");
		Thread.sleep(20+this.getId());
		waitingToHelp = true;
		Thread.sleep(20+this.getId());
		current.waitingforTicket = false;
				}
		Thread.sleep(20+this.getId());
		current.waitingforTicket = false;
		if(Main.numberOfCustomers == 0)Main.floorClerkFinished = true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
