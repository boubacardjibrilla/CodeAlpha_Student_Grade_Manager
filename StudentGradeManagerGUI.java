import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    ArrayList<Double> grades;

    Student(String name) {
        this.name = name;
        grades = new ArrayList<>();
    }

    void addGrade(double grade) {
        grades.add(grade);
    }

    double getAverage() {
        double sum = 0;

        for (double g : grades) {
            sum += g;
        }

        return grades.size() > 0 ? sum / grades.size() : 0;
    }

    double getHighest() {
        double max = Double.MIN_VALUE;

        for (double g : grades) {
            if (g > max) {
                max = g;
            }
        }

        return grades.size() > 0 ? max : 0;
    }

    double getLowest() {
        double min = Double.MAX_VALUE;

        for (double g : grades) {
            if (g < min) {
                min = g;
            }
        }

        return grades.size() > 0 ? min : 0;
    }
}

public class StudentGradeManagerGUI extends JFrame {

    private JTextField nameField;
    private JTextField gradeField;
    private JTextArea reportArea;

    private ArrayList<Student> students;
    private Student currentStudent;

    public StudentGradeManagerGUI() {

        students = new ArrayList<>();

        setTitle("Student Grade Manager");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel nameLabel = new JLabel("Student Name:");
        nameField = new JTextField();

        JLabel gradeLabel = new JLabel("Grade:");
        gradeField = new JTextField();

        JButton createButton = new JButton("Create Student");
        JButton addGradeButton = new JButton("Add Grade");
        JButton showReportButton = new JButton("Show Report");

        reportArea = new JTextArea();
        reportArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(reportArea);

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(gradeLabel);
        panel.add(gradeField);

        panel.add(createButton);
        panel.add(addGradeButton);

        panel.add(showReportButton);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Create Student
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = nameField.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter a student name.");
                    return;
                }

                currentStudent = new Student(name);
                students.add(currentStudent);

                JOptionPane.showMessageDialog(null,
                        "Student created successfully!");

                nameField.setText("");
            }
        });

        // Add Grade
        addGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentStudent == null) {
                    JOptionPane.showMessageDialog(null,
                            "Create a student first.");
                    return;
                }

                try {

                    double grade =
                            Double.parseDouble(gradeField.getText());

                    if (grade < 0 || grade > 100) {
                        JOptionPane.showMessageDialog(null,
                                "Grade must be between 0 and 100.");
                        return;
                    }

                    currentStudent.addGrade(grade);

                    JOptionPane.showMessageDialog(null,
                            "Grade added successfully!");

                    gradeField.setText("");

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(null,
                            "Enter a valid number.");
                }
            }
        });

        // Show Report
        showReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                reportArea.setText("");

                for (Student s : students) {

                    reportArea.append(
                            "========================\n");

                    reportArea.append(
                            "Student: " + s.name + "\n");

                    reportArea.append(
                            "Grades: " + s.grades + "\n");

                    reportArea.append(
                            String.format("Average: %.2f\n",
                                    s.getAverage()));

                    reportArea.append(
                            String.format("Highest: %.2f\n",
                                    s.getHighest()));

                    reportArea.append(
                            String.format("Lowest: %.2f\n",
                                    s.getLowest()));

                    reportArea.append(
                            "========================\n\n");
                }
            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                new StudentGradeManagerGUI().setVisible(true);
            }
        });
    }
}