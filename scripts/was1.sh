#!/bin/bash
#was1.sh

echo "----------------------------------" >> /home/ec2-user/final7_logs/was1.log

WAS1_PID=$(ps -ef | grep java | grep finance7* | grep 8081 | awk '{print $2}')

echo "[CI/CD] Current WAS1 PID: {$WAS1_PID}" >> /home/ec2-user/final7_logs/was1.log

echo "[CI/CD] WAS1 upgrade start" >> /home/ec2-user/final7_logs/was1.log
if [ ! -z ${WAS1_PID} ]; then
    kill -9 $WAS1_PID
    nohup java -jar -Dserver.port=8081 /home/ec2-user/final7/build/libs/*.jar >> /home/ec2-user/final7_logs/was1.log &
else
    nohup java -jar -Dserver.port=8081 /home/ec2-user/final7/build/libs/*.jar >> /home/ec2-user/final7_logs/was1.log &
fi
echo "[CI/CD] WAS1 upgrade finish" >> /home/ec2-user/final7_logs/was1.log

NEW_WAS1_PID=$(ps -ef | grep java | grep finance7* | grep 8081 | awk '{print $2}')

echo "[CI/CD] New WAS1 PID: {$NEW_WAS1_PID}" >> /home/ec2-user/final7_logs/was1.log

echo "----------------------------------" >> /home/ec2-user/final7_logs/was1.log

# For WAS1 안정화 후 WAS2 업그레이드
sleep 20
