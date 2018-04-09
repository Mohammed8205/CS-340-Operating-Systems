import java.util.Vector;

public class Main {
	
	public static volatile Vector<Customer> nCustomer = new Vector<Customer>();
	public static volatile Vector<FloorClerk> nFClerk = new Vector<FloorClerk>();
	public static volatile Vector<StorageClerk> nSClerk = new Vector<StorageClerk>();
	static int numberOfCustomers,numberofFclerk,numberofSclerk;
	public static volatile boolean floorClerkFinished = false;
	public static int willNeedHelp = 0;
	public static volatile boolean global = false;
	
	public static long time = System.currentTimeMillis();

	
	
	public static void main(String[] args){
		
		numberOfCustomers = Integer.parseInt(args[0]);
		numberofFclerk = Integer.parseInt(args[1]);
		numberofSclerk = Integer.parseInt(args[2]);
		
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+": "+ " Initial numbers : \n Customers: "+	numberOfCustomers+ "\n Floor Clerk: "+numberofFclerk+"\n Store Room Clerk: "+ numberofSclerk);
		
		for(int i = 0; i<numberOfCustomers; i++){
			nCustomer.add(new Customer(i));
		}
		for(int i = 0; i<numberofFclerk; i++){
			nFClerk.add(new FloorClerk(i));
		}
		
		for(int i = 0; i<numberofSclerk; i++){
			nSClerk.add(new StorageClerk(i));
		}
		
		for(int i = 0; i<nFClerk.size(); i++){
			nFClerk.get(i).start();
		}
		
		for(int i = 0; i<nSClerk.size(); i++){
			nSClerk.get(i).start();
		}
		
		for(int i = 0; i<nCustomer.size(); i++){
			nCustomer.get(i).start();
		}
	}

}
