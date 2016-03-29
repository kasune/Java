#!/bin/bash

javac -cp ../lib/activation.jar:../lib/dsn.jar:../lib/imap.jar:../lib/mailapi.jar:../lib/mail.jar:../lib/pop3.jar:../lib/smtp.jar:. SimpleMail.java

java -cp ../lib/activation.jar:../lib/dsn.jar:../lib/imap.jar:../lib/mailapi.jar:../lib/mail.jar:../lib/pop3.jar:../lib/smtp.jar: SimpleMail
