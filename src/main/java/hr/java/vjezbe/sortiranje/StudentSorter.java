package hr.java.vjezbe.sortiranje;

import hr.java.vjezbe.entitet.Student;

import java.util.Comparator;

public class StudentSorter implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        int usporedi = s1.getPrezime().compareTo(s2.getPrezime());
        if(usporedi > 0){
            return 1;
        }
        else if(usporedi < 0){
            return -1;
        }
        else {
            usporedi = s1.getIme().compareTo(s2.getIme());

            if(usporedi > 0){
                return 1;
            }
            else if(usporedi < 0){
                return -1;
            }

            return 0;
        }
    }
}
