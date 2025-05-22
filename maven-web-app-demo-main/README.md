
```markdown
# DevOps Academy - Maven Web Application

This is a simple Java Maven-based Spring MVC web application packaged as a WAR file. The project demonstrates a full DevOps CI/CD pipeline with the following stack:

- âœ… **Maven** for build management  
- âœ… **SonarQube** for code quality analysis  
- âœ… **Nexus** for artifact storage  
- âœ… **Docker** for containerization  
- âœ… **Apache Tomcat** for deployment  
- âœ… **Kubernetes** for container orchestration  
- âœ… **Jenkins** for CI/CD automation  

---

## í´§ Project Structure

```
maven-web-application/
â”œâ”€â”€ src/
â”‚   â”œâ”€â”€ main/
â”‚   â”‚   â””â”€â”€ java/com/mt/controller/HelloController.java
â”‚   â””â”€â”€ test/
â”‚       â””â”€â”€ java/com/mt/controller/HelloControllerTest.java
â”œâ”€â”€ pom.xml
â”œâ”€â”€ Dockerfile
â”œâ”€â”€ Jenkinsfile
â””â”€â”€ k8s-deployment.yaml
```

---

## íº€ Getting Started

### í·° Prerequisites

Ensure the following are installed:

- Java 8
- Maven 3.8+
- Docker
- Jenkins
- Kubernetes cluster (minikube, EKS, etc.)
- SonarQube
- Nexus Repository Manager

---

## í³¦ Maven Build

```bash
mvn clean install
```

This will generate the `range-rover.war` under `target/`.

---

## í³Š SonarQube Integration

Configure your `pom.xml` with:

```xml
<properties>
  <sonar.host.url>http://<sonarqube-ip>:9000</sonar.host.url>
  <sonar.login>your-token</sonar.login>
</properties>
```

To analyze:

```bash
mvn sonar:sonar
```

---

## í¾¯ Nexus Integration

In your `pom.xml`, set the `distributionManagement`:

```xml
<distributionManagement>
  <repository>
    <id>nexus</id>
    <url>http://<nexus-ip>:8081/repository/maven-releases/</url>
  </repository>
  <snapshotRepository>
    <id>nexus</id>
    <url>http://<nexus-ip>:8081/repository/maven-snapshots/</url>
  </snapshotRepository>
</distributionManagement>
```

Deploy with:

```bash
mvn deploy
```

---

## í°³ Docker Setup

### Dockerfile

```Dockerfile
FROM tomcat:9.0-jdk8-openjdk
RUN rm -rf /usr/local/tomcat/webapps/*
COPY target/range-rover.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
```

### Build & Run Docker

```bash
docker build -t devops-academy .
docker run -p 8080:8080 devops-academy
```

---

## â˜¸ï¸ Kubernetes Setup

### k8s-deployment.yaml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: devops-academy-app
spec:
  replicas: 2
  selector:
    matchLabels:
      app: devops-academy
  template:
    metadata:
      labels:
        app: devops-academy
    spec:
      containers:
      - name: webapp
        image: your-dockerhub/devops-academy:latest
        ports:
        - containerPort: 8080
---
apiVersion: v1
kind: Service
metadata:
  name: devops-academy-service
spec:
  type: LoadBalancer
  selector:
    app: devops-academy
  ports:
  - port: 80
    targetPort: 8080
```

### Apply

```bash
kubectl apply -f k8s-deployment.yaml
```

---

## í´ Jenkins Pipeline

### Jenkinsfile

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'
        jdk 'Java 8'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/m-pasima/maven-web-app-demo.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Code Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    dockerImage = docker.build("your-dockerhub/devops-academy:${env.BUILD_NUMBER}")
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        dockerImage.push()
                        dockerImage.push("latest")
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withKubeConfig([credentialsId: 'kubeconfig-id']) {
                    sh 'kubectl apply -f k8s-deployment.yaml'
                }
            }
        }
    }
}
```

---

## í³ Maven Local Repo Setup (Optional)

### Global Settings

Create or edit:

```bash
vi /opt/maven/conf/settings.xml
```

Add:

```xml
<localRepository>/opt/maven/repo</localRepository>
```

---

## í³Œ Best Practices

- Use `.m2/settings.xml` for local developer secrets
- Keep `pom.xml` environment-neutral
- Externalize credentials using Jenkins secrets or Kubernetes Secrets
- Use `mvn clean verify` in CI

---

## í¹‹ Support

For issues or improvements, open an issue on the GitHub repo or reach out to [Pasima](https://m-pasima.github.io/The-DevOps-Academy/).

---

## Â© DevOps Academy

```


