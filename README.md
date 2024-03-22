# Cafeshop Management System

Welcome to the Cafeshop Management System! This system is designed to manage employee accounts, handle user authentication, and provide a graphical interface for cafe operations.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Setup](#setup)
- [Usage](#usage)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Features

1. **User Authentication**
   - Login and registration functionalities for employees.
   - Forgot password feature.

2. **Main Form**
   - Graphical interface for cafe operations.
   - Allows employees to manage orders and inventory.

3. **Database Integration**
   - MySQL database for storing user information and system data.
   - Singleton pattern used for database connection management.

## Requirements

- Java Development Kit (JDK) 8 or later
- JavaFX SDK (if not included in JDK)
- MySQL database server
- JDBC driver for MySQL (included in most Java distributions)
- JasperReports library

## Setup

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/melita-pereira/Cafeshop.git
   ```

2. Import the project into your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

3. Create a MySQL database and set up the required tables.

4. Update the database connection details in the `Database.java` file if necessary.

## Usage

1. Run the `App.java` file to start the application.

2. On the login screen, use the provided credentials or register a new account.

3. Once logged in, you will be directed to the main form where you can manage cafe operations.

## Technologies Used

- Java SE
- JavaFX
- MySQL
- JDBC
- JasperReport
- CSS
- Singleton Design Pattern
- Factory Method Design Pattern

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/improvement`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature/improvement`).
6. Create a new Pull Request.

## License

This project is licensed under the [MIT License](LICENSE).
