#!/bin/bash

# Start Python Flask server
echo "Starting Python Flask server..."
python3 backend/python/data_processor.py &

# Compile and run Java MedicalDeviceMonitor
echo "Compiling and running Java MedicalDeviceMonitor..."
javac -d . backend/java/*.java
java backend.java.MedicalDeviceMonitor &

# Compile and run Java MedicalDeviceSimulator
echo "Compiling and running Java MedicalDeviceSimulator..."
java backend.java.MedicalDeviceSimulator &

# Start React development server
echo "Starting React development server..."
cd frontend
npm install
npm run dev