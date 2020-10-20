set IP 188.131.243.110
set PATH "/root/project/lvjia"
set NAME "lawfrim-0.0.1-SNAPSHOT.jar"
set DOCKER "lvjia"

#spawn mvn clean package
#interact
spawn scp -r $NAME root@$IP:$PATH
expect "password:"
send "youquer90AVENUE\r"
interact
#spawn ssh root@$IP
#expect "password:"
#send "youquer90AVENUE\r"
#expect "*]#"
#send "docker run -d -p 8081:8081 -v $PATH/$NAME:/usr/$NAME --name $DOCKER java:8u111 nohup java -Djava.security.egd=file:/dev/./urandom -jar /usr/$NAME &\r"
#send "logout\r"
#interact