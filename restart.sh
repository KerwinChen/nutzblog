

killall java
git reset --hard HEAD
git pull


sudo nohup mvn jetty:run -Djetty.port=80 > /dev/null 2>&1 &


tail -f out.log




