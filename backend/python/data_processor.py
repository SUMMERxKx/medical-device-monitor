import json
from datetime import datetime
from flask import Flask, request, jsonify
import logging

app = Flask(__name__)
logging.basicConfig(level=logging.INFO)

class DataProcessor:
    def __init__(self):
        self.data_store = {}

    def process_data(self, data):
        device_id = data['deviceId']
        metric_name = data['metricName']
        value = data['value']
        timestamp = datetime.now().isoformat()

        if device_id not in self.data_store:
            self.data_store[device_id] = {}
        
        if metric_name not in self.data_store[device_id]:
            self.data_store[device_id][metric_name] = []
        
        self.data_store[device_id][metric_name].append({
            'value': value,
            'timestamp': timestamp
        })

        logging.info(f"Received data: {data}")
        return self.analyze_data(device_id, metric_name)

    def analyze_data(self, device_id, metric_name):
        data = self.data_store[device_id][metric_name]
        if len(data) > 10:
            recent_values = [item['value'] for item in data[-10:]]
            avg = sum(recent_values) / len(recent_values)
            result = f"Alert: {device_id} - {metric_name} average over last 10 readings: {avg:.2f}"
            logging.info(result)
            return result
        return "Not enough data for analysis"

processor = DataProcessor()

@app.route('/process_data', methods=['POST'])
def process_data():
    data = request.json
    result = processor.process_data(data)
    return jsonify({"result": result})

if __name__ == "__main__":
    app.run(port=5000)