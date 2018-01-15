source /etc/profile
NAME=demo.jar

function stop() {
       pid=`ps -ef |grep  "$NAME" |grep -v "$0"|grep -v "grep"|awk '{print $2}'`
   test "$pid" != '' && echo "server run in $pid" || echo "pid is null"
for i in "$pid"
do
       test "$i" != '' && kill -9 "$i"
done	  
  echo "-----------------------------killed-----------------------------------" 
}

stop

