set IP 188.131.243.110
set PATH "/root/project/lvjia"
set NAME "lawfrim-main-0.0.1-SNAPSHOT.jar"
set DOCKER "lvjia"

cd ..
spawn mvn clean package
interact
# shellcheck disable=SC2164
cd sh
spawn scp -r $NAME root@$IP:$PATH
expect "password:"
send "youquer90AVENUE\r"
interact
spawn ssh root@$IP
expect "password:"
send "youquer90AVENUE\r"
expect "*]#"
send "docker restart $DOCKER\r"
send "logout\r"
interact