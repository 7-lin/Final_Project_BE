#!/bin/bash
#was2.sh

echo "----------------------------------" >> /home/ec2-user/final7_logs/was2.log

WAS2_PID=$(ps -ef | grep java | grep 8082 | awk '{print $2}')

echo "[CI/CD] Current WAS2 PID: ${WAS2_PID}" >> /home/ec2-user/final7_logs/was2.log

echo "[CI/CD] WAS2 upgrade start" >> /home/ec2-user/final7_logs/was2.log
if [ ! -z ${WAS2_PID} ]; then
    kill -9 ${WAS2_PID}
    nohup java -jar -Dserver.port=8082 /home/ec2-user/final7/build/libs/*.jar >> /home/ec2-user/final7_logs/was2.log &
else
    nohup java -jar -Dserver.port=8082 /home/ec2-user/final7/build/libs/*.jar >> /home/ec2-user/final7_logs/was2.log &
fi
echo "[CI/CD] WAS2 upgrade finish" >> /home/ec2-user/final7_logs/was2.log

NEW_WAS2_PID=$(ps -ef | grep java | grep 8082 | awk '{print $2}')

echo "[CI/CD] New WAS2 PID: ${NEW_WAS2_PID}" >> /home/ec2-user/final7_logs/was2.log

echo "----------------------------------" >> /home/ec2-user/final7_logs/was2.log

