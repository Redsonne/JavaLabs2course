

public class Student implements Comparable<Student> {
    String surname;
    int id;
    int course;
    int group;

    public Student(int id, String surname, int course, int group) {
        this.surname = surname;
        this.id = id;
        this.course = course;
        this.group = group;
    }

    public String getSurname() {
        return this.surname;
    }

    public int getCourse() {
        return this.course;
    }

    public int getGroup() {
        return this.group;
    }

    public int getId() {
        return this.id;
    }

    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id);
    }

    public String toString() {
        return this.id + " " + this.surname + " " + this.course + " " + this.group;
    }
}
