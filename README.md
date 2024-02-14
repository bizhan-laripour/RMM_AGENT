Build Dockerfile with this command
```
sudo docker build -t agent .

```

This module gets data of servers from zabbix server and inserts all these datas into elasticsearch database in a 
Scheduled period of time.
These datas are used in Agent project for comparing Thresholds and these datas
