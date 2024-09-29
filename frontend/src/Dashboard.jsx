import React, { useState, useEffect } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import { AlertTriangle, Activity, Heart, Droplet, Zap } from 'lucide-react';
import './Dashboard.css';

const Dashboard = () => {
  const [deviceData, setDeviceData] = useState({});
  const [alerts, setAlerts] = useState([]);

  useEffect(() => {
    const fetchData = () => {
      // Simulating data fetch. Replace this with your actual API call
      const newData = simulateDataFetch();
      setDeviceData(prevData => ({
        ...prevData,
        ...newData
      }));

      // Simulating alerts. Replace with your actual alert logic
      if (Math.random() > 0.8) {
        setAlerts(prevAlerts => [...prevAlerts, {
          id: Date.now(),
          message: `Alert: Abnormal reading detected for ${Object.keys(newData)[0]}`,
          timestamp: new Date().toLocaleTimeString()
        }]);
      }
    };

    const interval = setInterval(fetchData, 1000);
    return () => clearInterval(interval);
  }, []);

  const simulateDataFetch = () => {
    const devices = ['ECG_001', 'BP_002', 'SPO2_003'];
    const metrics = ['heart_rate', 'blood_pressure', 'oxygen_saturation'];
    const device = devices[Math.floor(Math.random() * devices.length)];
    const metric = metrics[Math.floor(Math.random() * metrics.length)];
    const value = parseFloat((Math.random() * 100 + 50).toFixed(2));
    
    return {
      [device]: {
        ...deviceData[device],
        [metric]: [...(deviceData[device]?.[metric] || []), { time: new Date().toLocaleTimeString(), value }].slice(-10)
      }
    };
  };

  const renderDeviceCard = (deviceId) => {
    const deviceInfo = deviceData[deviceId];
    if (!deviceInfo) return null;

    return (
      <div key={deviceId} className="bg-white rounded-lg shadow-md p-4 mb-4">
        <h3 className="text-xl font-bold mb-2 text-red-600">{deviceId}</h3>
        {Object.entries(deviceInfo).map(([metric, data]) => (
          <div key={metric} className="mb-4">
            <h4 className="text-lg font-semibold mb-2">{metric.replace('_', ' ').toUpperCase()}</h4>
            <ResponsiveContainer width="100%" height={200}>
              <LineChart data={data}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="time" />
                <YAxis />
                <Tooltip />
                <Line type="monotone" dataKey="value" stroke="#e53e3e" strokeWidth={2} dot={false} />
              </LineChart>
            </ResponsiveContainer>
            <p className="mt-2">Latest: {data[data.length - 1]?.value.toFixed(2)}</p>
          </div>
        ))}
      </div>
    );
  };

  return (
    <div className="bg-gray-100 min-h-screen p-8">
      <h1 className="text-4xl font-bold mb-8 text-red-600">Medical Device Monitoring</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {Object.keys(deviceData).map(renderDeviceCard)}
      </div>

      <div className="mt-8">
        <h2 className="text-2xl font-bold mb-4 text-red-600">Alerts</h2>
        {alerts.length > 0 ? (
          <ul className="bg-white rounded-lg shadow-md p-4">
            {alerts.map(alert => (
              <li key={alert.id} className="flex items-center mb-2">
                <AlertTriangle className="text-yellow-500 mr-2" />
                <span>{alert.message} - {alert.timestamp}</span>
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-600">No active alerts</p>
        )}
      </div>

      <div className="mt-8">
        <h2 className="text-2xl font-bold mb-4 text-red-600">System Status</h2>
        <div className="bg-white rounded-lg shadow-md p-4">
          <p className="flex items-center mb-2">
            <Activity className="text-green-500 mr-2" /> Java Backend: Active
          </p>
          <p className="flex items-center mb-2">
            <Zap className="text-green-500 mr-2" /> Python Analyzer: Active
          </p>
          <p className="flex items-center">
            <Heart className="text-green-500 mr-2" /> Device Simulator: Running
          </p>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;