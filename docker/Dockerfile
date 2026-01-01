# Selenium Automation Framework Dockerfile
FROM maven:3.9-eclipse-temurin-21

# Set environment variables
ENV MAVEN_OPTS="-Xmx1024m"
ENV CHROME_BIN=/usr/bin/google-chrome
ENV DISPLAY=:99

# Install dependencies
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    unzip \
    xvfb \
    && rm -rf /var/lib/apt/lists/*

# Install Google Chrome
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Install Firefox
RUN apt-get update && apt-get install -y firefox-esr && rm -rf /var/lib/apt/lists/*

# Create working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src
COPY testng-suites ./testng-suites

# Download dependencies
RUN mvn dependency:resolve

# Set entrypoint
ENTRYPOINT ["mvn", "test"]

# Default command (can be overridden)
CMD ["-Denv=dev", "-Dbrowser=chrome", "-Dheadless=true"]
