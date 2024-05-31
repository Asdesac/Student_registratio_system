import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentsInformationSystem extends JFrame {
    private Connection con;
    private Statement sta;

    private JLabel lbl_student_id, lbl_student_name, lbl_student_age, lbl_student_contact;
    private JTextField txt_student_id, txt_student_name, txt_student_age, txt_student_contact;
    private JRadioButton rb_male, rb_female;
    private ButtonGroup genderGroup;
    private JButton btn_add, btn_edit, btn_delete, btn_search;
    private JTextField txt_search;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    private ArrayList<Student> studentList;

    private static final String FILE_PATH = "student_data.txt";

    public StudentsInformationSystem() {
        super("Students Information System");

        // Initialize components
        lbl_student_id = createLabel("Student ID");
        txt_student_id = createTextField();
        lbl_student_name = createLabel("Student Name");
        txt_student_name = createTextField();
        lbl_student_age = createLabel("Age");
        txt_student_age = createTextField();
        lbl_student_contact = createLabel("Contact");
        txt_student_contact = createTextField();

        rb_male = new JRadioButton("Male");
        rb_female = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(rb_male);
        genderGroup.add(rb_female);

        btn_add = createButton("Add", new Color(0, 204, 0));
        btn_edit = createButton("Edit", new Color(148, 0, 211));
        btn_delete = createButton("Delete", new Color(255, 0, 0));
        btn_search = createButton("Search", new Color(0, 102, 204));

        txt_search = createTextField();

        studentList = new ArrayList<>();

        // Initialize table
        String[] columns = {"Student ID", "Name", "Age", "Gender", "Contact"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(studentTable);

        // Set custom table header and row colors
        JTableHeader header = studentTable.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        studentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        studentTable.setRowHeight(25);
        studentTable.setSelectionBackground(new Color(70, 130, 180));
        studentTable.setSelectionForeground(Color.WHITE);

        // Database connection
        try {
            String username = "root";
            String password = "";
            String port = "3308";
            String server = "localhost";
            String db_file = "student_result";
            String url = "jdbc:mysql://" + server + ":" + port + "/" + db_file + "?useSSL=false";

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, username, password);
            sta = con.createStatement();
            System.out.println("Database connection established");
        } catch (Exception exp) {
            System.out.println(exp);
        }

        // Layout
        JPanel panel_main = new JPanel(new BorderLayout());
        JPanel panel_north = new JPanel(new GridBagLayout());
        JPanel panel_center = new JPanel(new BorderLayout());
        JPanel panel_south = new JPanel(new FlowLayout());

        panel_main.setBackground(new Color(135, 206, 250));
        panel_north.setBackground(new Color(135, 206, 250));
        panel_center.setBackground(new Color(135, 206, 250));
        panel_south.setBackground(new Color(135, 206, 250));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        panel_north.add(lbl_student_id, c);
        c.gridx = 1;
        c.gridwidth = 2;
        panel_north.add(txt_student_id, c);
        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 1;
        panel_north.add(lbl_student_name, c);
        c.gridx = 1;
        c.gridwidth = 2;
        panel_north.add(txt_student_name, c);
        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 2;
        panel_north.add(lbl_student_age, c);
        c.gridx = 1;
        c.gridwidth = 2;
        panel_north.add(txt_student_age, c);
        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 3;
        panel_north.add(new JLabel("Gender"), c);
        c.gridx = 1;
        panel_north.add(rb_male, c);
        c.gridx = 2;
        panel_north.add(rb_female, c);

        c.gridx = 0;
        c.gridy = 4;
        panel_north.add(lbl_student_contact, c);
        c.gridx = 1;
        c.gridwidth = 2;
        panel_north.add(txt_student_contact, c);
        c.gridwidth = 1;

        c.gridx = 3;
        c.gridy = 0;
        panel_north.add(btn_add, c);
        c.gridy = 1;
        panel_north.add(btn_edit, c);
        c.gridy = 2;
        panel_north.add(btn_delete, c);

        c.gridx = 0;
        c.gridy = 5;
        panel_north.add(new JLabel("Search:"), c);
        c.gridx = 1;
        panel_north.add(txt_search, c);
        c.gridx = 2;
        panel_north.add(btn_search, c);

        panel_center.add(scrollPane, BorderLayout.CENTER);

        panel_main.add(panel_north, BorderLayout.NORTH);
        panel_main.add(panel_center, BorderLayout.CENTER);
        panel_main.add(panel_south, BorderLayout.SOUTH);

        add(panel_main);

        // Load initial student data
        loadStudentData();

        // Event listeners
        btn_add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addStudent();
                clearInputFields();
            }
        });

        btn_edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                editStudent();
                clearInputFields();
            }
        });

        btn_delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                deleteStudent();
                clearInputFields();
            }
        });

        btn_search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                searchStudents();
            }
        });

        studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = studentTable.getSelectedRow();
                    if (selectedRow != -1) {
                        displayStudentDetails(selectedRow);
                    }
                }
            }
        });

        // Frame settings
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(0, 102, 204));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20); // Make the input field wider
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        return textField;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private boolean validateInputs() {
        String studentID = txt_student_id.getText().trim();
        String studentName = txt_student_name.getText().trim();
        String age = txt_student_age.getText().trim();
        String contact = txt_student_contact.getText().trim();

        // Check if any fields are empty
        if (studentID.isEmpty() || studentName.isEmpty() || age.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled out", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if contact starts with "09" and is 10 digits long
        if (!contact.matches("^09\\d{8}$")) {
            JOptionPane.showMessageDialog(null, "Contact must start with '09' and be 10 digits long", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if age is between 10 and 100
        try {
            int ageInt = Integer.parseInt(age);
            if (ageInt < 10 || ageInt > 100) {
                JOptionPane.showMessageDialog(null, "Age must be between 10 and 100", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Age must be a number", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if student name contains only letters, spaces, and underscores
        if (!studentName.matches("^[a-zA-Z_ ]+$")) {
            JOptionPane.showMessageDialog(null, "Student name can only contain letters, spaces, and underscores", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void addStudent() {
        if (validateInputs()) {
            try {
                String studentID = txt_student_id.getText().trim();
                String studentName = txt_student_name.getText().trim();
                String age = txt_student_age.getText().trim();
                String gender = rb_male.isSelected() ? "Male" : "Female";
                String contact = txt_student_contact.getText().trim();

                // Insert into database
                String sql = "INSERT INTO students (student_id, student_name, age, gender, contact) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, studentID);
                pst.setString(2, studentName);
                pst.setString(3, age);
                pst.setString(4, gender);
                pst.setString(5, contact);
                pst.executeUpdate();

                // Update local list
                studentList.add(new Student(studentID, studentName, age, gender, contact));

                // Update table
                Object[] rowData = {studentID, studentName, age, gender, contact};
                tableModel.addRow(rowData);

                // Save to file system
                saveStudentToFile(new Student(studentID, studentName, age, gender, contact));

                JOptionPane.showMessageDialog(null, "Student Added Successfully");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            if (validateInputs()) {
                try {
                    String studentID = txt_student_id.getText().trim();
                    String studentName = txt_student_name.getText().trim();
                    String age = txt_student_age.getText().trim();
                    String gender = rb_male.isSelected() ? "Male" : "Female";
                    String contact = txt_student_contact.getText().trim();

                    // Update in database
                    String sql = "UPDATE students SET student_name = ?, age = ?, gender = ?, contact = ? WHERE student_id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, studentName);
                    pst.setString(2, age);
                    pst.setString(3, gender);
                    pst.setString(4, contact);
                    pst.setString(5, studentID);
                    pst.executeUpdate();

                    // Update local list
                    Student student = studentList.get(selectedRow);
                    student.setName(studentName);
                    student.setAge(age);
                    student.setGender(gender);
                    student.setContact(contact);

                    // Update table
                    tableModel.setValueAt(studentName, selectedRow, 1);
                    tableModel.setValueAt(age, selectedRow, 2);
                    tableModel.setValueAt(gender, selectedRow, 3);
                    tableModel.setValueAt(contact, selectedRow, 4);

                    // Save to file system
                    saveAllStudentsToFile();

                    JOptionPane.showMessageDialog(null, "Student Updated Successfully");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a student to edit");
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String studentID = (String) tableModel.getValueAt(selectedRow, 0);

                // Delete from database
                String sql = "DELETE FROM students WHERE student_id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, studentID);
                pst.executeUpdate();

                // Update local list
                studentList.remove(selectedRow);

                // Update table
                tableModel.removeRow(selectedRow);

                // Save to file system
                saveAllStudentsToFile();

                JOptionPane.showMessageDialog(null, "Student Deleted Successfully");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a student to delete");
        }
    }

    private void searchStudents() {
        String searchText = txt_search.getText().trim().toLowerCase();
        tableModel.setRowCount(0); // Clear the table

        for (Student student : studentList) {
            if (student.getId().toLowerCase().contains(searchText) ||
                    student.getName().toLowerCase().contains(searchText) ||
                    student.getAge().toLowerCase().contains(searchText) ||
                    student.getGender().toLowerCase().contains(searchText) ||
                    student.getContact().toLowerCase().contains(searchText)) {
                Object[] rowData = {student.getId(), student.getName(), student.getAge(), student.getGender(), student.getContact()};
                tableModel.addRow(rowData);
            }
        }
    }

    private void displayStudentDetails(int rowIndex) {
        Student student = studentList.get(rowIndex);
        txt_student_id.setText(student.getId());
        txt_student_name.setText(student.getName());
        txt_student_age.setText(student.getAge());
        if (student.getGender().equals("Male")) {
            rb_male.setSelected(true);
        } else {
            rb_female.setSelected(true);
        }
        txt_student_contact.setText(student.getContact());
    }

    private void loadStudentData() {
        try {
            String sql = "SELECT * FROM students";
            ResultSet rs = sta.executeQuery(sql);
            while (rs.next()) {
                String studentID = rs.getString("student_id");
                String studentName = rs.getString("student_name");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String contact = rs.getString("contact");

                studentList.add(new Student(studentID, studentName, age, gender, contact));

                Object[] rowData = {studentID, studentName, age, gender, contact};
                tableModel.addRow(rowData);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveStudentToFile(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(student.toFileFormat());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAllStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : studentList) {
                writer.write(student.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearInputFields() {
        txt_student_id.setText("");
        txt_student_name.setText("");
        txt_student_age.setText("");
        genderGroup.clearSelection();
        txt_student_contact.setText("");
    }

    public static void main(String[] args) {
        new LoginSystem();
    }
}

class Student {
    private String id;
    private String name;
    private String age;
    private String gender;
    private String contact;

    public Student(String id, String name, String age, String gender, String contact) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContact() {
        return contact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String toFileFormat() {
        return id + "," + name + "," + age + "," + gender + "," + contact;
    }
}

class LoginSystem extends JFrame {
    private JTextField txt_username;
    private JPasswordField txt_password;
    private JButton btn_login;

    public LoginSystem() {
        super("Admin Login");

        // Initialize components
        JLabel lbl_username = new JLabel("Username");
        txt_username = new JTextField(15);
        JLabel lbl_password = new JLabel("Password");
        txt_password = new JPasswordField(15);
        btn_login = new JButton("Login");

        // Layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(lbl_username, c);
        c.gridx = 1;
        panel.add(txt_username, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(lbl_password, c);
        c.gridx = 1;
        panel.add(txt_password, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        panel.add(btn_login, c);

        add(panel);

        // Event listener for login button
        btn_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                login();
            }
        });

        // Frame settings
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void login() {
        String username = txt_username.getText();
        String password = new String(txt_password.getPassword());

        if (username.equals("admin") && password.equals("admin123")) {
            new StudentsInformationSystem();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
