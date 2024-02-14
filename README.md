This service compares Thresholds and Zabbix datas that had inserted into elasticsearch database.

if the thresholds are being touched an alarm will be raised and push into the RabbitMQ queue that RMM module consumes this queue.

Build Dockerfile with this command
```
sudo docker build -t agent .

```


