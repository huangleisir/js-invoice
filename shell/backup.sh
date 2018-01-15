source /etc/profile
NAME=demo.jar


function backup() {
if [ ! -d backup ]; then
   mkdir backup
fi

    t=$(date +%Y%m%d%H%M%S)
if [  -f "$NAME" ]; then
     cp  "$NAME" "$NAME.$t"
     mv "$NAME.$t" backup
     ls -lt backup | awk '{if(NR>=11){print "backup/"$9}}' | xargs rm -f
fi



  echo "-----------------------------bak---------------------------------------" 
}

backup

