package rukindergarten;
import java.util.*;
import java.io.File;
/**
 * This class is designed to test each method in the Transit file interactively
 * 
 * @author Ethan Chou 
 * @author Maksims Kurjanovics Kravcenko
 * @author Kal Pandit
 */

public class Driver {
    public static void main(String[] args) {
		String[] methods = {"makeClassroom", "seatStudents", "insertMusicalChairs", "playMusicalChairs", "addLateStudent", "deleteLeavingStudent"};
		String[] options = {"Test a new input file", "Test another method on the same file", "Quit"};
		int controlChoice = 1;
        Classroom studentClassroom = new Classroom();
		
		do {
			StdOut.print("Enter a layered list input file => ");
			String inputFile = StdIn.readLine();

			do {
				StdOut.println("\nWhat method would you like to test?");
				for (int i = 0; i < methods.length; i++) {
					StdOut.printf("%d. %s\n", i+1, methods[i]);
				}
				StdOut.print("Enter a number => ");
				int choice = Integer.parseInt(StdIn.readLine());

				switch (choice) {
					case 1:
					String seating = getSeatingFileChoice();
					studentClassroom = testMakeClassroom(inputFile, seating);
					studentClassroom.printClassroom();
					break;
                    case 2:
                        testSeatStudents(studentClassroom);
						studentClassroom.printClassroom();
                        break;
					case 3:
						testInsertMusicalChairs(studentClassroom);
						studentClassroom.printClassroom();

						break;
					case 4:
						testPlayMusicalChairs(studentClassroom);
						studentClassroom.printClassroom();
						break;
					case 5:
						testAddStudent(studentClassroom);
						studentClassroom.printClassroom();
						break;
					case 6:
					testDeleteStudent(studentClassroom);
					studentClassroom.printClassroom();
						break;
					default:
						StdOut.println("Not a valid option!");
				}
				StdIn.resetFile();
				StdOut.println("What would you like to do now?");
				for (int i = 0; i < 3; i++) {
					StdOut.printf("%d. %s\n", i+1, options[i]);
				}
				StdOut.print("Enter a number => ");
				controlChoice = Integer.parseInt(StdIn.readLine());
			} while (controlChoice == 2);
		} while (controlChoice == 1);
    }

    private static Classroom testMakeClassroom(String filename, String seatingFile) {
		// Call student's makeList method
	    Classroom studentClassroom = new Classroom();
		studentClassroom.makeClassroom(filename, seatingFile);
		return studentClassroom;
    }

	private static void testSeatStudents(Classroom studentClassroom) {
		studentClassroom.seatStudents();
		
	}

	private static void testInsertMusicalChairs(Classroom studentClassroom) {
		studentClassroom.insertMusicalChairs();
        
	}
	
	private static void testPlayMusicalChairs(Classroom studentClassroom) {
		StdOut.println("Here is the classroom after a long game of musical chairs: ");
		studentClassroom.playMusicalChairs();
		StdOut.println();
		
	}

	private static void testAddStudent(Classroom studentClassroom) {
        StdOut.print("\nWrite the student's first name -> ");
        String studentName = StdIn.readString();
		StdOut.print("\nWrite the student's last name -> ");
        String lastName = StdIn.readString();
		StdOut.print("\nWrite the student's height as a number -> ");
        int height = StdIn.readInt();
        studentClassroom.addLateStudent(studentName, lastName, height);
	}
	
	private static void testDeleteStudent(Classroom studentClassroom) {
        StdOut.print("\nWrite the student's first name -> ");
        String firstName = StdIn.readString();
		StdOut.print("\nWrite the student's last name -> ");
        String lastName = StdIn.readString();
        studentClassroom.deleteLeavingStudent(firstName, lastName);
	}
	private static String getSeatingFileChoice() {
        ArrayList<String> choices = new ArrayList<>();
        File[] allFiles = new File(".").listFiles();
        StdOut.println("What seating file do you want to test on?");
        int numChoice = 1;
        for (File f : allFiles) {
            if (f.getName().endsWith(".in") && f.getName().contains("seating")) {
                choices.add(numChoice + ". " + f.getName());
                numChoice++;
            }
        }
        for (int i = 0; i < choices.size(); i++) {
            StdOut.println(choices.get(i));
        }
        StdOut.print("Enter a number => ");
        int number = Integer.parseInt(StdIn.readLine());
        return choices.get(number - 1).split(" ")[1];
    }
}
