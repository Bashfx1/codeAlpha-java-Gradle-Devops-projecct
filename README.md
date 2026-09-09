# codeAlpha-java-Gradle-Devops-projecct
this is an internship project for CodeAlpha
# Java Gradle CI/CD Pipeline with GitHub Actions & AWS EC2

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge\&logo=openjdk)
![Gradle](https://img.shields.io/badge/Gradle-9.7.1-02303A?style=for-the-badge\&logo=gradle)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-CI%2FCD-2088FF?style=for-the-badge\&logo=githubactions)
![AWS EC2](https://img.shields.io/badge/AWS-EC2-FF9900?style=for-the-badge\&logo=amazonaws)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

## 📌 Overview

This project demonstrates a **Continuous Integration and Continuous Delivery (CI/CD) pipeline** for a Java application built with Gradle.

The pipeline automatically:

1. Runs automated tests.
2. Builds the Java application.
3. Packages the application as an executable JAR file.
4. Stores the JAR as a GitHub Actions artifact.
5. Deploys the JAR to an AWS EC2 instance.
6. Starts the application automatically on the EC2 server.

The goal of this project is to demonstrate core DevOps principles by automating the software build, testing, and deployment process.

---

## 🏗️ Architecture

```text
                 Developer
                     │
                     │ git push
                     ▼
              ┌───────────────┐
              │    GitHub     │
              │   Repository  │
              └───────┬───────┘
                      │
                      ▼
             ┌──────────────────┐
             │  GitHub Actions  │
             │                  │
             │  1. Checkout     │
             │  2. Test         │
             │  3. Build        │
             │  4. Package JAR  │
             └────────┬─────────┘
                      │
                      │ Artifact
                      ▼
             ┌──────────────────┐
             │   EC2 Deployment │
             │                  │
             │  SCP → SSH       │
             └────────┬─────────┘
                      │
                      ▼
             ┌──────────────────┐
             │    AWS EC2       │
             │    Ubuntu        │
             │                  │
             │  Java 17         │
             │  app.jar         │
             └────────┬─────────┘
                      │
                      ▼
                Java Application
```

---

## 🛠️ Technologies Used

| Technology         | Purpose                                    |
| ------------------ | ------------------------------------------ |
| **Java 17**        | Application development                    |
| **Gradle 9.7.1**   | Build automation and dependency management |
| **JUnit**          | Automated testing                          |
| **Git & GitHub**   | Source code management                     |
| **GitHub Actions** | CI/CD automation                           |
| **AWS EC2**        | Application deployment                     |
| **Ubuntu**         | EC2 operating system                       |
| **SSH**            | Secure remote deployment                   |
| **SCP**            | Secure JAR file transfer                   |

---

## 📂 Project Structure

```text
java-gradle-devops/
│
├── .github/
│   └── workflows/
│       └── ci-cd.yml
│
├── app/
│   ├── build/
│   │   └── libs/
│   │       └── app.jar
│   │
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/
│   │   │           └── bash/
│   │   │               └── app/
│   │   │                   └── App.java
│   │   │
│   │   └── test/
│   │       └── java/
│   │           └── com/
│   │               └── bash/
│   │                   └── app/
│   │                       └── AppTest.java
│   │
│   └── build.gradle
│
├── gradle/
│   └── ...
│
├── gradlew
├── gradlew.bat
├── gradle.properties
├── settings.gradle
└── README.md
```

---

## ⚙️ Application

The application is a simple Java command-line application that accepts a name and returns a greeting.

Example:

```bash
java -jar app.jar Bash
```

Output:

```text
Hello, Bash!
```

If no name is provided:

```bash
java -jar app.jar
```

Output:

```text
Hello, Guest!
```

---

# 🔄 CI/CD Pipeline

The pipeline is triggered automatically when code is pushed to the `main` branch.

### 1. Checkout

GitHub Actions checks out the latest source code:

```yaml
- name: Checkout code
  uses: actions/checkout@v4
```

### 2. Set Up Java

The workflow installs and configures JDK 17:

```yaml
- name: Set up JDK 17
  uses: actions/setup-java@v4
  with:
    java-version: '17'
    distribution: 'temurin'
```

### 3. Set Up Gradle

Gradle is configured using the official GitHub Actions Gradle setup:

```yaml
- name: Set up Gradle
  uses: gradle/actions/setup-gradle@v4
```

### 4. Run Tests

Automated tests are executed before deployment:

```bash
./gradlew test
```

If the tests fail, the deployment job does not run.

### 5. Build Application

The application is compiled and packaged:

```bash
./gradlew clean build
```

This produces an executable JAR:

```text
app/build/libs/app.jar
```

### 6. Upload Build Artifact

The generated JAR is uploaded as a GitHub Actions artifact:

```yaml
- name: Upload JAR
  uses: actions/upload-artifact@v4
```

### 7. Deploy to AWS EC2

After a successful build, GitHub Actions downloads the JAR and transfers it to the EC2 instance using SCP.

The application is deployed to:

```text
/home/ubuntu/app/app.jar
```

### 8. Start Application

The deployment workflow starts the Java application remotely through SSH:

```bash
nohup java -jar /home/ubuntu/app/app.jar \
  > /home/ubuntu/app/app.log 2>&1 &
```

The application output can be viewed with:

```bash
cat /home/ubuntu/app/app.log
```

---

# 🔐 GitHub Secrets

Sensitive EC2 connection information is stored using **GitHub Actions Secrets** rather than hard-coded in the workflow.

The following secrets are configured:

| Secret         | Description             |
| -------------- | ----------------------- |
| `EC2_HOST`     | EC2 public IPv4 address |
| `EC2_USERNAME` | EC2 SSH username        |
| `EC2_SSH_KEY`  | EC2 private SSH key     |

Secrets are referenced in the workflow using:

```yaml
${{ secrets.EC2_HOST }}
${{ secrets.EC2_USERNAME }}
${{ secrets.EC2_SSH_KEY }}
```

No private SSH credentials are stored in the repository.

---

# 🚀 Running Locally

## Prerequisites

Make sure you have:

* Java 17
* Git
* Gradle Wrapper included in the project

Clone the repository:

```bash
git clone https://github.com/Bashfx1/codeAlpha-java-Gradle-Devops-projecct.git
```

Navigate into the project:

```bash
cd codeAlpha-java-Gradle-Devops-projecct
```

Make the Gradle wrapper executable if necessary:

```bash
chmod +x gradlew
```

Run the tests:

```bash
./gradlew test
```

Build the application:

```bash
./gradlew clean build
```

Run the JAR:

```bash
java -jar app/build/libs/app.jar
```

Expected output:

```text
Hello, Guest!
```

You can also provide a name:

```bash
java -jar app/build/libs/app.jar Bash
```

Expected output:

```text
Hello, Bash!
```

---

# ☁️ AWS EC2 Deployment

The application is deployed to an Ubuntu-based AWS EC2 instance.

The deployment directory is:

```text
/home/ubuntu/app
```

The deployed application:

```text
/home/ubuntu/app/app.jar
```

Application logs:

```text
/home/ubuntu/app/app.log
```

PID file:

```text
/home/ubuntu/app/app.pid
```

To manually view the application log:

```bash
cat /home/ubuntu/app/app.log
```

---

# 🔁 Continuous Delivery Flow

Every push to `main` follows this process:

```text
git push
    ↓
GitHub
    ↓
GitHub Actions
    ↓
Checkout Code
    ↓
Run Tests
    ↓
Gradle Build
    ↓
Create JAR
    ↓
Upload Artifact
    ↓
Download Artifact
    ↓
SCP → EC2
    ↓
SSH → EC2
    ↓
Start Java Application
    ↓
Deployment Complete ✅
```

This removes the need to manually build and copy the application after every code change.

---

# 🎯 Project Objectives

The main objectives of this project were to:

* Understand Continuous Integration.
* Understand Continuous Delivery.
* Automate Java application builds.
* Automate testing.
* Create an executable Java JAR.
* Build a GitHub Actions workflow.
* Secure deployment credentials using GitHub Secrets.
* Deploy an application to AWS EC2.
* Automate remote application deployment using SSH.
* Apply practical DevOps principles to a Java project.

---

# 📚 DevOps Concepts Demonstrated

This project demonstrates practical experience with:

* **Version Control**
* **Continuous Integration**
* **Continuous Delivery**
* **Build Automation**
* **Automated Testing**
* **Artifact Management**
* **Cloud Infrastructure**
* **Secure Credential Management**
* **Remote Deployment**
* **Infrastructure/Application Automation**

---

# 🔮 Future Improvements

Possible improvements for future versions include:

* Add a web API using Spring Boot.
* Add automated application health checks.
* Add deployment notifications.
* Add separate staging and production environments.
* Add deployment rollback functionality.
* Add infrastructure provisioning with Terraform.
* Add application monitoring.

> These improvements are intentionally outside the current project scope. The current implementation focuses on understanding the fundamentals of CI/CD and Java deployment.

---

# 👨‍💻 Author

**Bashir Shaibu**

Cloud Engineering / DevOps Enthusiast

Focused on:

* Cloud Engineering
* DevOps
* CI/CD
* AWS
* Linux
* Automation

---

## 📄 License

This project is licensed under the MIT License.

