# Medical Device Monitoring System

## Overview

This project is a comprehensive medical device monitoring system. It simulates medical devices, collects and processes data, and visualizes it in real-time on a web dashboard. The system consists of multiple components working together to provide a complete end-to-end solution for medical device monitoring.

## System Components

1. **MedicalDeviceSimulator (Java)**: Simulates medical devices and generates mock data.
2. **MedicalDeviceMonitor (Java)**: Receives and processes data from the simulator.
3. **Data Analysis Server (Python)**: Analyzes the data and generates alerts.
4. **Dashboard (React)**: Visualizes the data and alerts in real-time.

## Features

- Simulation of multiple medical devices (ECG, Blood Pressure, SPO2)
- Real-time data collection and processing
- Data analysis and alert generation
- Dynamic visualization of device metrics and alerts

## Technologies Used

- Java (JDK 11 or later)
- Python 3.7 or later
- Flask (Python web framework)
- React.js
- Tailwind CSS
- Recharts for data visualization
- Lucide React for icons

## Prerequisites

- Java Development Kit (JDK) 11 or later
- Python 3.7 or later
- Node.js (v14.0.0 or later)
- npm (v6.0.0 or later)

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/SUMMERxKx/medical-device-monitor.git
   ```

2. Navigate to the project directory:
   ```
   cd medical-device-monitoring-system
   ```

3. Install Python dependencies:
   ```
   pip install flask
   ```

4. Install Node.js dependencies:
   ```
   npm install
   ```

## Running the Application

Start each component in the following order:

1. Start the Python Flask server:
   ```
   python backend/python/data_processor.py
   ```

2. Start the Java MedicalDeviceMonitor:
   ```
   java -cp backend/java MedicalDeviceMonitor
   ```

3. Start the MedicalDeviceSimulator:
   ```
   java -cp backend/java MedicalDeviceSimulator
   ```

4. Start the React dashboard:
   ```
   npm run dev
   ```

Open [http://localhost:5173](http://localhost:5173) to view the dashboard in the browser.

## System Flow

1. **MedicalDeviceSimulator (Java)**
   - Simulates three medical devices: ECG, Blood Pressure monitor, and SPO2 (oxygen saturation) monitor.
   - Sends data every second to the Java backend.
   - Data includes device ID, metric name, and a randomly generated value within a realistic range.
   - Uses Java's `ScheduledExecutorService` to send data at regular intervals.
   - Connects to the Java backend using a Socket connection on localhost:8080.

2. **MedicalDeviceMonitor (Java)**
   - Listens for incoming connections from the simulator on port 8080.
   - Receives and processes the data from the simulator.
   - Forwards the processed data to the Python server for analysis.

3. **Data Analysis Server (Python)**
   - Receives data from the Java backend.
   - Analyzes the data and generates alerts based on predefined criteria.
   - Stores processed data and makes it available for the dashboard.

4. **Dashboard (React)**
   - Fetches processed data and alerts from the Python server.
   - Displays real-time visualizations of device metrics.
   - Shows alerts and system status.
