Select gftest --> Run GfTest clean --> Run GfTest verify.

All in ejb and war will be cleaned, compiled and than all tests will be executed.

If queue/topic needs to be created go to /ejb/src/test/java/com/home/gftest/jms/CommonJmsUtility.java
and run it as 'Java Application'. It will create the needed queues and topics.

The ear deployment on Payara Server will fail, but for tests this is ok.

The ear deployment on Payara Server will be successful if the user/password is correct in file password.properties in folder Payara_Server\payara5\glassfish\domains.

For results look in the logs folder:
- GfTest-All.log
- GfTest-Part.log
