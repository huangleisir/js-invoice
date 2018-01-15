source /etc/profile
NAME=demo.jar
CONFIG_LOCATION=./config/application-test.properties
#JAVA_OPT="-Xms1024m -Xmx1024m -XX:PermSize=256M -XX:MaxPermSize=512m" 
JAVA_OPTS="$JAVA_OPTS -Xms1024m -Xmx1024m -XX:MaxNewSize=256m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -Djava.awt.headless=true -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager"

function start() {
            stop
           nohup java -jar $JAVA_OPT -Dspring.config.location=$CONFIG_LOCATION  "$NAME" >nohup.out 2>&1 & 
}


function stop() {
       pid=`ps -ef |grep  "$NAME" |grep -v "$0"|grep -v "grep"|awk '{print $2}'`
   test "$pid" != '' && echo "server run in $pid" || echo "pid is null"
for i in "$pid"
do
       test "$i" != '' && kill -9 "$i"
done	  
  echo "-----------------------------killed-----------------------------------" 
}

start
tailf nohup.out

