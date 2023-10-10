## License Management System

### Overview

The License Management System is a web-based application designed to help enterprises keep track of their network devices and software licenses. It provides a user-friendly dashboard for monitoring the status of licenses, sends email notifications to remind users of license expirations, and allows for easy updates of license status.

### Technology Stack

- **Frontend**: React
- **Backend**: Java 11, Spring Boot 3.1.1
- **Database**: MySQL
- **Testing**: Mockito
- **Graphs**: Chart.js (for visualizing data)

### Features

1. **Device and Software Tracking**: Keep a comprehensive record of network devices and software licenses within your enterprise. Store relevant details such as device/software name, license number, company name, purchase date, expiry date, and more.

2. **Email Notifications**: Automatically send email notifications at regular intervals to remind users about upcoming license expirations. These notifications will help users take timely action to renew or update licenses.

3. **User-Friendly Dashboard**: Access a visually appealing dashboard that displays the current status of all licenses. The dashboard provides a quick overview of which licenses are active, expired, or nearing expiration.

4. **License Status Updates**: Easily update the status of licenses through the UI interface. Mark licenses as active, expired, or any other relevant status as needed.

5. **License Expiration Detection**: The system automatically detects when a software or device license is about to expire based on the expiry date. It sends notifications to the admin and relevant stakeholders.

### Getting Started

To get started with the License Management System, follow these steps:

1. **Clone the Repository**: Clone this GitHub repository to your local development environment.

2. **Configure the Backend**:
   - Set up your MySQL database and configure the database connection in the Spring Boot application.properties file.
   - Configure email settings in the application.properties file, including SMTP server details and email credentials.

3. **Run the Backend**: Start the Spring Boot backend by running the main application class.

4. **Configure the Frontend**:
   - Navigate to the `frontend` directory.
   - Install frontend dependencies using `npm install`.
   - Configure the backend API URL in the frontend code if necessary.

5. **Run the Frontend**: Start the React frontend by running `npm start`.

6. **Access the Application**: Open your web browser and navigate to the URL where the frontend is hosted (usually `http://localhost:3000` by default).

### Testing

The License Management System uses Mockito for unit testing. You can write test cases to ensure the reliability and correctness of your application.

### Visualizations

The system uses Chart.js for generating graphical visualizations to represent license status and trends. You can customize these graphs as needed to display the data most effectively.

### Contributions

Contributions to this project are welcome. If you'd like to improve the License Management System or add new features, please fork the repository and submit a pull request.

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.