package projectt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


class Cab {
	
    int CabNumber;
    Scanner sc = new Scanner(System.in);
    String cabType;

    Cab(int CabNumber, String cabType) {
        this.CabNumber = CabNumber;
        this.cabType = cabType;
    }

    void select() {
        System.out.println("Select Type of car you want to select \n1)Sedan\n2)Toyota\n3)Mini bus");
        int choice = sc.nextInt();
    }
}

class Driver {
    boolean isAvailable;
    String DriverName;
    int CabNumber;
    int mobileNo;

    Driver(String DriverName, int CabNumber, int mobileNo) {
        this.DriverName = DriverName;
        this.CabNumber = CabNumber;
        this.mobileNo = mobileNo;
        this.isAvailable = true;
    }

    void Driverdetails(Customer customer) {
        // Check if this driver is the assigned driver for the customer
        if (customer.assignedDriver != null && customer.assignedDriver.DriverName.equals(this.DriverName)) {
            System.out.println("Driver details for " + customer.Name + "'s assigned driver (" + DriverName + "):");
            System.out.println("\nDriver name is: " + DriverName);
            System.out.println("\nCab number is: " + CabNumber);
            System.out.println("\nMobile number is: " + mobileNo);
        } else {
            System.out.println("This driver is not assigned to the customer.");
        }
    }
}

class Customer {
    Scanner sc = new Scanner(System.in);
    String Name;
    long phoneNo;
    String pickup_Location;
    String destination;
    Driver assignedDriver;
    boolean hasBooked;
    boolean isLoggedIn = false;
    boolean isRegistered=false;

    void assignDriver(Driver driver) {
        this.assignedDriver = driver;
        this.hasBooked = true;  // Set the flag to true when a booking is made

        if (driver != null) {
            System.out.println("Driver assigned to " + this.Name + ": " + driver.DriverName);
            driver.isAvailable = false;
        } else {
            System.out.println("No driver assigned to " + this.Name);
        }
    }

    void register() {
        System.out.println("Enter the customer name:");
        Name = sc.nextLine();
        System.out.println("Enter the phone number(10 digit mobile:)");
        phoneNo = Long.parseLong(sc.nextLine());
        System.out.println("Registration Successful!");
        System.out.println();
        System.out.println("*********************************************************************");
        isRegistered=true; }
   

    void login() {
    if(!isRegistered) {
        System.out.println("It seem you have not registerd! Please Register first!");
        }

    else if (isLoggedIn==false) {
        System.out.println("Enter your name:");
             String inputName = sc.nextLine();
             System.out.println("Enter your phone number:");
             long inputPhoneNo = Long.parseLong(sc.nextLine());
          if (inputName.equals(Name) && inputPhoneNo == phoneNo) {
         
         
             System.out.println("Login Successful!");
             System.out.println();
             System.out.println("*********************************************************************");
             isLoggedIn=true;
             
         }
          else {
                 System.out.println("Invalid credentials. Please try again.");
             }
             }
                     
            else  {
            System.out.println("You have already made a booking in this session.");
                System.out.println("1. Make Another Booking");
                System.out.println("2. Logout");
                int choice = sc.nextInt();

                if (choice == 2) {
                    logout();
                }
            }
       
    }
    void startJourney() {
        if (hasBooked) {
            System.out.println("Thank you for riding with us");
            System.out.println("Starting the journey:");
        } else {
            System.out.println("You need to make a booking first.");
        }
    }
 void endJourney() {
        if (hasBooked) {
            System.out.println("Thank you for riding with us");
            System.out.println("Ending the journey:");
            System.out.println("Please make payment");
        } else {
            System.out.println("You need to make a booking first.");
        }
    }
    void logout() {
        hasBooked = false;  // Reset the booking flag when the user logs out
        System.out.println("You have been logged out.");
    }

    void payment(double fare) {
        System.out.println("$$$$$$$$ Payment Details: $$$$$$$$");
        System.out.println("Your fare amount is: " + fare);
        System.out.println("1. Cash Payment");
        System.out.println("2. Card Payment");
        System.out.println("Enter your choice:");
        int paymentChoice = sc.nextInt();

        switch (paymentChoice) {
            case 1:
                System.out.println("Cash Payment Selected");
                System.out.println("Please pay the amount to the driver.");
                System.out.println("Payment successful. Thank you for riding with us!");
                break;
            case 2:
                System.out.println("Card Payment Selected");
                System.out.println("Please enter your card details:");
                // Implement card payment logic here
                // For example, you can take card details and validate them.
                System.out.println("Payment successful. Thank you for riding with us!");
                break;
            default:
                System.out.println("Invalid choice. Please select a valid payment method.");
                break;
        }
    }
}
class Booking_Queue {
    int front;
    int rear;
    int Maxsize;
    Customer array[];
    Driver driverArray[];
    int graph[][];
    
    String[] cities = { "Pune", "Mumbai", "Nashik", "Dhule", "Parbhani", "Amalner" };

    Booking_Queue(int Maxsize) {
        front = -1;
        rear = -1;
        this.Maxsize = Maxsize;
        array = new Customer[Maxsize];
        driverArray = new Driver[Maxsize];

        // Initialize driverArray with valid drivers
        driverArray[0] = new Driver("Mr. Rajesh Tope", 1, 987654352);
        driverArray[1] = new Driver("Mr.Shivaay Oberoi", 2, 987234512);
        driverArray[2] = new Driver("Mr Prem Patil", 3, 932345533);
        driverArray[3] = new Driver("Mr.Jay Mishra", 4, 982345322);
        driverArray[4] = new Driver("Mr.Santosh So1"
        		+ "nar", 5, 987645262);
        graph = new int[][] {
            {0, 154, 214, 335, 379,0},
            {154, 0, 166, 323, 522,358},
            {214, 166, 0, 157, 376,0},
            {335, 323, 157, 0, 351,37},
            {379, 522, 376, 351, 0,383} ,
            {0,358,0,37,383,0}};
       
    }

    boolean isFull() {
        return rear == Maxsize;
    }

    boolean isEmpty() {
        return front == rear;
    }

    void enqueue(Customer obj) {
        if (isFull()) {
            System.out.println("Cab is full");
        } else {
            rear++;
            array[rear] = obj;
            Driver assignedDriver = null;

            for (int i = 0; i <= rear; i++) {
                if (driverArray[i].isAvailable) {
                    assignedDriver = driverArray[i];
                    break;
                }
            }

            if (assignedDriver != null) {
                System.out.println("Assigning " + assignedDriver.DriverName + " to " + obj.Name);
                assignedDriver.isAvailable = false;
                obj.assignDriver(assignedDriver);
            } else {
                System.out.println("No available drivers for " + obj.Name);
            }
        }
    }

    Customer dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        } else {
            Customer dequeuedCustomer = array[front + 1];
            front++;
            System.out.println("Dequeued element: " + dequeuedCustomer.Name);

            Driver assignedDriver = dequeuedCustomer.assignedDriver;
            assignedDriver.isAvailable = true;
            dequeuedCustomer.assignDriver(null);

            return dequeuedCustomer;
        }
    }
    
    //Dijkstra's Algorithm:
    void FindShortestPath(String startCity, String endCity) {
    	//String[] cities = { "Pune", "Mumbai", "Nashik", "Dhule", "Parbhani", "Amalner" };

      int startLocation = Arrays.asList(cities).indexOf(startCity); //to find index of start city
        int endLocation = Arrays.asList(cities).indexOf(endCity); //to find index of end city
        //Arrays.asList() is a method in Java that is used to convert an array into a List

        int[] distances = new int[graph.length];
        boolean[] visited = new boolean[graph.length];
        int[] parent = new int[graph.length];

        for (int i = 0; i < graph.length; i++) {
            distances[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

        distances[startLocation] = 0;

        for (int i = 0; i < graph.length - 1; i++) {
            int u = minDistance(distances, visited);
            visited[u] = true;

            for (int v = 0; v < graph.length; v++) {
                if (!visited[v] && graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE
                        && distances[u] + graph[u][v] < distances[v]) {
                    distances[v] = distances[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        // Output the shortest distance and path
        System.out.println("Shortest distance from location " + startLocation + " to location " + endLocation + ": " + distances[endLocation]+"Km");

        // Construct the path
        List<Integer> path = new ArrayList<>();
        int currentNode = endLocation;
        while (currentNode != startLocation) {
            path.add(currentNode);
            currentNode = parent[currentNode];
        }
        path.add(startLocation);
        Collections.reverse(path);

        System.out.println("Shortest path: " + path);
    }

    private int minDistance(int[] distances, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < min) {
                min = distances[i];
                minIndex = i;
            }
        }

        return minIndex;
   

        
   
    }
  }


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Cab obj = new Cab(5, "abc");
        Booking_Queue objj = new Booking_Queue(5);
        Customer obj1 = new Customer();
        boolean isLoggedIn = false; // New flag to track whether a user is logged in
        boolean isRegistered ;

        int flag;
        do {
            System.out.println("\n******************WELCOME TO OUR CAB BOOKING SYSTEM******************");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Book a Cab");
            System.out.println("4. View Driver Details");
            System.out.println("5. Start Journey");
            System.out.println("6. End Journey and Make Payment");
            System.out.println("7. Logout");
            System.out.println("*********************************************************************");
            System.out.print("\nEnter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    obj1.register();
                    break;
                case 2:
                    obj1.login();
                    isLoggedIn = true;
                    break;
                case 3:
                    if (!obj1.hasBooked && isLoggedIn) {
                        System.out.println("Book The Cab");
                        System.out.println("Available cities for booking: Pune, Mumbai, Nashik, Dhule, Parbhani, Amalner");
                        System.out.print("Enter the starting city: ");
                        String startCity = sc.next();
                        System.out.print("Enter the destination city: ");
                        String endCity = sc.next();
                        objj.FindShortestPath(startCity, endCity);
                        obj.select();
                        objj.enqueue(obj1);
                        obj1.hasBooked = true;
                        System.out.println("Booking successful! A driver will be assigned to you shortly.");
                    } else if (!isLoggedIn) {
                        System.out.println("You need to log in first.");
                    } else {
                        System.out.println("You have already made a booking in this session.");
                    }
                    break;
                case 4:
                    if (obj1.assignedDriver != null) {
                        System.out.println("****************** Driver Details ******************");
                        obj1.assignedDriver.Driverdetails(obj1);
                    } else {
                        System.out.println("No driver assigned for this customer.");
                    }
                    break;
                case 5:
                    System.out.println("****************** Journey Started ******************");
                    obj1.startJourney();
                    break;
                case 6:
                    System.out.println("****************** Journey Ended ******************");
                    obj1.endJourney();
                    obj1.payment(500.00); // You can calculate the actual fare and pass it here.
                    break;
                case 7:
                    obj1.logout();
                    isLoggedIn = false;
                    System.out.println("Logged out successfully.");
                    break;
            }

            System.out.print("Enter 1 to continue and 0 to exit: ");
            flag = sc.nextInt();
        } while (flag == 1);
    }
}
