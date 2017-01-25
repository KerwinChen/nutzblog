
javapid=`ps aux|grep jetty:run | grep -v grep | awk '{print $2}' `


if [ -n "$javapid" ] ; then
echo  "java pid:  $javapid ";
echo  "kill -9 $javapid" ;
kill -9 $javapid
fi


git reset --hard HEAD
git pull

mvn clean 
mvn compile
mvn package

nohup mvn jetty:run -Djetty.port=8080 > /dev/null 2>&1 &


tail -F out.log


