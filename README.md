# Medical Device Monitoring Dashboard

## Overview

This project is a real-time medical device monitoring dashboard. It provides a visual interface for tracking various medical devices and their metrics, displaying alerts, and showing system status.

## Features

- Real-time data visualization for multiple medical devices
- Dynamic charts for various metrics (e.g., heart rate, blood pressure, oxygen saturation)
- Alert system for abnormal readings
- System status display for backend components


## Technologies Used

- React.js
- Tailwind CSS
- Recharts for data visualization
- Lucide React for icons

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Node.js (v14.0.0 or later)
- npm (v6.0.0 or later)

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/SUMMERxKx/medical-device-monitor.git
   ```

2. Navigate to the project directory:
   ```
   cd medical-device-dashboard
   ```

3. Install the dependencies:
   ```
   npm install
   ```

## Running the Application

To run the application in development mode:

```
npm run dev
```

Open [http://localhost:5173](http://localhost:5173) to view it in the browser.

## Project Structure

```
src/
├── index.css       # Global styles and Tailwind directives
├── main.jsx        # Entry point of the application
├── App.jsx         # Main App component
├── Dashboard.jsx   # Dashboard component
└── Dashboard.css   # Dashboard-specific styles
```

## Customization

- To modify the theme or add custom styles, edit the `index.css` and `Dashboard.css` files.
- To add or modify device metrics, update the `simulateDataFetch` function in `Dashboard.jsx`.
- To change the layout or add new features, modify the `Dashboard.jsx` component.
