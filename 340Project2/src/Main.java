import java.util.concurrent.Semaphore;

public class Main {

	
	public static Semaphore dokanbondho = new Semaphore(0,true);
	
	public static void main(String[] args) {
		
		int kotoCustomer = Integer.parseInt(args[0]);
		int koitaFClerk = Integer.parseInt(args[1]);
		int koitaSClerk = Integer.parseInt(args[2]);
		
		Customer.setKor(kotoCustomer,koitaFClerk);
		
		FloorClerk.setKor(koitaFClerk);
		
		StoreRoomClerk.setKor(koitaSClerk);
	}

}
