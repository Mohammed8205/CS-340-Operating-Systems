
public class StorageClerk extends Thread {

	
	int id;
	public volatile boolean need = true;
	public static volatile boolean noLonger = true;
	public static final double max = 80.00;
	public static volatile int group = 0;
	Customer serving = null;
	public double target = 0.0;
	public static int avil = 0;
	public boolean currently = false;
	
	public StorageClerk(int i){
		id = i;
	}
	
	public void run(){
		try{
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+":  "+getName()+" : "+"Storage room clerk "+ this.getName()+" is now arriving to work ");
			Thread.sleep(100+this.getId());
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+":  "+getName()+" : "+"Storage room clerk "+ this.getName() + " is now at work and waiting for Customers  to come to Store Room");
			help();
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+":  "+getName()+" : "+"No more Customer left to help Store clerk "+ this.getName()+" is going to sleep");
			Thread.sleep(99999999);
		}
		catch(Exception e){
			System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+":  "+getName()+" : "+"Store closed Store Room Clerk"+ this.getName()+" is going home! ");
		}
	}
	public void help(){
		while(noLonger){
			try{
				while(need){
					currently = false;
					if(Customer.left == 0)break;
				}
				if(Customer.left == 0)break;
				Thread.sleep(100+this.getId());
				group++;
				if(serving != null){
				if(serving.weight > max){
					while(group % 2 != 0);
				}
				System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+":  "+getName()+" : "+"Now Floor clerk "+ this.getName()+ " is serving customer "+ serving.getName());
				Thread.sleep(400+this.getId());
				group = 0;
				Thread.sleep(100+this.getId());
				System.out.println("["+(System.currentTimeMillis()-Main.time)+"] "+":  "+getName()+" : "+"This is Floor clerk "+this.getName()+" Customer "+ serving.getName()+ " now has his/her item");
				serving.storeWait = false;
				}
				need = true;
				if(Customer.left == 0)break;
			}
			catch(Exception e){
				//System.out.println("Store closed Store Room Clerk"+ this.getName()+" is going home! ");
			}
		}
	}
}
