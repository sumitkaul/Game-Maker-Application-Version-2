#!/bin/bash

PIDFILE="/u/kspace/www/fluency/student-files/fall2012/a9/team1/gameserverpid"
LOGFILE="/u/kspace/www/fluency/student-files/fall2012/a9/team1/gameServer.log"
JARFILE="./team1.jar"
MAINCLASS="gameServer.GameMakerServer"
if [ -a $PIDFILE ] 
then
  PID=`cat $PIDFILE`
  kill -9 $PID
fi

java -cp $JARFILE $MAINCLASS > $LOGFILE 2>&1 &
pid=$!
echo $pid > $PIDFILE
