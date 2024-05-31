<!DOCTYPE html>
<html>
<head>
    <title>README - Students Information System</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #4682B4;
            text-align: center;
            font-size: 36px;
            font-weight: bold;
        }
        h2 {
            color: #4169E1;
            font-size: 28px;
            margin-top: 40px;
        }
        p {
            font-size: 18px;
            line-height: 1.6;
        }
        .section {
            margin: 20px 0;
        }
        .highlight {
            color: #FF4500;
            font-weight: bold;
        }
        .button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px 2px;
            cursor: pointer;
            border-radius: 8px;
        }
        .button:hover {
            background-color: #45a049;
        }
        .code-block {
            background-color: #f4f4f4;
            padding: 10px;
            border-left: 5px solid #4682B4;
            font-family: 'Courier New', monospace;
        }
    </style>
</head>
<body>
    <h1>Students Information System</h1>
    <div class="section">
        <h2>Overview</h2>
        <p>Welcome to the <span class="highlight">Students Information System</span> project. This application is designed to manage student information, including their ID, name, age, gender, and contact details. The system allows you to add, edit, delete, and search for student records efficiently.</p>
    </div>
    <div class="section">
        <h2>Features</h2>
        <ul>
            <li><span class="highlight">Add</span> new student records</li>
            <li><span class="highlight">Edit</span> existing student records</li>
            <li><span class="highlight">Delete</span> student records</li>
            <li><span class="highlight">Search</span> for student records</li>
        </ul>
    </div>
    <div class="section">
        <h2>Getting Started</h2>
        <p>To get started with the Students Information System, follow the steps below:</p>
        <div class="code-block">
            <p>1. Clone the repository:</p>
            <pre><code>git clone https://github.com/your-repo/students-information-system.git</code></pre>
            <p>2. Open the project in your preferred IDE (e.g., IntelliJ, Eclipse).</p>
            <p>3. Ensure you have MySQL installed and running.</p>
            <p>4. Create a database named <span class="highlight">student_result</span> and run the provided SQL script to set up the necessary tables.</p>
            <p>5. Update the database connection settings in the <span class="highlight">StudentsInformationSystem</span> class if necessary.</p>
            <p>6. Run the project.</p>
        </div>
    </div>
    <div class="section">
        <h2>Usage</h2>
        <p>Upon running the project, you will be prompted with a login screen. Use the following credentials to access the system:</p>
        <ul>
            <li>Username: <span class="highlight">admin</span></li>
            <li>Password: <span class="highlight">admin123</span></li>
        </ul>
        <p>After logging in, you can start managing student information using the buttons and fields provided.</p>
    </div>
    <div class="section">
        <h2>Contributing</h2>
        <p>We welcome contributions from the community. To contribute, please follow these steps:</p>
        <div class="code-block">
            <p>1. Fork the repository.</p>
            <p>2. Create a new branch for your feature or bug fix:</p>
            <pre><code>git checkout -b feature-name</code></pre>
            <p>3. Commit your changes:</p>
            <pre><code>git commit -m "Description of your changes"</code></pre>
            <p>4. Push to the branch:</p>
            <pre><code>git push origin feature-name</code></pre>
            <p>5. Create a pull request.</p>
        </div>
    </div>
    <div class="section">
        <h2>License</h2>
        <p>This project is licensed under the MIT License. See the <a href="LICENSE">LICENSE</a> file for more details.</p>
    </div>
    <div class="section" style="text-align: center;">
        <a href="https://github.com/your-repo/students-information-system" class="button">View on GitHub</a>
    </div>
</body>
</html>
