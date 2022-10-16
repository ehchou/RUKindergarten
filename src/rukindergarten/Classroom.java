package rukindergarten;
/**
 * This class represents a Classroom, with a 
 * studentsInLine pointer to the front of a linked list of students, a boolean array for filled 
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine;
    private SNode musicalChairs; // references the LAST student in the CLL.
    private boolean[][] seatingAvailability;
    private Student[][] studentsSitting;

    public Classroom(SNode l, SNode m, boolean[][] a, Student[][] s) {
		studentsInLine = l;
        musicalChairs = m;
		seatingAvailability = a;
        studentsSitting = s;
	}

    public Classroom() {
        this(null, null, null, null);
    }
    // CHANGE -- alphabetical 
    // makeClassroom -> initialize seating chart, too
    // insert two parameters -> studentInfo and seatingChart
    public void makeClassroom(String studentInfo, String seatingChart) {
        //WRITE YOUR CODE HERE
        studentsInLine = null;
        StdIn.setFile(studentInfo);
        int length = StdIn.readInt();
        // Read all 
        for (int i = 0; i < length; i++) {
            String f = StdIn.readString();
            String l = StdIn.readString();
            int h = StdIn.readInt();
            Student newStudent = new Student(f, l, h);
            SNode ptr = studentsInLine;
            if (ptr == null) {
                studentsInLine = new SNode(newStudent, null);
                ptr = studentsInLine;
            }
            else {
                if (ptr.getStudent().compareNameTo(newStudent) >= 0)
                {
                studentsInLine = new SNode(newStudent, ptr);
                ptr = studentsInLine;
                }
                else {
                    SNode curr = ptr;
                    while (curr.getNext() != null && curr.getNext().getStudent().compareNameTo(newStudent) < 0) {
                        curr = curr.getNext();
                    }
                    SNode newSNode = new SNode(newStudent, curr.getNext());
                    curr.setNext(newSNode);
                }

            }
        }
        StdIn.setFile(seatingChart);
        int rows = StdIn.readInt();
        int cols = StdIn.readInt();
        seatingAvailability = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String val = StdIn.readString();
                if (val.equals("T")) seatingAvailability[i][j] = true;
                else seatingAvailability[i][j] = false;
            }
        }
        studentsSitting = new Student[rows][cols];
    }
    //seatStudents
    public void seatStudents() {
        // check chairs first -- will only work if seatedStudents works
        if (musicalChairs != null && musicalChairs.getNext() == musicalChairs) {
            Student winner = musicalChairs.getStudent();
            boolean isMarked = false;
            musicalChairs = null; // clear out musical chairs
            for (int i = 0; i < seatingAvailability.length; i++) {
                for (int j = 0; j < seatingAvailability[0].length; j++) {
                if (isMarked == false && seatingAvailability[i][j] == true && studentsSitting[i][j] == null) {
                    seatingAvailability[i][j] = false;
                    studentsSitting[i][j] = winner;
                    isMarked = true;
                }
            }
        }
        }
        SNode ptr = getStudentsInLine(), prev = null;
            for (int i = 0; i < seatingAvailability.length; i++) {
                for (int j = 0; j < seatingAvailability[0].length; j++) {
                if (seatingAvailability[i][j] == true && studentsSitting[i][j] == null) {
                    if (ptr != null) {
                        prev = ptr;
                        studentsSitting[i][j] = ptr.getStudent();
                        studentsInLine = studentsInLine.getNext();
                        ptr = ptr.getNext();
                        prev = null;
                    }
                }
            }
        }
    }
    // print classroom all together!
    // handle empty classrooms and CLLs
    // points back to front
    public void insertMusicalChairs() {
        //WRITE YOUR CODE HERE
        SNode last = null, first = null;
        Student[][] studentsInChairs = getStudentsSitting();
        for (int i = 0; i < studentsInChairs.length; i++) {
            for (int j = 0; j < studentsInChairs[0].length; j++) {
                if (studentsInChairs[i][j] != null) {
                    seatingAvailability[i][j] = true;
                    Student studentToMove = studentsInChairs[i][j];
                    studentsSitting[i][j] = null;
                    SNode student = new SNode(studentToMove, null);
                    if (first == null) {
                        last = student;
                        first = student;
                        student.setNext(first);
                    }
                    else {
                        last.setNext(student);
                        last = student;
                        last.setNext(first);
                    }
                }
            }
        }
        musicalChairs = last;
    }
    // winner in chair and everyone else in eliminatedStudents
    public void playMusicalChairs() {
        //WRITE YOUR CODE HERE
        StdRandom.setSeed(2022);
        SNode next = getMusicalChairs().getNext(), ptr = null;
        int size = 1;
        while (next != getMusicalChairs()) {
            size++;
            next = next.getNext();
        }
        SNode previous = null;
        while (size > 1) {        
            ptr = getMusicalChairs().getNext();
            //number between 1 and the current amount of players
            int sizePtr = StdRandom.uniform(0, size);
            int eliminatePtr = 0;
            //Traversal through the CLL
            while (eliminatePtr < sizePtr) {
                previous = ptr;
                ptr = ptr.getNext();
                eliminatePtr++;
            }
            // break up entering by height -> no eliminatedStudents
            int compareByHeight = 0;
            Student eliminatedStudent = ptr.getStudent();
            // Deletion from cll
            if (getMusicalChairs().getNext().getStudent() == eliminatedStudent) {
                SNode front = getMusicalChairs().getNext();
                SNode last = getMusicalChairs();
                last.setNext(front.getNext());
            }
            else if (getMusicalChairs().getStudent() == eliminatedStudent) {
                previous.setNext(ptr.getNext());
                musicalChairs = previous;
            }
            else previous.setNext(ptr.getNext());
            size--;
            // sort out non-winning students as they come
            // refactor ptr here
            ptr = studentsInLine;
            if (ptr == null) {
                studentsInLine = new SNode(eliminatedStudent, null);
                ptr = studentsInLine;
            }
            else {
                if (eliminatedStudent.getHeight() >= studentsInLine.getStudent().getHeight()) compareByHeight = 1;
                else if (eliminatedStudent.getHeight() == studentsInLine.getStudent().getHeight()) compareByHeight = 0;
                else compareByHeight = -1;
                if (compareByHeight <= 0)
                {
                studentsInLine = new SNode(eliminatedStudent, ptr);
                ptr = studentsInLine;
                }
                else {
                    SNode curr = ptr;
                    while (curr.getNext() != null && curr.getNext().getStudent().getHeight() < eliminatedStudent.getHeight()) {
                        curr = curr.getNext();
                    }
                    SNode newSNode = new SNode(eliminatedStudent, curr.getNext());
                    curr.setNext(newSNode);
                }

            }
        }
        // need to put winner in -- first from musicalChairs and then into the seating chart


        seatStudents();
           } 
    public void addLateStudent(String firstName, String lastName, int height) {
        // Adds to the end of musical chairs
        if (musicalChairs != null) {
            SNode chairs = getMusicalChairs();
            chairs.setNext(new SNode(new Student(firstName, lastName, height), getMusicalChairs().getNext()));
            musicalChairs = chairs.getNext();
        }
        // Adds to end of student line
        else if (studentsInLine != null) {
            SNode ptr = getStudentsInLine();
            SNode prev = null;
           while (ptr != null) {
            prev = ptr;
            ptr = ptr.getNext();
           }
           prev.setNext(new SNode(new Student(firstName, lastName, height), null));
        }
        // Adds to the seating chart, in the first available seat
        else {
            boolean isMarked = false;
                for (int i = 0; i < seatingAvailability.length; i++) {
                    for (int j = 0; j < seatingAvailability[0].length; j++) {
                    if (isMarked == false && seatingAvailability[i][j] == true && studentsSitting[i][j] == null) {
                        seatingAvailability[i][j] = true;
                        studentsSitting[i][j] = new Student(firstName, lastName, height);
                        isMarked = true;
                    }
                }
            }
        }  
    }
    public void deleteLeavingStudent(String firstName, String lastName) {
        // Delete student from seating chart, if they're there
        for (int i = 0; i < seatingAvailability.length; i++) {
            for (int j = 0; j < seatingAvailability[0].length; j++) {
            if (studentsSitting[i][j] != null) {
                if (studentsSitting[i][j].getFirstName().equals(firstName) && studentsSitting[i][j].getLastName().equals(lastName)) {
                    studentsSitting[i][j] = null;
                    return;
                }
            }
        }
        // Delete student from musical chairs, if they're there
        if (getMusicalChairs() != null) {
        SNode cllPtr = getMusicalChairs().getNext(), previous = null;
        while (cllPtr != getMusicalChairs()) {
            previous = cllPtr;
            if (cllPtr.getStudent().getFirstName().equals(firstName) && cllPtr.getStudent().getLastName().equals(lastName)) {

                if (getMusicalChairs().getNext().getStudent() == cllPtr.getStudent()) {
                    SNode front = getMusicalChairs().getNext();
                    SNode last = getMusicalChairs();
                    last.setNext(front.getNext());
                }
                else if (getMusicalChairs().getStudent() == cllPtr.getStudent()) {
                    previous.setNext(cllPtr.getNext());
                    musicalChairs = previous;
                }
                else previous.setNext(cllPtr.getNext());

                return;
            }
            cllPtr = cllPtr.getNext();
        }
    }
    // They're in the line, delete from there
    else {
        SNode point = getStudentsInLine(), prev = null;
        if (point != null && point.getStudent().getFirstName().equals(firstName) && point.getStudent().getLastName().equals(lastName)) {
            studentsInLine = point.getNext();
            return;
        }
        else {
            while (point != null && !point.getStudent().getFirstName().equals(firstName) && !point.getStudent().getLastName().equals(lastName)) {
                prev = point;
                point = point.getNext();
            }
            if (point == null) prev = null;
            else prev.setNext(point.getNext());
        }
}
        }

    }

    /**
     * Used by driver to display Classroom
     * DO NOT edit.
     */
    public void printStudentsInLine() {
                //Print studentsInLine
                StdOut.println("Students in Line:");
                if (studentsInLine == null) StdOut.println("EMPTY");
                for (SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext()) {
                    StdOut.print(ptr.getStudent().getPrintName());
                    if (ptr.getNext() != null) {StdOut.print(" -> ");}
                }
                StdOut.println();
                StdOut.println();
    }
    public void printSeatedStudents() {
        StdOut.println("Sitting Students:");
        if (studentsSitting != null) {
            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[i].length; j++) {
                    String stringToPrint = "";
                    if (studentsSitting[i][j] == null) {
                        if (seatingAvailability[i][j] == false) {stringToPrint = "X";}
                        else stringToPrint = "EMPTY";
                    }
                    else {stringToPrint = studentsSitting[i][j].getPrintName();}
                    StdOut.print(stringToPrint);
                    for (int o = 0; o < (10 - stringToPrint.length()); o++) {
                        StdOut.print(" ");
                    }
                }
                StdOut.println();
            }
        }
        StdOut.println();
    }
    public void printMusicalChairs() {
        StdOut.println("Students in Musical Chairs:");
        if (musicalChairs == null) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for (ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().getPrintName() + " -> ");
        }
        if (ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().getPrintName() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingAvailability() { return seatingAvailability; }
    public void setSeatingAvailability(boolean[][] a) { seatingAvailability = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}
