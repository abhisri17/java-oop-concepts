/**
 * Encapsulation example:
 * StudentRecord with marks, computed grade, and validation.
 */
public class StudentRecord {

    private String rollNumber;
    private String name;
    private int totalMarks; // 0-500

    public StudentRecord(String rollNumber, String name, int totalMarks) {
        this.rollNumber = rollNumber;
        this.name = name;
        setTotalMarks(totalMarks);
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        if (totalMarks >= 0 && totalMarks <= 500) {
            this.totalMarks = totalMarks;
        } else {
            System.out.println("Marks must be between 0 and 500.");
        }
    }

    public String getGrade() {
        double percentage = (totalMarks / 500.0) * 100;
        if (percentage >= 80) return "A";
        else if (percentage >= 60) return "B";
        else if (percentage >= 40) return "C";
        else return "D";
    }

    public static void main(String[] args) {
        StudentRecord s = new StudentRecord("R001", "Ravi", 380);
        System.out.println("Grade: " + s.getGrade());
    }
}